package com.gulimall.integral.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gulimall.common.constant.ICommonConstants;
import com.gulimall.common.utils.AmountUtil;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.utils.DateUtil;
import com.gulimall.integral.constant.IntegralMemberGradeConstant;
import com.gulimall.integral.constant.IntegralUserGradeLogConstants;
import com.gulimall.integral.dao.IntegralMemberFreeGradeDao;
import com.gulimall.integral.dao.IntegralUserFreeDao;
import com.gulimall.integral.dao.IntegralUserGradeLogDao;
import com.gulimall.integral.entity.IntegralMemberFreeGradeEntity;
import com.gulimall.integral.entity.IntegralUserFreeEntity;
import com.gulimall.integral.entity.IntegralUserGradeLogEntity;
import com.gulimall.integral.feign.OrderFeignService;
import com.gulimall.integral.service.IntegralFeeGradeService;
import com.gulimall.integral.to.OrderTo;

@Service
public class IntegralFeeGradeServiceImpl implements IntegralFeeGradeService {
	@Autowired
	OrderFeignService			orderFeignService;
	@Autowired
	IntegralUserFreeDao			integralUserFreeDao;
	@Autowired
	IntegralMemberFreeGradeDao	integralMemberFreeGradeDao;
	@Autowired
	IntegralUserGradeLogDao		integralUserGradeLogDao;

	//todo:实现用户等级的更新
	@Override
	public void updateUserFreeGrade(OrderTo orderTo) {
		CommonResult<OrderTo> orderInfoTo = orderFeignService.getOrderStatus(orderTo.getOrderSn());
		if (orderInfoTo.isOk()) {
			OrderTo orderInfo = orderInfoTo.getData();
			BigDecimal payAmount = orderInfo.getPayAmount();
			//todo:查找配置的等级
			List<IntegralMemberFreeGradeEntity> freeGradeList = integralMemberFreeGradeDao.selectList(new QueryWrapper<IntegralMemberFreeGradeEntity>());
			//todo:查找用户之前的免费等级
			IntegralUserFreeEntity userFreeGrade = integralUserFreeDao.selectOne(new LambdaQueryWrapper<IntegralUserFreeEntity>().eq(IntegralUserFreeEntity::getUserId, orderInfo.getMemberId().toString()));
			//todo:算出用户提升至哪一个等级
			String totalAmount = AmountUtil.addFenRetFen(userFreeGrade.getGradeAmount(), payAmount.toString());
			IntegralMemberFreeGradeEntity afterGrade = null;
			if (userFreeGrade != null) {
				for (int i = 0; i < freeGradeList.size() - 1; i++) {
					if (AmountUtil.compareAmount(totalAmount, freeGradeList.get(i).getGreaterAmount()) >= 0 && AmountUtil.compareAmount(totalAmount, freeGradeList.get(i + 1).getGreaterAmount()) < 0) {
						afterGrade = freeGradeList.get(i);
					}
					if (i == freeGradeList.size() - 1) {
						if (AmountUtil.compareAmount(totalAmount, freeGradeList.get(i).getGreaterAmount()) >= 0) {
							afterGrade = freeGradeList.get(i);
						}
					}
				}
			}
			IntegralMemberFreeGradeEntity pinkGrade = freeGradeList.get(0);
			IntegralMemberFreeGradeEntity goldGrade = freeGradeList.get(1);
			Boolean isPinkOrGold = false;
			if (afterGrade.getId().equals(pinkGrade.getId()) || afterGrade.getId().equals(goldGrade.getId())) {
				isPinkOrGold = true;
			}
			//todo:去更新用户的等级
			if (afterGrade != null) {
				IntegralUserFreeEntity userFreeGradeInfo = new IntegralUserFreeEntity();
				userFreeGradeInfo.setUserId(userFreeGrade.getUserId());
				userFreeGradeInfo.setId(userFreeGrade.getId());
				userFreeGradeInfo.setGradeAmount(totalAmount);
				userFreeGradeInfo.setUpdateDate(new Date());
				userFreeGradeInfo.setMemberFreeGradeId(afterGrade.getId());
				userFreeGradeInfo.setEffectDate(new Date());
				userFreeGradeInfo.setExpiredDate(isPinkOrGold ? IntegralMemberGradeConstant.RefValue.pinkExpiredDate : DateUtil.addDateWithDays(new Date(), Integer.valueOf(afterGrade.getLastDay())));
				integralUserFreeDao.updateById(userFreeGradeInfo);

				//todo:去插入用户的等级变更记录
				IntegralUserGradeLogEntity toAddLog = new IntegralUserGradeLogEntity();
				toAddLog.setChangeValue(afterGrade.getId());
				toAddLog.setBeforeValue(userFreeGrade.getMemberFreeGradeId());
				toAddLog.setCurrentValue(afterGrade.getId());
				toAddLog.setCreateDate(new Date());
				toAddLog.setUserId(userFreeGrade.getUserId());
				toAddLog.setExpiredDate((isPinkOrGold ? IntegralMemberGradeConstant.RefValue.pinkExpiredDate : DateUtil.addDateWithDays(new Date(), Integer.valueOf(afterGrade.getLastDay()))));
				toAddLog.setId(UUID.randomUUID().toString());
				toAddLog.setEffectDate(new Date());
				toAddLog.setIsDeleted(ICommonConstants.INT_TRUE);
				toAddLog.setEffectDate(new Date());
				toAddLog.setRefType(IntegralUserGradeLogConstants.RefType.tradeSuccessUpgrade);
				toAddLog.setReason(IntegralUserGradeLogConstants.Reason.tradeSuccessUpgrade);
				//refValue记录的是orderId
				toAddLog.setRefValue(orderInfo.getOrderSn());
				integralUserGradeLogDao.insert(toAddLog);
			}

		}
	}

