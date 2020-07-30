package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.dao.AttrGroupDao;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.service.AttrGroupService;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.Query;
import com.gulimall.service.utils.QueryPage;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(PageVo pageParam, Long categoryId) {
        LambdaQueryWrapper<AttrGroupEntity> wrapper = new LambdaQueryWrapper<>();

        if (categoryId != 0L) {
            wrapper.eq(AttrGroupEntity::getCategoryId, categoryId);
        }
        if (pageParam != null && !StringUtils.isEmpty(pageParam.getKey())) {
            String key = pageParam.getKey();
            wrapper.and((e) -> {
                e.eq(AttrGroupEntity::getAttrGroupId, key).or().likeRight(AttrGroupEntity::getAttrGroupName, key);
            });
        }
        IPage<AttrGroupEntity> page = this.page(new QueryPage<AttrGroupEntity>().getPage(pageParam), wrapper);
        return new PageUtils(page);
    }



}