package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.convert.AttrConvert;
import com.gulimall.product.dao.AttrGroupDao;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.service.AttrGroupService;
import com.gulimall.product.service.CategoryService;
import com.gulimall.product.vo.AttrGroupVo;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.QueryPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    CategoryService categoryService ;

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

    @Override
    public PageUtils queryAttrRelationPage(PageVo pageParams, Long attrGroupId) {
//        new LambdaQueryWrapper<>()
//
//        baseMapper.selectPage() ;

        return null ;
    }

    @Override
    public AttrGroupVo getAttrGroupInfo(Long attrGroupId) {
        AttrGroupEntity attrGroup = baseMapper.selectById(attrGroupId);
        List<Long> categoryPath = categoryService.findCategoryPath(attrGroup.getCategoryId());
        AttrGroupVo attrGroupVo = AttrConvert.INSTANCE.entity2vo(attrGroup);
        attrGroupVo.setCategoryPath(categoryPath);
        return attrGroupVo ;
    }

    @Override
    public List<AttrGroupEntity> getAllGroupByCategoryId(Long categoryId ) {
       return baseMapper.selectList(
                new LambdaQueryWrapper<AttrGroupEntity>()
                        .eq(AttrGroupEntity::getCategoryId , categoryId)) ;
    }
}