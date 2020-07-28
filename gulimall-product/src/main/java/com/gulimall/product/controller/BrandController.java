package com.gulimall.product.controller;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.utils.R;
import com.gulimall.product.entity.BrandEntity;
import com.gulimall.product.service.BrandService;
import com.gulimall.service.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 品牌
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
   // @RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
//   @RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("product:brand:save")
    public CommonResult save(@RequestBody BrandEntity brand){
		brandService.save(brand);
        return CommonResult.ok("品牌添加成功");
    }

    /**
     * 品牌信息 修改
     */
    @PutMapping("/update")
//   @RequiresPermissions("product:brand:update")
    public CommonResult update(@RequestBody BrandEntity brand){
		brandService.updateById(brand);
        return CommonResult.ok("品牌信息修改成功");
    }

    /**
     * 更新品牌 状态
     * @param brandEntity 包含 id 与 状态
     */
    @PutMapping("/update/status")
    public CommonResult updateStatus(@RequestBody BrandEntity brandEntity){
        brandService.updateById(brandEntity)  ;
        return CommonResult.ok("状态更新成功") ;
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
