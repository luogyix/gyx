package com.agree.framework.struts2.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class WelcomeFileFilter implements Filter {
	public void destroy() {
		indexFile = null;
	}

	private String indexFile;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String contextPath = httpServletRequest.getContextPath();
		String requestURI = httpServletRequest.getRequestURI();
		if (contextPath.equalsIgnoreCase(requestURI)
				|| (contextPath + "/").equalsIgnoreCase(requestURI)) {
			String url = "";
			if (indexFile.startsWith("/")) {
				url = contextPath + indexFile;
			} else {
				url = contextPath + "/" + indexFile;
			}
			httpServletResponse.sendRedirect(url);
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		indexFile = "welcome";
		String x = arg0.getInitParameter("indexFile");
		if (StringUtils.isNotBlank(x)) {
			indexFile = x;
		}
	}

}
