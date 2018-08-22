package com.agree.abt.action.configManager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.abt.model.confManager.CompScreenDisplay;
import com.agree.abt.service.confmanager.ICompScreenDisplayService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

public class CompScreenDisplayAction extends BaseAction{
	private static final Logger logger = LoggerFactory.getLogger(CompScreenDisplayAction.class);
	private static final long serialVersionUID = 1L;
	private ICompScreenDisplayService compScreenDisplayService;
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {		
		return SUCCESS;
	}
	
	/**
	 * 查询综合屏信息
	 */
	public String queryCompScreen() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);		 
		Page pageInfo = new Page();
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		List<CompScreenDisplay> list = compScreenDisplayService.queryCompScreenInfo(user.getUnitid());
		try {
			super.setActionResult(pageInfo, list, ret);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("错误提示", e);
		}
		return AJAX_SUCCESS;
	}
	
	/**
	 * 分页查询综合屏信息
	 */
	public String queryCompScreen4Page() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Page pageInfo = this.getPage(jsonObj); 
		List<CompScreenDisplay> list = compScreenDisplayService.queryCompScreen4Page(jsonObj,pageInfo,user.getUnitid());
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 添加综合屏信息
	 */
	public String addCompScreen() throws Exception{
		String inputJsonStr = super.getJsonString();
		ServiceReturn ret = compScreenDisplayService.addCompScreenInfo(inputJsonStr);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 修改综合屏信息
	 */
	public String editCompScreen() throws Exception{
		String inputJsonStr = super.getJsonString();
		ServiceReturn ret = compScreenDisplayService.editCompScreenInfo(inputJsonStr);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	/**
	 * 删除综合屏信息
	 */
	public String delCompScreen() throws Exception{
		String inputJsonStr = super.getJsonString();
		ServiceReturn ret = compScreenDisplayService.delCompScreenInfo(inputJsonStr);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	

	public ICompScreenDisplayService getCompScreenDisplayService() {
		return compScreenDisplayService;
	}

	public void setCompScreenDisplayService(
			ICompScreenDisplayService compScreenDisplayService) {
		this.compScreenDisplayService = compScreenDisplayService;
	}
	
}
