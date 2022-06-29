package com.gulimall.pay.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.nacos.common.util.UuidUtils;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.gson.Gson;
import com.gulimall.common.constant.ICommonConstants;
import com.gulimall.common.constant.IPayConstants;
import com.gulimall.common.utils.DataFormatUtil;
import com.gulimall.pay.config.PayChannelPropertyConfig;
import com.gulimall.pay.config.WeixinTemplate;
import com.gulimall.pay.dao.PayDao;
import com.gulimall.pay.dao.TransDao;
import com.gulimall.pay.dao.TransDetailDao;
import com.gulimall.pay.entity.PayDo;
import com.gulimall.pay.entity.TransDetailDo;
import com.gulimall.pay.entity.TransDo;
import com.gulimall.pay.service.PayCallbackService;

@Service
public class PayCallbackServiceImpl implements PayCallbackService {
	Logger						logger	= LoggerFactory.getLogger(PayCallbackServiceImpl.class);
	@Autowired
	PayChannelPropertyConfig	payChannelPropertyConfig;
	@Autowired
	TransDao					transDoMapper;
	@Autowired
	PayDao						payDoMapper;
	@Autowired
	TransDetailDao				transDetailDoMapper;

	@Override
	public String doWeixinPayCallback(HttpServletRequest request, HttpServletResponse response) {
		String resXml = "";
		try {
			ServletInputStream inputStream = request.getInputStream();
			//todo:将inputStream转换为xmlString
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			try {
				//todo:使用bufferReader读取流数据，使用stringBuilder转化数据
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line + "/n");
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				try {
					//todo:关闭流操作
					inputStream.close();
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
			resXml = stringBuilder.toString();
		} catch (Exception e) {
			logger.info("wxCallback failed" + e.getMessage());
			return ICommonConstants.WxAppPayConstants.retWxErr;
		}
		if (resXml == null) { return ICommonConstants.WxAppPayConstants.retWxErr; }
		boolean flag = false;
		Map<String, String> param = payChannelPropertyConfig.getWxAppPayMap();
		WeixinTemplate weixinTemplate = null;
		Map<String, String> notifyMap = null;
		try {
			//todo：微信的返回信息是xml格式，通过工具类将它解析成Map集合
			notifyMap = WXPayUtil.xmlToMap(resXml);
			weixinTemplate = new WeixinTemplate(param);
			WXPay wxPay = new WXPay(weixinTemplate);
			//todo:判断支付结果通知中的sign是否有效
			flag = wxPay.isPayResultNotifySignatureValid(notifyMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		//todo:得到回调的结果集的状态,对状态进行判断
		String returnCode = notifyMap.get(IPayConstants.WxAppPayCallback.returnCode);
		if (returnCode.equals(IPayConstants.WxH5PayConstants.fail)) {
			logger.error(notifyMap.get("return_msg"));
			return IPayConstants.WxAppPayConstants.retWxErr;
		}
		String resultCode = notifyMap.get("result_code");
		if (resultCode.equals(IPayConstants.WxH5PayConstants.fail)) {
			logger.error(notifyMap.get("err_code_des"));
			return IPayConstants.WxAppPayConstants.retWxErr;
		}
		//todo:得到结果集中的商户订单号，微信支付订单号，通知的交易金额
		String outTradeNo = notifyMap.get(IPayConstants.WxAppPayCallback.outTradeNo);
		String transactionId = notifyMap.get(IPayConstants.WxAppPayCallback.transactionId);
		String totalFee = notifyMap.get(IPayConstants.WxAppPayCallback.totalFee);
		if (flag && !DataFormatUtil.isNullOrEmpty(outTradeNo) && !DataFormatUtil.isNullOrEmpty(transactionId) && !DataFormatUtil.isNullOrEmpty(totalFee)) {
			//todo:更新交易表，更新支付表，插入交易明细
			TransDo callBackTrans = transDoMapper.selectByThirdReqId(outTradeNo);
			if (callBackTrans != null) {
				if (callBackTrans.getTransStatus().equals(IPayConstants.TransStatus.transCallbackSuccess)) { return IPayConstants.WxAppPayConstants.retWxSuccess; }
				callBackTrans.setThirdTradeResId(transactionId);
				callBackTrans.setThirdTradeCallbackTime(new Date());
				callBackTrans.setThirdTradeCallbackAmount(totalFee);
				callBackTrans.setTransStatus(returnCode.equals(IPayConstants.WxAppPayConstants.returnCodeSuccess) ? IPayConstants.TransStatus.transCallbackSuccess : IPayConstants.TransStatus.transCallbackFailed);
				transDoMapper.updateByPrimaryKeySelective(callBackTrans);
				PayDo callBackPay = new PayDo();
				callBackPay.setId(callBackTrans.getPayId());
				callBackPay.setPayStatus(returnCode.equals(IPayConstants.WxAppPayConstants.returnCodeSuccess) ? IPayConstants.PayStatus.payCallbackSuccess : IPayConstants.PayStatus.payCallbackFailed);
				payDoMapper.updateByPrimaryKeySelective(callBackPay);
				TransDetailDo callBackTransDetail = new TransDetailDo();
				callBackTransDetail.setId(UuidUtils.generateUuid());
				callBackTransDetail.setCreateTime(new Date());
				callBackTransDetail.setTransId(callBackTrans.getId());
				callBackTransDetail.setDetailType(IPayConstants.TransDetailType.wxPayCallback);
				callBackTransDetail.setTransDetail(new Gson().toJson(notifyMap));
				transDetailDoMapper.insertSelective(callBackTransDetail);

				//todo:更改订单状态,将订单状态改为支付
				PayDo pay = payDoMapper.selectByPrimaryKey(callBackTrans.getPayId());
				if (returnCode.equals(IPayConstants.WxAppPayConstants.resultCodeSuccess)) {
					//processCallbackOrderSuccess(pay.getOrderId());
				}
				//todo:推送给内部所有人订单成功信息
			}

		}
		return null;

	}
}
