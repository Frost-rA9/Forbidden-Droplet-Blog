package com.droplet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.Category;

public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
