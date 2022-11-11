package com.droplet.job;

import com.droplet.constants.SystemConstants;
import com.droplet.domain.entity.Article;
import com.droplet.redis.RedisCache;
import com.droplet.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {

    private final RedisCache redisCache;

    private final ArticleService articleService;

    @Autowired
    public UpdateViewCountJob(RedisCache redisCache, ArticleService articleService) {
        this.redisCache = redisCache;
        this.articleService = articleService;
    }

    @Scheduled(cron = "* 0/10 * * * ?")
    public void updateViewCount() {
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.VIEW_COUNT_CACHE_ID);
        List<Article> articleList = viewCountMap.entrySet().stream().map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue())).collect(Collectors.toList());
        articleService.updateBatchById(articleList);
    }
}
