package com.droplet.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.droplet.constants.SystemConstants;
import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.Article;
import com.droplet.domain.vo.ArticleListVo;
import com.droplet.domain.vo.HotArticleVo;
import com.droplet.domain.vo.PageVo;
import com.droplet.mapper.ArticleMapper;
import com.droplet.service.ArticleService;
import com.droplet.service.CategoryService;
import com.droplet.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private final CategoryService categoryService;

    @Autowired
    public ArticleServiceImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 查询访问量前十的热门文章
     *
     * @return 热门文章集合
     */
    @Override
    public ResponseResult getHotArticleList() {
        // 查询
        LambdaQueryWrapper<Article> lambdaQueryWrap = new LambdaQueryWrapper<>();
        lambdaQueryWrap.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        lambdaQueryWrap.orderByDesc(Article::getViewCount);
        // 分页设置
        Page<Article> page = new Page<>(1, 10);
        page(page, lambdaQueryWrap);

        List<Article> articleList = page.getRecords();
        // Bean Copy
        /* List<HotArticleVo> hotArticleVoList = new ArrayList<>();
        for (Article article : articleList) {
            HotArticleVo articleVo = new HotArticleVo();
            BeanUtils.copyProperties(article, articleVo);
            hotArticleVoList.add(articleVo);
        } */
        List<HotArticleVo> hotArticleVoList = BeanCopyUtils.copyBeanList(articleList, HotArticleVo.class);
        return ResponseResult.okResult(hotArticleVoList);
    }

    /**
     * 查询文章列表
     *
     * @param pageNum    页码
     * @param pageSize   页面大小
     * @param categoryId 分类id
     * @return 文章列表
     */
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 组装查询条件
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();

        articleLambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        articleLambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        articleLambdaQueryWrapper.orderByDesc(Article::getIsTop);

        // 分页设置
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, articleLambdaQueryWrapper);
        // 查询分类名
        List<Article> articleList = page.getRecords();
        articleList = articleList.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        // 封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
