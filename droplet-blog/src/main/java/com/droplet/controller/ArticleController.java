package com.droplet.controller;

import com.droplet.domain.ResponseResult;
import com.droplet.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return articleService.getHotArticleList();
    }

    /**
     * 查询文章列表
     *
     * @param pageNum    页码
     * @param pageSize   页面大小
     * @param categoryId 分类id
     * @return 文章列表
     */
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    /**
     * 查询文章详情
     * @param id 文章id
     * @return 文章详情
     */
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }
}
