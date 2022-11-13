package com.droplet.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.droplet.constants.SystemConstants;
import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.Comment;
import com.droplet.domain.vo.CommentVo;
import com.droplet.domain.vo.PageVo;
import com.droplet.enums.AppHttpCodeEnum;
import com.droplet.exception.SystemException;
import com.droplet.mapper.CommentMapper;
import com.droplet.mapper.UserMapper;
import com.droplet.service.CommentService;
import com.droplet.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private final UserMapper userMapper;

    @Autowired
    public CommentServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 评论列表
     *
     * @param commentType 评论类型
     * @param articleId   文章Id
     * @param pageNum     页码
     * @param pageSize    页面大小
     * @return 结果
     */
    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        // 查询根评论
        LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentLambdaQueryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId);
        commentLambdaQueryWrapper.eq(Comment::getRootId, SystemConstants.BLOG_COMMENT_ROOT);
        commentLambdaQueryWrapper.eq(Comment::getType, commentType);
        // 分页
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, commentLambdaQueryWrapper);
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        // 设置子评论
        commentVoList.forEach(commentVo -> commentVo.setChildren(getChildren(commentVo.getId())));
        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    /**
     * 新增评论
     *
     * @param comment 评论
     * @return 结果
     */
    @Override
    public ResponseResult addComment(Comment comment) {
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 评论结果转换（设置昵称）
     *
     * @param commentList 评论列表
     * @return 结果
     */
    private List<CommentVo> toCommentVoList(List<Comment> commentList) {
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);
        commentVoList = commentVoList.stream()
                .peek(commentVo -> {
                    commentVo.setUsername(userMapper.selectById(commentVo.getCreateBy()).getUserName());
                    if (commentVo.getToCommentUserId() != -1) {
                        commentVo.setToCommentUserNmae(userMapper.selectById(commentVo.getToCommentUserId()).getNickName());
                    }
                })
                .collect(Collectors.toList());
        return commentVoList;
    }

    /**
     * 查询子评论
     *
     * @param rootId 根评论Id
     * @return 结果
     */
    private List<CommentVo> getChildren(Long rootId) {
        LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentLambdaQueryWrapper.eq(Comment::getRootId, rootId);
        commentLambdaQueryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> commentList = list(commentLambdaQueryWrapper);
        return toCommentVoList(commentList);
    }
}
