package com.droplet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.droplet.domain.entity.Link;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LinkMapper extends BaseMapper<Link> {
}
