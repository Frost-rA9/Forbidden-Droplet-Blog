package com.droplet.service;

import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);
}
