package com.droplet.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.Article;
import com.droplet.mapper.ArticleMapper;
import com.droplet.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Override
    public ResponseResult getHotArticleList() {
        LambdaQueryWrapper<Article> lambdaQueryWrap = new LambdaQueryWrapper<>();
        lambdaQueryWrap.eq(Article::getStatus, 0);
        lambdaQueryWrap.orderByDesc(Article::getViewCount);
        Page<Article> page = new Page<>(1, 10);
        page(page, lambdaQueryWrap);
        List<Article> articleList = page.getRecords();
        return ResponseResult.okResult(articleList);
    }
}
