package com.agree.framework.web.action.administration;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.administration.IAdministrationService;
import com.agree.framework.web.service.base.IPageQueryService;

public class UpdatePassRedoAction extends BaseAction{

	private static final long serialVersionUID = 1L;

	private IAdministrationService administrationService;
	private IPageQueryService      pageQueryService;
	public void setAdministrationService(
			IAdministrationService administrationService) {
		this.administrationService = administrationService;
	}
	public void setPageQueryService(IPageQueryService pageQueryService) {
		this.pageQueryService = pageQueryService;
	}

	public String loadPage() throws Exception{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		sRet.put(ServiceReturn.FIELD2, super.getLogonUser(true));
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute("actionresult", retObj.toString());
		return SUCCESS;
	}
	
	public String queryuser() throws Exception{
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		User user = (User)JSONObject.toBean(jsonObj, User.class);
		user.setLocale(super.getLocale().toString());
		ServiceReturn sRet = this.pageQueryService.queryPage(user, "SystemUser.selectSystemUsers", "SystemUser.selectSystemUsersCount");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		super.setActionresult(retObj.toString());
		return AJAX_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String passwordRedo() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(User.class);
		List<User> users = (List<User>) JSONArray.toCollection(jsonArray,config);
		ServiceReturn sRet = this.administrationService.passwordRedo_itransc(users);
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
}
