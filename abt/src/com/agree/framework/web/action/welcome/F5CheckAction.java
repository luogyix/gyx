package com.agree.framework.web.action.welcome;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.service.welcome.IF5CheckService;

/**
 * 
 * @ClassName:F5CheckAction.java 
 * @company 赞同科技
 * @author 王明哲
 * @date 2017-1-16下午1:46:45
 */
public class F5CheckAction extends BaseAction
{

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(F5CheckAction.class);//日志
	private IF5CheckService f5CheckService;
	public IF5CheckService getF5CheckService() 
	{
		return f5CheckService;
	}
	public void setF5CheckService(IF5CheckService f5CheckService) 
	{
		this.f5CheckService = f5CheckService;
	}
	public void check() throws Exception 
	{
		Object time = this.f5CheckService.querySqlTime();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		logger.debug(SUCCESS + " worktime:" + time.toString());
		Writer writer = response.getWriter();
		writer.write("success");
		writer.flush();
		/*ServletOutputStream out = response.getOutputStream();
		out.flush();*/
	}
}
