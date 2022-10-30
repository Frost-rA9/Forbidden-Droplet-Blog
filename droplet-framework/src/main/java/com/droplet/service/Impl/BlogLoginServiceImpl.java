package com.droplet.service.Impl;

import com.droplet.constants.SystemConstants;
import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.LoginUser;
import com.droplet.domain.entity.User;
import com.droplet.domain.vo.BlogUserLoginVo;
import com.droplet.domain.vo.UserInfoVo;
import com.droplet.redis.RedisCache;
import com.droplet.service.BlogLoginService;
import com.droplet.utils.BeanCopyUtils;
import com.droplet.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    private final AuthenticationManager authenticationManager;

    private final RedisCache redisCache;

    @Autowired
    public BlogLoginServiceImpl(AuthenticationManager authenticationManager, RedisCache redisCache) {
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
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        // 判断是否认证通过
        if (Objects.isNull(authentication)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        // 获取userid，生成token
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtil.createJWT(userId);
        // 将用户信息存入redis
        redisCache.setCacheObject(SystemConstants.BLOG_LOGIN_CACHE_ID + userId, loginUser);
        // 将token以及userinfo封装 返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(token, userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }
}
