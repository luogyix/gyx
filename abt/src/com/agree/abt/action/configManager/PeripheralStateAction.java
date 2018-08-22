package com.agree.abt.action.configManager;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.confManager.BtDevPeripheralState;
import com.agree.abt.service.confmanager.IPeripheralStateService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;

/**
 * 设备外设状态
 * @classname:
 * @author wangguoqing
 * @company 赞同科技
 * 2015-4-3
 */
@SuppressWarnings({ "unchecked" })
public class PeripheralStateAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private IPeripheralStateService peripheralStateService;

	
	/**
	 *加载主页面
	 * @return
	 * @throws Exception
	 */
	public String loadPage() throws Exception{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject retObj = super.convertServiceReturnToJson(sRet); 
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}

	/**
	 * 查询外设状态
	 * @return
	 * @throws Exception
	 */
	public String queryPeripheralState() throws Exception {
		JSONObject obj = JSONObject.fromObject(super.getJsonString());//获取页面传值
		Page pageInfo = this.getPage(obj); //得到Page分页对象	
		List<BtDevPeripheralState> list = peripheralStateService.queryPeripheralState(obj,pageInfo);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 
	 * @Description: 添加外设状态信息
	 * @return String
	 * @throws Exception
	 */
	public String addPeripheralState() throws Exception {
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		BtDevPeripheralState devps = (BtDevPeripheralState)JSONObject.toBean(obj, BtDevPeripheralState.class);
		ServiceReturn ret = peripheralStateService.addPeripheralState(devps);
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 
	 * @Description: 修改外设状态信息
	 * @return String
	 * @throws Exception
	 */
	public String editPeripheralState() throws Exception {
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		BtDevPeripheralState devps = (BtDevPeripheralState)JSONObject.toBean(obj, BtDevPeripheralState.class);
		ServiceReturn ret = peripheralStateService.editPeripheralState(devps);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}

	/**
	 * @Description: 删除外设状态信息
	 * @return String
	 * @throws Exception
	 */
	public String delPeripheralState() throws Exception {
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(BtDevPeripheralState.class);
		List<BtDevPeripheralState> ids = (List<BtDevPeripheralState>) JSONArray.toCollection(jsonArray,config);
		ServiceReturn ret = this.peripheralStateService.delPeripheralState(ids);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	public IPeripheralStateService getPeripheralStateService() {
		return peripheralStateService;
	}
	public void setPeripheralStateService(
			IPeripheralStateService peripheralStateService) {
		this.peripheralStateService = peripheralStateService;
	}

	
}
