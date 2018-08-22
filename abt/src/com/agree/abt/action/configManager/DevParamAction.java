package com.agree.abt.action.configManager;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.confManager.BtDevParam;
import com.agree.abt.service.confmanager.IDevParam;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
/**
 * 设备参数Action
 * @author linyuedong
 */
@SuppressWarnings({"unchecked"})
public class DevParamAction extends BaseAction {
	private static final long serialVersionUID = 4183607367175033991L;
	private IDevParam devParam;

	public IDevParam getDevParam() {
		return devParam;
	}

	public void setDevParam(IDevParam devParam) {
		this.devParam = devParam;
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
	 * 分页查询自助设备参数
	 * @return
	 * @throws Exception
	 */
	public String queryDevParamPage() throws Exception{
		String inputString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputString);
		Page pageInfo = this.getPage(obj);
		List<BtDevParam> list = devParam.queryDevParamPage(pageInfo);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	public String queryDevParam() throws Exception{
		String inputString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputString);
		Page pageInfo = this.getPage(obj);
		List<BtDevParam> list = devParam.queryDevParam();
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	} 
	/**
	 * 添加自助设备参数
	 */
	public String addDevParam() throws Exception{
		String inputString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputString);
		ServiceReturn ret = devParam.addDevParam(obj);
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	/**
	 * 
	 * 修改自助设备参数
	 */
	public String editDevParam() throws Exception{
		String inputString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputString);
		ServiceReturn ret = devParam.editDevParam(obj);
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	/**
	 * 删除自助设备参数
	 */
	public String delDevParam() throws Exception{
		String inputString = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputString);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(BtDevParam.class);
		List<BtDevParam> btdev = (List<BtDevParam>) JSONArray.toCollection(jsonArray, config);
		ServiceReturn ret = devParam.delDevParam(btdev);
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
}
