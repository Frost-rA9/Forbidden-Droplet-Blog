package com.droplet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult getHotArticleList();
}
