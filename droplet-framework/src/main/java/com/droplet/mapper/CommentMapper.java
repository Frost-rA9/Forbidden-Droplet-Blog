package com.droplet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.droplet.domain.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
