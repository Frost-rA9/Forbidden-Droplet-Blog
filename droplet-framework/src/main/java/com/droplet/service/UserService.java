package com.droplet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.User;

public interface UserService extends IService<User> {
    ResponseResult userinfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}
