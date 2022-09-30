package com.gulimall.pay.dto;

import com.gulimall.pay.constants.PayConstants;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;
import java.util.Date;

public class CreatePayReqDto {

    @ApiModelProperty("订单号")
    String orderId;

    @ApiModelProperty("支付通道 [wxAppPay|aliAppPay|aliPcreditInstallmentPay][wxH5Pay][JSAPI][spjsapi]")
    @Pattern(regexp = PayConstants.PayChannel.payChannelPattern, message = "错误的支付通道")
    String payChannel;

    @ApiModelProperty("支付日期")
    Date payDate;

    @ApiModelProperty("支付类型 1:正常支付 2：退款")
    @Pattern(regexp = PayConstants.PayType.payTypePattern, message = "错误的支付类型")
    String	payType;
    @ApiModelProperty("wxH5Pay 支付时需要填写 场景信息来源 [Android|IOS|WAP] ")
    String	SourceType;
    @ApiModelProperty("支付通道为 JSAPI|spjsapi 时必填")
    String	openid;

    @ApiModelProperty("终端IP JSAPI|wxH5Pay|spjsapi 支付时必传")
    String ip;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel == null ? null : payChannel.trim();
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getSourceType() {
        return SourceType;
    }

    public void setSourceType(String sourceType) {
        SourceType = sourceType == null ? null : sourceType.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }
}
