package com.gulimall.common.constant;

public interface IPayConstants {
	String pay = "/pay";

	interface MappingConstants {
		String	createPay	= "/createPay";
		String	refundPay	= "/refundPay";
		String	queryPay	= "/queryPay/{orderId}";
		String	cancelPay	= "/cancelPay/{id}";
	}

	interface UrlConstants {
		String refundPay = IPayConstants.pay + MappingConstants.refundPay;
	}

	interface RspCode {
		String	payChannelNotExist			= "payChannelNotExist|支付通道不存在";
		String	doCreatePayErr				= "doCreatePayErr|创建支付单失败";
		String	payChannelParamErr			= "payChannelParamErr|支付通道参数未配置";
		String	doVerifyRefundPayErr		= "doVerifyRefundPayErr|该订单暂时不能退款";
		String	doQueryPayErr				= "doQueryPayErr|查询支付单失败";
		String	payNotExist					= "payNotExist|支付单不存在";
		String	payStatusError				= "payStatusError|支付状态有误";
		String	sourceTypePatternErr		= "sourceTypePatternErr|支付场景信息有误";
		String	openIdCanNotEmpty			= "openIdCanNotEmpty|openId不能为空";
		String	userPointNotEnoughErr		= "userPointNotEnoughErr|用户可以抵扣的魅力值不足";
		String	alreadyIntegralMemberErr	= "alreadyIntegralMemberErr|您已经是会员了,不能重复购买";

	}

	interface PayChannel {
		String	payChannelPattern			= "^[wxAppPay|aliAppPay|aliPcreditInstallmentPay][wxH5Pay][JSAPI][spjsapi]*$";
		String	wxAppPay					= "wxAppPay";
		String	wxH5Pay						= "wxH5Pay";
		String	aliAppPay					= "aliAppPay";
		String	jsapi						= "JSAPI";
		String	spjsapi						= "spjsapi";
		String	aliAppPcreditInstallmentPay	= "aliPcreditInstallmentPay";
	}

	/**
	 * 订单支付方式
	 */
	enum OrderPayChannelEnum {
		notPay("微信安全支付", PayChannel.wxAppPay),
		paid("微信安全支付", PayChannel.wxH5Pay),
		delivered("支付宝", PayChannel.aliAppPay),
		complete("微信安全支付", PayChannel.jsapi),
		orderRefundApply("微信安全支付", PayChannel.spjsapi),
		orderRefundSuccess("支付宝-花呗", PayChannel.aliAppPcreditInstallmentPay);

		String	name;
		String	index;

		OrderPayChannelEnum(String name, String index) {
			this.index = index;
			this.name = name;
		}

