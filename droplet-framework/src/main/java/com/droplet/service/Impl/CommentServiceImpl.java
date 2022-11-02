package com.droplet.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.droplet.constants.SystemConstants;
import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.Comment;
import com.droplet.domain.vo.CommentVo;
import com.droplet.domain.vo.PageVo;
import com.droplet.mapper.CommentMapper;
import com.droplet.service.CommentService;
import com.droplet.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    /**
     * 评论列表
     *
     * @param articleId 文章Id
     * @param pageNum   页码
     * @param pageSize  页面大小
     * @return 结果
     */
    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentLambdaQueryWrapper.eq(Comment::getArticleId, articleId);
        commentLambdaQueryWrapper.eq(Comment::getRootId, SystemConstants.BLOG_COMMENT_ROOT);

        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, commentLambdaQueryWrapper);

        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(page.getRecords(), CommentVo.class);
        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }
}
