package com.agree.framework.web.service.administration;

import java.util.List;
import java.util.Map;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.dao.entity.PasswordEntity;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.Module;
import com.agree.framework.web.form.administration.Role;
import com.agree.framework.web.form.administration.Unit;
import com.agree.framework.web.form.administration.User;
@SuppressWarnings({"rawtypes"})
public interface IAdministrationService  {
	
	/**************System Module Service, Start****************/
	//get all system modules for manipulation
	public ServiceReturn getAllSystemModules() throws Exception;
	//get my system modules for manipulation
	public ServiceReturn getMySystemModules(Long userid) throws Exception;
	//add system module
	public ServiceReturn addSystemModule_itransc(Module module) throws Exception;
	//edit system module
	public ServiceReturn editSystemModule_itransc(Module module) throws Exception;
	//delete system module
	public ServiceReturn deleteSystemModule_itransc(Module module) throws Exception;
	//get system modules by role
	public ServiceReturn getSystemModulesByRole(Role role) throws Exception;

	/**
	 * 
	 * @Title: findModules 
	 * @Description: 分页查询菜单
	 * @param @param map
	 * @param @param orders
	 * @param @param pageInfo
	 * @param @return    参数 
	 * @return List    返回类型 
	 * @throws Exception 
	 * @throws
	 */
	public List findModules(Map map, String[][] orders,Page pageInfo) throws Exception;
	/**************System Module Service, End****************/
	
	
	/**************System Role Service, Start****************/
	public List queryRolepage(Map map, Page pageinfo) throws Exception;
	//add system role
	public ServiceReturn addSystemRole_itransc(Role role) throws Exception;
	//edit system role
	public ServiceReturn editSystemRole_itransc(Role role) throws Exception;
	//edit system role
	public ServiceReturn editSystemRole(Role role) throws Exception;
	//delete system role
	public ServiceReturn deleteSystemRole_itransc(List<Role> roles) throws Exception;
	
	public ServiceReturn getSystemRolesByUserAndUnit(User user) throws Exception;
	/**************System Role Service, End****************/
	
	
	/**************System User Service, Start****************/
	//query system user
	public List queryUserPage(Map map, Page pageInfo) throws Exception;
	public List queryUserPage4Excel(Map map, Page pageInfo) throws Exception;
	//add system user
	public ServiceReturn addSystemUser_itransc(User user) throws Exception;
	//edit system user
	public ServiceReturn editSystemUser(User user) throws Exception;
	//delete system role
	public ServiceReturn deleteSystemUser_itransc(List<User> users,User login) throws Exception;
	//resetpasswd
	public ServiceReturn resetuserspasswd(List<User> users,User login)throws Exception;
	
	//edit system role
	public ServiceReturn editSystemUser_itransc(User user) throws Exception;

	public ServiceReturn assignUserRoles_itransc(User user) throws Exception;
	
	public ServiceReturn updatePassword_itransc(PasswordEntity passwordEntity) throws Exception;
	
	public ServiceReturn getUserChecked(PasswordEntity passwordEntity) throws Exception;
	
	public ServiceReturn passwordRedo_itransc(List<User> users) throws Exception;
	
	public User getUserData_itransc(User user) throws Exception;
	/**
	 * 获取登陆用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn getuser(User user) throws Exception;
	/**************System User Service, End****************/
	
	
	/**************System Unit Service, Start****************/
	//add system role
	public ServiceReturn addSystemUnit_htransc(Unit unit) throws Exception;
	//edit system role
	public ServiceReturn editSystemUnit_htransc(Unit unit) throws Exception;
	//delete system role
	public ServiceReturn deleteSystemUnit_htransc(Unit unit) throws Exception;
	/**************System Unit Service, End****************/
	
	
	/** 
	 * @Title: findLog 
	 * @Description: 分页查询日志信息
	 * @param @param map
	 * @param @param orders
	 * @param @param pageInfo
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return List    返回类型 
	 * @throws 
	 */ 
	public List findLog( Map map, Page pageInfo) throws Exception;
	public List findLogDoExcel(Map map) throws Exception;
	
	public ServiceReturn isInSameUnit(User user,Map map ) throws Exception;
	
	/** 
	 * @Title: authuser 
	 * @Description: 将用户设为授权用户
	 * @param userid
	 * @return
	 * @throws Exception    
	 */ 
	public ServiceReturn authuser(List users) throws Exception;
	
	
	/** 
	 * @Title: authuser 
	 * @Description: 取消用户授权权限
	 * @return
	 * @throws Exception    
	 */ 
	public ServiceReturn cancelAuthuser(List users) throws Exception;
	
	/** 
	 * @Title: authuser 
	 * @Description: 赋予用户岗位
	 * @return
	 * @throws Exception    
	 */ 
	public ServiceReturn assignQuarters(Map map) throws Exception;
	
	/** 
	 * @Title: authuser 
	 * @Description: 取消用户岗位
	 * @return
	 * @throws Exception    
	 */ 
	public ServiceReturn cancelQuarters(List users) throws Exception;
	
}
