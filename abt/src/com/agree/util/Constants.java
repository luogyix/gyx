package com.agree.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName: Constants 
 * @Description: 业务管理台常量定义
 * @company agree   
 * @author lifeifei   
 * @date Aug 30, 2011 9:23:13 PM 
 *
 */
public class Constants {
	//支付产品代码
	static public final  String PRODUCTCODE_HVPS="1001";	//大额支付产品
	static public final  String PRODUCTCODE_BEPS="1002";	//小额支付产品
	static public final  String PRODUCTCODE_SIPS="1004";    //清算账户管理
	static public final  String PRODUCTCODE_CPM="1009";    //协议管理
	static public final  String PRODUCTCODE_IBPS="1003";   	//网银互联
	static public final  String PRODUCTCODE_CCMS="1005";   	//公共管理
	static public final  String PRODUCTCODE_PPM="1006";   	//智能汇路管理
	static public final  String PRODUCTCODE_NXPS="1010";         //农信银支付产品
	public static final String ZICHAN = "assets"; //资产文件夹，所有工程的 配置、接口、用例、流水 文件所放置的相对跟目录
	public static final String encoding = "UTF-8";//平台编码
	
	/**
	 * 获取产品代码
	 */
	public static String getProductcode(String productcode) {
		if (productcode.toUpperCase().equals("PRODUCTCODE_HVPS")){
			return PRODUCTCODE_HVPS;
		}
		if (productcode.toUpperCase().equals("PRODUCTCODE_BEPS")){
			return PRODUCTCODE_BEPS;
		}
		if (productcode.toUpperCase().equals("PRODUCTCODE_SIPS")){
			return PRODUCTCODE_SIPS;
		}
		if (productcode.toUpperCase().equals("PRODUCTCODE_CPM")){
			return PRODUCTCODE_CPM;
		}
		if (productcode.toUpperCase().equals("PRODUCTCODE_IBPS")){
			return PRODUCTCODE_IBPS;
		}
		if (productcode.toUpperCase().equals("PRODUCTCODE_CCMS")){
			return PRODUCTCODE_CCMS;
		}
		if (productcode.toUpperCase().equals("PRODUCTCODE_PPM")){
			return PRODUCTCODE_PPM;
		}
		if (productcode.toUpperCase().equals("PRODUCTCODE_NXPS")){
			return PRODUCTCODE_NXPS;
		}
		return "";
	}
	
	//机构相关
	static public final  String BRANCHTYPE_TOP = "1";	//机构类型-总行
	
	//产品服务类
	static public final  String SVRBUSISTATUS_OPEN  = "1"; //服务状态-开通
	static public final  String SVRBUSISTATUS_CLOSE = "2"; //服务状态-关闭
	
	//支付类
	static public final  String MBFLAG_SEND = "1"; //往帐
	static public final  String MBFLAG_RECV = "2"; //来帐
	static public final  String PAYFLAG_CREDIT = "1"; //借贷标记-贷记
	static public final  String PAYFLAG_DEBIT  = "2"; //借贷标记-借记
	static public final  String QUEUE  = "PR07"; //农信银 排队标记-以排队
	static public final  String NXPSBUSITYPECANCEL  = "K108"; //农信银 业务撤销
	static public final  String NXPSBUSITYPELINEUP  = "JC03"; //农信银 - 排队撤销
	static public final  String[] PAYFLAG_PAYMENT  = {"0","1","2","4","s"}; //借贷标记-支付类
	static public final  String[] CENTERSTATUS_INPROC_HVPS = {"PR04","PR09","PR08","PRFF","PRB9"}; //大额终态支付业务人行状态列表
	static public final  String[] CENTERSTATUS_CANCEL_HVPS = {"PR12"}; //大额可撤销业务人行状态列表
	static public final  String[] CENTERSTATUS_INPROC_BEPS = {"PR03","PR04","PR09","PR08","PRFF","PRB9"}; //小额终态支付业务人行状态列表
	static public final  String[] CENTERSTATUS_CANCEL_BEPS = {"PR11"}; //小额可撤销业务人行状态列表

