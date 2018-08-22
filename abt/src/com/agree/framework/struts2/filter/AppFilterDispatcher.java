package com.agree.framework.struts2.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.struts2.webserver.IStartupObject;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;
/**
 * 
 * @ClassName: AppFilterDispatcher 
 * @Description: 系统应用过滤器，调用初始化启动类Startup
 * @company agree   
 * @author haoruibing   
 * @date 2011-7-29 下午02:54:12 
 *
 */
public class AppFilterDispatcher extends StrutsPrepareAndExecuteFilter {
	private static Logger logger = LoggerFactory.getLogger(AppFilterDispatcher.class);
	private IStartupObject startupObject = null;
	/**
	 * @Description:系统初始化方法
	 * @param ：FilterConfig过滤器配置
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		String app_name = filterConfig.getInitParameter("AppStartupOjbectName");
		String sys_name = filterConfig.getInitParameter("SysStartupOjbectName");
		//获取Spring上下文
		WebApplicationContext springAppContext = WebApplicationContextUtils
				.getWebApplicationContext(filterConfig.getServletContext());
		//通过Spring上下文获取启动对象
		startupObject = (IStartupObject) springAppContext
				.getBean(sys_name);
		if(startupObject != null){
			startupObject.initializeContextVariables(filterConfig.getServletContext());//启动加载系统参数
		}
		//调用应用的初始化方法
		if(app_name != null){
			IStartupObject appStartupObject = (IStartupObject) springAppContext.getBean(app_name);
			try {
				appStartupObject.initApplicationVariables(filterConfig.getServletContext());//启动加载应用参数
			} catch (Exception e) {
				 logger.error(e.getMessage(),e);
			}
		}
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain fileter) throws IOException, ServletException {
		long starttime = System.currentTimeMillis();
		UUID uuid = UUID.randomUUID();
		MDC.put(IDictABT.MSGID, uuid.toString());
		String url = "";
		if(request instanceof HttpServletRequest)
		{
			url = ((HttpServletRequest)request).getRequestURL().toString();
			Object o = ((HttpServletRequest) request).getSession().getAttribute(ApplicationConstants.LOGONUSER);
			if(o instanceof User)
			{
				User u = (User)o;
				if(u.getUnitid()!=null && !u.getUnitid().equals(""))
				{
					MDC.put(IDictABT.BRANCH, u.getUnitid());
				}
			}
			
		}
		super.doFilter(request, response, fileter);
		logger.info("url: " +url + ",service.timing：" + (System.currentTimeMillis() - starttime) +"ms");
		MDC.remove(IDictABT.MSGID);
		MDC.remove(IDictABT.BRANCH);
	}
	@Override
	public void destroy() {
		super.destroy();
		startupObject.destory();
	}
}
