package com.gulimall.ware.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.utils.R;
import com.gulimall.common.vo.PageVo;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.QueryPage;
import com.gulimall.ware.dao.WareInfoDao;
import com.gulimall.ware.entity.WareInfoEntity;
import com.gulimall.ware.feign.MemberFeignService;
import com.gulimall.ware.service.WareInfoService;
import com.gulimall.ware.vo.FareResponseVo;
import com.gulimall.ware.vo.MemberAddressVo;

@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

	@Autowired
	MemberFeignService memberFeignService;

	@Override
	public PageUtils queryPage(PageVo params) {
		LambdaQueryWrapper<WareInfoEntity> wrapper = new LambdaQueryWrapper<>();
		String key = params.getKey();
		if (!StringUtils.isEmpty(key)) {
			wrapper.eq(WareInfoEntity::getId, key).or().like(WareInfoEntity::getName, key).or().like(WareInfoEntity::getAddress, key).or().like(WareInfoEntity::getAreacode, key);
		}
		IPage<WareInfoEntity> page = this.page(new QueryPage<WareInfoEntity>().getPage(params), wrapper);
		return new PageUtils(page);
	}

	/**
	 * 依据地址来计算运费,就需要远程调用查到用户的地址
	 * @param addressId
	 * @return
	 */
	@Override
	public FareResponseVo getFare(Long addressId) {
		FareResponseVo retVo = new FareResponseVo();
		R result = memberFeignService.info(addressId);
		Object memberReceiveAddress = result.get("memberReceiveAddress");
		MemberAddressVo address = new MemberAddressVo();
		BeanUtils.copyProperties(memberReceiveAddress, address);
		if (address != null) {
			String fareString = address.getPhone().substring(address.getPhone().length() - 1, address.getPhone().length());
			BigDecimal fare = new BigDecimal(fareString);
			retVo.setAddress(address);
			retVo.setFare(fare);
			return retVo;
		}
		return null;
	}

}