	//规则类
	static public final  String RULESTATUS_VALID = "1"; //规则状态-有效	
	static public final  String BUSITYPE_ALL = "0"; //业务类型-0表示全部
	static public final  String BUSIKIND_ALL = "0"; //业务种类-0表示全部	
	static public final  String CHECKLEVEL_ALL = "1"; //检查级别-通用规则
	static public final  String CHECKLEVEL_SPCL = "2"; //检查级别-特殊规则
	static public final  String FINDUNDERBRNO = "2"; //查询范围-查询辖属
	static public final  String FINDMYBRNO = "1"; //查询范围-查询本机构
	static public final  String MNGTYPE_EDHF = "PC02"; //自动拆借管理类型 - PC02(额度恢复)  
	static public final  String CHANNEL_ALL="0";//渠道-0表示全部
	
	//流动性管理类
	static public final  String LMCTRADETYPE_OPER = "1"; //流动性管理交易类型-管理操作
	static public final  String LMCTRADETYPE_NOTIF = "2"; //流动性管理交易类型-人行通知
	
	//报文处理类
	static public final String SEND = "1"; //往帐
	static public final String RECEIVE = "2"; //来帐
	public static final String HEADER = "header";//报文头
	static public final String PROSUSSCESS = "0"; //成功
	static public final String PROFAILED = "1"; //失败
	public static final String SECURITYINFO = "securityInfo";//信息域
	public static final String DEFAULT = "default";//报文对象默认存储区间，可以放置一些报文参数
	public static final String BANKCOMMHEADER = "BankCommHeader";//行内信息请求头
	public static final String CNAPSHEADER = "CNAPSHeader";//现代化支付网关头
	public static final String RSPHDR = "RspHdr";//返回xm报文头
	public static final String CONTENT = "Content";//报文正文
	public static final String CHANNELCODE_BSMS = "010";//统一支付平台定义的渠道代码-业务管理台
	public static final String RSPHDR_STATUS = "status";//业务处理平台同步应答_受理状态节点
	public static final String RSPHDR_STATUS_SUCCESS = "1";//业务处理平台同步应答_受理状态_受理成功
		
	//AFAAFP文件传输协议
	public static final String MC="UPBS";//应用代码
	public static final String TC="1234";//交易代码
	public static final int STAT_SUCCESS=0;//交易成功
	public static final String REMOTEPATH="REMOTEPATH";//远程路径
	public static final String SENDFILE="SENDFILE";//上传文件
	public static final String MASK="MASK";//掩码
	public static final String MASK_P="MASK_P";//上传文件 'P'
	public static final String MASK_G="MASK_G";//下载文件	'G'
	public static final String SENDFILENAME="SENDFILENAME";//上传文件名称
	public static final String OPSTAT="OPSTAT";	//操作状态
	public static final String RETFILECONTS="RETFILECONTS";//返回文件信息
	public static final String REMOTEPATH_SEND="Send";		//往账目录
	public static final String REMOTEPATH_RECV="Recv";		//来账目录

	
	//数据字典类
	public static final String DICTTYPE_BRANCHMAPBAKHVPS="branchmapbakhvps";//机构行号映射与大额同步更新的产品代码
	public static final String DICTTYPE_BRANCHMAPBAKBEPS="branchmapbakbeps";//机构行号映射与小额同步更新的产品代码
	public static final String DICTTYPE_BRANCHPXYBAKHVPS="branchpxybakhvps";//行内机构代理与大额同步更新的产品代码
	public static final String DICTTYPE_BRANCHPXYBAKBEPS="branchpxybakbeps";//行内机构代理与小额同步更新的产品代码
	
	//日间差错处理
	public static final String EXCPSTATUS_INIT="0";			// 0-初始待处理
	public static final String EXCPSTATUS_FAIL="2";			// 2-处理失败
	public static final String EXCPSTATUS_SUCCESS="1";			// 1-处理成功
	public static final String YES="Y";			// YES
	
	
	//事件类型
	public static final String EVENTTYPE_PAYENT="0";		//0:单笔支付类流水
	public static final String EVENTTYPE_BEPSPACK="2";		//2:小额包交易流水 
	
	//人行处理状态
	public static final String CENTERSTATUS_PRFF="PRFF";
	public static final String CENTERSTATUS_PRB9="PRB9";
	
	//业务处理步骤
	public static final String TRADEBUSISTEP_RECEIPT="09";	//已回执
	public static final String TRADEBUSISTEP_UNSEND = "'00','02','03','0A'";//未发出往账
	public static final String TRADEBUSISTEP_UNRECV = "'13','16','22'";//未入账来账挂账
	
	//农信银-交易业务处理步骤
	public static final String TRADEBUSISTEP_NXPSSENT = "'04'"; //发送MBFE
	//农信银-交易步骤状态
	public static final String TRADESTATUS_NXPSSUCCESS = "'1'";  // 成功
	public static final String TRADESTATUS_NXPSTIMEOUT = "'2'"; //超时
	
