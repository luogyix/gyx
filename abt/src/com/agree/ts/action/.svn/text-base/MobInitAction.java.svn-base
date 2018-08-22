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
import com.agree.framework.web.form.administration.User;
import com.agree.ts.service.WorkHandlerService;
import com.agree.util.Constants;

/**
 * TODO
 * @author xhc
 * @E-mail xuhc@agree.com.cn
 * @date Mar 12, 2014 2:02:45 PM
 * @copyright (c) by 赞同科技 2014
 */
public class MobInitAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private WorkHandlerService workHandlerService;
	private static final Logger logger = LoggerFactory.getLogger(MobInitAction.class);
	
	/**
	 * 获得客户端ip
	 * @param request
	 * @return ip地址
	 */
	private String getClientIp(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
	}
	
	/**
	 * 设备初始化处理
	 * @return json数据
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public String receiveJson()  throws UnsupportedEncodingException, IOException{
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
			JSONObject recvJson = JSONObject.fromObject(msgStr);
			Object tcode = recvJson.get("trancode");
			if(tcode!=null && tcode.equals(Constants.InitInterface)){
				try {
					String ip = getClientIp(request);
					recvJson.element("mob_ip", ip);
					User user = new User();
					user.setChannelID("04");
					user.setUnitid(recvJson.getString("branch"));
					retJson = workHandlerService.fechData(recvJson.toString(),user);
					String sessionId = request.getSession().getId();
					retJson.element("sessionId", sessionId);
//					this.saveSession();
				} catch (Exception e) {
					logger.error("业务处理过程报错",e);
					retJson.element("H_ret_status", "F");
					retJson.element("H_ret_code","HOS015");
					retJson.element("H_ret_desc",Constants.ERRORCODE_MSG.get("HOS015")+e.getMessage());
				}
			}else{
				retJson.element("H_ret_status", "F");
				retJson.element("H_ret_code","HOS016");
				retJson.element("H_ret_desc",Constants.ERRORCODE_MSG.get("HOS016"));
			}
		}
		PrintWriter pw = super.getResponse().getWriter();
		pw.print(retJson);
		pw.flush();
		pw.close();
		return null;
	}

	public WorkHandlerService getWorkHandlerService() {
		return this.workHandlerService;
	}

	public void setWorkHandlerService(WorkHandlerService workHandlerService) {
		this.workHandlerService = workHandlerService;
	}
	
	
	
}
