package com.droplet.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.droplet.constants.SystemConstants;
import com.droplet.domain.ResponseResult;
import com.droplet.domain.entity.Link;
import com.droplet.domain.vo.LinkVo;
import com.droplet.mapper.LinkMapper;
import com.droplet.service.LinkService;
import com.droplet.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    /**
     * 获取所有友链
     *
     * @return 友链
     */
    @Override
    public ResponseResult getAllLink() {
        // 查询友链
        LambdaQueryWrapper<Link> linkLambdaQueryWrapper = new LambdaQueryWrapper<>();
        linkLambdaQueryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> linkList = list(linkLambdaQueryWrapper);
        // 转换Vo
        List<LinkVo> linkVoList = BeanCopyUtils.copyBeanList(linkList, LinkVo.class);
        // 封装返回
        return ResponseResult.okResult(linkVoList);
    }
}
