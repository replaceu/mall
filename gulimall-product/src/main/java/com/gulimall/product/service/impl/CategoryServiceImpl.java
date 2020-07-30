package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.product.dto.CategoryDto;
import com.gulimall.product.convert.CategoryConvert;
import com.gulimall.product.dao.CategoryDao;
import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.service.CategoryService;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }
    @Override
    public List<CategoryDto> listCategoryWithTree() {
        // 查询所有分类
        List<CategoryEntity> allCategory = this.baseMapper.selectList(null);
        // 组装成父子结构
        List<CategoryDto> categoryTree = findChildrenCategory(allCategory, 0L);

        return categoryTree;
    }

    @Override
    public void removeMenuByIds(List<Long> catIds) {
//        TODO  检查当前 删除菜单 是否 被其他地方 引用

//        逻辑删除
        baseMapper.deleteBatchIds(catIds) ;
    }

    private List<CategoryDto> findChildrenCategory(List<CategoryEntity> allCateGory, Long  parentCategoryId) {
        // 找 2 级
        return allCateGory.stream()
                .filter(category-> category.getParentCid() == parentCategoryId )
                .map(category ->{
                    // TODO 已处理的数据 进行删除 下一次就不会重新处理
//                    allCateGory.remove(category) ;
                    // 属性对拷
                    CategoryDto categoryDto = CategoryConvert.INSTANCE.entity2dto(category);

                    categoryDto.setChildren(findChildrenCategory(allCateGory , category.getCatId()  ));  // 找  3 级
                    return categoryDto ;
                })
                .sorted(Comparator.comparingInt(CategoryDto::getSort))
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> findCategoryPath(Long categoryId) {
        List<Long> categoryPath = new ArrayList<>(4);

        findCategoryParentId(categoryId, categoryPath);

        Collections.reverse(categoryPath);
        return categoryPath;
    }

    private void findCategoryParentId(Long categoryChildId, List<Long> path) {
        path.add(categoryChildId) ;
        CategoryEntity categoryEntity = baseMapper.selectById(categoryChildId);
        if (categoryEntity != null && categoryEntity.getParentCid() != 0L ){
            findCategoryParentId(categoryEntity.getParentCid() , path);
        }
    }

}