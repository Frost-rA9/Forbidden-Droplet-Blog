package com.droplet.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.droplet.domain.entity.Article;
import com.droplet.mapper.ArticleMapper;
import com.droplet.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}
