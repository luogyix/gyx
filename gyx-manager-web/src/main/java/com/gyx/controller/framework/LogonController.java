package com.gyx.controller.framework;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gyx.administration.User;
import com.gyx.controller.base.BaseController;
import com.gyx.controller.webserver.ApplicationConstants;
import com.gyx.exception.AppException;
import com.gyx.pubutil.PropertiesUtils;
import com.gyx.service.framework.welcome.ILogonService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/logon")
public class LogonController extends BaseController{
	
	private static final  Logger loger = LoggerFactory.getLogger(LogonController.class);
	PropertiesUtils pro = new PropertiesUtils();
	
	@Autowired
	private ILogonService logonService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/logon")
	public void logon(){
		pro.getFile("properties/conf.properties");
		String jsonString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(jsonString);
		User user = (User) JSONObject.toBean(obj, User.class);
		HttpServletRequest request = super.getRequest();
		HttpSession httpSession = request.getSession();
		User sessionUser = (User)httpSession.getAttribute(ApplicationConstants.LOGONUSER);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(sessionUser != null){
			if(user.getUsercode() == null){
				user.setUsercode(sessionUser.getUsercode());
				user.setLogintime(sdf.format(new Date()));
			}
		}
		Map<String,Object> map = null;
		//验证用户密码，获得用户数据及拥有菜单
		try {
			map = logonService.getLogonUser_itransc(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			loger.error(LogonController.class.getName()+"类，"+"logon方法抛出异常");
			new AppException("获取用户拥有菜单异常，请联系开发人员");
		}
		//设置用户ip及登陆时间
		User resultUser = (User) map.get("user");
		resultUser.setHostip(request.getLocalAddr()+":"+request.getLocalPort());
		resultUser.setLogintime(sdf.format(new Date()));
		
		httpSession.setAttribute(ApplicationConstants.LOGONUSER, resultUser);
		List sessionList = (List) httpSession.getServletContext().getAttribute(ApplicationConstants.SESSIONID);
		if(sessionList!=null && sessionList.size() > 0){
			Object[] sessionCopyArray = sessionList.toArray();
			for(int i = 0 ; i < sessionCopyArray.length ; i++){
				HttpSession sess = (HttpSession) sessionCopyArray[i];
				User slUser = (User) sess.getAttribute(ApplicationConstants.LOGONUSER);
				if(slUser.getUsercode().equals(resultUser.getUsercode()) && 
						sess.getId().equals(request.getSession().getId())){
					sessionList.remove(sess);
					sess.invalidate();
				}
			}
		}
		sessionList.add(httpSession);
		httpSession.getServletContext().setAttribute(ApplicationConstants.SESSIONID, sessionList);
		//从conf.properties中根据键获得值
		String ss = pro.read("session.timeout");
		if(ss == null || ss.equals("")){
			ss = "-1";
		}
		int timeOut = Integer.parseInt(ss);
		//设置session失效时间
		httpSession.setMaxInactiveInterval(timeOut);
	}

}
