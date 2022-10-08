package com.gulimall.pay.constants;

public interface PayConstants {
	String weixinPay = "/weixinPay";

	interface MappingConstants {
		String	weixinNative			= "/weixinNative/{productId}";
		String	weixinNativePyCallback	= "/weixinNativePyCallback";
	}

	interface PayChannel {
		String	payChannelPattern			= "^[wxAppPay|aliAppPay|aliPcreditInstallmentPay][wxH5Pay][JSAPI][spjsapi]*$";
		String	wxAppPay					= "wxAppPay";
		String	wxH5Pay						= "wxH5Pay";
		String	aliAppPay					= "aliAppPay";
		String	jsapi						= "JSAPI";
		String	spjsapi						= "spjsapi";
		String	nativePay					= "nativePay";
		String	aliAppPcreditInstallmentPay	= "aliPcreditInstallmentPay";
	}

	interface PayType {
		String	payTypePattern	= "^[1|2]*$";
		String	pay				= "1";
		String	refund			= "2";
	}

	interface OrderStatus {
		String notPay = "notPay";
	}

	interface NotifyType {
		String nativeNotify = "nativeUrl";
	}
}
