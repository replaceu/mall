package com.gulimall.product.controller;

import com.gulimall.common.product.dto.AttrGroupDto;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.valid.AddGroup;
import com.gulimall.common.valid.UpdateGroup;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.convert.AttrGroupConvert;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.service.AttrGroupService;
import com.gulimall.product.service.CategoryService;
import com.gulimall.service.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 属性分组
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @GetMapping("/list/{categoryId}")
    // @RequiresPermissions("product:attrgroup:list")
    public CommonResult list(PageVo pageVo, @PathVariable Long categoryId) {
        PageUtils page = attrGroupService.queryPage(pageVo, categoryId);
        return CommonResult.ok().data(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{attrGroupId}")
//   @RequiresPermissions("product:attrgroup:info")
    public CommonResult info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        List<Long> categoryPath = categoryService.findCategoryPath(attrGroup.getCategoryId());
        AttrGroupDto attrGroupDto = AttrGroupConvert.INSTANCE.entity2dto(attrGroup);
        attrGroupDto.setCategoryPath(categoryPath);
        return CommonResult.ok().data(attrGroupDto);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("product:attrgroup:save")
    public CommonResult save(@RequestBody @Validated(AddGroup.class) AttrGroupDto attrGroupDto) {
        AttrGroupEntity attrGroupEntity = AttrGroupConvert.INSTANCE.dto2entity(attrGroupDto);
        attrGroupService.save(attrGroupEntity);
        return CommonResult.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
//   @RequiresPermissions("product:attrgroup:update")
    public CommonResult update(@RequestBody @Validated(UpdateGroup.class) AttrGroupDto attrGroupDto) {
        AttrGroupEntity attrGroupEntity = AttrGroupConvert.INSTANCE.dto2entity(attrGroupDto);
        attrGroupService.updateById(attrGroupEntity);
        return CommonResult.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
//    @RequiresPermissions("product:attrgroup:delete")
    public CommonResult delete(@RequestBody List<Long> attrGroupIds) {
        if (attrGroupIds != null && attrGroupIds.size() > 0) {
            attrGroupService.removeByIds(attrGroupIds);
        }
        return CommonResult.ok();
    }

}
