package com.gyx.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
 * SpringMvc获得Request对象<br>
 * @author 高艺祥<br>
 * 创建时间：2018-02-05
 *
 */
public class GetHttpServletRequest {
	/**
	 * 获得Request对象
	 * @return Request
	 */
	public static HttpServletRequest getRequest(){
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return requestAttributes.getRequest();
	}
}
