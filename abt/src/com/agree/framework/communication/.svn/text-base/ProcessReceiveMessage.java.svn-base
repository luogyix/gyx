/**
 * 文件名: ProcessReceiveMessage.java
 * 创建时间: 2010-1-6 下午03:53:32
 * 命名空间: com.css.resoft.ctax.workflow
 */
package com.agree.framework.communication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 类说明:
 * 
 * @author haoruibing
 *
 */
public class ProcessReceiveMessage {
	
	private static final Logger logger = LoggerFactory.getLogger(ProcessReceiveMessage.class);
	private String serviceCode;
	private Message msg;
	
	public ProcessReceiveMessage(String serviceCode,Message msg) {
		this.msg=msg;
		this.serviceCode=serviceCode;
		
	}
	
	public Message process() throws Exception {
		Action action =null;
		Message returnMsg=null;
		try {
			 action = (Action) Class.forName(serviceCode + "Action")
					.newInstance();
			 returnMsg=action.excude(msg);
		} catch (Exception e) {
			logger.error(serviceCode+"Action不存在",e);
		}
		return returnMsg;
	}
	
}
