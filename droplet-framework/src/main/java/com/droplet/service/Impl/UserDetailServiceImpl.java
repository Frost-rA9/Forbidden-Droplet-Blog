package com.droplet.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.droplet.domain.entity.LoginUser;
import com.droplet.domain.entity.User;
import com.droplet.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    @Autowired
    public UserDetailServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        LambdaQueryWrapper<User> userLambdaQueryWrap = new LambdaQueryWrapper<>();
        userLambdaQueryWrap.eq(User::getUserName, username);
        User user = userMapper.selectOne(userLambdaQueryWrap);
        // 判断用户是否存在 若不存在抛出异常
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 返回用户信息
        // TODO 查询权限信息并封装
        return new LoginUser(user);
    }
}
