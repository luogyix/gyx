package com.agree.framework.web.action.administration;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.service.administration.IAdministrationService;
import com.agree.util.ActionUtil;
/**
 * 
 * @ClassName: SystemLogAction 
 * @Description: 调用service，获取查询信息
 * @company agree   
 * @author dhs  
 * @date 2011-10-14 下午03:39:21 
 *
 */
@SuppressWarnings("rawtypes")
public class SystemLogAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IAdministrationService 	administrationService;
	
	public void setAdministrationService(
			IAdministrationService administrationService) {
		this.administrationService = administrationService;
	}

	public String loadPage() throws Exception{
		//User user=super.getLogonUser(true);
		//ServiceReturn sRet = this.administrationService.getuser(user);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	public String querylog() throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Page pageInfo = this.getPage(jsonObj);//得到Page分页对象
		List units=super.getMyUnits();//获得当前用户及其下属部门ID集合
		//jsonObj.put("units","%|"+ units.getUnitid()+"|%");
		jsonObj.put("units",units);
		ActionUtil.setDimPara(jsonObj, "username");//设置username的值为%username%
		ActionUtil.setDimPara(jsonObj, "operation");
		List list = administrationService.findLog(jsonObj, pageInfo);//查询数据
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}	
	/**
	 * @Title: excelHvpspaymentInfo 
	 * @Description: 下载Excel调用方法
	 * @param @throws Exception    参数 
	 * @throws
	 */
	public void excelLogInfo() throws Exception{
		String jsonString = ServletActionContext.getRequest().getParameter("querycondition_str");
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		List units=super.getMyUnits();//获得当前用户及其下属部门ID集合
		jsonObj.put("units", units);
		ActionUtil.setDimPara(jsonObj, "username");//设置username的值为%username%
		ActionUtil.setDimPara(jsonObj, "operation");
		List list = this.administrationService.findLogDoExcel(jsonObj); //查询数据
		String path="bsmsMnguseroprlog.xls";		
		String file = "操作记录查询";
		super.doExcel(path, list, jsonObj, file);
		
	}
	
}
