package com.agree.framework.web.action.welcome;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
@SuppressWarnings({"rawtypes"})
public class ExitSystemAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 public String exit() throws Exception{
	 		JSONObject jsonObj = super.getInputJsonObject();
	 		String name = jsonObj.getString("name");
	 		if("exit".equals(name)){
	 			HttpServletRequest request = super.getRequest();
	 			HttpSession session = request.getSession();        
	 	        String sessionId=session.getId();
	 	    	List sessionList = (List)session.getServletContext().getAttribute(ApplicationConstants.SESSIONID);
	 	    	sessionList.remove(sessionId);
	 			session.invalidate();
	 		}
			ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
	 		sRet.put(ServiceReturn.FIELD2, true);
	 		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
	 		super.setActionresult(returnJson.toString()); 
	 		return AJAX_SUCCESS;		
	       }
}
