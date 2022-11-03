package com.droplet.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.User;
import com.droplet.domain.vo.UserInfoVo;
import com.droplet.mapper.UserMapper;
import com.droplet.service.UserService;
import com.droplet.utils.BeanCopyUtils;
import com.droplet.utils.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 查询用户信息
     *
     * @return 用户信息
     */
    @Override
    public ResponseResult userinfo() {
        User user = getById(SecurityUtils.getUserId());
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }
}
