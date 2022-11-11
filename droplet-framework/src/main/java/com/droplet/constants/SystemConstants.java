package com.droplet.constants;

public class SystemConstants {

    /**
     * 草稿文章
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     * 正常发布文章
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    public static final String STATUS_NORMAL = "0";

    /**
     * 友链审核通过
     */
    public static final String LINK_STATUS_NORMAL = "0";

    /**
     * 博客登录缓存ID
     */
    public static final String BLOG_LOGIN_CACHE_ID = "blogLoginId:";

    /**
     * 根评论
     */
    public static final int BLOG_COMMENT_ROOT = -1;

    /**
     * 文章评论
     */
    public static final String ARTICLE_COMMENT = "0";

    /**
     * 友链评论
     */
    public static final String LINK_COMMENT = "1";

    /**
     * 浏览量缓存KEY
     */
    public static final String VIEW_COUNT_CACHE_ID = "article:viewCount";
}
