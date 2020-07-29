package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.dao.BrandDao;
import com.gulimall.product.entity.BrandEntity;
import com.gulimall.product.service.BrandService;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.QueryPage;
import org.springframework.stereotype.Service;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {


//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<BrandEntity> page = this.page(
//                new Query<BrandEntity>().getPage(params),
//                new QueryWrapper<BrandEntity>()
//        );
//
//        return new PageUtils(page);
//    }


    @Override
    public PageUtils queryPage(PageVo pageParams) {
        IPage<BrandEntity> page = this.page(new QueryPage<BrandEntity>().getPage(pageParams), null);
        return new PageUtils(page);
    }
}