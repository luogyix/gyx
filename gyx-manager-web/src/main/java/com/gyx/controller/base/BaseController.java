package com.gyx.controller.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gyx.util.GetHttpServletRequest;
/**
 * 控制层的基础类
 * @author 高艺祥<br>
 * 				最后修改日期：2017-12-08
 *
 */
@Component
public class BaseController{
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	/**
	 * 获得request中的数据，并转化为json格式
	 * @author 高艺祥<br>
	 * 				最后修改日期：2017-12-08
	 * @return JsonString
	 */
	public static String getJsonString(){
		HttpServletRequest request = GetHttpServletRequest.getRequest();
		Object reqResult = request.getAttribute("actiondetail");
		if(reqResult == null){
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getLocalizedMessage(),e);
			}
			BufferedReader reader = null;
			StringBuffer sb = new StringBuffer();
			
			try {
				reader=request.getReader();
				String str = "";
				while((str = reader.readLine()) != null){
					sb.append(str);
				}
			} catch (IOException e) {
				logger.error(e.getLocalizedMessage(), e);
			}
			return sb.toString();
		}else{
			return reqResult.toString();
		}
	}
	
	/**
	 * 获得Request对象
	 * @return Request
	 */
	public static HttpServletRequest getRequest(){
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return requestAttributes.getRequest();
	}

}
