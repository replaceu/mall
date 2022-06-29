package com.gulimall.product.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.product.convert.CategoryConvert;
import com.gulimall.product.dao.CategoryDao;
import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.service.CategoryBrandRelationService;
import com.gulimall.product.service.CategoryService;
import com.gulimall.product.vo.Category2Vo;
import com.gulimall.product.vo.Category3Vo;
import com.gulimall.product.vo.CategoryVo;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.Query;

import lombok.extern.slf4j.Slf4j;

@Service("categoryService")
@Slf4j
@CacheConfig(cacheNames = "product:category")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
	@Autowired
	private CategoryBrandRelationService categoryBrandRelationService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<CategoryEntity> page = this.page(new Query<CategoryEntity>().getPage(params), new QueryWrapper<CategoryEntity>());

		return new PageUtils(page);
	}

	@Override
	@Cacheable(key = "#root.methodName")
	public List<CategoryVo> listCategoryWithTree() {
		//查询所有分类
		List<CategoryEntity> allCategory = this.baseMapper.selectList(null);
		//组装成父子结构
		return null;
	}

	@Override
	@CacheEvict(allEntries = true)
	public void removeMenuByIds(List<Long> catIds) {
		//TODO 检查当前删除菜单是否被其他地方引用
		baseMapper.deleteBatchIds(catIds);
	}

	private List<CategoryVo> findChildrenCategory(List<CategoryEntity> allCateGory, Long parentCategoryId) {
		//找2级
		return allCateGory.stream().filter(category -> category.getParentCid() == parentCategoryId).map(category -> {
			//TODO 已处理的数据进行删除下一次就不会重新处理
			//allCateGory.remove(category);
			//属性对拷
			CategoryVo categoryVo = CategoryConvert.INSTANCE.entity2vo(category);
			categoryVo.setChildren(findChildrenCategory(allCateGory, category.getCatId())); //找3级
			return categoryVo;
		}).sorted(Comparator.comparingInt(CategoryVo::getSort)).collect(Collectors.toList());
	}

	@Override
	public List<Long> findCategoryPath(Long categoryId) {
		List<Long> categoryPath = new ArrayList<>(4);
		findCategoryParentId(categoryId, categoryPath);
		Collections.reverse(categoryPath);
		return categoryPath;
	}

	@Override
	@CacheEvict(allEntries = true)
	public void updateCategoryDetail(CategoryVo categoryVo) {
		CategoryEntity categoryEntity = CategoryConvert.INSTANCE.vo2entity(categoryVo);
		baseMapper.updateById(categoryEntity);

		//TODO更新级联信息
		if (!StringUtils.isEmpty(categoryEntity.getName())) {
			//三级分类才会进行更新二级不会进行关联?
			categoryBrandRelationService.updateCategoryName(categoryEntity.getCatId(), categoryEntity.getName());
		}
	}

	@Override
	@Cacheable(key = "#root.methodName")
	public List<CategoryEntity> getCategoryLevel1() {
		return baseMapper.selectList(new LambdaQueryWrapper<CategoryEntity>().eq(CategoryEntity::getCatLevel, 1));
	}

	//TODO 会产生堆外内存溢出异常
	//1）springboot2.0以后默认使用lettuce作为作为redis的客户端。使用netty进行通信
	//2)lettuce的bug导致netty堆外内存溢出-Xms300m netty如果没有指定堆外内存，默认-Xmx300m
	//io.netty.util.internal.PlatformDependent.incrementMemoryCounter
	//可以通过-Dio.netty.maxDirectMemory进行设置
	//解决方案：不能使用-Dio.netty.maxDirectMemory只去调大堆外内存
	//1）升级lettuce客户端
	//2）切换使用jedis
	@Cacheable(key = "#root.methodName")
	@Override
	public Map<String, List<Category2Vo>> getCategoryJson() {
		//一级分类
		//TODO 遍历了两次
		List<CategoryEntity> allCategory = this.baseMapper.selectList(null);

		return findCategoryJson(allCategory, 0L);
	}

	private Map<String, List<Category2Vo>> findCategoryJson(List<CategoryEntity> allCateGory, Long parentCategoryId) {
		return allCateGory.stream().filter(category -> {
			return category.getParentCid() == parentCategoryId;
		}).collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
			return allCateGory.stream().filter(level2 -> {
				return level2.getParentCid() == v.getCatId();
			}).map(level2 -> {
				Category2Vo category2Vo = new Category2Vo();
				category2Vo.setId(level2.getCatId().toString());
				category2Vo.setCategory1Id(v.getCatId().toString());
				category2Vo.setName(level2.getName());
				//三级分类
				List<Category3Vo> collect = allCateGory.stream().filter(level3 -> {
					return level3.getParentCid() == level2.getCatId();
				}).map(level3 -> {
					Category3Vo category3Vo = new Category3Vo();
					category3Vo.setCategory2Id(level2.getCatId().toString());
					category3Vo.setId(level3.getCatId().toString());
					category3Vo.setName(level3.getName());
					return category3Vo;
				}).collect(Collectors.toList());
				category2Vo.setCategory3List(collect);
				return category2Vo;
			}).collect(Collectors.toList());
		}));
	}

	private void findCategoryParentId(Long categoryChildId, List<Long> path) {
		path.add(categoryChildId);
		CategoryEntity categoryEntity = baseMapper.selectById(categoryChildId);
		if (categoryEntity != null && categoryEntity.getParentCid() != 0L) {
			findCategoryParentId(categoryEntity.getParentCid(), path);
		}
	}

	public List<CategoryEntity> listWithTree() {
		List<CategoryEntity> entities = baseMapper.selectList(null);
		//过滤出一级分类
		List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity -> {
			return categoryEntity.getParentCid() == 0;
		}).map((menus -> {
			menus.setChildren(getChildren(menus, entities));
			return menus;
		})).sorted((m1, m2) -> {
			return m1.getSort() == null ? 0 : m1.getSort() - (m2.getSort() == null ? 0 : m2.getSort());
		}).collect(Collectors.toList());
		return level1Menus;
	}

	//递归查找所有菜单的子菜单
	private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {
		List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
			return categoryEntity.getParentCid() == root.getCatId();
		}).map(categoryEntity -> {
			//递归找到子菜单
			categoryEntity.setChildren(getChildren(root, all));
			return categoryEntity;

		}).sorted((m1, m2) -> {
			return m1.getSort() == null ? 0 : m1.getSort() - (m2.getSort() == null ? 0 : m2.getSort());
		}).collect(Collectors.toList());
		return children;
	}
}