		public static OrderPayChannelEnum getByIndex(String index) {
			OrderPayChannelEnum[] values = OrderPayChannelEnum.values();
			for (OrderPayChannelEnum value : values) {
				if (value.getIndex().equals(index)) { return value; }
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getIndex() {
			return index;
		}

		public void setIndex(String index) {
			this.index = index;
		}
	}

	interface PayType {
		String	payTypePattern	= "^[1|2]*$";
		String	pay				= "1";
		String	refund			= "2";
	}

	interface SourceType {
		String	sourceTypePattern	= "^[Android|IOS|WAP]*$";
		String	android				= "Android";
		String	ios					= "IOS";
		String	wap					= "WAP";
	}

	interface PayStatus {
		//初始化
		String payInit = "0";
		//回调通知成功
		String payCallbackSuccess = "3";
		//回调通知失败
		String payCallbackFailed = "4";
		//调用第三方api失败
		String payResFailed = "5";
		//取消支付
		String payCancel = "6";
	}

	interface TransStatus {
		//初始化
		String transInit = "0";
		//获取第三方支付id
		String transReqThirdTradeId = "1";
		//获得第三方支付id
		String transResThirdTradeId = "2";
		//调用第三方api失败
		String transResFailed = "5";
		//回调通知成功
		String transCallbackSuccess = "3";
		//回调通知失败
		String transCallbackFailed = "4";
		//取消支付交易
		String transCancel = "6";
	}

	interface TransDetailType {
		String	aliPayReq							= "aliPayReq";
		String	aliPayRes							= "aliPayRes";
		String	aliPayCallback						= "aliPayCallback";
		String	aliRefundReq						= "aliRefundReq";
		String	aliRefundRes						= "aliRefundRes";
		String	aliRefundCallback					= "aliRefundCallback";
		String	aliTradeQuery						= "aliTradeQuery";
		String	wxTradeQuery						= "wxTradeQuery";
		String	wxPayReq							= "wxPayReq";
		String	wxPayRes							= "wxPayRes";
		String	wxPayCallback						= "wxPayCallback";
		String	wxRefundReq							= "wxRefundReq";
		String	wxRefundRes							= "wxRefundRes";
		String	wxRefundCallback					= "wxRefundCallback";
		String	wxH5TradeQuery						= "wxH5TradeQuery";
		String	wxH5PayReq							= "wxH5PayReq";
		String	wxH5PayRes							= "wxH5PayRes";
		String	wxH5PayCallback						= "wxH5PayCallback";
		String	wxH5RefundReq						= "wxH5RefundReq";
		String	wxH5RefundRes						= "wxH5RefundRes";
		String	wxH5RefundCallback					= "wxH5RefundCallback";
		String	wxJsapiTradeQuery					= "wxJsapiTradeQuery";
		String	wxJsapiPayReq						= "wxJsapiPayReq";
		String	wxJsapiPayRes						= "wxJsapiPayRes";
		String	wxJsapiPayCallback					= "wxJsapiPayCallback";
		String	wxJsapiRefundReq					= "wxJsapiRefundReq";
		String	wxJsapiRefundRes					= "wxJsapiRefundRes";
		String	wxJsapiRefundCallback				= "wxJsapiRefundCallback";
		String	wxSpjsapiPayReq						= "wxSpjsapiPayReq";
		String	wxSpjsapiPayRes						= "wxSpjsapiPayRes";
		String	wxSpjsapiRefundReq					= "wxSpjsapiRefundReq";
		String	wxSpjsapiRefundRes					= "wxSpjsapiRefundRes";
		String	wxSpjsapiTradeQuery					= "wxSpjsapiTradeQuery";
		String	wxSpjsapiPayCallback				= "wxSpjsapiPayCallback";
		String	aliPcreditInstallmentPayReq			= "aliPcreditInstallmentPayReq";
		String	aliPcreditInstallmentPayRes			= "aliPcreditInstallmentPayRes";
		String	aliPcreditInstallmentPayCallback	= "aliPcreditInstallmentPayCallback";
		String	aliPcreditInstallmentRefundReq		= "aliPcreditInstallmentRefundReq";
		String	aliPcreditInstallmentRefundRes		= "aliPcreditInstallmentRefundRes";
		String	aliPcreditInstallmentRefundCallback	= "aliPcreditInstallmentRefundCallback";
		String	aliPcreditInstallmentTradeQuery		= "aliPcreditInstallmentTradeQuery";
	}

	interface AliAppPayMap {
		String	url					= "url";
		String	appId				= "appId";
		String	appPrivateKey		= "appPrivateKey";
		String	charset				= "charset";
		String	aliPayPublicKey		= "aliPayPublicKey";
		String	timeoutExpress		= "timeoutExpress";
		String	payNotifyUrl		= "payNotifyUrl";
		String	signType			= "signType";
		String	enablePayChannels	= "enablePayChannels";
	}

	interface AliPcreditInstallmentPayMap {
		String	url					= "url";
		String	appId				= "appId";
		String	appPrivateKey		= "appPrivateKey";
		String	charset				= "charset";
		String	aliPayPublicKey		= "aliPayPublicKey";
		String	timeoutExpress		= "timeoutExpress";
		String	payNotifyUrl		= "payNotifyUrl";
		String	signType			= "signType";
		String	enablePayChannels	= "enablePayChannels";
	}

	interface AliAppPayConstants {
		String	retAliSuccess	= "success";
		String	retAliFail		= "fail";
		String	SUCCESS			= "10000";
		String	tradeSuccess	= "TRADE_SUCCESS";
		String	tradeClosed		= "TRADE_CLOSED";
		String	tradeFinished	= "TRADE_FINISHED";
	}

	interface AliAppPayCallback {
		String	notifyTime	= "notify_time";
		String	notifyType	= "notify_type";
		String	notifyId	= "notify_id";
		String	appId		= "app_id";
		String	charset		= "charset";
		String	version		= "version";
		String	signType	= "sign_type";
		String	sign		= "sign";
		String	tradeNo		= "trade_no";
		String	outTradeNo	= "out_trade_no";
		String	tradeStatus	= "trade_status";
		String	totalAmount	= "total_amount";
	}

	interface WxAppPayCallback {
		String	returnCode		= "return_code";
		String	outTradeNo		= "out_trade_no";
		String	transactionId	= "transaction_id";
		String	totalFee		= "total_fee";
		String	reqInfo			= "req_info";
	}

	interface WxAppPayMap {
		String	appId			= "appId";
		String	mchId			= "mchId";
		String	key				= "key";
		String	certPath		= "certPath";
		String	connectTimeOut	= "connectTimeOut";
		String	readTimeOut		= "readTimeOut";
		String	payNotifyUrl	= "payNotifyUrl";
		String	tradeTypeApp	= "tradeTypeApp";
		String	refundNotifyUrl	= "refundNotifyUrl";
	}

	interface WxAppPayConstants {
		String	returnCodeClosed	= "CLOSED";
		String	returnCodeSuccess	= "SUCCESS";
		String	resultCodeSuccess	= "SUCCESS";
		String	refundStatusSuccess	= "SUCCESS";
		String	retWxErr			= "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文处理失败]]></return_msg>" + "</xml> ";
		String	retWxSuccess		= "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

	}

	interface WxAppRefundCallback {
		String	refundStatus	= "refund_status";
		String	transactionId	= "transaction_id";
		String	outTradeNo		= "out_trade_no";
		String	refundId		= "refund_id";
		String	outRefundNo		= "out_refund_no";
		String	refundFee		= "refund_fee";

	}

	interface WxH5PayCallback {
		String	returnCode		= "return_code";
		String	outTradeNo		= "out_trade_no";
		String	transactionId	= "transaction_id";
		String	totalFee		= "total_fee";
		String	reqInfo			= "req_info";
	}

	interface WxH5PayMap {
		String	appId			= "appId";
		String	mchId			= "mchId";
		String	key				= "key";
		String	certPath		= "certPath";
		String	connectTimeOut	= "connectTimeOut";
		String	readTimeOut		= "readTimeOut";
		String	payNotifyUrl	= "payNotifyUrl";
		String	tradeTypeH5		= "tradeTypeH5";
		String	refundNotifyUrl	= "refundNotifyUrl";
	}

	interface WxH5PayConstants {
		String	fail				= "FAIL";
		String	returnCodeClosed	= "CLOSED";
		String	returnCodeSuccess	= "SUCCESS";
		String	resultCodeSuccess	= "SUCCESS";
		String	refundStatusSuccess	= "SUCCESS";
		String	retWxErr			= "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文处理失败]]></return_msg>" + "</xml> ";
		String	retWxSuccess		= "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

	}

	interface WxH5RefundCallback {
		String	refundStatus	= "refund_status";
		String	transactionId	= "transaction_id";
		String	outTradeNo		= "out_trade_no";
		String	refundId		= "refund_id";
		String	outRefundNo		= "out_refund_no";
		String	refundFee		= "refund_fee";

	}

	interface WxJsapiPayCallback {
		String	returnCode		= "return_code";
		String	outTradeNo		= "out_trade_no";
		String	transactionId	= "transaction_id";
		String	totalFee		= "total_fee";
		String	reqInfo			= "req_info";
	}

	interface WxJsapiPayMap {
		String	appId			= "appId";
		String	mchId			= "mchId";
		String	key				= "key";
		String	certPath		= "certPath";
		String	connectTimeOut	= "connectTimeOut";
		String	readTimeOut		= "readTimeOut";
		String	payNotifyUrl	= "payNotifyUrl";
		String	tradeTypeJsapi	= "tradeTypeJsapi";
		String	refundNotifyUrl	= "refundNotifyUrl";
	}

	interface WxJsapiPayConstants {
		String	fail				= "FAIL";
		String	returnCodeClosed	= "CLOSED";
		String	returnCodeSuccess	= "SUCCESS";
		String	resultCodeSuccess	= "SUCCESS";
		String	refundStatusSuccess	= "SUCCESS";
		String	retWxErr			= "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文处理失败]]></return_msg>" + "</xml> ";
		String	retWxSuccess		= "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

	}

	interface WxJsapiRefundCallback {
		String	refundStatus	= "refund_status";
		String	transactionId	= "transaction_id";
		String	outTradeNo		= "out_trade_no";
		String	refundId		= "refund_id";
		String	outRefundNo		= "out_refund_no";
		String	refundFee		= "refund_fee";

	}

	interface WxSpjsapiPayCallback {
		String	returnCode		= "return_code";
		String	outTradeNo		= "out_trade_no";
		String	transactionId	= "transaction_id";
		String	totalFee		= "total_fee";
		String	reqInfo			= "req_info";
	}

	interface WxSpjsapiPayMap {
		String	appId				= "appId";
		String	mchId				= "mchId";
		String	key					= "key";
		String	certPath			= "certPath";
		String	connectTimeOut		= "connectTimeOut";
		String	readTimeOut			= "readTimeOut";
		String	payNotifyUrl		= "payNotifyUrl";
		String	tradeTypeSpjsapi	= "tradeTypeSpjsapi";
		String	refundNotifyUrl		= "refundNotifyUrl";
	}

	interface WxSpjsapiPayConstants {
		String	fail				= "FAIL";
		String	returnCodeClosed	= "CLOSED";
		String	returnCodeSuccess	= "SUCCESS";
		String	resultCodeSuccess	= "SUCCESS";
		String	refundStatusSuccess	= "SUCCESS";
		String	retWxErr			= "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文处理失败]]></return_msg>" + "</xml> ";
		String	retWxSuccess		= "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

	}

	interface WxSpjsapiRefundCallback {
		String	refundStatus	= "refund_status";
		String	transactionId	= "transaction_id";
		String	outTradeNo		= "out_trade_no";
		String	refundId		= "refund_id";
		String	outRefundNo		= "out_refund_no";
		String	refundFee		= "refund_fee";

	}

}
