package com.droplet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.Link;

public interface LinkService extends IService<Link> {
    ResponseResult getAllLink();
}
