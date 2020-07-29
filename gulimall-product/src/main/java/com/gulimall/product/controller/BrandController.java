package com.gulimall.product.controller;

import com.gulimall.common.product.dto.BrandDto;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.utils.R;
import com.gulimall.common.valid.UpdateGroup;
import com.gulimall.common.valid.UpdateStatusGroup;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.convert.BrandConvert;
import com.gulimall.product.entity.BrandEntity;
import com.gulimall.product.service.BrandService;
import com.gulimall.service.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    @GetMapping("/list")
    // @RequiresPermissions("product:brand:list")
    public CommonResult list(@RequestParam PageVo pageParam) {
        PageUtils page = brandService.queryPage(pageParam);
        return CommonResult.ok().data(page);
    }
    /**
     * 信息
     */
    @GetMapping("/info/{brandId}")
//   @RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);
        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("product:brand:save")
    public CommonResult save(@RequestBody BrandEntity brand) {
        brandService.save(brand);
        return CommonResult.ok("品牌添加成功");
    }

    /**
     * 品牌信息 修改
     */
    @PutMapping("/update")
    public CommonResult update(@RequestBody @Validated(value = {UpdateGroup.class}) BrandDto brandDto) {
        BrandEntity brandEntity = BrandConvert.INSTANCE.dto2entity(brandDto);
        brandService.updateById(brandEntity);
        return CommonResult.ok("品牌信息修改成功");
    }

    /**
     * 更新品牌 状态
     *
     * @param brandDto 包含 id 与 状态
     */
    @PutMapping("/update/status")
    public CommonResult updateStatus(@RequestBody @Validated(UpdateStatusGroup.class) BrandDto brandDto) {
        BrandEntity brandEntity = BrandConvert.INSTANCE.dto2entity(brandDto);
        brandService.updateById(brandEntity);
        return CommonResult.ok("状态更新成功");
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
//    @RequiresPermissions("product:brand:delete")
    public CommonResult delete(@RequestBody List<Long> brandIds) {
        if (brandIds != null && brandIds.size() > 0)
            brandService.removeByIds(brandIds);
        return CommonResult.ok("删除成功");
    }
}
