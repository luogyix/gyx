/**
 * 
 */
package com.agree.framework.web.action.welcome;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.struts2.webserver.IStartupObject;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.Module;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.administration.IAdministrationService;
import com.agree.framework.web.service.administration.ILogonService;
import com.agree.ts.action.MobServeAction;
import com.agree.ts.service.WorkHandlerService;
import com.agree.util.Constants;
import com.agree.util.DateUtil;
import com.agree.util.MD5;
import com.agree.util.PropertiesUtils;

/**
 * @author Administrator
 *
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class LogonAction extends BaseAction {
	private Logger logger = LoggerFactory.getLogger(LogonAction.class);//日志
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	PropertiesUtils pro = new PropertiesUtils();
	private IStartupObject initService;
	private ILogonService logonService;
	private IAdministrationService administrationService;
	private WorkHandlerService workHandlerService;
	
	public void setAdministrationService(
			IAdministrationService administrationService) {
		this.administrationService = administrationService;
	}

	public void setLogonService(ILogonService logonService) {
		this.logonService = logonService;
	}
	
	private String userinfo;
	public void setUserinfo(String userinfo) {
		this.userinfo = userinfo;
	}
	
	public String logon() throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		pro.getFile("/conf.properties");
		String jsonString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(jsonString);
		User user = (User)JSONObject.toBean(obj, User.class);

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession hsession = request.getSession();
		User user1=(User)hsession.getAttribute(ApplicationConstants.LOGONUSER);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if(user1 != null){
			if(user.getUsercode() == null){
				user.setUsercode(user1.getUsercode());
				user.setLogintime(sdf.format(new Date()));
			}
		}
		//user.setUsercode(user.getUsercode().toUpperCase());
		Map map = logonService.getLogonUser_itransc(user);
		User result_user =(User)map.get("user");
		List<Module> privileges = (List<Module>)map.get("privileges");
		Set modules=new HashSet();
		List treeModules=new ArrayList();
		for(Module module:privileges){
			if(0==(module.getPrivilegeType())){
				treeModules.add(module);
			}
			modules.add(module);
		}
		
		result_user.setCatalog_and_privileges(modules);
		//判断是否首次登录或者3个月未登录或者密码失效
		
		//boolean logon = (Boolean)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.PASSWORDRULESFLAG);
		
		if((Boolean)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.PASSWORDRULESFLAG)){
			if(result_user.getLogintime()!=null&&result_user.getInfoex2()!=null){
	    		Date logon_date = null;
	    		String logintime = result_user.getLogintime();
	    		if(result_user.getLogintime().indexOf("-")>0){
	    			logon_date = sdf.parse(logintime);
	    		}else{
	    			logintime = logintime.substring(0,4)+"-"+logintime.substring(4,6)+"-"+logintime.substring(6,8)+" "+logintime.substring(8,10)+":"+logintime.substring(10,12)+":"+logintime.substring(12,14);
	    			logon_date = sdf.parse(logintime);
	    		}
	    		Date lastmoddate = sdf.parse(result_user.getInfoex2());
	    		int num = DateUtil.getIntervalMonth(logon_date,new Date());
	    		int lastnum = DateUtil.getIntervalMonth(lastmoddate, new Date());
	    		if(num>=3){
	    			ret.put("flag", "systemuserpass");
	    		}else{
	    			//判断密码修改时间
	    			if(lastnum < 3){
	    				ret.put("flag", "success");
	        			//设置登录时间
	        			result_user.setLogintime(sdf.format(new Date()));
	        		}else{
	        			ret.put("flag", "systemuserpass");
	        		}
	    		}
	    	}else{
	    		ret.put("flag", "systemuserpass");
	    	}
		}else{
			result_user.setLogintime(sdf.format(new Date()));
		}
		//设置登录次数
		if(result_user.getLogincnt()!=null){
			result_user.setLogincnt(result_user.getLogincnt()+1);
		}else {
			result_user.setLogincnt(1);
		}
		//设置登录ip
		result_user.setHostip(request.getRemoteAddr() + ":"
				+ String.valueOf(request.getRemotePort()));
		
		hsession.setAttribute(ApplicationConstants.LOGONUSER, result_user);
		List sessionList = (List)hsession.getServletContext().getAttribute(ApplicationConstants.SESSIONID);
		if(sessionList == null){
			sessionList = new ArrayList();
			Collections.synchronizedList(sessionList);
		}else{//同一用户不能在同时重复登录
			Object[] sessionListCopy= sessionList.toArray();
			for(int i=0; i< sessionListCopy.length; i++){
				HttpSession sess = (HttpSession)sessionListCopy[i];
				try{
					User us = (User)sess.getAttribute(ApplicationConstants.LOGONUSER);
					if(result_user.getUserid().equals(us.getUserid()) && !(sess.getId().equals(request.getSession().getId()))){
						sessionList.remove(sess);
						sess.invalidate();
					}
				}catch(java.lang.IllegalStateException e){
					sessionList.remove(sess);
				}
			}
		}
		sessionList.add(hsession);
		hsession.getServletContext().setAttribute(ApplicationConstants.SESSIONID, sessionList);
		String ss = pro.read("session.timeout");
		if(ss == null || ss.equals("")){
			ss = "-1";
		}
		int timeOut = Integer.parseInt(ss);
//		pro.close();
		hsession.setMaxInactiveInterval(timeOut);
		administrationService.editSystemUser_itransc(result_user);
		ret.put(ServiceReturn.FIELD1, treeModules);
		ret.put(ServiceReturn.FIELD2, map.get("projects"));
		ret.put(ServiceReturn.FIELD3, result_user.getUnitid());
		//重新初始化部门内存信息
		ServletContext context = ServletActionContext.getServletContext();
		initService.setUnits(context);
		initService.setParameters(context);
		super.setActionresult(super.convertServiceReturnToJson(ret).toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 柜员令牌验证
	 * @return
	 * @throws Exception
	 */
	public String token() throws Exception {
		TokenAction token = (TokenAction)ContextLoader.getCurrentWebApplicationContext().getBean("tokenAction");
		JSONObject returnJson = token.checktoken();
		if(returnJson.size()==1){
			String validate = returnJson.get("fail").toString();
			HttpServletRequest req = ServletActionContext.getRequest();
			req.setAttribute("validate", validate);
			return "fail";
		}
		String success =  returnJson.get("result").toString();
		String usercode = returnJson.get("field1").toString();
		if("true".equals(success)){
			ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
			pro.getFile("/conf.properties");
			User user = new User();
			user.setUsercode(usercode);
			Map map = logonService.getLogonUser_teller(user);
			HttpServletRequest request = ServletActionContext.getRequest();
			User result_user =(User)map.get("user");
			
			List<Module> privileges = (List<Module>)map.get("privileges");
			Set modules=new HashSet();
			List treeModules=new ArrayList();
			for(Module module:privileges){
				if(0==(module.getPrivilegeType())){
					treeModules.add(module);
				}
				modules.add(module);
			}
			
			result_user.setCatalog_and_privileges(modules);
			//设置登录次数
			if(result_user.getLogincnt()!=null){
				result_user.setLogincnt(result_user.getLogincnt()+1);
			}else {
				result_user.setLogincnt(1);
			}
			//设置登录ip
			result_user.setHostip(ServletActionContext.getRequest().getRemoteAddr() + ":"
					+ String.valueOf(ServletActionContext.getRequest().getRemotePort()));
			//设置登录时间
			result_user.setLogintime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			
			request.getSession().setAttribute(ApplicationConstants.LOGONUSER, result_user);
			List sessionList = (List)request.getSession().getServletContext().getAttribute(ApplicationConstants.SESSIONID);
			if(sessionList == null){
				sessionList = new ArrayList();
				Collections.synchronizedList(sessionList);
			}else{//同一用户不能在同时重复登录
				Object[] sessionListCopy= sessionList.toArray();
				for(int i=0; i< sessionListCopy.length; i++){
					HttpSession sess = (HttpSession)sessionListCopy[i];
					try{
						User us = (User)sess.getAttribute(ApplicationConstants.LOGONUSER);
						if(result_user.getUserid().equals(us.getUserid()) && !(sess.getId().equals(request.getSession().getId()))){
							sessionList.remove(sess);
							sess.invalidate();
						}
					}catch(java.lang.IllegalStateException e){
						sessionList.remove(sess);
					}
				}
			}
			sessionList.add(request.getSession());
			request.getSession().getServletContext().setAttribute(ApplicationConstants.SESSIONID, sessionList);
			String ss = pro.read("session.timeout");
			if(ss == null || ss.equals("")){
				ss = "-1";
			}
			int timeOut = Integer.parseInt(ss);
			pro.close();
			request.getSession().setMaxInactiveInterval(timeOut);
			administrationService.editSystemUser_itransc(result_user);
			ret.put(ServiceReturn.FIELD1, treeModules);
			ret.put(ServiceReturn.FIELD2, map.get("projects"));
			//重新初始化部门内存信息
			ServletContext context = ServletActionContext.getServletContext();
			initService.setUnits(context);
			initService.setParameters(context);
	//		super.setActionresult(super.convertServiceReturnToJson(ret).toString());
			request.setAttribute("actionresult", super.convertServiceReturnToJson(ret).toString());
			return SUCCESS;
		}else{
			throw new AppException("登录失败");
		}
	}
	
	/***
	 * 手持用户登陆交易，
	 * 上第三方求证用户正确性，返回排队机号等待，初始化session对像
	 * 输入输出参数，由接口而定,
	 */
	public void mobileLogin() throws Exception{
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		String msgStr = request.getParameter("msg");
		JSONObject retJson = new JSONObject();
		if(msgStr == null || msgStr.equals("")){
			retJson.element("H_ret_status", "F");
			retJson.element("H_ret_code","ACO001");
			retJson.element("H_ret_desc",Constants.ERRORCODE_MSG.get("ACO001"));
		}else{
			JSONObject json = JSONObject.fromObject(msgStr);
			
			//密码md5加密
			MD5 md5 = new MD5();
			String password=json.getString("password");
			password=md5.getMD5String(password);
			json.remove("password");
			json.accumulate("password", password);
			//{"usercode":"75500301","password":"888888","hostip":"00:00:00:00:00:00","device_num":"3","trancode":"ibp.pub.u001.01","H_Node":"755003","H_Teller":"999999","sessionId":"F393650EF14AB0B3E55AEF756AA81882"}
			User user = new User();
			user.setChannelID("04");
			user.setUsercode(json.getString("usercode"));
			user.setUnitid(json.getString("branch"));
			try {
				retJson = workHandlerService.fechData(json.toString(),user);
				//retJson = workHandlerService.work(json.toString(),user);//数据交互
			} catch (Exception e) {
				logger.error(MobServeAction.class+"业务处理过程报错",e );
				retJson.element("H_ret_status", "F");
				retJson.element("H_ret_code","HOS015");
				retJson.element("H_ret_desc",Constants.ERRORCODE_MSG.get("HOS015")+e.getMessage());
			}
			String wrongCode=retJson.getString("H_ret_status");
			
			if(wrongCode.equals("S")){
				Map map = logonService.getLogonUser_teller(user);
				User result_user =(User)map.get("user");
				result_user.setChannelID("04");
				//---用户session添加---
				request.getSession().setAttribute(ApplicationConstants.LOGONUSER, result_user);
				List sessionList = (List)request.getSession().getServletContext().getAttribute(ApplicationConstants.SESSIONID_PD);
				if(sessionList == null){
					sessionList = new ArrayList();
				}else{//同一用户不能在同时重复登录
					Object[] sessionListCopy = sessionList.toArray();
					for(int i=0; i< sessionListCopy.length; i++){
						HttpSession sess = (HttpSession)sessionListCopy[i];
						try{
							User us = (User)sess.getAttribute(ApplicationConstants.LOGONUSER);
							if(result_user.getUserid().equals(us.getUserid()) && !(sess.getId().equals(request.getSession().getId()))){
								sessionList.remove(sess);
								sess.invalidate();
							}
						}catch(java.lang.IllegalStateException e){
							sessionList.remove(sess);
						}
					}
				}
				sessionList.add(request.getSession());
				request.getSession().getServletContext().setAttribute(ApplicationConstants.SESSIONID_PD, sessionList);
				//---用户session添加结束---
			}
			PrintWriter pw = super.getResponse().getWriter();
			pw.print(retJson);
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 手持用户登出
	 */
	public void mobileExit() throws Exception{
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject retJson = new JSONObject();
		
		String msgStr = request.getParameter("msg");
		if(msgStr == null || msgStr.equals("")){
			retJson.element("H_ret_status", "F");
			retJson.element("H_ret_code","HOS014");
			retJson.element("H_ret_desc",Constants.ERRORCODE_MSG.get("HOS014"));
		}else{
			JSONObject json = JSONObject.fromObject(msgStr);
			User user = new User();
			user.setChannelID("04");
			user.setUsercode(json.getString("usercode"));
			user.setUnitid(json.getString("branch"));
			try {
				retJson = workHandlerService.fechData(msgStr,getLogonUser(false));
			} catch (Exception e) {
				logger.error(MobServeAction.class+"业务处理过程报错",e );
				retJson.element("H_ret_status", "F");
				retJson.element("H_ret_code","HOS015");
				retJson.element("H_ret_desc",Constants.ERRORCODE_MSG.get("HOS015")+e.getMessage());
			}
		}
		
		String wrongCode=retJson.getString("H_ret_status");
		
		if(wrongCode.equals("S")){
			//清理用户session
//			HttpSession session = request.getSession();        
//	        String sessionId=session.getId();
//	    	List sessionList = (List)session.getServletContext().getAttribute(ApplicationConstants.SESSIONID);
//	    	sessionList.remove(sessionId);
//			session.invalidate();
			//清理设备初始化session
//			Object padSessionId = JSONObject.fromObject(msgStr).get("sessionId");
//			Map<String, HttpSession> sessionMap = (Map<String, HttpSession>)session.getServletContext().getAttribute(ApplicationConstants.SESSIONMAP);
//			if(sessionMap.containsKey(padSessionId)){
//				HttpSession sess = sessionMap.remove(padSessionId);
//				sess.invalidate();
//			}
		}
 		//super.setActionresult(returnJson.toString()); 
 		PrintWriter pw = super.getResponse().getWriter();
		pw.print(retJson);
		pw.flush();
		pw.close();
	}
	/**
	 * 柜面登录(tellerLogon)
	 * @return 
	 * @return
	 * @throws Exception
	 */
	public String tellerLogon() throws Exception
	{
		/**
		 * 得到url，获取里面的参数，发送到AFA后台进行验证，返回结果
		 */
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		
		HttpServletRequest req = ServletActionContext.getRequest();//获取request域
		User user = new User();
		//得到url里的参数
		String userNo = req.getParameter("userNo");
		String tokenNo = req.getParameter("tokenNo");
		String teller = req.getParameter("tlrNo");
		String branch = req.getParameter("brNo");
		//添加用户（user）信息，若用户信息为空，会报空指针异常
		user.setUnitid(branch);
		user.setUsercode(teller);
		user.setHostip(request.getRemoteAddr() + ":"
				+ String.valueOf(request.getRemotePort()));
		//发送报文到后台afa交易
		//初始化natp
		cona.reInit();
		cona.setBMSHeader("ibp.bms.b002.01", user);
		cona.set("userNo", userNo);
		cona.set("tokenNo", tokenNo);
		cona.set("teller", teller);
		cona.set("branch", branch);
		cona.exchange();
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		pro.getFile("/conf.properties");
		Map map = logonService.getLogonUser_teller(user);
		User result_user =(User)map.get("user");
		
		//加载机构树
		List<Module> privileges = (List<Module>)map.get("privileges");
		Set modules = new HashSet();
		List treeModules=new ArrayList();
		for(Module module:privileges){
			if(0==(module.getPrivilegeType())){
				treeModules.add(module);
			}
			modules.add(module);
		}
		
		result_user.setCatalog_and_privileges(modules);
		//设置登录次数
		if(result_user.getLogincnt()!=null)
		{
			result_user.setLogincnt(result_user.getLogincnt()+1);
		}else {
			result_user.setLogincnt(1);
		}
		//设置登录ip
		result_user.setHostip(ServletActionContext.getRequest().getRemoteAddr() + ":"
				+ String.valueOf(ServletActionContext.getRequest().getRemotePort()));
		//设置登录时间
		result_user.setLogintime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		request.getSession().setAttribute(ApplicationConstants.LOGONUSER, result_user);
		List sessionList = (List)request.getSession().getServletContext().getAttribute(ApplicationConstants.SESSIONID);
		if(sessionList == null)
		{
			sessionList = new ArrayList();
			Collections.synchronizedList(sessionList);
		}else{
			//同一用户不能在同时重复登录
			Object[] sessionListCopy = sessionList.toArray();
			for(int i = 0; i < sessionListCopy.length; i++)
			{
				HttpSession sess = (HttpSession)sessionListCopy[i];
				try
				{
					User us = (User)sess.getAttribute(ApplicationConstants.LOGONUSER);
					if(result_user.getUserid().equals(us.getUserid()) && !(sess.getId().equals(request.getSession().getId())))
					{
						sessionList.remove(sess);
						sess.invalidate();
					}
				}catch(java.lang.IllegalStateException e)
				{
					sessionList.remove(sess);
				}
			}
		}
		sessionList.add(request.getSession());
		request.getSession().getServletContext().setAttribute(ApplicationConstants.SESSIONID, sessionList);
		String ss = pro.read("session.timeout");
		if(ss == null || ss.equals("")){
			ss = "-1";
		}
		int timeOut = Integer.parseInt(ss);
		pro.close();
		request.getSession().setMaxInactiveInterval(timeOut);
		administrationService.editSystemUser_itransc(result_user);
		ret.put(ServiceReturn.FIELD1, treeModules);
		ret.put(ServiceReturn.FIELD2, map.get("projects"));
		//重新初始化部门内存信息
		ServletContext context = ServletActionContext.getServletContext();
		initService.setUnits(context);
		initService.setParameters(context);
		request.setAttribute("actionresult", super.convertServiceReturnToJson(ret).toString());
		return SUCCESS;
	}
	public String checkout() throws Exception {
		return AJAX_SUCCESS;
	}
	
	public String redirect() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		//String b = (String) request.getParameter("userinfo");
		request.setAttribute("actionresult", this.userinfo);
		return SUCCESS;
	}
	public String redirectToChangePwd() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		//String b = (String) request.getParameter("userinfo");
		request.setAttribute("actionresult", this.userinfo);
		return "systemuserpass";
	}

	public void setInitService(IStartupObject initService) {
		this.initService = initService;
	}

	public WorkHandlerService getWorkHandlerService() {
		return workHandlerService;
	}

	public void setWorkHandlerService(WorkHandlerService workHandlerService) {
		this.workHandlerService = workHandlerService;
	}
	public ABTComunicateNatp getCona() 
	{
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) 
	{
		this.cona = cona;
	}
}
