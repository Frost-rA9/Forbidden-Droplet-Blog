package com.droplet.controller;

import com.droplet.annotation.SystemLog;
import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.User;
import com.droplet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     *
     * @return 用户信息
     */
    @GetMapping("/userInfo")
    public ResponseResult userInfo() {
        return userService.userinfo();
    }

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 结果
     */
    @PostMapping("/register")
    @SystemLog(businessName = "用户注册")
    public ResponseResult register(@RequestBody User user) {
        return userService.register(user);
    }
}
