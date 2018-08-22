package com.agree.abt.action.configManager;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.confManager.BtSysBranchNeig;
import com.agree.abt.service.confmanager.IAroundBranchService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;

/**
 * 周边网点配置
 * @company 赞同科技
 * @author XiWang
 * @date 2015-1-14
 */
@SuppressWarnings({"unchecked"})
public class AroundBranchAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IAroundBranchService aroundBranchService;
	
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD2,super.getUnitTreeList());
		sRet.put(ServiceReturn.FIELD3, BaseAction.getAllUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 查询周边网点配置
	 * @return
	 * @throws Exception 
	 */
	public String queryAroundBranch() throws Exception{
		JSONObject obj = JSONObject.fromObject(super.getJsonString());//获取页面传值
		Page pageInfo = this.getPage(obj); //得到Page分页对象	
		List<BtSysBranchNeig> list = aroundBranchService.queryAroundBranch(obj,pageInfo);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
//		List<BtSysBranchNeig> list = (List<BtSysBranchNeig>)ret.get(ServiceReturn.FIELD1);
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 添加周边网点配置
	 * @return
	 * @throws Exception 
	 */
	public String addAroundBranch() throws Exception{
//		HttpServletRequest request = ServletActionContext.getRequest();
//		request.setCharacterEncoding("utf-8");
//		String strJson = request.getParameter("strJson");
//		ServiceReturn ret = aroundBranchService.addAroundBranch(strJson);
//		String string = super.convertServiceReturnToJson(ret).toString();
//		super.setActionresult(string);
//		return AJAX_SUCCESS;
		
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		BtSysBranchNeig neig = (BtSysBranchNeig)JSONObject.toBean(obj, BtSysBranchNeig.class);
		ServiceReturn ret = aroundBranchService.addAroundBranch(neig);
		
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 修改周边网点配置
	 * @return
	 * @throws Exception 
	 */
	public String editAroundBranch() throws Exception{
//		HttpServletRequest request = ServletActionContext.getRequest();
//		request.setCharacterEncoding("utf-8");
//		String strJson = request.getParameter("strJson");
//		ServiceReturn ret = aroundBranchService.addAroundBranch(strJson);
//		String string = super.convertServiceReturnToJson(ret).toString();
//		super.setActionresult(string);
//		return AJAX_SUCCESS;
		
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		BtSysBranchNeig neig = (BtSysBranchNeig)JSONObject.toBean(obj, BtSysBranchNeig.class);
		ServiceReturn ret = aroundBranchService.editAroundBranch(neig);
		
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 通过ID删除周边网点配置
	 * @return
	 * @throws Exception 
	 */
	public String delAroundBranchById() throws Exception{
//		HttpServletRequest req = ServletActionContext.getRequest();
//		String inputJsonStr = super.getJsonString();
//		JSONObject obj = JSONObject.fromObject(inputJsonStr);
//		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
//		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
//		String str = super.convertServiceReturnToJson(ret).toString();
//		super.setActionresult(str);
//		return AJAX_SUCCESS;
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(BtSysBranchNeig.class);
		List<BtSysBranchNeig> ids = (List<BtSysBranchNeig>) JSONArray.toCollection(jsonArray,config);
		//User login = super.getLogonUser(false);
		
		ServiceReturn ret = this.aroundBranchService.delAroundBranch(ids);//删除临近网点操作
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}

	public IAroundBranchService getAroundBranchService() {
		return aroundBranchService;
	}

	public void setAroundBranchService(IAroundBranchService aroundBranchService) {
		this.aroundBranchService = aroundBranchService;
	}
}
