package com.droplet.controller;

import com.droplet.domain.ResponseResult;
import com.droplet.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 查询热门文章
     *
     * @return 热门文章
     */
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() {
        ResponseResult responseResult = articleService.getHotArticleList();
    }
}
