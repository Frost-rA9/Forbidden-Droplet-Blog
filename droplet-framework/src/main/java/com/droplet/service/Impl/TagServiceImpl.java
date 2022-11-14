package com.droplet.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.droplet.domain.entity.Tag;
import com.droplet.mapper.TagMapper;
import com.droplet.service.TagService;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
}
