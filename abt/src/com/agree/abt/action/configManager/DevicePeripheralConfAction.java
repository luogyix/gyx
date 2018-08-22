package com.agree.abt.action.configManager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.confManager.BtDevDevicePeripheralState;
import com.agree.abt.service.confmanager.IDevicePeripheralState;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
/**
 * 设备对应外设状态配置
 * @author linyuedong
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class DevicePeripheralConfAction extends BaseAction {
	private static final long serialVersionUID = -4201750470285776923L;
	private IDevicePeripheralState devicePeripheralState;
	
	public IDevicePeripheralState getDevicePeripheralState() {
		return devicePeripheralState;
	}

	public void setDevicePeripheralState(
			IDevicePeripheralState devicePeripheralState) {
		this.devicePeripheralState = devicePeripheralState;
	}

	/**
	 * 加载主页面
	 * @return
	 * @throws Exception
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	/**
	 * 根据设备类型查询外设类型
	 * @throws Exception
	 */
	public void queryPeripheralType() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
    	String device_type = req.getParameter("devicetype");
    	List list = devicePeripheralState.queryPeripheralType(device_type);
    	ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	/**
	 * 根据外设类型查询外设状态
	 * @throws Exception
	 */
	public void queryPeripheralState() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
    	String p_type_key = req.getParameter("p_type_key");
    	List list = devicePeripheralState.queryPeripheralStatus(p_type_key);
    	ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD2, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	/**
	 * 分页查询设备对应外设类型配置
	 * @return
	 * @throws Exception
	 */
	public String queryDevicePeripheralStatePage() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputString);
		Page pageinfo = this.getPage(obj); //得到Page分页对象
		String branch = user.getUnitid();  
		List<BtDevDevicePeripheralState> list = devicePeripheralState.queryDevicePeripheralState(branch,pageinfo);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		super.setActionResult(pageinfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 添加配置
	 * @return
	 * @throws Exception
	 */
	public String addDevicePeripheralState() throws Exception{
		String inputString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputString);
		ServiceReturn ret = devicePeripheralState.addDevicePeripheralState(obj);
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	/**
	 * 修改配置
	 * @return
	 * @throws Exception
	 */
	public String editDevicePeripheralState() throws Exception{
		String inputString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputString);
		ServiceReturn ret = devicePeripheralState.editDevicePeripheralState(obj);
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	/**
	 * 删除配置
	 * @return
	 * @throws Exception
	 */
	public String delDevicePeripheralState() throws Exception{
		String inputString = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputString);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(BtDevDevicePeripheralState.class);
		List<BtDevDevicePeripheralState> ids = (List<BtDevDevicePeripheralState>) JSONArray.toCollection(jsonArray,config);
		ServiceReturn ret = devicePeripheralState.delDevicePeripheralState(ids);
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
}
