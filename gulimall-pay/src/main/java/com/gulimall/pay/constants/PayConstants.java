package com.gulimall.pay.constants;

public interface PayConstants {
	String weixinPay = "/weixinPay";

	interface MappingConstants {
		String	weixinNative			= "/weixinNative/{productId}";
		String	weixinNativePyCallback	= "/weixinNativePyCallback";
		String	weixinDecrypt			= "/weixinDecrypt";
		String	recordPaymentInfo		= "/recordPaymentInfo";
		String	userCancelOrderPay		= "/userCancelOrderPay";
		String	queryOrderPayStatus		= "/queryOrderPayStatus";
		String	refundOrderPay			= "/refundOrderPay";
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
		String	notPay	= "notPay";
		Integer	cancel	= 3;
		Integer	refund	= 4;
	}

	interface NotifyType {
		String nativeNotify = "nativeUrl";
	}

	interface WeixinApiType {
		String	closeOrderById	= "/v3/pay/transactions/out-trade-no/%s/close";
		String	queryPayStatus	= "/v3/pay/transactions/out-trade-no/%s";
		String	domesticRefunds	= "/v3/refund/domestic/refunds";
		String	refundNotify	= "/api/wx-pay/refunds/notify";
	}
}
