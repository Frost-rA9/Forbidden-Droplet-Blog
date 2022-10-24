package com.droplet.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.droplet.constants.SystemConstants;
import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.Article;
import com.droplet.domain.entity.Category;
import com.droplet.domain.vo.CategoryVo;
import com.droplet.mapper.CategoryMapper;
import com.droplet.service.ArticleService;
import com.droplet.service.CategoryService;
import com.droplet.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    public ArticleService articleService;

    @Autowired
    public CategoryServiceImpl(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    public ResponseResult getCategoryList() {
        // 获取文章
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleLambdaQueryWrapper);

        // 获取文章分类id，使用Set去重
        Set<Long> categoryIdSet = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());

        // 查询分类表
        List<Category> categoryList = listByIds(categoryIdSet);
        categoryList = categoryList.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

        // 封装为Vo
        List<CategoryVo> categoryVoList = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        return ResponseResult.okResult(categoryVoList);
    }
}