	@Override
	public void reduceUserFreeGrade(OrderTo orderTo) {
		CommonResult<OrderTo> orderInfoTo = orderFeignService.getOrderStatus(orderTo.getOrderSn());
		if (orderInfoTo.isOk()) {
			OrderTo orderInfo = orderInfoTo.getData();
			BigDecimal payAmount = orderInfo.getPayAmount();
			//todo:查找配置的等级
			List<IntegralMemberFreeGradeEntity> freeGradeList = integralMemberFreeGradeDao.selectList(new QueryWrapper<IntegralMemberFreeGradeEntity>());
			//todo:查找用户之前的免费等级
			IntegralUserFreeEntity userFreeGrade = integralUserFreeDao.selectOne(new LambdaQueryWrapper<IntegralUserFreeEntity>().eq(IntegralUserFreeEntity::getUserId, orderInfo.getMemberId().toString()));
			//todo:算出用户降低至哪一个等级
			String totalAmount = AmountUtil.subtractFenRetFen(userFreeGrade.getGradeAmount(), payAmount.toString());
			IntegralMemberFreeGradeEntity afterGrade = null;
			if (userFreeGrade != null) {
				for (int i = freeGradeList.size() - 1; i >= 0; i--) {
					if (AmountUtil.compareAmount(totalAmount, freeGradeList.get(i).getGreaterAmount()) >= 0 && AmountUtil.compareAmount(totalAmount, freeGradeList.get(i + 1).getGreaterAmount()) < 0) {
						afterGrade = freeGradeList.get(i);
					}
					if (i == 0) {
						if (AmountUtil.compareAmount(totalAmount, freeGradeList.get(i).getGreaterAmount()) >= 0) {
							afterGrade = freeGradeList.get(i);
						}
					}
				}
			}
			IntegralMemberFreeGradeEntity pinkGrade = freeGradeList.get(0);
			IntegralMemberFreeGradeEntity goldGrade = freeGradeList.get(1);
			Boolean isPinkOrGold = false;
			if (afterGrade.getId().equals(pinkGrade.getId()) || afterGrade.getId().equals(goldGrade.getId())) {
				isPinkOrGold = true;
			}
			//todo:去更新用户的等级
			if (afterGrade != null) {
				IntegralUserFreeEntity userFreeGradeInfo = new IntegralUserFreeEntity();
				userFreeGradeInfo.setUserId(userFreeGrade.getUserId());
				userFreeGradeInfo.setId(userFreeGrade.getId());
				userFreeGradeInfo.setGradeAmount(totalAmount);
				userFreeGradeInfo.setUpdateDate(new Date());
				userFreeGradeInfo.setMemberFreeGradeId(afterGrade.getId());
				userFreeGradeInfo.setEffectDate(new Date());
				userFreeGradeInfo.setExpiredDate(isPinkOrGold ? IntegralMemberGradeConstant.RefValue.pinkExpiredDate : DateUtil.addDateWithDays(new Date(), Integer.valueOf(afterGrade.getLastDay())));
				integralUserFreeDao.updateById(userFreeGradeInfo);

				//todo:去插入用户的等级变更记录
				IntegralUserGradeLogEntity toAddLog = new IntegralUserGradeLogEntity();
				toAddLog.setChangeValue(afterGrade.getId());
				toAddLog.setBeforeValue(userFreeGrade.getMemberFreeGradeId());
				toAddLog.setCurrentValue(afterGrade.getId());
				toAddLog.setCreateDate(new Date());
				toAddLog.setUserId(userFreeGrade.getUserId());
				toAddLog.setExpiredDate((isPinkOrGold ? IntegralMemberGradeConstant.RefValue.pinkExpiredDate : DateUtil.addDateWithDays(new Date(), Integer.valueOf(afterGrade.getLastDay()))));
				toAddLog.setId(UUID.randomUUID().toString());
				toAddLog.setEffectDate(new Date());
				toAddLog.setIsDeleted(ICommonConstants.INT_TRUE);
				toAddLog.setEffectDate(new Date());
				toAddLog.setRefType(IntegralUserGradeLogConstants.RefType.refundSuccessDemote);
				toAddLog.setReason(IntegralUserGradeLogConstants.Reason.refundSuccessDemote);
				//refValue记录的是orderId
				toAddLog.setRefValue(orderInfo.getOrderSn());
				integralUserGradeLogDao.insert(toAddLog);
			}

		}
	}
}
