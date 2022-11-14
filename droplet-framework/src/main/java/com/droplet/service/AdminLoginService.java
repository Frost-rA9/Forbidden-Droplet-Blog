package com.droplet.service;

import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.User;

public interface AdminLoginService {
    ResponseResult login(User user);
}
