package com.droplet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.Comment;

public interface CommentService extends IService<Comment> {
    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);
}
