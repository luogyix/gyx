package com.agree.framework.struts2.interceptors;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.form.administration.BsmsMnguseroprlog;
import com.agree.framework.web.form.administration.Module;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.administration.ILogService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * 
 * @ClassName: ActionLoggingInterceptor 
 * @Description: 记录日志过滤器，检查该动作是否需要记录日志
 * @company agree   
 * @author haoruibing   
 * @date 2011-7-29 下午03:07:16 
 *
 */
public class ActionLoggingInterceptor extends AbstractInterceptor {

	
	private static final long serialVersionUID = 1L;
	
	private static final SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * @Description:过滤器方法,记录动作日志
	 * @param arg0
	 * @return String
	 * @throws Exception
	 */
	public String intercept(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub
		String result = arg0.invoke();
		User logonUser = (User)ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		if(logonUser != null){
			Set<Module> modules = logonUser.getCatalog_and_privileges();
			int flag = 0;
			String modulename = "";
			for(Module module:modules){//查看该用户是否有该动作权限以及是否记录该日志
				String method = ServletActionContext.getRequest().getRequestURL().toString();
				if(module.getModuleaction().equals(method)&& module.getLogflag().equals((short)1)){
					modulename = module.getModulename();
					flag++;
					break;
				}
			}
			if(flag > 0){//如果记录该日志......
				WebApplicationContext springAppContext = WebApplicationContextUtils
						.getWebApplicationContext(ServletActionContext.getServletContext());
				ILogService logService = (ILogService) springAppContext.getBean("LogService");
	
				BsmsMnguseroprlog useroprlog = new BsmsMnguseroprlog();
				String now = df.format(Calendar.getInstance().getTime());
				useroprlog.setUnitid(logonUser.getUnitid());
				useroprlog.setUnitname(logonUser.getUnit().getUnitname().replace("珠海华润银行", "").replace("股份有限公司", ""));
				useroprlog.setLogdate(now.substring(0, 8));
				useroprlog.setLogtime(now.substring(8, 14));
				useroprlog.setUsercode(logonUser.getUsercode());
				useroprlog.setUsername(logonUser.getUsername());
				useroprlog.setOperation(modulename);
				String ip = ServletActionContext.getRequest().getRemoteAddr() + ":"
						+ Integer.toString(ServletActionContext.getRequest().getRemotePort());
				useroprlog.setHostip(ip);
				logService.insertUserOprLog_itransc(useroprlog);
			}
		}
		
		return result;
	}

}
