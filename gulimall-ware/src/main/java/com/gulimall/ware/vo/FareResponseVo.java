package com.gulimall.ware.vo;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @author Carter
 * @date 2021-08-31 03:45
 * @description:
 * @version:
 */

@Data
public class FareResponseVo {
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
