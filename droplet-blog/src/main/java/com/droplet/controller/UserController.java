package com.droplet.controller;

import com.droplet.domain.ResponseResult;
import com.droplet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @GetMapping("/userInfo")
    public ResponseResult userInfo() {
        return userService.userinfo();
    }
}
