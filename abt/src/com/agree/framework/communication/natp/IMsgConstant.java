package com.agree.framework.communication.natp;

public interface IMsgConstant {
	/**
	 * 网关报文常量
	 */
	public static final String GW_gate1="GATE1";
	//固定不变
	public static final String GW_HEAD_versionNo="versionNo";
	public static final String GW_HEAD_toEncrypt="toEncrypt";
	public static final String GW_HEAD_commType="commType";
	public static final String GW_HEAD_senderSN="senderSN";
	public static final String GW_HEAD_senderTime="senderTime";
	public static final String GW_HEAD_senderDate="senderDate";
	public static final String GW_HEAD_gwErrorCode="gwErrorCode";
	public static final String GW_HEAD_gwErrorMessage="gwErrorMessage";
	public static final String GW_HEAD_errorPrifix = "GWERR";
	
	//可能会变动的
	public static final String GW_HEAD_transCode="transCode";
	public static final String GW_HEAD_tradeCode="tradeCode";
	public static final String GW_HEAD_commCode="commCode";
	public static final String GW_HEAD_receiverId="receiverId";
	public static final String GW_HEAD_senderId="senderId";
	//网关报文体
	public static final String GW_HEAD_abc_oid="abc_oid";
	public static final String GW_HEAD_Teller_tradeCode="Teller_tradeCode";
	
	
	/**
	 * 柜面常量
	 */
	public static final String TELLER_ABS_OID = "GF_ABS_TELLER";
	public static final String TELLER_Teller_tradeCode = "tradeCode";
	public static final String TELLER_abc_oid = "abc_oid";
	public static final String TELLER_rsendTime = "rsendTime";
	public static final String TELLER_content = "content";
	public static final String TELLER_app = "app";
	public static final String TELLER_props = "props";
	public static final String TELLER_WDPT = "WDPT";
	//201106111724新加活动字段TELLER_TradeCode和GW_HEAD_tradeCode是对应的,两个的值一致
	public static final String TELLER_receiverId = "receiverId";
	public static final String TELLER_senderId = "senderId";
	public static final String TELLER_TradeCode = "GW_tradeCode";
	public static final String TELLER_WDVC01 = "WDVC01";
	public static final String TELLER_WDVC02 = "WDVC02";
	
	
	
	//贵宾请求信息
	
	//贵宾返回的信息
	/**
	 * 贵宾系统错误码
	 */ 
	public static final String VCRM_errorCode = "errorCode";
	/**
	 * 贵宾系统错误信息
	 */
	public static final String VCRM_errorMsg = "errorMsg";
	/**
	 * 成功
	 */
	public static final String VCRM_errorCode_000000 = "000000";
	/**
	 * 无交易码
	 */
	public static final String VCRM_errorCode_999999 = "999999";
	/**
	 * 不存在该交易
	 */
	public static final String VCRM_errorCode_999998 = "999998";
	/**
	 * 报文操作异常
	 */
	public static final String VCRM_errorCode_999997 = "999997";
	/**
	 * 客户未找到
	 */
	public static final String VCRM_errorCode_800001 = "800001";
	
	//排队机常量 
	public static final String PDJ_isPDJ = "isPDJ";
	public static final String PDJ_PDJ = "PKHDJ";
	//请求字段
	public static final String PDJ_pId = "pId";
	public static final String PDJ_certificationNo = "certificationNo";
	public static final String PDJ_orgNo = "orgNo";
	//返回字段
	public static final String PDJ_isVIP = "isVIP";
	public static final String PDJ_errorCode = "errorCode";
	public static final String PDJ_errorMsg = "errorMsg";
	
	//网点平台错误消息常量定义
	/**
	 * 网点平台错误码
	 */
	public static final String WDPT_errorCode = "errorCode";
	/**
	 * 网点平台错误消息
	 */
	public static final String WDPT_errorMsg = "errorMsg";
	/**
	 * 消息内容缺失
	 */
	public static final String WDPT_error00 = "00";
	/**
	 * 网关通讯异常
	 */
	public static final String WDPT_error01 = "01";
	/**
	 * 等待超时
	 */
	public static final String WDPT_error02 = "02";
	/**
	 * 客户无客户经理
	 */
	public static final String WDPT_error03 = "03";
	/**
	 * 客户未找到
	 */
	public static final String WDPT_error04 = "04";
	/**
	 * 正确
	 */
	public static final String WDPT_error11 = "11";
	
	//与手机银行定义的错误码
	/**
	 * 正确
	 */
	public static final String WDPT_error_000000 = "000000";
	/**
	 * 该网点不支持预约
	 * 
	 */
	public static final String WDPT_error_000001 = "000001";
	/**
	 * 网点不存在可用排队机
	 *
	 */
	public static final String WDPT_error_000002 = "000002";
	/**
	 * 网点未绑定预约业务
	 *
	 */
	public static final String WDPT_error_000003 = "000003";
	/**
	 * 预约业务不在服务中
	 *
	 */
	public static final String WDPT_error_000004 = "000004";
	/**
	 * 当天已存在该客户预约记录
	 *
	 */
	public static final String WDPT_error_000005 = "000005";
	
}
