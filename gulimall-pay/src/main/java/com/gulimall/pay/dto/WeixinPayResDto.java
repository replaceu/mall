package com.gulimall.pay.dto;

public class WeixinPayResDto {
    //订单id
    private String orderId;

    //支付的二维码
    private String nativePay;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getNativePay() {
        return nativePay;
    }

    public void setNativePay(String nativePay) {
        this.nativePay = nativePay == null ? null : nativePay.trim();
    }
}
