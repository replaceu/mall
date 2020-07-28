package com.gulimall.product.controller;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.product.dto.CategoryDto;
import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 商品三级分类
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有 三级分类
     */
    @GetMapping("/list/tree")
    // @RequiresPermissions("product:category:list")
    public CommonResult list() {
        List<CategoryDto> categoryDto = categoryService.listCategoryWithTree();
        return CommonResult.ok().data(categoryDto);
    }

    /**
     * 通过分类id 获取详细信息
     *
     * @param categoryId 分类id
     */

    @GetMapping("/info/{categoryId}")
    //   @RequiresPermissions("product:category:info")
    public CommonResult categoryInfo(@PathVariable Long categoryId) {
        CategoryEntity categoryEntity = categoryService.getById(categoryId);
        return CommonResult.ok().data(categoryEntity);
    }

    /**
     * 保存 分类
     */
    @PostMapping("/save")
//    @RequiresPermissions("product:category:save")
    public CommonResult save(@RequestBody CategoryEntity category) {
        categoryService.save(category);
        return CommonResult.ok(category.getName() + " 菜单 保存成功");
    }

    /**
     * 菜单 修改
     */
    @PutMapping("/update")
//   @RequiresPermissions("product:category:update")
    public CommonResult update(@RequestBody CategoryEntity category) {
        categoryService.updateById(category);
        return CommonResult.ok("菜单 修改成功");
    }

    /**
     * 修改 排序
     */
    @PutMapping("/update/sort")
//   @RequiresPermissions("product:category:update")
    public CommonResult updateSort(@RequestBody List<CategoryEntity> categories) {
        categoryService.updateBatchById(categories);
        return CommonResult.ok("修改成功");
    }

    /**
     * 批量删除 分类
     */
    @DeleteMapping("/delete")
//    @RequiresPermissions("product:category:delete")
    public CommonResult delete(@RequestBody @Validated @NotEmpty(message = "没有需要删除的数据") List<Long> catIds) {
        categoryService.removeMenuByIds(catIds);
        return CommonResult.ok("批量删除成功");
    }
}
