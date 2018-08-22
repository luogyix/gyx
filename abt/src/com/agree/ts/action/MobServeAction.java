package com.agree.ts.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.web.action.base.BaseAction;
import com.agree.ts.service.WorkHandlerService;
import com.agree.util.Constants;

/**
 * TODO
 * @author xuhc
 * @date Nov 14, 2013 10:00:46 AM
 * @copyright (c) by 赞同科技 2013
 */
public class MobServeAction extends BaseAction {
		
	private static final long serialVersionUID = 1L;
	private WorkHandlerService workHandlerService;
	private static final Logger logger = LoggerFactory.getLogger(MobServeAction.class);
	
	/**
	 * 主流程处理手持设备发送的信息，返回处理结果
	 * @return json数据
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public String receiveJson() throws UnsupportedEncodingException, IOException{
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject retJson = new JSONObject();
		
		String msgStr = request.getParameter("msg");
		if(msgStr == null || msgStr.equals("")){
			retJson.element("H_ret_status", "F");
			retJson.element("H_ret_code","HOS014");
			retJson.element("H_ret_desc",Constants.ERRORCODE_MSG.get("HOS014"));
		}else{
//			if(checkSession(msgStr)){
				try {
					retJson = workHandlerService.fechData(msgStr,getLogonUser(false));
				} catch (Exception e) {
					logger.error("业务处理过程报错",e );
					retJson.element("H_ret_status", "F");
					retJson.element("H_ret_code","HOS015");
					retJson.element("H_ret_desc",Constants.ERRORCODE_MSG.get("HOS015")+e.getMessage());
				}
		}
		PrintWriter pw = super.getResponse().getWriter();
		pw.print(retJson);
		pw.flush();
		pw.close();
		return null;
	}

	
	/**** setter getter ****/
	public WorkHandlerService getWorkHandlerService() {
		return this.workHandlerService;
	}

	public void setWorkHandlerService(WorkHandlerService workHandlerService) {
		this.workHandlerService = workHandlerService;
	}
	
}
