package com.agree.abt.action.configManager;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.confManager.BtDevDevicePeripheralState;
import com.agree.abt.service.confmanager.IDevInfoPeripheralService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
@SuppressWarnings({"unchecked"})
public class DevInfoPeripheralAction extends BaseAction {
	//private Logger logger = Logger.getLogger(DeviceInfoAction.class);
	private static final long serialVersionUID = 1L;
	//private ABTComunicateNatp cona;

	
	private IDevInfoPeripheralService devInfoPeripheralService;
	public IDevInfoPeripheralService getDevInfoPeripheralService() {
		return devInfoPeripheralService;
	}
	public void setDevInfoPeripheralService(
			IDevInfoPeripheralService devInfoPeripheralService) {
		this.devInfoPeripheralService = devInfoPeripheralService;
	}
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
	 * 查询设备对应外设
	 * @return
	 * @throws Exception
	 */
	public String queryPeripheralInfo() throws Exception{
		JSONObject obj = JSONObject.fromObject(super.getJsonString());
		String branch = obj.getString("branch");
		String devicetype = obj.getString("devicetype");
		String device_num = obj.getString("device_num");
		Page pageinfo = this.getPage(obj); //得到Page分页对象
		List<BtDevDevicePeripheralState> list = devInfoPeripheralService.queryDevPeripheral(branch,devicetype,device_num,pageinfo);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		super.setActionResult(pageinfo, list, ret);
		return AJAX_SUCCESS;
	}
}