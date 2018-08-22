package com.agree.framework.web.action.administration;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.administration.IAdministrationService;

public class UpdateLockAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;

	private IAdministrationService administrationService;
	public void setAdministrationService(
			IAdministrationService administrationService) {
		this.administrationService = administrationService;
	}

	public String loadPage() throws Exception{
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String updateLock() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		User user1 = (User)JSONObject.toBean(obj, User.class);
		//User user=(User)ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		User user2 = this.administrationService.getUserData_itransc(user1);
		if(user2 != null){
			ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
			List l = (List)ServletActionContext.getRequest().getSession().getServletContext().getAttribute(ApplicationConstants.LOGONUSERID);
			l.remove(user2.getUserid());
			ServletActionContext.getRequest().getSession().getServletContext().setAttribute(ApplicationConstants.LOGONUSERID, l);
			JSONObject returnJson = super.convertServiceReturnToJson(ret);
			super.setActionresult(returnJson.toString());
		}else{
			JSONObject robj = new JSONObject();
			robj.put("message", "您输入的用户名不正确,请重新输入！");
			robj.put("res", true);
			robj.put("result", true);
			super.setActionresult(robj.toString());
		}
		return AJAX_SUCCESS;
	}

}
