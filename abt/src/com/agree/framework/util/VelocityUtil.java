/**
 * 文件名: TemplateUtil.java
 * 创建时间: 2009-12-3 下午04:57:38
 * 命名空间: com.css.resoft.ctax.util
 */
package com.agree.framework.util;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.communication.Message;
import com.agree.framework.exception.AppException;


/**
 * 类说明:
 * 
 * @author haoruibing
 *
 */
public class VelocityUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(VelocityUtil.class);
	private static VelocityEngine velocityEngine = new VelocityEngine(); 
	
	static {
		Properties properties = new Properties();
		properties.setProperty("file.resource.loader.path", VelocityUtil.class.getResource("/resources/pack").getFile());
		try {
			velocityEngine.init(properties);
		} catch(Exception e) {
			logger.error("加载Velocity错误!",e);
		}
	}
	
	public VelocityUtil() {
		// empty function
	}
	
	public static String merge(String template, Message msg, String encoding) {
		StringWriter result = new StringWriter();
		VelocityContext context = new VelocityContext();
		context.put("msg", msg);
		try {
			velocityEngine.mergeTemplate(template, encoding, context, result);
		} catch (ResourceNotFoundException e) {
			throw new AppException("无法组装报文，未发现报文模版文件！");
		} catch (ParseErrorException e) {
			throw new AppException("无法组装报文，解析报文模版错误！");
		} catch (Exception e) {
			throw new AppException("报文组包错误！");
		} 
		return result.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public static String merge(String template, Map msg, String encoding) {
		StringWriter result = new StringWriter();
		VelocityContext context = new VelocityContext();
		context.put("msg", msg);
		try {
			velocityEngine.mergeTemplate(template, encoding, context, result);
		} catch (ResourceNotFoundException e) {
			throw new AppException("无法组装报文，未发现报文模版文件！");
		} catch (ParseErrorException e) {
			throw new AppException("无法组装报文，解析报文模版错误！");
		} catch (Exception e) {
			throw new AppException("报文组包错误！");
		} 
		return result.toString();
	}
	
	public static String merge(String template, Message msg) {
		return merge(template, msg, "GB2312");
	}
}
