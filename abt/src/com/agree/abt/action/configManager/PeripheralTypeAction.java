package com.agree.abt.action.configManager;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.confManager.BtDevPeripheralType;
import com.agree.abt.service.confmanager.IPeripheralTypeService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;

/**
 * 设备外设类型
 * @classname:PeripheralTypeAcyion.java
 * @author wangguoqing
 * @company 赞同科技
 * 2015-3-30
 */
@SuppressWarnings({ "unchecked" })
public class PeripheralTypeAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private IPeripheralTypeService peripheralTypeService;
	
	/**
	 *加载主页面
	 * @return
	 * @throws Exception
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		//sRet.put(ServiceReturn.FIELD2,super.getAllUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet); 
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 查询外设类型
	 * @return
	 * @throws Exception
	 */
	public String queryPeripheralType() throws Exception {
		String str = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(str);//获取页面传值
		Page pageInfo = this.getPage(obj); //得到Page分页对象	
		List<BtDevPeripheralType> list = peripheralTypeService.queryPeripheralType(obj,pageInfo);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 查询简单外设类型
	 * @return
	 * @throws Exception
	 */
	public void queryPeripheralTypeSmall() throws Exception {
		//HttpServletRequest req = ServletActionContext.getRequest();
		//Page pageInfo = this.getPage();
		List<BtDevPeripheralType> list = peripheralTypeService.queryPeripheralType(null,null);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	/**
	 * 
	 * @Description: 添加外设类型信息
	 * @return String
	 * @throws Exception
	 */
	public String addPeripheralType() throws Exception {
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		BtDevPeripheralType devpt = (BtDevPeripheralType)JSONObject.toBean(obj, BtDevPeripheralType.class);
		ServiceReturn ret = peripheralTypeService.addPeripheralType(devpt);
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 
	 * @Description: 修改外设信息
	 * @return String
	 * @throws Exception
	 */
	public String editPeripheralType() throws Exception {
		String json = getJsonString();
		ServiceReturn ret = peripheralTypeService.editPeripheralType(json);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}

	/**
	 * @Description: 删除外设类型信息
	 * @return String
	 * @throws Exception
	 */
	public String delPeripheralType() throws Exception {
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(BtDevPeripheralType.class);
		List<BtDevPeripheralType> ids = (List<BtDevPeripheralType>) JSONArray.toCollection(jsonArray,config);
		ServiceReturn ret = this.peripheralTypeService.delPeripheralType(ids);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}

	public IPeripheralTypeService getPeripheralTypeService() {
		return peripheralTypeService;
	}

	public void setPeripheralTypeService(IPeripheralTypeService peripheralTypeService) {
		this.peripheralTypeService = peripheralTypeService;
	}

}
