package com.agree.ts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.web.action.base.BaseAction;
import com.agree.ts.service.WorkHandlerService;
import com.agree.util.Constants;

/**
 * 手持排队机服务action入口
 * @ClassName: QMServerAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2014-7-10
 */
public class QMServerAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private WorkHandlerService workHandlerService;
	
	private static final Logger logger = LoggerFactory.getLogger(QMServerAction.class);
	
	/**
	 * backbone接入
	 * @throws Exception 
	 * */
	public String receiveJson2() throws Exception{
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject retJson = new JSONObject();
		String msgStr = request.getParameter("msg");
		msgStr=(java.net.URLDecoder.decode(msgStr,"UTF-8"));
		if(msgStr == null || msgStr.equals("")){
			retJson.element("H_ret_status", "F");
			retJson.element("H_ret_code","HOS014");
			retJson.element("H_ret_desc",Constants.ERRORCODE_MSG.get("HOS014"));
		}else{
			try {
				retJson = workHandlerService.fechData(msgStr,getLogonUser(false));
			} catch (Exception e) {
				logger.error("业务处理过程报错",e );
				retJson.element("H_ret_status", "F");
				retJson.element("H_ret_code","HOS015");
				retJson.element("H_ret_desc",Constants.ERRORCODE_MSG.get("HOS015")+e.getMessage());
			}
		}
		this.setActionresult(retJson.toString());
		return JSONP_SUCCESS;
	}
	
	/**** setter getter ****/
	public WorkHandlerService getWorkHandlerService() {
		return this.workHandlerService;
	}

	public void setWorkHandlerService(WorkHandlerService workHandlerService) {
		this.workHandlerService = workHandlerService;
	}
}
