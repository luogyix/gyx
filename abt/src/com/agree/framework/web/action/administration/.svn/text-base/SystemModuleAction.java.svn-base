package com.agree.framework.web.action.administration;

import java.util.List;

import javax.servlet.ServletContext;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.IStartupObject;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.Module;
import com.agree.framework.web.service.administration.IAdministrationService;
import com.agree.util.ActionUtil;

/** 
 * @ClassName: SystemModuleAction 
 * @Description: TODO
 * @company agree   
 * @author haoruibing   
 * @date 2011-8-31 上午10:57:29 
 *  
 */ 
@SuppressWarnings("rawtypes")
public class SystemModuleAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private IAdministrationService administrationService;
	private IStartupObject initService;
	public void setAdministrationService(
			IAdministrationService administrationService) {
		this.administrationService = administrationService;
	}
	
	/** 
	 * @Title: loadAllModules 
	 * @Description: 查询所有的菜单
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws 
	 */ 
	public String loadAllModules() throws Exception{
		ServiceReturn sRet = this.administrationService.getAllSystemModules();
		JSONObject jsonObj = super.convertServiceReturnToJson(sRet);
		super.setActionresult(jsonObj.toString());
		return SUCCESS;
	}
	
	/** 
	 * @Title: addModule 
	 * @Description: 添加菜单
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws 
	 */ 
	public String addModule() throws Exception{
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Module module = (Module)JSONObject.toBean(jsonObj,Module.class);
		module.setAuthflag("0");
		ServiceReturn sRet = this.administrationService.addSystemModule_itransc(module);
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		//重新初始化菜单内存
		ServletContext context = ServletActionContext.getServletContext();
		initService.setMouduleList(context);
		super.setActionresult(retObj.toString());
		return AJAX_SUCCESS;
	}
	
	/** 
	 * @Title: editModule 
	 * @Description: 编辑菜单
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws 
	 */ 
	public String editModule() throws Exception{
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		ActionUtil.getDictKey(jsonObj, "logflag");
		Module module = (Module)JSONObject.toBean(jsonObj,Module.class);
		module.setAuthflag("0");
		ServiceReturn sRet = this.administrationService.editSystemModule_itransc(module);
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		//重新初始化菜单内存
		ServletContext context = ServletActionContext.getServletContext();
		initService.setMouduleList(context);
		super.setActionresult(retObj.toString());
		return AJAX_SUCCESS;
	}
	
	/** 
	 * @Title: deleteModule 
	 * @Description: 删除菜单
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws 
	 */ 
	public String deleteModule() throws Exception{
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Module module = (Module)JSONObject.toBean(jsonObj,Module.class);
		ServiceReturn sRet =this.administrationService.deleteSystemModule_itransc(module);
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		//重新初始化菜单内存
		ServletContext context = ServletActionContext.getServletContext();
		initService.setMouduleList(context);
		super.setActionresult(retObj.toString());
		return AJAX_SUCCESS;
	}
	/**
	 * 
	 * @Title: queryModules 
	 * @Description: 分页查询菜单列表
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public String queryModules() throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Page pageInfo = this.getPage(jsonObj); //得到Page分页对象
		ActionUtil.setSpecPara(jsonObj, "modulename", "like"); //设置modulename的条件连接符为like
		String[][] orders = { { "modulelevel", "asc" } }; //设置排序
		List list = administrationService.findModules(jsonObj, orders, pageInfo); //查询数据
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}

	public void setInitService(IStartupObject initService) {
		this.initService = initService;
	}
}
