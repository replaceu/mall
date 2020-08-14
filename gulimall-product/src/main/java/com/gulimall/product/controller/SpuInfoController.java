package com.gulimall.product.controller;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.utils.R;
import com.gulimall.product.entity.SpuInfoEntity;
import com.gulimall.product.service.SpuInfoService;
import com.gulimall.product.vo.SpuPageVo;
import com.gulimall.product.vo.SpuSaveVo;
import com.gulimall.service.utils.PageUtils;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;



/**
 * spu信息
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@RestController
@RequestMapping("/product/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
   // @RequiresPermissions("product:spuinfo:list")
    public CommonResult list(SpuPageVo params){
        PageUtils page = spuInfoService.queryPageOnCondition(params);

        return CommonResult.ok().data(page) ;
    }

    /**
     * 商品上架
     * @param spuId 需要上架的id
     * @return
     */
    @PutMapping("/{spuId}/up")
    public CommonResult up(@PathVariable Long spuId){
        spuInfoService.up(spuId) ;
        return CommonResult.ok() ;
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//   @RequiresPermissions("product:spuinfo:info")
    public R info(@PathVariable("id") Long id){
		SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("product:spuinfo:save")
    public CommonResult save(@RequestBody SpuSaveVo spuSaveVo , HttpServletRequest request){
		spuInfoService.saveInfo(spuSaveVo);
        return CommonResult.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//   @RequiresPermissions("product:spuinfo:update")
    public R update(@RequestBody SpuInfoEntity spuInfo){
		spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("product:spuinfo:delete")
    public R delete(@RequestBody Long[] ids){
		spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
