package com.gyx.controller.webserver;

import java.io.File;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;

import com.gyx.administration.User;
import com.gyx.exception.AppException;
import com.gyx.util.GetHttpServletRequest;

/**
 * 
 * @ClassName: ApplicationConstants 
 * @Description: 常量定义
 * @company agree   
 * @author haoruibing   
 * @date 2011-7-29 下午03:41:02 
 *
 */
public class ApplicationConstants {
	
	//
	public static final String    ACTIONRESULT       = "actionresult";
	public static final String    SESSIONID          = "sessionid";
	public static final String    SESSIONID_PD       = "sessionidpd";
	public static final String    AJAXPARAMETER      = "requesttype";
	public static final String    AJAXPARAMETERVALUE = "ajax";
	public static final String    ROOTPRIVILEGE        = "rootprivilege";
	public static final String    TIMEOUTPRIVI		   = "timeoutprivi";
	public static final String    MESSAGESENDRULE      = "messagesendrule";
	public static final String    MESSAGESENDNODE      = "messagesendnode";
	
	public static final String    EXCEPTION_MSG      = "exception_msg";
	
	public static final String	  SESSIONMAP		= "sessionMap";		//cust网关sessionMap名称
	public static final String	  AFAJSONIP		    = "afa.json.ip";		// AFA JSON 通讯IP
	public static final String	  AFAJSONPORT		= "afa.json.port";		//AFA JSON 通讯PORT
	
	//部门信息相关
	public static final String    UNITLEVEL          = "unitlevel";
	public static final String    SYSTEMUNITTREE     = "systemunittree";
	public static final String    SYSTEMUNITS		 = "systemunits";
	public static final Integer   LASTUNITLEVEL		 = 3; 
	public static final String    BRANCH			 = "branch";
	
	//用户信息相关
	public static final String    USERLEVEL          = "userlevel";
	public static final String    LOGONUSER          = "logonuser";
	public static final String    LOGONUSERID        = "logonuserid";
	public static final String    RESETPASSWORD 	 = "888888";	//用户初始默认密码
	public static final Integer   USERSTATE_DEL 	 = 9; 		//用户状态-已删除

	//菜单相关
	public static final String    MODULELIST	     = "mouduleList";
	
	//数据字典相关
	public static final String    MACHINEVIEWFLAG     = "machine_view_flag";
	public static final String    PASSWORDRULESFLAG     = "password_rules_flag";
	public static final String    QUEUERULESFLAG     = "queue_rules_flag";	
	public static final String    SYSTEMDICTIONARY     = "systemdictionary";	
	public static final String    SYSTEMDICTIONARYLIST = "systemdictionarylist";
	public static final String    APPDICTIONARY		   ="appdictionary";
	public static final String    DICTINFOENABLE       = "0" ;//数据字典项已启用
	//模块信息相关
	public static final Short    LASTMODULETYPE           = 3 ;//菜单类型的动作层数字
	
	//用户授权类
	public static final String NEEDAUTH="1";//需要授权
	public static final String DONOTNEEDAUTH="0";//不需要授权
	
	public static final String   SUCCESS="S";//与afa通信成功判断标志	
	public static final String   FAILURE ="F";//与afa通信失败判断标志
	
	public static final String   NATPCONFIGBEAN         = "natpConfigBean" ;//natp通信服务配置信息
	public static final String   NATPCONFIGBEANV2         = "natpConfigBeanV2" ;//natp通信服务配置信息
	
	public static final String NATP_CACHE = "natp_cache";//natp相关缓存信息
	public static final String PUSHLET_CACHE = "pushlet_cache";//pushlet推送相关信息
	
	//预填单常量
	public static final String DOWNLOAD_FOLDER = "download";
	public static final String PACKAGEUPDATE_LOCK="packageupdate_lock";
	public static final String PACKAGECREATE_LOCK="packagecreate_lock";
	public static final String LOGONPROJECT = "logonproject";
	public static final String COMMUNICATION_PUSHTYPE = "COMMUNICATION_PUSHTYPE";							//报文接收推送协议
	public static final String COMMUNICATION_AFAHTTPCONF = "COMMUNICATION_AFAHTTPCONF";							//AfaHttp通讯协议信息
	
	public static final ServletContext getContext(){
		ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		return context;
	}
	
	/**
	 * 获得工程的目录
	 * @return
	 */
	public static final String getBasePath(){
		Properties prop = System.getProperties();		
		String os = prop.getProperty("os.name");
		os = os.toLowerCase();
		if(os.indexOf("linux")>=0){
			return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+File.separator;
		}else if(os.indexOf("windows")>=0){
			return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		}else{
			return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		}
	}
	
	/**
	 * 获得当前会话的用户
	 * @return
	 */
	public static final User getSessionLogonUser(){
		Object logonUser = GetHttpServletRequest.getRequest().
				getSession().getAttribute(ApplicationConstants.LOGONUSER);
		if(logonUser != null){
			return (User)logonUser;
		}else{
			throw new AppException("请重新登陆");
		}
		
	}
}
