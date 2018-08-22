package com.agree.framework.web.action.administration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.Module;
import com.agree.framework.web.form.administration.Role;
import com.agree.framework.web.service.administration.IAdministrationService;
import com.agree.util.ActionUtil;
/**
 * @ClassName: SystemRoleAction 
 * @Description: 
 * @company agree   
 * @author lilei
 * @date 2011-10-13 下午04:46:20 
 *
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class SystemRoleAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private IAdministrationService administrationService;
	public void setAdministrationService(IAdministrationService administrationService) {
		this.administrationService = administrationService;
	}

	public String loadPage() throws Exception{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());
		sRet.put(ServiceReturn.FIELD3, super.getLogonUser(false));//获取登录用户的信息
		//返回全部module
		List list=(List) ServletActionContext.getServletContext().getAttribute(ApplicationConstants.MODULELIST);
		sRet.put(ServiceReturn.FIELD4, list);
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	/**
	 * 角色查询action方法
	 * @Title: queryrole 
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public String queryrole() throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Page pageInfo = this.getPage(jsonObj); //得到Page分页对象
		ActionUtil.setDimPara(jsonObj, "rolename");//设置rolename的值为%rolename%
		List<?> list = this.administrationService.queryRolepage(jsonObj,pageInfo);
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 添加角色
	 * @Title: addrole 
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public String addrole() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_SET);
		config.setCollectionType(Set.class);
		config.setRootClass(Module.class);
		Set<Module> modules = (Set<Module>) JSONArray.toCollection(obj.getJSONArray("modules"),config);
		TreeSet set=new TreeSet();//构造一个TreeSet
		set.addAll(modules);
		String loginid = super.getLogonUser(false).getUserid();
		obj.put("createuser", loginid);
		obj.put("createdate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		Role role = (Role)JSONObject.toBean(obj, Role.class);
		role.setModules(set);
		
		ServiceReturn sRet = this.administrationService.addSystemRole_itransc(role);
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
		
	}
	/**
	 * 编辑角色
	 * @Title: editrole 
	 * @Description: 
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public String editrole() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_SET);
		config.setCollectionType(Set.class);
		config.setRootClass(Module.class);
		Set<Module> modules = (Set<Module>) JSONArray.toCollection(obj.getJSONArray("modules"),config);
		TreeSet set=new TreeSet();//构造一个TreeSet
		set.addAll(modules);
		String loginid = super.getLogonUser(false).getUserid();
		obj.put("lastmoduser", loginid);
		obj.put("lastmoddate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		Role role = (Role)JSONObject.toBean(obj, Role.class);
		role.setModules(set);
		
		ServiceReturn sRet = this.administrationService.editSystemRole(role);
		
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	/**
	 * 删除角色
	 * @Title: deleterole 
	 * @Description: 
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public String deleterole() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(Role.class);
		List<Role> roles = (List<Role>) JSONArray.toCollection(jsonArray,config);
		
		ServiceReturn ret = this.administrationService.deleteSystemRole_itransc(roles);
		super.setActionresult(super.convertServiceReturnToJson(ret).toString());
		return AJAX_SUCCESS;
	}
	/**
	 * 角色权限分配
	 * @Title: getSystemRoleModule 
	 * @Description:
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public String getSystemRoleModule() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		Role role = (Role)JSONObject.toBean(obj, Role.class);
		
		ServiceReturn ret = this.administrationService.getSystemModulesByRole(role);
		JSONObject jsonObj = super.convertServiceReturnToJson(ret);
		super.setActionresult(jsonObj.toString());
		return AJAX_SUCCESS;
	}
}