	//农信银-汇兑业务
	public static final String NXPS_EXCHANGE="'nps.120.001.01'";
	
	//通知类报文号-系统类通知
	public static final String NOTICE_SYS="'ccms.809.001.01','ccms.801.001.01','ccms.803.001.01','ccms.807.001.01'";
	//通知类报文号-公共管理类通知
	public static final String NOTICE_PUBMNG="'ccms.903.001.01','ccms.906.001.01','ccms.907.001.01','ccms.913.001.01','ccms.915.001.01','ccms.916.001.01','ccms.917.001.01','ccms.926.001.01'";
	//通知类报文号-清算管理类通知
	public static final String NOTICE_CLRMNG="'saps.372.001.01','nets.356.001.01'";
	//通知类报文号-预警类通知
	public static final String NOTICE_WARN="'saps.360.001.01','nets.355.001.01','saps.362.001.01'";
	
	//农信银通知类
	//通知类报文号-系统类通知
	public static final String NXPS_NOTICE_SYS="'nps.801.001.01','nps.803.001.01','nps.807.001.01','nps.809.001.01'";
	//通知类报文号-公共管理类通知
	public static final String NXPS_NOTICE_PUBMNG="'nps.919.001.01','nps.903.001.01'";
	//通知类报文号-清算管理类通知
	public static final String NXPS_NOTICE_CLRMNG="'nps.366.001.01'";
	//通知类报文号-预警类通知
	public static final String NXPS_NOTICE_WARN="'nps.360.001.01','nps.362.001.01'";
	
	
	//人行行号（NPC、CCPC节点号）长度
	public static final String BAKNLENGTH_CENTER="4";
	
	//头寸预报相关
	public static final String AMPREPORTPRI_QUERYALL = "0";//可查询全行
	public static final String AMPREPORTPRI_REPORTANDQUERYBELOW = "1";//可预报及查询辖属
	public static final String AMPREPORTPRI_REPORTANDQUERYSELF = "2";//可预报及查询自身
	
	//汇票业务相关-业务类型
	public static final String NXPSCORPBUSITYPE_AX="A200";  //汇票-业务类型
	public static final String NXPSCORPBUSITYPE_BX="B200";  //汇票-业务类型
	
	//汇票业务相关-业务种类
	public static final String NXPSCORPBUSIKIND_AX="02201"; //汇票-业务种类
	public static final String NXPSCORPBUSIKIND_BX="02202"; //汇票-业务种类
	public static final String NXPSCORPBUSIKIND_CX="02203"; //汇票-业务种类
	public static final String NXPSCORPBUSIKIND_DX="02204"; //汇票-业务种类
	public static final String NXPSCORPBUSIKIND_EX="02205"; //汇票-业务种类
	public static final String NXPSCORPBUSIKIND_FX="02206"; //汇票-业务种类
	public static final String NXPSCORPBUSIKIND_GX="02207"; //汇票-业务种类
	public static final String NXPSCORPBUSIKIND_HX="02208"; //汇票-业务种类
	
	
	//农信银支付业务报文号
	public static final String NXPSPAYMENT_CORPTRADETYPE="'nps.120.001.01','nps.140.001.01','nps.142.001.01','nps.170.001.01','nps.270.001.01'"; //汇票-业务种类
	
	//afa NATP 通讯使用的编码
	public static final String NATP_ENCODING = "gbk";
	
	
	//cust网关常量
	public static final	String CUST_FROMJSON_TRANCODE = "trancode";	//交易码字段 json报文上送
	public static final int SESSION_VALIDTIME = 60*60*15;	//session有效时间 15小时
	public static final Map<String,String> ERRORCODE_MSG = new HashMap<String, String>();	//保存手持设备session
	public static final String InitInterface = "ibp.pub.d000.01";	//设备初始化接口
	
	static{
		ERRORCODE_MSG.put("HOS014", "收到的数据有误，未收到任何数据。");
		ERRORCODE_MSG.put("HOS015", "业务处理过程报错。");
		ERRORCODE_MSG.put("HOS016", "不能处理该请求，请确认请求为设备初始化请求。");
		ERRORCODE_MSG.put("HOS017", "设备没有初始化或初始化信息已过期，请先进行设备初始化操作。");
		ERRORCODE_MSG.put("HOS018", "交易代码不能识别。");
	}
	
}
