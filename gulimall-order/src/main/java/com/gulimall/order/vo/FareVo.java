package com.gulimall.order.vo;

import java.math.BigDecimal;

/**
 * @author Carter
 * @date 2021-09-03 06:24
 * @description:
 * @version:
 */
public class FareVo {

	private BigDecimal		fare;
	private MemberAddressVo	address;

	public BigDecimal getFare() {
		return fare;
	}

	public void setFare(BigDecimal fare) {
		this.fare = fare;
	}

	public MemberAddressVo getAddress() {
		return address;
	}

	public void setAddress(MemberAddressVo address) {
		this.address = address;
	}

}
