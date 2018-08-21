package com.gyx.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class RequesetStreamFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ServletRequest requestWrapper = null;
		if(request instanceof HttpServletRequest){
			requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
		}
		if(requestWrapper == null){
			chain.doFilter(request, response);
		}else{
			chain.doFilter(requestWrapper, response);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
