package com.gulimall.common.constant;

public interface ICommonConstants {
	//36个0,总部id
	String ADMINISTRATOR_ID = "000000000000000000000000000000000000";

	Integer	INT_TRUE	= 1;
	Integer	INT_FALSE	= 0;

	Boolean	BLN_TRUE	= Boolean.TRUE;
	Boolean	BLN_FALSE	= Boolean.FALSE;

	String	STR_TRUE	= "1";
	String	STR_FALSE	= "0";

	String STR_NULL = "null";

	String empty = "";

	String pidRoot = "0";

	String initUseInventory = "0";

	String SUCCESS = "success";

	String	splitCommaSympol	= ",";
	String	splitCombineSympol	= "---";

	String DATE_INIT = "1991-01-01 00:00:01";

	String UNKNOW_COMPANY_ID = "__UNKNOW_COMPANY"; // 触发无法查到数据

	interface RspCode {
		String	secondKillErr				= "secondKillErr|用户秒杀失败";
		String	userUnLockErr				= "userUnLockErr|用户已经秒杀过该商品";
		String	userSecondKillNumErr		= "userSecondKillNumErr|用户秒杀的商品数量不合理";
		String	secondKillTimeUnMatchErr	= "secondKillTimeUnMatchErr|秒杀活动时间已过";
		String	secondKillSkuOntExistErr	= "secondKillSkuOntExistErr|秒杀的商品不存在";
		String	skuUnLockErr				= "skuUnLockErr|锁定库存失败，无需解锁";
		String	wareHasNoSkuErr				= "wareHasNoSkuErr|抱歉，没有足够的库存";
	}

	interface MappingConstants {
		String	aliPayCallback						= "/aliPayCallback";
		String	wxPayCallback						= "/wxPayCallback";
		String	wxH5PayCallback						= "/wxH5PayCallback";
		String	wxJsapiPayCallback					= "/wxJsapiPayCallback";
		String	wxSpjsapiPayCallback				= "/wxSpjsapiPayCallback";
		String	aliPcreditInstallmentPayCallback	= "/aliPcreditInstallmentPayCallback";

	}

	interface WxAppPayConstants {
		String	returnCodeClosed	= "CLOSED";
		String	returnCodeSuccess	= "SUCCESS";
		String	resultCodeSuccess	= "SUCCESS";
		String	refundStatusSuccess	= "SUCCESS";
		String	retWxErr			= "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文处理失败]]></return_msg>" + "</xml> ";
		String	retWxSuccess		= "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
	}

}
