package com.droplet.service.Impl;

import com.droplet.constants.SystemConstants;
import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.LoginUser;
import com.droplet.domain.entity.User;
import com.droplet.redis.RedisCache;
import com.droplet.service.AdminLoginService;
import com.droplet.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    private final AuthenticationManager authenticationManager;

    private final RedisCache redisCache;

    @Autowired
    public AdminLoginServiceImpl(AuthenticationManager authenticationManager, RedisCache redisCache) {
        this.authenticationManager = authenticationManager;
        this.redisCache = redisCache;
    }

    /**
     * 登录
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        // 判断是否认证通过
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 获取userid，生成token
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtil.createJWT(userId);
        // 将用户信息存入redis
        redisCache.setCacheObject(SystemConstants.ADMIN_LOGIN_CACHE_ID + userId, loginUser, 1, TimeUnit.HOURS);
        // 将token封装 返回
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        return ResponseResult.okResult(tokenMap);
    }
}
