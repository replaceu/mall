package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.product.dto.CategoryDto;
import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.service.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
public interface CategoryService extends IService<CategoryEntity> {
    PageUtils queryPage(Map<String, Object> params);
    /**
     * 查询所有的分类， 并组装成父子结构
     * @return
     */
    List<CategoryDto> listCategoryWithTree();

    /**
     * 通过菜单id 批量删除 菜单
     * @param catIds
     */
    void removeMenuByIds(List<Long> catIds);
}

