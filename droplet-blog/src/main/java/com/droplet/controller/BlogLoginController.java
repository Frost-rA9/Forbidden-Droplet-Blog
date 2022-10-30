package com.droplet.controller;

import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.User;
import com.droplet.enums.AppHttpCodeEnum;
import com.droplet.exception.SystemException;
import com.droplet.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {

    private final BlogLoginService blogLoginService;

    @Autowired
    public BlogLoginController(BlogLoginService blogLoginService) {
        this.blogLoginService = blogLoginService;
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }
}
