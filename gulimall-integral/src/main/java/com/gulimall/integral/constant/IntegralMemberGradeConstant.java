package com.gulimall.integral.constant;

import java.util.Date;

import com.gulimall.common.utils.DateUtil;

public interface IntegralMemberGradeConstant {
	interface RefValue {
		Date pinkExpiredDate = DateUtil.parseDateInLongFormat("2099-12-31 23:59:59");
	}
}
