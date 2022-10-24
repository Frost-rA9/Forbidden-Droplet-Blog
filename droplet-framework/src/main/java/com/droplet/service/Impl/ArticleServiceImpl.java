package com.droplet.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.Article;
import com.droplet.domain.vo.HotArticleVo;
import com.droplet.mapper.ArticleMapper;
import com.droplet.service.ArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    /**
     * 查询访问量前十的热门文章
     *
     * @return 热门文章集合
     */
    @Override
    public ResponseResult getHotArticleList() {
        // 查询
        LambdaQueryWrapper<Article> lambdaQueryWrap = new LambdaQueryWrapper<>();
        lambdaQueryWrap.eq(Article::getStatus, 0);
        lambdaQueryWrap.orderByDesc(Article::getViewCount);
        // 分页设置
        Page<Article> page = new Page<>(1, 10);
        page(page, lambdaQueryWrap);

        List<Article> articleList = page.getRecords();
        // Bean Copy
        List<HotArticleVo> hotArticleVoList = new ArrayList<>();
        for (Article article : articleList) {
            HotArticleVo articleVo = new HotArticleVo();
            BeanUtils.copyProperties(article, articleVo);
            hotArticleVoList.add(articleVo);
        }
        return ResponseResult.okResult(hotArticleVoList);
    }
}
