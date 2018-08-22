package com.agree.framework.web.action.administration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.Role;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.administration.IAdministrationService;
import com.agree.util.ActionUtil;
/**
 * 用户操作Action
 * @ClassName: SystemUserAction 
 * @company agree   
 * @author  lilei
 * @date 2011-8-24 下午05:47:52 
 *
 */
@SuppressWarnings("unchecked")
public class SystemUserAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IAdministrationService administrationService;

	public void setAdministrationService(IAdministrationService administrationService) {
		this.administrationService = administrationService;
	}
	/**
	 * 进入页面
	 * 将登录用户信息、部门集合、和创建用户的时候的默认密码传到页面
	 * @Title: loadPage 
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public String loadPage() throws Exception{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());//获取部门集合
		sRet.put(ServiceReturn.FIELD2, super.getLogonUser(true));//获取登录用户信息
		sRet.put(ServiceReturn.FIELD6, ApplicationConstants.RESETPASSWORD);//获取默认密码
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 查询用户
	 * 
	 * @Title: queryuser 
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public String queryuser() throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		
		Page pageInfo = this.getPage(jsonObj); //得到Page分页对象	
		ActionUtil.setDimPara(jsonObj, "username");//设置username的值为%username%
		//List<?> units = super.getMyUnits();//获取部门集合
		//jsonObj.put("units", units);

		List<?> list = this.administrationService.queryUserPage(jsonObj,pageInfo);//分页查询用户
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 添加系统用户
	 * @Title: adduser 
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public String adduser() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		String createuser = super.getLogonUser(false).getUserid();
		obj.put("createuser", createuser);
		obj.put("createdate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		User user = (User)JSONObject.toBean(obj, User.class);
		user.setUserid(obj.getString("usercode"));//设置userid为usercode(用户登录名)
		user.setInfoex1("0");//无授权
		long userType = 2;
		user.setUsertype(String.valueOf(userType));
		ServiceReturn sRet = this.administrationService.addSystemUser_itransc(user);
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	/**
	 * 编辑系统用户
	 * @Title: edituser 
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public String edituser() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		String lastmoduser = super.getLogonUser(false).getUserid();
		obj.put("lastmoduser", lastmoduser);//用户修改操作员id
		obj.put("lastmoddate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//修改时间
		User user = (User)JSONObject.toBean(obj, User.class);
		user.setInfoex1("0");//无授权
		long userType = 2;
		user.setUsertype(String.valueOf(userType));
		ServiceReturn sRet = this.administrationService.editSystemUser(user);//用户信息修改
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	/**
	 * 删除系统用户
	 * @Title: deleteuser 
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public String deleteuser() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(User.class);
		List<User> users = (List<User>) JSONArray.toCollection(jsonArray,config);
		User login = super.getLogonUser(false);
		
		ServiceReturn ret = this.administrationService.deleteSystemUser_itransc(users,login);//删除用户操作
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	/**
	 * 返回给页面用户分配到的角色和未分配到的角色
	 * @Title: getSystemUserRoles 
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public String getSystemUserRoles() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(inputJsonStr);
		User user = (User)JSONObject.toBean(jsonObj,User.class);
		
		ServiceReturn sRet = this.administrationService.getSystemRolesByUserAndUnit(user);//返回角色分配情况
		String string = super.convertServiceReturnToJson(sRet).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	/**
	 * 用户密码重置
	 * @Title: resetuserspasswd 
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public String resetuserspasswd() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(User.class);
		List<User> users = (List<User>) JSONArray.toCollection(jsonArray,config);
		User login = super.getLogonUser(false);
		
		ServiceReturn ret = this.administrationService.resetuserspasswd(users,login);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 用户角色分配确定
	 * @Title: assignUserRole 
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public String assignUserRole() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_SET);
		config.setCollectionType(Set.class);
		config.setRootClass(Role.class);
		Set<Role> roles = (Set<Role>) JSONArray.toCollection(obj.getJSONArray("roles"),config);
		User user = (User)JSONObject.toBean(obj, User.class);
		user.setRoles(roles);
		
		ServiceReturn sRet = this.administrationService.assignUserRoles_itransc(user);
		String string = super.convertServiceReturnToJson(sRet).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	/** 
	 * @Title: author 
	 * @Description: 授权用户校验
	 * @return
	 * @throws Exception    
	 */ 
	public  String author() throws Exception{
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		User user=super.getLogonUser(false);
		ServiceReturn ret =administrationService.isInSameUnit(user,jsonObj);
		ret.put(ServiceReturn.FIELD1, ServletActionContext.getRequest().getServletPath());//原请求路径
		ret.put(ServiceReturn.FIELD2, jsonString);//原请求参数
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}

	/** 
	 * @Title: authuser 
	 * @Description: 将用户设为授权用户
	 * @return
	 * @throws Exception    
	 */ 
	public  String authuser() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(User.class);
		List<User> users = (List<User>) JSONArray.toCollection(jsonArray,config);
		ServiceReturn ret =administrationService.authuser(users);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	/** 
	 * @Title: cancelAuthor 
	 * @Description: 取消用户授权权限
	 * @return
	 * @throws Exception    
	 */ 
	public  String cancelAuth() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(User.class);
		List<User> users = (List<User>) JSONArray.toCollection(jsonArray,config);
		ServiceReturn ret =administrationService.cancelAuthuser(users);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	/** 
	 * @Title: assignQuarters 
	 * @Description: 分配岗位
	 * @return
	 * @throws Exception    
	 */ 
	public  String assignQuarters() throws Exception{
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		ServiceReturn ret =administrationService.assignQuarters(jsonObj);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	/** 
	 * @Title: cancelQuarters 
	 * @Description: 删除岗位
	 * @return
	 * @throws Exception    
	 */ 
	public  String cancelQuarters() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(User.class);
		List<User> users = (List<User>) JSONArray.toCollection(jsonArray,config);
		ServiceReturn ret =administrationService.cancelQuarters(users);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	/** 
	 * @Title: exportExcel 
	 * @Description: 导出excel
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws 
	 */ 
	public String exportExcel()throws Exception{
		String jsonString = ServletActionContext.getRequest().getParameter("querycondition_str");
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Page pageInfo = new Page();
		pageInfo.setStart(1);
		pageInfo.setLimit(100000);
		List<?> list = this.administrationService.queryUserPage4Excel(jsonObj,pageInfo);//分页查询用户
		//性别
		Map<String,String> map1 = new HashMap<String,String>();
		map1.put("1", "1-男");
		map1.put("2", "2-女");
		//状态
		Map<String,String> map2 = new HashMap<String,String>();
		map2.put("1", "1-启用");
		map2.put("2", "2-禁用");
		for(int i=0; i<list.size(); i++){//循环翻译要导出到excel中的需要翻译的字段
			
			Map<String,String> sexMap = ((HashMap<String, String>)(list.get(i)));
			String sex = String.valueOf(sexMap.get("sex"));
			if("".equals(sex)||"null".equals(sex)){
			}else{
				sexMap.put("sex", map1.get(sex));
			}
			
			Map<String,String> stateMap = ((HashMap<String, String>)(list.get(i)));
			String state = String.valueOf(stateMap.get("state"));
			if("".equals(state)||"null".equals(state)){
				stateMap.put("state", "无数据");
			}else{
				stateMap.put("state", map2.get(state));
			}
		}
		
		String path="BranchUser.xls";
		String file = "用户列表";
		//进行时间格式转换
		super.doExcel(path, list, jsonObj, file);
		return null;
	}
}