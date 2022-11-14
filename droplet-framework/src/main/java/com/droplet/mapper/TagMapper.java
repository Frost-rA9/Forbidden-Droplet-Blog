package com.droplet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.droplet.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
}
