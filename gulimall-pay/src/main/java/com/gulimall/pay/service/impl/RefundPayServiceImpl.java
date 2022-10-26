package com.gulimall.pay.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.gulimall.common.utils.R;
import com.gulimall.pay.dao.PayRefundInfoDao;
import com.gulimall.pay.dto.PayRefundDto;
import com.gulimall.pay.dto.PayRefundInfoDto;
import com.gulimall.pay.entity.PayRefundInfoEntity;
import com.gulimall.pay.feign.OrderFeignService;
import com.gulimall.pay.service.RefundPayService;
import com.gulimall.pay.vo.PayVo;

@Service
public class RefundPayServiceImpl implements RefundPayService {
	@Autowired
	OrderFeignService	orderFeignService;
	@Autowired
	PayRefundInfoDao	payRefundInfoDao;

	@Override
	public PayRefundInfoDto createRefundByOrderId(PayRefundDto payRefundDto) {

		R originalOrderR = orderFeignService.getOrderPay(payRefundDto.getOrderId());
		PayVo originalOrder = originalOrderR.getData(new TypeReference<PayVo>() {
		});

		PayRefundInfoEntity insertRefund = new PayRefundInfoEntity();
		insertRefund.setOrderId(payRefundDto.getOrderId());
		insertRefund.setReason(payRefundDto.getRefundReason());
		insertRefund.setRefundFee(originalOrder.getTotalAmount());
		insertRefund.setRefundId(UUID.randomUUID().toString());
		payRefundInfoDao.insert(insertRefund);

		PayRefundInfoDto retDto = new PayRefundInfoDto();
		BeanUtils.copyProperties(insertRefund, retDto);
		return retDto;
	}

	@Override
	@Transactional
	public void updateRefundOrder(String content) {
		//将json字符串转换成Map
		Gson gson = new Gson();
		Map<String, String> resultMap = gson.fromJson(content, HashMap.class);

		//根据退款单编号修改退款单
		QueryWrapper<PayRefundInfoEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("refund_no", resultMap.get("out_refund_no"));

		//设置要修改的字段
		PayRefundInfoEntity refundInfo = new PayRefundInfoEntity();
		refundInfo.setRefundId(resultMap.get("refund_id"));//微信支付退款单号
		//查询退款和申请退款中的返回参数
		if (resultMap.get("status") != null) {
			refundInfo.setRefundStatus(resultMap.get("status"));//退款状态
			refundInfo.setContentReturn(content);//将全部响应结果存入数据库的content字段
		}
		//退款回调中的回调参数
		if (resultMap.get("refund_status") != null) {
			refundInfo.setRefundStatus(resultMap.get("refund_status"));//退款状态
			refundInfo.setContentNotify(content);//将全部响应结果存入数据库的content字段
		}
		//更新退款单
		payRefundInfoDao.update(refundInfo, queryWrapper);
	}
}
