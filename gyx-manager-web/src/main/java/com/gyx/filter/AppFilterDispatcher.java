package com.gyx.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.GenericFilterBean;

import com.gyx.administration.User;

public class AppFilterDispatcher extends GenericFilterBean {
	private static Logger logger = LoggerFactory.getLogger(AppFilterDispatcher.class);
	//启动系统时就被调用
	public void ini(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);

	}

	//对请求或响应进行操作
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		long starttime = System.currentTimeMillis();
		String url = "";
		if(request instanceof HttpServletRequest){
			url = ((HttpServletRequest) request).getRequestURI();
			Object obj= ((HttpServletRequest) request).getSession().getAttribute("loginUser");
			if (obj instanceof User) {
				User user = (User) obj;
				if(user.getUnitid() != null && !user.getUnitid().equals("")){
					MDC.put("branch", user.getUnitid());
				}
			}
		}
		logger.info("url: " +url + "，此过滤器处理此请求花费：" + (System.currentTimeMillis() - starttime) +"ms");
		MDC.remove("branch");
		chain.doFilter(request, response);
	}
	
	//停止系统时调用
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}
	
	
}
