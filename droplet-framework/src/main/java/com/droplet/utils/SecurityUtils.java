package com.droplet.utils;

import com.droplet.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    /**
     * 获取Authentication
     *
     * @return Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取登录用户
     *
     * @return 登录用户
     */
    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getAuthorities();
    }

    /**
     * 获取登录用户Id
     *
     * @return 登录用户Id
     */
    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }

    /**
     * 判断是否为管理员
     *
     * @return 结果
     */
    public static Boolean isAdmin() {
        return getUserId() != null && 1L == getUserId();
    }
}
