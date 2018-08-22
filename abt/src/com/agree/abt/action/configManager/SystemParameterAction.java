package com.agree.abt.action.configManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.confManager.BtSysInterface;
import com.agree.abt.model.confManager.BtSysParameter;
import com.agree.abt.model.confManager.BtTsfMetadata;
import com.agree.abt.service.confmanager.ISelfMenuService;
import com.agree.abt.service.confmanager.ISystemParameterService;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

/**
 * 排队机界面-叫号规则参数配置action
 * @ClassName: SystemParameterAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2014-4-25
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SystemParameterAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private ISystemParameterService systemParameterService;
	private ISelfMenuService selfMenuService;
	private ABTComunicateNatp cona;
	
	public ISelfMenuService getSelfMenuService() {
		return selfMenuService;
	}

	public void setSelfMenuService(ISelfMenuService selfMenuService) {
		this.selfMenuService = selfMenuService;
	}
	

	/**
	 * 根据参数ID查询系统参数信息
	 * @throws Exception
	 */
	public void querySystemParameterById()throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String strJson = request.getParameter("strJson");
		JSONObject obj = JSONObject.fromObject(strJson);
		String parameter_id=obj.getString("interface_id");
		BtSysParameter sysParameter=systemParameterService.querySystemParameterById(parameter_id);
		if ("0".equals(sysParameter.getDefault_flag())) {
			sysParameter.setDefault_flag("1");
		} else if ("1".equals(sysParameter.getDefault_flag())){
			sysParameter.setDefault_flag("0");
		}
		JSONObject parameter=JSONObject.fromObject(sysParameter);
		sRet.put(ServiceReturn.FIELD1,parameter);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(sRet));
	}
	
	/**
	 * 把机构号作为条件，查询所有系统参数
	 * @throws Exception
	 */
	public void querySystemParameterByBranch()throws Exception{
		//JSONObject obj = JSONObject.fromObject(super.getJsonString());//获取页面传值
				HttpServletRequest request = ServletActionContext.getRequest();
				String branch = request.getParameter("branch");
				String template_id = (String)request.getSession().getAttribute("template");
				//判断是否模板状态,现在判断是否属于模板选定的范围内,是的话,则改变机构号
				ServiceReturn ret = null;
				if(template_id!=null){
					ret = systemParameterService.querySystemParameterByBranch(template_id);
				}else{
					ret = systemParameterService.querySystemParameterByBranch(branch);
				}
				List<BtSysParameter> list = (List<BtSysParameter>) ret.get(ServiceReturn.FIELD1);
				for (BtSysParameter btSysParameter : list) {
					if ("0".equals(btSysParameter.getDefault_flag())) {
						btSysParameter.setDefault_flag("1");
					} else if ("1".equals(btSysParameter.getDefault_flag())){
						btSysParameter.setDefault_flag("0");
					}
				}
				
				ServletActionContext.getResponse().setCharacterEncoding("utf-8");
				ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	
	/**
	 * 查询系统参数
	 * @return
	 * @throws Exception 
	 */
	public void querySystemParameter() throws Exception{
		//JSONObject obj = JSONObject.fromObject(super.getJsonString());//获取页面传值
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String branch = request.getParameter("branch");
		String template_id = (String)request.getSession().getAttribute("template");
		String parameter_flag = request.getParameter("parameter_flag");
		//判断是否模板状态,现在判断是否属于模板选定的范围内,是的话,则改变机构号
		ServiceReturn ret = null;
		if(template_id!=null){
			ret = systemParameterService.querySystemParameter(template_id,parameter_flag);
		}else{
			if(branch==null && parameter_flag==null){
				branch = user.getUnitid();
				parameter_flag = "10";
			}
			ret = systemParameterService.querySystemParameter(branch,parameter_flag);
		}
		List<BtSysParameter> list = (List<BtSysParameter>) ret.get(ServiceReturn.FIELD1);
		for (BtSysParameter btSysParameter : list) {
			if ("0".equals(btSysParameter.getDefault_flag())) {
				btSysParameter.setDefault_flag("1");
			} else if ("1".equals(btSysParameter.getDefault_flag())){
				btSysParameter.setDefault_flag("0");
			}
		}
		
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	/**
	 * 添加系统参数
	 * @return
	 * @throws Exception 
	 */
	public String addSystemParameter() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		String template_id = (String)request.getSession().getAttribute("template");
		ServiceReturn ret;
		String strJson="";
		String parameter = request.getParameter("strJson");
		if(parameter == null || parameter.equals("")){
			strJson = super.getJsonString();
		}
		JSONObject obj = JSONObject.fromObject(strJson);
		
		if(template_id!=null){
			ret = systemParameterService.addSystemParameter(obj,template_id);
		}else {
			ret = systemParameterService.addSystemParameter(obj);
		}
		
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 修改系统参数
	 * @return
	 * @throws Exception 
	 */
	public String editSystemParameter() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		
		//String strJson = request.getParameter("strJson");
		
		String strJson="";
		String parameter = request.getParameter("strJson");
		if(parameter == null || parameter.equals("")){
			strJson = super.getJsonString();
		}
		JSONObject obj = JSONObject.fromObject(strJson);
		String template_id = (String)request.getSession().getAttribute("template");
		ServiceReturn ret;
		if(template_id!=null){
			ret = systemParameterService.editSystemParameter(obj,template_id);
		}else {
			ret = systemParameterService.editSystemParameter(obj);
		}
		
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 根据参数ID删除参数信息及相关联的界面信息和菜单信息
	 */
	public String delSystemParameterByParameterId() throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		HttpServletRequest req = ServletActionContext.getRequest();
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		cona.setBMSHeader("ibp.bms.b209_4.01", user);
		cona.set("parameter_id", obj.getString("parameter_id"));
		cona.exchange();
		List<BtSysInterface> list=selfMenuService.queryAllNodeToList(obj.getString("parameter_id"));
		list.remove(0);
		if(list!=null&&list.size()>0){
			for(BtSysInterface sysInterface:list){
				try {
					JSONObject obj1=JSONObject.fromObject(sysInterface);
					ret=selfMenuService.deleteMenuLeafById(obj1);
				} catch (Exception e) {
					ret.setSuccess(false);
					ret.setOperaterResult(false);
					ret.setErrmsg("删除失败");
					break;
				}
			}
		}
		
		String str = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(str);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 通过ID删除所选系统参数
	 * @return
	 * @throws Exception 
	 */
	public String delSystemParameterById() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		cona.setBMSHeader("ibp.bms.b209_4.01", user);
		cona.set("parameter_id", obj.getString("parameter_id"));
		HashMap map = (HashMap) cona.exchange();
		String validate="";
		validate=cona.validMap(map);
		if(validate!=null&&validate.trim().length()>0){
			throw new AppException(validate);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String str = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(str);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 复制菜单
	 * @return
	 * @throws Exception
	 * @{@link Date} 2016-10-25
	 */
	public String copysystemParameter() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		String strJson = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(strJson);
		String template_id = (String)request.getSession().getAttribute("template");
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		boolean check_flag = false;
		
		if("true".equals(obj.getString("default_flag"))||"1".equals(obj.getString("default_flag"))){
			boolean flag = false;
			//判断是否模板状态,现在判断是否属于模板选定的范围内,是的话,则改变机构号
			if(template_id!=null){
				//查询是否有默认
				flag = systemParameterService.flagSystemParameter(template_id, obj.getString("parameter_flag"));
			}else{
				//查询是否有默认
				flag = systemParameterService.flagSystemParameter(user.getUnitid(), obj.getString("parameter_flag"));
			}
			if(!flag){//默认标志已存在
				ret.setSuccess(false);
				ret.setOperaterResult(false);
/********************2017-01-11/18:49 高艺祥修改，将以下内容注释******************************************************************/
				throw new AppException("此机构已存在默认参数");
				/*
				 * 
				ret.setErrmsg("此机构已存在默认的参数！");
				check_flag = true;
				String string = super.convertServiceReturnToJson(ret).toString();
				super.setActionresult(string);
				return AJAX_SUCCESS;
				*/
/********************2017-01-11/18:49 高艺祥修改，将以上内容注释******************************************************************/
				
			}
		}
		check_flag=systemParameterService.isExist(ret, obj.getString("parameter_name"),obj.getString("parameter_flag"));
		if (!check_flag) {
			//判断是否模板状态,现在判断是否属于模板选定的范围内,是的话,则改变机构号
			if(template_id!=null){
				ret=systemParameterService.copysystemParameter(obj,template_id);
			}else{
				ret=systemParameterService.copysystemParameter(obj);
			}
		}
		if(!ret.getSuccess()){
			String string = super.convertServiceReturnToJson(ret).toString();
			super.setActionresult(string);
			return AJAX_SUCCESS;
		}
		String parameter_id=(String) ret.get("parameter_id");
		String interface_id=(String) ret.get("interface_id");
		List<BtSysInterface> list=selfMenuService.queryAllNodeToList(parameter_id);
		list.remove(0);
		if(list!=null&&list.size()>0){
			for(BtSysInterface sysInterface:list){
				try {
					String metadata_id="{'metadata_id':'"+sysInterface.getMetadata_id()+"'}";
					JSONObject obj1=JSONObject.fromObject(metadata_id);
					List<BtTsfMetadata> metadatalist=selfMenuService.queryMetadataById(obj1);
					BtTsfMetadata metadata=metadatalist.get(0);
					metadata.setMetadata_id("");
					metadata.setInterface_id(interface_id);
					ret=selfMenuService.saveMetadata(metadata);
					String metadata_id1=(String) ret.get("metadata_id");
					sysInterface.setMetadata_id(metadata_id1);
					sysInterface.setInterface_id(interface_id);
					sysInterface.setInterface_name(obj.getString("parameter_name"));
					ret=selfMenuService.saveSysInterface(sysInterface);
				} catch (Exception e) {
					ret.setSuccess(false);
					ret.setOperaterResult(false);
					ret.setErrmsg("复制失败");
				}
			}
		}
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	/**
	 * 复制系统参数
	 * @return
	 * @throws Exception 
	 */
	public String copySystemParameter() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		String strJson = super.getJsonString();
		
		JSONObject obj = JSONObject.fromObject(strJson);
		String template_id = (String)request.getSession().getAttribute("template");
		
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		//BtSysParameter systemParameter = (BtSysParameter)JSONObject.fromObject(strJson);
		boolean check_flag = false;
		
		if("true".equals(obj.getString("default_flag"))||"1".equals(obj.getString("default_flag"))){
			boolean flag = false;
			//判断是否模板状态,现在判断是否属于模板选定的范围内,是的话,则改变机构号
			if(template_id!=null){
				//查询是否有默认
				flag = systemParameterService.flagSystemParameter(template_id, obj.getString("parameter_flag"));
			}else{
				//查询是否有默认
				flag = systemParameterService.flagSystemParameter(user.getUnitid(), obj.getString("parameter_flag"));
			}
			if(!flag){//默认标志已存在
//===2017-01-11 高艺祥修改部分 。修改内容：注释区域内代码，并添加向页面报错语句===============start===============================================================				
				/*ret.setSuccess(false);
				ret.setOperaterResult(false);
				ret.setErrmsg("此机构已存在默认的参数！");
				return ret;*/
				throw new AppException("此机构已存在默认参数");
//===========================================end====================================================================
			}
		}
		check_flag=systemParameterService.isExist(ret, obj.getString("parameter_name"),obj.getString("parameter_flag"));
		if (!check_flag) {
			cona.reInit();
			cona.setBMSHeader("ibp.bms.b209_5.01", user);
			//循环取出json对象的数据
			Iterator keys = obj.keys();
			while (keys.hasNext()) {
				String key = keys.next().toString();
				if("default_flag".equals(key)){
					cona.set(key,"true".equals(obj.getString(key))?"0":"1");
				}else if("parameter_id".equals(key)){
					cona.set("parameter_id_old",obj.getString(key));
				}else{
					cona.set(key,obj.getString(key));
				}
			}
			
			//判断是否模板状态,现在判断是否属于模板选定的范围内,是的话,则改变机构号
			if(template_id!=null){
				cona.set_cover("branch",template_id);
			}else{
				cona.set_cover("branch",user.getUnitid());
			}
			
			cona.set("parameter_id_new",UUID.randomUUID().toString().substring(0, 8));
			HashMap map = (HashMap) cona.exchange();
			String validate="";
			validate=cona.validMap(map);
			if(validate!=null&&validate.trim().length()>0){
				throw new AppException(validate);
			}
		}
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}

	public ISystemParameterService getSystemParameterService() {
		return systemParameterService;
	}

	public void setSystemParameterService(
			ISystemParameterService systemParameterService) {
		this.systemParameterService = systemParameterService;
	}

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
}
