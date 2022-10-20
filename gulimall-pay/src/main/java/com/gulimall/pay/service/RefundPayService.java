package com.gulimall.pay.service;

import com.gulimall.pay.dto.PayRefundDto;
import com.gulimall.pay.dto.PayRefundInfoDto;

public interface RefundPayService {
	PayRefundInfoDto createRefundByOrderId(PayRefundDto payRefundDto);
}
