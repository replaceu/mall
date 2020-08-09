package com.gulimall.product.controller;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.utils.R;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.service.AttrService;
import com.gulimall.product.vo.AttrRespVo;
import com.gulimall.product.vo.AttrVo;
import com.gulimall.service.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品属性
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;


    /**
     * 获取 列表
     *
     * @param pageParams 分页参数
     * @param attrType   属性类型  0 为 基本    1 为销售
     * @param categoryId 分类 id
     */
    @GetMapping("/{attrType}/list/{categoryId}")
    public CommonResult listAttr(PageVo pageParams, @PathVariable("attrType") int attrType, @PathVariable(value = "categoryId", required = false) Long categoryId) {
        PageUtils data = attrService.queryList(pageParams, attrType, categoryId);

        return CommonResult.ok().data(data);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{attrId}")
//   @RequiresPermissions("product:attr:info")
    public CommonResult info(@PathVariable("attrId") Long attrId) {
        AttrRespVo attrRespVo = attrService.getAttrInfo(attrId);
        return CommonResult.ok().data(attrRespVo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attrVo) {
        attrService.saveAttrInfo(attrVo);
        return R.ok();
    }

    /**
     * 修改  更新相关信息
     */
    @PutMapping("/update")
//   @RequiresPermissions("product:attr:update")
    public CommonResult update(@RequestBody AttrVo attrVo) {
        attrService.updateAttrInfo(attrVo);
        return CommonResult.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{attrType}")
//    @RequiresPermissions("product:attr:delete")
    public CommonResult delete(@RequestBody List<Long> attrIds, @PathVariable Long attrType) {
        if (attrIds != null || attrIds.size() > 0) {
            attrService.removeAttrInfo(attrIds, attrType);
        }
        return CommonResult.ok();
    }

}
