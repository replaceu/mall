package com.gulimall.integral.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gulimall.common.utils.AmountUtil;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.utils.DataFormatUtil;
import com.gulimall.integral.dao.IntegralMemberFreeGradeDao;
import com.gulimall.integral.dao.IntegralUserFeeDao;
import com.gulimall.integral.dao.IntegralUserFreeDao;
import com.gulimall.integral.entity.IntegralMemberFreeGradeEntity;
import com.gulimall.integral.entity.IntegralUserFreeEntity;
import com.gulimall.integral.feign.OrderFeignService;
import com.gulimall.integral.service.IntegralFeeGradeService;
import com.gulimall.integral.to.OrderTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class IntegralFeeGradeServiceImpl implements IntegralFeeGradeService {
    @Autowired
    OrderFeignService orderFeignService;
    @Autowired
    IntegralUserFreeDao integralUserFreeDao;
    @Autowired
    IntegralMemberFreeGradeDao integralMemberFreeGradeDao;

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
            if (userFreeGrade != null) {
                for (int i = 0; i < freeGradeList.size() - 1; i++) {
                    if (AmountUtil.compareAmount(totalAmount,freeGradeList.get(i).getGreaterAmount())>=0&&AmountUtil.compareAmount(totalAmount,freeGradeList.get(i+1).getGreaterAmount())<0){


                    }
                }
            }


        }
    }
}
