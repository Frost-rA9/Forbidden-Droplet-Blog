package com.droplet.runner;

import com.droplet.constants.SystemConstants;
import com.droplet.domain.entity.Article;
import com.droplet.mapper.ArticleMapper;
import com.droplet.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {

    private final ArticleMapper articleMapper;

    private final RedisCache redisCache;

    @Autowired
    public ViewCountRunner(ArticleMapper articleMapper, RedisCache redisCache) {
        this.articleMapper = articleMapper;
        this.redisCache = redisCache;
    }


    @Override
    public void run(String... args) throws Exception {
        List<Article> articleList = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articleList.stream().collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        redisCache.setCacheMap(SystemConstants.VIEW_COUNT_CACHE_ID, viewCountMap);
    }
}
