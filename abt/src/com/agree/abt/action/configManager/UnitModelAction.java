package com.agree.abt.action.configManager;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.Unit;

/**
 * 机构参数模板配置
 * @author XiWang
 */
public class UnitModelAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	
	private ABTComunicateNatp cona;
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		List<Unit> list =  super.getUnitTreeList();
		sRet.put(ServiceReturn.FIELD1,list);//获取部门集合
		sRet.put(ServiceReturn.FIELD2, super.getLogonUser(false));//获取登录用户信息
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 使用模板
	 * @return
	 * @throws Exception
	 */
	public String saveModel() throws Exception{
		
		//HttpServletRequest req = ServletActionContext.getRequest();
		//User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject.fromObject(inputJsonStr);
		Page pageInfo =new Page();
//		 
//		cona.setHeader("", user.getUnitid(), user.getUsercode(),"03");
//		cona.set("branch", user.getUnitid());
//		cona.set("modelno", obj.getString("modelno"));
//		HashMap<String, List<String>> map = (HashMap)cona.exchange();
//		String validate="";
//		validate=cona.validMap(map);
//		if(validate!=null&&validate.trim().length()>0){
//			throw new AppException(validate);
//		}
		ServiceReturn ret = new ServiceReturn(true, "");
		super.setActionResult(pageInfo, null, ret);
		return AJAX_SUCCESS;
	}
	
	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}

	public ABTComunicateNatp getCona() {
		return cona;
	}
}
