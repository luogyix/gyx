package com.agree.abt.action.devmanager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.service.devmanager.IDeviceInfoService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;



/**
 * 设备信息配置Action
 */
public class DeviceInfoAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private IDeviceInfoService deviceInfoService;

	/**
	 * 加载主页面
	 * @return
	 * @throws Exception
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 查询设备信息
	 * @return
	 * @throws Exception
	 */
	public String queryDeviceInfo() throws Exception {
		//HttpServletRequest req = ServletActionContext.getRequest();
		//User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		//List<Map> list = new ArrayList<Map>();
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, null, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 查询设备信息
	 * @return
	 * @throws Exception
	 */
	public String queryDeviceInfoPage() throws Exception {
		//HttpServletRequest req = ServletActionContext.getRequest();
		//User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		Page pageInfo = this.getPage(obj);
		//List<Map> list = new ArrayList<Map>();
		
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, null, ret);
		return AJAX_SUCCESS;
	}
	
	
	
	/**
	 * 添加设备信息
	 * @return String
	 * @throws Exception
	 */
	public String addDeviceInfo() throws Exception {
		//HttpServletRequest req = ServletActionContext.getRequest();
		//User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//String inputJsonStr = super.getJsonString();
		//JSONObject obj = JSONObject.fromObject(inputJsonStr);
		ServiceReturn tet=new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 修改设备信息
	 * @return String
	 * @throws Exception
	 */
	public String editDeviceInfo() throws Exception {
		//HttpServletRequest req = ServletActionContext.getRequest();
		//User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//String inputJsonStr = super.getJsonString();
		//JSONObject obj = JSONObject.fromObject(inputJsonStr);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 删除设备信息
	 * @return String
	 * @throws Exception
	 */

	public String delDeviceInfo() throws Exception {
		//HttpServletRequest req = ServletActionContext.getRequest();
		//User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
//		JsonConfig config = new JsonConfig();
//		config.setArrayMode(JsonConfig.MODE_LIST);
//		config.setCollectionType(List.class);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = JSONObject.fromObject(jsonArray.getString(i));
			/*Object obj[] = */jsonObj.values().toArray();
			//实现
		}
	
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}

	public IDeviceInfoService getDeviceInfoService() {
		return deviceInfoService;
	}

	public void setDeviceInfoService(IDeviceInfoService deviceInfoService) {
		this.deviceInfoService = deviceInfoService;
	}
}
