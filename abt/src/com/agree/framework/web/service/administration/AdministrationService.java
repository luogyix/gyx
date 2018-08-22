package com.agree.framework.web.service.administration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.dao.entity.PasswordEntity;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.Module;
import com.agree.framework.web.form.administration.Role;
import com.agree.framework.web.form.administration.Unit;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.form.base.BsmsMngrolemodule;
import com.agree.framework.web.form.base.BsmsMnguserrole;
import com.agree.framework.web.service.base.BaseService;
import com.agree.util.MD5;
/**
 * @ClassName: AdministrationService 
 * @Description: 
 * @company agree   
 * @author lilei
 * @date 2011-10-13 下午04:43:18 
 *
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class AdministrationService extends BaseService implements IAdministrationService {
	private Log logger = LogFactory.getLog(AdministrationService.class);	

	/**************System Module Service, Start****************/
	/**
	 * 返回整个module树
	 */
	public ServiceReturn getAllSystemModules() throws Exception {
		//返回所有module（菜单）
		List<Module> list = sqlDao_h.getRecordList("from Module  order by  modulelevel,moduleorder asc");
		List reaultList=new ArrayList();
		if(list != null && list.size() != 0){
			for(Module module:list){//取出菜单信息
				boolean isleaf=true;
				for(Module module1:list){//循环判断，是否有该菜单的子节点，有的话则修改标志为false，
					if(module1.getParentmoduleid().equals(module.getModuleid())){
						isleaf=false;
						break;
					}
				}
				module.setIsleaf(isleaf);
				reaultList.add(module);
			}
		}
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, reaultList);
		return sRet;
	}
	/**
	 * 返回当前用户拥有权限的module树
	 * @param userid 登录用户的id
	 * 
	 */
	public ServiceReturn getMySystemModules(Long userid) throws Exception{
		String hql ="from Module where moduleid in (select distinct moduleid from BsmsMngrolemodule where roleid in (select roleid from BsmsMnguserrole where userid = ?)) order by  modulelevel,moduleorder asc";
		List<Module> list = sqlDao_h.getRecordList(hql, false, userid);
		if(list != null && list.size() != 0){
			for(int i=0;i<list.size();i++){
				if(list.get(i).getModuletype()!=ApplicationConstants.LASTMODULETYPE){
					list.get(i).setIsleaf(false);
				}else{
					list.get(i).setIsleaf(true);
				}
				
			}
		}
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, list);
		return sRet;
		
	}

	/**
	 * <p>Title: addSystemModule_itransc</p> 
	 * <p>Description: 添加系统菜单</p> 
	 * @param module 菜单对象
	 * @return
	 * @throws Exception 
	 * @see com.agree.framework.web.service.administration.IAdministrationService#addSystemModule_itransc(com.agree.framework.web.form.administration.Module) 
	 */ 
	public ServiceReturn addSystemModule_itransc(Module module) throws Exception {
		String moduleid=(String) sqlDao_h.getRecord("select max(moduleid) from Module");
		module.setModuleid(String.valueOf((Integer.parseInt(moduleid)+1)));
		sqlDao_h.saveRecord(module);
		List<Module> list = sqlDao_h.getRecordList("from Module  order by  modulelevel,moduleorder asc");
		List reaultList=new ArrayList();
		if(list != null && list.size() != 0){
			for(Module m:list){//取出菜单信息
				boolean isleaf=true;
				for(Module module1:list){//循环判断，是否有该菜单的子节点，有的话则修改标志为false，
					if(module1.getParentmoduleid().equals(m.getModuleid())){
						isleaf=false;
						break;
					}
				}
				m.setIsleaf(isleaf);
				reaultList.add(m);
			}
		}
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		sRet.put(ServiceReturn.FIELD1, reaultList);
		return sRet;
	}

	/**
	 * <p>Title: deleteSystemModule_itransc</p> 
	 * <p>Description: 删除系统菜单</p> 
	 * @param module
	 * @return
	 * @throws Exception 
	 * @see com.agree.framework.web.service.administration.IAdministrationService#deleteSystemModule_itransc(com.agree.framework.web.form.administration.Module) 
	 */ 
	public ServiceReturn deleteSystemModule_itransc(Module module) throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		 module = (Module) sqlDao_h.getRecord("from Module model where model.moduleid='"+ module.getModuleid()+"'");
		
		List<Module> allModule=sqlDao_h.getRecordList("from Module model where model.modulelevel>? order by model.modulelevel asc",module.getModulelevel());
		List<String> prent=new ArrayList();
		prent.add(module.getModuleid());//父节点集合
		for(Module iter_module :allModule){
			String parentId=iter_module.getParentmoduleid();
			String moduleId=iter_module.getModuleid();
			for(String pid:prent){//如果其父节点在父节点集合里，则要删除该节点
				if(parentId.equals(pid)){
					prent.add(moduleId);//将其节点号也加入到父节点集合里
					sqlDao_h.deleteRecord(iter_module);
					break;
				}
			}
		}
		sqlDao_h.deleteRecord(module);
		//删除BsmsMngrolemodule表里的角色权限关系
		for(String moduleid:prent){
			String[] ids={moduleid};
			sqlDao_h.excuteHql("delete from BsmsMngrolemodule where module_id=?", ids);
		}
		
		
		return sRet;
	}

	/**
	 * <p>Title: editSystemModule_itransc</p> 
	 * <p>Description: 修改系统菜单</p> 
	 * @param module
	 * @return
	 * @throws Exception 
	 * @see com.agree.framework.web.service.administration.IAdministrationService#editSystemModule_itransc(com.agree.framework.web.form.administration.Module) 
	 */ 
	public ServiceReturn editSystemModule_itransc(Module module) throws Exception {
		sqlDao_h.updateRecord(module);
		ServiceReturn sRet = getAllSystemModules();
		return sRet;
	}
	/**
	 * 为角色分配权限
	 */
	public ServiceReturn getSystemModulesByRole(Role role) throws Exception {
		String roleid= role.getRoleid();
		Map map=new HashMap();
		map.put("roleid", roleid);
		String hql="select  new Module(m.moduleid,  m.modulename,  m.parentmoduleid,"+
		"m.moduletype,  m.moduleaction,  m.moduleorder,"+
		" m.modulelevel,  m.logflag,  m.privilegeType, m.createuser,"+
		" m.createdate,  m.lastmoduser,  m.lastmoddate,"+
		 "m.moduleImg,  m.authflag,  m.remark1,  m.remark2,m.remark3) "+
		" from BsmsMngrolemodule r,Module m "+
		" where r.roleid =:roleid  and r.moduleid=m.moduleid  "+
		" order by modulelevel,moduleorder asc ";
		List modules=sqlDao_h.getRecordList(hql, map, false);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		sRet.put(ServiceReturn.FIELD1, modules);
		return sRet;
	}

	/* (non-Javadoc)
	 * <p>Title: findModules</p> 
	 * <p>Description:根据条件查找菜单 </p> 
	 * @param map      参数集合
	 * @param orders   排序
	 * @param pageInfo 分页对象
	 * @return
	 * @throws Exception 
	 * @see com.agree.framework.web.service.administration.IAdministrationService#findModules(java.util.Map, java.lang.String[][], com.agree.framework.dao.entity.Page) 
	 */ 
	public List findModules(Map map, String[][] orders, Page pageInfo) throws Exception {
		List list=sqlDao_h.queryPage(Module.class, map, orders, pageInfo,false);
		return list;
	}
	/**************System Module Service, End****************/

	
	
	/**************System Role Service, Start****************/
	/**
	 * 角色分页查询操作
	 * 
	 */
	
	public List queryRolepage(Map map, Page pageinfo) throws Exception{
		List list=null;
		String hql = "select new map(R.roleid as roleid, R.rolename as rolename, R.roledescribe as roledescribe," +
				" R.enabled as enabled,R.roletype as roletype,R.default_flag as default_flag," +
				" R.rolelevel as rolelevel) from Role R where 1 = 1 ";
		
		HashMap paramaHashMap=new HashMap();
		paramaHashMap.putAll(map);
		if(paramaHashMap.get("rolename")!=null && !paramaHashMap.get("rolename").toString().equals("")){
			hql = hql+" and R.rolename like :rolename";
		}
		if(paramaHashMap.get("enabled")!=null && !paramaHashMap.get("enabled").toString().equals("")){
			paramaHashMap.put("enabled",Long.valueOf((String)paramaHashMap.get("enabled")) );//转为Long类型
			hql = hql+" and R.enabled = :enabled";
		}
		
		hql = hql+" order by cast(R.rolelevel, int) asc,cast(R.roleid, int) asc";//hql语句拼写完成
		
		list = sqlDao_h.queryPage(hql,paramaHashMap,pageinfo,false);//此方法传入的hql为完整的hql，不需传入排序参数
		return list;
	}
	/**
	 * 添加角色
	 */
	public ServiceReturn addSystemRole_itransc(Role role) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		
		//判断是否已拥有这个角色
		Map map=new HashMap();
		map.put("rolename", role.getRolename());
		//map.put("usercode", usercode);
		Long count = sqlDao_h.getRecordCount("select count(*) from Role where rolename=:rolename",map);
		if(count > 0){//角色已存在
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("该角色名已存在，请校对！");
			return ret;
		}
		
		List<Role> list = sqlDao_h.getRecordList("from Role t order by cast(t.roleid, int) desc", false);
		int i=Integer.parseInt(list.get(0).getRoleid())+1;
		role.setRoleid(String.valueOf(i));
		//是否有越权添加的权限.
		if(role.getModules() != null&&role.getModules().size() != 0){
			User us = (User)(User)ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
			Set<Role> roles = us.getRoles();
			Iterator<Role> it = roles.iterator();
			boolean isMax = false;
			while (it.hasNext()) {
				String userrolelevel = it.next().getRolelevel();
				if(userrolelevel != null&&Integer.parseInt(userrolelevel)==0){//0级可做任何操作
					isMax = true;
					break;
				}
			}
			if(!isMax){
				Set<Module> priList = us.getCatalog_and_privileges();
				Set<Module> modules = role.getModules();
				logger.info("priList modules.size()="+priList.size());
				logger.info("selected modules.size()="+modules.size());
				for(Module module : modules){
					boolean flag = false;
					for (Module pri : priList) {
						if(pri.getModuleid().equals(module.getModuleid())){
							flag = true;
						}
					}
					if(!flag){
						ret.setSuccess(false);
						ret.setOperaterResult(false);
						ret.setErrmsg("无法操作本用户所不具备的权限,请确认.");
						return ret;
					}
				}
			}
		}
		sqlDao_h.saveRecord(role);
		
		if(role.getModules() != null&&role.getModules().size() != 0){
			Set<Module> modules = role.getModules();
			for(Module module : modules){
				BsmsMngrolemodule tmp_role = new BsmsMngrolemodule();
				tmp_role.setRoleid(String.valueOf(i));
				tmp_role.setModuleid(module.getModuleid());
				logger.info("id="+i+",moduleid="+module.getModuleid());
				sqlDao_h.saveRecord(tmp_role);
			}
		}
		return ret;
	}

	public ServiceReturn deleteSystemRole_itransc(List<Role> roles) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
//		for(Role role : roles){
//			Map map=new HashMap();
//			map.put("roleid",role.getRoleid());
//			Long count = sqlDao_h.getRecordCount("select count(*) from BsmsMnguserrole where roleid=:roleid",map);
//			if(count > 0){
//				throw new AppException("角色还在被用户使用中，删除操作失败！请检查用户的角色分配情况...");
//			}
//		}
		for(Role role : roles){
			String roleid = role.getRoleid();
			Object[] paras={roleid};
			sqlDao_h.excuteHql("DELETE FROM BsmsMngrolemodule u WHERE u.roleid = ?", paras);
			sqlDao_h.excuteHql("DELETE FROM BsmsMnguserrole u WHERE u.roleid = ?", paras);
			sqlDao_h.excuteHql("DELETE FROM Role u WHERE u.roleid = ?", paras);
			
		}
		return ret;
	}

	public ServiceReturn editSystemRole_itransc(Role role) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		Map map=new HashMap();
		map.put("rolename", role.getRolename());
		map.put("roleid", role.getRoleid());
		Long count = sqlDao_h.getRecordCount("select count(*) from Role where rolename=:rolename and roleid!=:roleid",map);
		if(count > 0){//角色已存在
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("该角色名已存在，请校对！");
			return ret;
		}
		try{
			sqlDao_h.updateRecord(role);
		
			String roleid = role.getRoleid();
			Object[] paras={roleid};
			sqlDao_h.excuteHql("DELETE FROM BsmsMngrolemodule u WHERE u.roleid = ?", paras);
			if(role.getModules() != null){
				Set<Module> modules = role.getModules();
				for(Module module : modules){
						BsmsMngrolemodule tmp_role = new BsmsMngrolemodule();
						tmp_role.setRoleid(role.getRoleid());
						tmp_role.setModuleid(module.getModuleid());
						sqlDao_h.saveRecord(tmp_role);
				}
			}
			return ret;
		}catch(Exception e){
			ret.setSuccess(false);
			logger.error(e.getMessage(),e);
			return ret;
		}
	}
	/**
	 * 编辑角色信息，通过给定的修改信息，修改角色信息，有则改之，无则不变
	 */
	public ServiceReturn editSystemRole(Role role) throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		try{
//			sqlDao_h.updateRecord(role);
			Map map=new HashMap();
			map.put("rolename", role.getRolename());
			map.put("roleid", role.getRoleid());
			Long count = sqlDao_h.getRecordCount("select count(*) from Role where rolename=:rolename and roleid!=:roleid",map);
			if(count > 0){//角色已存在
				ret.setSuccess(false);
				ret.setOperaterResult(false);
				ret.setErrmsg("该角色名已存在！");
				return ret;
			}
			//是否有越权添加的权限.
			if(role.getModules() != null&&role.getModules().size() != 0){
				
				User us = (User)(User)ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
				Set<Role> roles = us.getRoles();
				Iterator<Role> it = roles.iterator();
				boolean isMax = false;
				while (it.hasNext()) {
					String userrolelevel = it.next().getRolelevel();
					if(userrolelevel != null&&Integer.parseInt(userrolelevel)==0){//0级可做任何操作
						isMax = true;
						break;
					}
				}
				if(!isMax){
					Set<Module> priList = us.getCatalog_and_privileges();
					Set<Module> modules = role.getModules();
					logger.info("priList modules.size()="+priList.size());
					logger.info("selected modules.size()="+modules.size());
					
					for(Module module : modules){
						boolean flag = false;
						for (Module pri : priList) {
							if(pri.getModuleid().equals(module.getModuleid())){
								flag = true;
							}
						}
						if(!flag){
							ret.setSuccess(false);
							ret.setOperaterResult(false);
							ret.setErrmsg("无法操作本用户所不具备的权限,请确认.");
							return ret;
						}
					}
				}
			}
			
			Object[] paramrole = {role.getRolename(),role.getRoledescribe(),role.getEnabled(),role.getRoletype(),
									role.getLastmoduser(),role.getLastmoddate(),role.getDefault_flag(),role.getRolelevel(),role.getRoleid()};
			String hql = "update Role set rolename=?,roledescribe=?,enabled=?,roletype=?,lastmoduser=?,lastmoddate=?,default_flag=?,rolelevel=? where roleid=?";
			sqlDao_h.excuteHql(hql,paramrole);
			
			String roleid = role.getRoleid();
			Object[] paras={roleid};
			sqlDao_h.excuteHql("DELETE FROM BsmsMngrolemodule u WHERE u.roleid = ?", paras);
			if(role.getModules() != null&&role.getModules().size() != 0){
				Set<Module> modules = role.getModules();
				for(Module module : modules){
						BsmsMngrolemodule tmp_role = new BsmsMngrolemodule();
						tmp_role.setRoleid(role.getRoleid());
						tmp_role.setModuleid(module.getModuleid());
						sqlDao_h.saveRecord(tmp_role);
				}
			}
			return ret;
		}catch(Exception e){
			ret.setSuccess(false);
			logger.error(e.getMessage(),e);
			return ret;
		}
	}
	/**
	 * 返回角色分配情况
	 * @param user为要分配角色的用户
	 * @return  rolelist 未分配角色
	 * @return  userRolelist  以分配角色
	 * 
	 */
	public ServiceReturn getSystemRolesByUserAndUnit(User user) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		Map map=new HashMap();
		map.put("userid", user.getUserid());
		//引用sql语句
//		String[] keys={"roleid","rolename"};
//		String selectRolesByUnit="select r.roleid,r.rolename from T_BSMS_MNGROLEINFO r " +
//				"where r.roleid not in (select t.role_id from T_BSMS_MNGUSERROLE t where t.user_id = :userid)";
//		String selectRolesByUserId="select r.roleid,r.rolename from T_BSMS_MNGROLEINFO r " +
//				"where r.roleid in (select t.role_id from T_BSMS_MNGUSERROLE t where t.user_id = :userid)";
//		List roleList = sqlDao_h.getRecordListBySql(selectRolesByUnit, map,keys);
//		List userRoleList = sqlDao_h.getRecordListBySql(selectRolesByUserId, map,keys);
		//引用hql语句
		String selectRolesByUnit =  "select new map(r.roleid as roleid,r.rolename as rolename) from Role r " +
									"where r.roleid not in (select t.roleid from BsmsMnguserrole t where t.userid = :userid)";
		String selectRolesByUserId= "select new map(r.roleid as roleid,r.rolename as rolename) from Role r " +
									"where r.roleid in (select t.roleid from BsmsMnguserrole t where t.userid = :userid)";
		//过滤角色列表
		User us = (User)(User)ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Set<Role> roles = us.getRoles();
		Iterator<Role> it = roles.iterator();
		boolean isMax = false;
		String upperlevel = "9";
		while (it.hasNext()) {
			String userrolelevel = it.next().getRolelevel();
			if(userrolelevel != null){
				if(Integer.parseInt(userrolelevel)==0){//0级不过滤任何列表
					isMax = true;
					break;
				}else if(Integer.parseInt(userrolelevel)<Integer.parseInt(upperlevel)){
					upperlevel = userrolelevel;
				}
			}
			
		}
		if(!isMax){//进行sql补全
			selectRolesByUnit += " and r.rolelevel is null or r.rolelevel > :rolelevel";
			map.put("rolelevel", upperlevel);
		}
		List roleList = sqlDao_h.getRecordList(selectRolesByUnit, map, false);
		Map map2=new HashMap();
		map2.put("userid", user.getUserid());
		List userRoleList = sqlDao_h.getRecordList(selectRolesByUserId, map2, false);
		//[{roleid=1, rolename=系统超级用户}]
		//写入FIELD中
		ret.put(ServiceReturn.FIELD1, roleList);
		ret.put(ServiceReturn.FIELD2, userRoleList);
		return ret;
	}
	/**************System Role Service, End****************/

	
	
	/**************System User Service, Start****************/
	/**
	 * 用户分页查询
	 * @param	map 参数集合
	 * @param   pageInfo
	 * @return
	 * @throws Exception
	 * 
	 */
	public List queryUserPage(Map map, Page pageInfo) throws Exception{
		List list=null;
		String hql = "select new map(us.userid as userid, us.usercode as usercode, us.username as username," +
				"us.usertype as usertype, us.state as state,us.infoex1 as infoex1,us.infoex2 as infoex2,us.infoex3 as infoex3," +
				"us.unitid as unitid,un.unitname as unitname, un.unitid as unitid,us.sex as sex, " +
				"us.mailbox as mailbox,us.telphone as telphone, us.cellphone as cellphone," +
				"us.tellerno as tellerno)";
		hql = hql+" from User us left join us.unit un";
		hql = hql+" where us.state!="+ApplicationConstants.USERSTATE_DEL;//部门分类
		
		HashMap paramaHashMap=new HashMap();
		paramaHashMap.putAll(map);
		if(paramaHashMap.get("username")!=null && !paramaHashMap.get("username").toString().equals("")){
			paramaHashMap.put("username", "%" + paramaHashMap.get("username") + "%");
			hql = hql+" and (us.username like :username or us.usercode like :username)";
		}
		if(paramaHashMap.get("state")!=null && !paramaHashMap.get("state").toString().equals("")){
			Long long1 = Long.valueOf((String)paramaHashMap.get("state"));//转为Long类型
			paramaHashMap.put("state",long1 );
			hql = hql+" and us.state = :state";
		}
		if(paramaHashMap.get("usertype")!=null && !paramaHashMap.get("usertype").toString().equals("")){
			Long long2 = Long.valueOf((String)paramaHashMap.get("usertype"));
			paramaHashMap.put("usertype",long2);
			hql = hql+" and us.usertype = :usertype";
		}
		if(paramaHashMap.get("unitid")!=null && !paramaHashMap.get("unitid").toString().equals("")){
			paramaHashMap.put("unitid", "%|" + (String)paramaHashMap.get("unitid") + "|%");
			hql = hql+" and (un.unitlist like :unitid or un.unitname is null)";
		}
		//hql = hql+" and us.unitid = un.unitid";//部门精确
		hql = hql+" order by un.bank_level asc, un.unitid desc, us.usercode asc";//hql语句拼写完成
		
		list = sqlDao_h.queryPage(hql,paramaHashMap,pageInfo,false);//此方法传入的hql为完整的hql，不需传入排序参数
		//-------------------------------------------------------------------------
		for (int i = 0; i < list.size(); i++) {
			Map userMap=new HashMap();
			userMap.put("userid", (String)((HashMap)list.get(i)).get("userid"));
			String selectRolesByUserId= "select new map(r.roleid as roleid,r.rolename as rolename) from Role r " +
				"where r.roleid in (select t.roleid from BsmsMnguserrole t where t.userid = :userid)";
			List userRoleList = sqlDao_h.getRecordList(selectRolesByUserId, userMap, false);
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < userRoleList.size(); j++) {
				Map temp = (HashMap)userRoleList.get(j);
				sb.append(temp.get("rolename")+","); 
			}
			String user_role = "";
			if(sb.length()>0){
				user_role = sb.toString().substring(0,sb.length()-1);
			}
			((HashMap)list.get(i)).put("role_name", user_role);
		}
		return list;
	}
	/**
	 * 用户分页查询
	 * @param	map 参数集合
	 * @param   pageInfo
	 * @return
	 * @throws Exception
	 * 
	 */
	public List queryUserPage4Excel(Map map, Page pageInfo) throws Exception{
		List list=null;
		String hql = "select new map(us.userid as userid, us.usercode as usercode, us.username as username," +
		"us.usertype as usertype, us.state as state,us.infoex1 as infoex1," +
		"us.unitid as unitid,un.unitname as unitname, un.unitid as unitid,us.sex as sex, " +
		"us.mailbox as mailbox,us.telphone as telphone, us.cellphone as cellphone," +
		"us.tellerno as tellerno)";
		hql = hql+" from User us left join us.unit un";
		hql = hql+" where us.state!="+ApplicationConstants.USERSTATE_DEL;//部门分类
		
		HashMap paramaHashMap=new HashMap();
		paramaHashMap.putAll(map);
		if(paramaHashMap.get("username")!=null && !paramaHashMap.get("username").toString().equals("")){
			paramaHashMap.put("username", "%" + paramaHashMap.get("username") + "%");
			hql = hql+" and (us.username like :username or us.usercode like :username)";
		}
		if(paramaHashMap.get("state")!=null && !paramaHashMap.get("state").toString().equals("")){
			Long long1 = Long.valueOf((String)paramaHashMap.get("state"));//转为Long类型
			paramaHashMap.put("state",long1 );
			hql = hql+" and us.state = :state";
		}
		if(paramaHashMap.get("usertype")!=null && !paramaHashMap.get("usertype").toString().equals("")){
			Long long2 = Long.valueOf((String)paramaHashMap.get("usertype"));
			paramaHashMap.put("usertype",long2);
			hql = hql+" and us.usertype = :usertype";
		}
		if(paramaHashMap.get("unitid")!=null && !paramaHashMap.get("unitid").toString().equals("")){
			paramaHashMap.put("unitid", "%|" + (String)paramaHashMap.get("unitid") + "|%");
			hql = hql+" and (un.unitlist like :unitid or un.unitname is null)";
		}
		//hql = hql+" and us.unitid = un.unitid";//部门精确
		hql = hql+" order by un.bank_level asc, un.unitid desc, us.usercode asc";//hql语句拼写完成
		
		list = sqlDao_h.queryPage(hql,paramaHashMap,pageInfo,false);//此方法传入的hql为完整的hql，不需传入排序参数
		//-------------------------------------------------------------------------
		return list;
	}
	/**
	 * 添加用户
	 */
	public ServiceReturn addSystemUser_itransc(User user) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String usercode = user.getUsercode();
		Map map=new HashMap();
		map.put("usercode", usercode);
		Long count = sqlDao_h.getRecordCount("select count(*) from User where usercode="+"'"+usercode+"'"+" and state<>9");
		
		if(count > 0){//用户登录账号已存在
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("该用户已存在！");
			return ret;
		}
		MD5 md5 = new MD5();
		user.setPassword(md5.getMD5String(user.getPassword()));
		Object userid = sqlDao_h.getRecord("select userid from User where usercode="+"'"+usercode+"'"+" and state=9");
		if(userid !=null){//如果存在已删除用户，则改为修改原用户信息
			user.setUserid((String) userid);
			user.setLastmoduser(user.getCreateuser());
			user.setLastmoddate(user.getCreatedate());
			this.editSystemUser(user);
		}else{
			user.setUserid(user.getUsercode());
			sqlDao_h.saveRecord(user);
		}
		return ret;
	}
	/**
	 * 用户信息修改，作用在用户信息管理界面的修改操作
	 * 只修改指定的字段，其余字段不动
	 * @param user 修改信息的用户对象
	 */
	public ServiceReturn editSystemUser(User user) throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		if(user.getUnitid() == null || user.getUnitid().toString().equals("")){
			Object[] paramuser = {
					user.getUsertype(),user.getSex(),user.getState(),user.getMailbox(),
					user.getTelphone(),user.getCellphone(),user.getLastmoduser(),user.getLastmoddate(),user.getTellerno(),user.getUsername(),user.getUserid()
			};
			String hql = "update User set usertype=?,sex=?,state=?,mailbox=?,telphone=?,cellphone=?,lastmoduser=?,lastmoddate=?,tellerno=?,username=? where userid=?";
			
			int updated = sqlDao_h.excuteHql(hql,paramuser);
			
			if(updated == 1){
				return ret;
			}
			ret.setSuccess(false);
			return ret;
		}
		
		Object[] paramuser = {
				user.getUnitid(),user.getUsertype(),user.getSex(),user.getState(),user.getMailbox(),
				user.getTelphone(),user.getCellphone(),user.getLastmoduser(),user.getLastmoddate(),user.getTellerno(),user.getUsername(),user.getUserid()
		};
		String hql = "update User set unitid=?,usertype=?,sex=?,state=?,mailbox=?,telphone=?,cellphone=?,lastmoduser=?,lastmoddate=?,tellerno=?,username=? where userid=?";
		
		int updated = sqlDao_h.excuteHql(hql,paramuser);
		
		if(updated == 1){
			return ret;
		}
		ret.setSuccess(false);
		return ret;
	}
	/**
	 * 删除用户，将用户状态值改为9表示用户以删除，不可以将用户从数据库中直接删除
	 * @param users 删除用户集合
	 * @param login 操作的用户
	 */
	public ServiceReturn deleteSystemUser_itransc(List<User> users,User login)
			throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		for (User user :users){
			Object[] paramuserpasswd={ApplicationConstants.USERSTATE_DEL.longValue(),login.getUserid().toString(),date,user.getUserid()};//"",
			String hql = "update User set state=?, lastmoduser=?, lastmoddate=?  where userid=?";
			
			int updated = sqlDao_h.excuteHql(hql,paramuserpasswd);
			
			if(updated != 1){
				ret.setSuccess(false);
				ret.setErrmsg("删除用户失败");
				return ret;
			}
			Object[] paramuser={user.getUserid()};
			//删除用户角色对应关系
			String hql2 = "delete from BsmsMnguserrole where userid = ?";
			sqlDao_h.excuteHql(hql2,paramuser);
			//删除用户session
			HttpServletRequest request = ServletActionContext.getRequest();
			List sessionList = (List)request.getSession().getServletContext().getAttribute(ApplicationConstants.SESSIONID);
			if(sessionList == null){
				sessionList = new ArrayList();
				Collections.synchronizedList(sessionList);
			}else{//同一用户不能在同时重复登录
				Object[] sessionListCopy= sessionList.toArray();
				for(int i=0; i< sessionListCopy.length; i++){
					HttpSession sess = (HttpSession)sessionListCopy[i];
					try{
						User us = (User)sess.getAttribute(ApplicationConstants.LOGONUSER);
						if(user.getUserid().equals(us.getUserid()) && !(sess.getId().equals(request.getSession().getId()))){
							sessionList.remove(sess);
							sess.invalidate();
						}
					}catch(java.lang.IllegalStateException e){
						sessionList.remove(sess);
					}
				}
			}
			sessionList.add(request.getSession());
			request.getSession().getServletContext().setAttribute(ApplicationConstants.SESSIONID, sessionList);
		}
		
		return ret;
	}
	/**
	 * 密码重置，将用户密码设置为初始值，初始值在ApplicationConstants中设置。
	 * 
	 */
	public ServiceReturn resetuserspasswd(List<User> users,User login)throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		MD5 md5 = new MD5();
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String resetpassword=ApplicationConstants.RESETPASSWORD;//获取初始密码
		for (User user :users){
			String defaultpasswd =  md5.getMD5String(resetpassword);//初始密码设置
			Object[] paramuserpasswd={defaultpasswd,login.getUserid().toString(),date,user.getUserid()};
//			Object[] paramuserpasswd={user.getPassword(),login.getUserid().toString(),date,user.getUserid()};
			String hql = "update User set password=?, lastmoduser=?, lastmoddate=?,infoex2='' where userid=?";
			int updated = sqlDao_h.excuteHql(hql,paramuserpasswd);
			if(updated != 1){
				ret.setSuccess(false);
				return ret;
			}
		}
		
		return ret;
	}
	
	/** 
	 * @Title: authuser 
	 * @Description: 将该用户设为授权用户
	 * @return
	 * @throws Exception    
	 */ 
	public ServiceReturn authuser(List users) throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		for(int i=0;i<users.size();i++){
			String hql = "update User set infoex1=? where userid=?";
			User user = (User)users.get(i);
			Object[] paramuserpasswd={ApplicationConstants.NEEDAUTH,user.getUserid()};
			int updated = sqlDao_h.excuteHql(hql,paramuserpasswd);
			if(updated != 1){
				ret.setSuccess(false);
				ret.setErrmsg("");
				return ret;
			}
		}
		return ret;
	}
	
	/** 
	 * @Title: authuser 
	 * @Description: 取消用户授权权限
	 * @return
	 * @throws Exception    
	 */ 
	public ServiceReturn cancelAuthuser(List users) throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		for(int i=0;i<users.size();i++){
			String hql = "update User set infoex1=? where userid=?";
			User user = (User)users.get(i);
			Object[] paramuserpasswd={ApplicationConstants.DONOTNEEDAUTH,user.getUserid()};
			int updated = sqlDao_h.excuteHql(hql,paramuserpasswd);
			if(updated != 1){
				ret.setSuccess(false);
				ret.setErrmsg("");
				return ret;
			}
		}
		return ret;
	}
	
	/**
	 * 为用户分配角色
	 */
	public ServiceReturn assignUserRoles_itransc(User user) throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String userid = user.getUserid();
		Object[] paras={userid};
		sqlDao_h.excuteHql("DELETE FROM BsmsMnguserrole u WHERE u.userid = ?", paras);
		if(user.getRoles() != null){
			Set<Role> roles = user.getRoles();
			for(Role role : roles){
				BsmsMnguserrole ur=new BsmsMnguserrole();
				ur.setRoleid(role.getRoleid());
				ur.setUserid(userid);
				sqlDao_h.saveRecord(ur);
			}
		}
		return sRet;
	}


	/**
	 * 编辑用户信息
	 */
	public ServiceReturn editSystemUser_itransc(User user) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		
		 try {
			sqlDao_h.updateRecord(user);
		} catch (Exception e) {
			ret.setSuccess(false);
			logger.error(e.getLocalizedMessage(),e);
			throw e;
		}
		return ret;
	}

	/**
	 * 修改密码方法
	 */
	public ServiceReturn updatePassword_itransc(PasswordEntity passwordEntity) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String lastmoddate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		Object[] parameters={passwordEntity.getUpdatePassword(),lastmoddate,lastmoddate,new String(passwordEntity.getUserId())};
		int updated=sqlDao_h.excuteHql("update User set PASSWORD =?,infoex2=?,logintime=? where userid=?", parameters);
		if(updated == 1){
			return ret;
		}
		ret.setSuccess(false);
		return ret;
	}
	
	public User getUserData_itransc(User user) throws Exception {
		String sql="SELECT u.*,r.*,un.UNITID, un.UNITNAME, un.PARENTUNITID, un.UNITLEVEL FROM BSMS_MNGUSERINFO u LEFT OUTER JOIN "+
	    	"BSMS_MNGUSERROLE ur ON u.USERID = ur.USER_ID LEFT JOIN BSMS_MNGROLEINFO "+
	    	"r ON ur.ROLE_ID = r.ROLEID INNER JOIN BSMS_MNGUNITINFO un ON "+
	    	"u.UNITID = un.UNITID where u.USERCODE = #usercode#";
		User result_user = (User) sqlDao_h.getRecordBySql(sql, user.getClass());
		return result_user;
	}
	
	public ServiceReturn getUserChecked(PasswordEntity passwordEntity) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String userId=passwordEntity.getUserId();
		String passwd=passwordEntity.getNowPassword();
		String hql=" FROM User where USERID = "+"'"+userId+"'"+" AND PASSWORD = '"+passwd+"'";
		
		User user = (User)sqlDao_h.getRecord(hql);
		
		//Long num=sqlDao_h.getRecordCount(hql);
		
		if(user==null){
			ret.setSuccess(false);
		}else{
			ret.put(ServiceReturn.FIELD1, user);
		}
		return ret;
	}
	
	public ServiceReturn passwordRedo_itransc(List<User> users) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
//		MD5 md5 = new MD5();
//		for(User user : users){
//			//logger.info("-==-==-"+user.getUserid());
//			user.setPassword(md5.getMD5String("111111"));
//			sqlDao_i.updateRecord("SystemUser.updateRedoPassword", user);
//		}
		return ret;
	}
	/**************System User Service, End****************/

	
	/**************System Unit Service, Start****************/
	public ServiceReturn addSystemUnit_htransc(Unit unit) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		ret.put(ServiceReturn.FIELD1, sqlDao_h.saveRecord(unit));
		return ret;
	}

	
	public ServiceReturn editSystemUnit_htransc(Unit unit) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		Unit temp_unit = sqlDao_h.getRecordById(Unit.class, unit.getUnitid());
		if(temp_unit != null){
			temp_unit.setUnitname(unit.getUnitname());
			return ret;
		}
		//修改的数据已被删除
		ret.setSuccess(ServiceReturn.FAILURE);
		return ret;
	}
	
	
	public ServiceReturn deleteSystemUnit_htransc(Unit unit) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		int id= Integer.parseInt(unit.getUnitid());
		List<Unit> unit_list = sqlDao_h.getAllRecords(Unit.class);
		List<Unit> temp_list =new ArrayList();
		for(Unit temp_unit:unit_list){
			if(Integer.parseInt(temp_unit.getUnitid())==id){
				sqlDao_h.deleteRecord(temp_unit);
				break;
			}
			if(temp_list.size()==0){
				if(Integer.parseInt(temp_unit.getParentunitid())==id){
					sqlDao_h.deleteRecord(temp_unit);
					temp_list.add(temp_unit);
				}
			}else{
				for(Unit u:temp_list){
					if(Integer.parseInt(u.getParentunitid())==id){
						sqlDao_h.deleteRecord(u);
						temp_list.add(u);
					}
				}
			}
			
		}
		
		return ret;
	}
	/**************System Unit Service, End****************/

	//获取操作员信息
	public ServiceReturn getuser(User user) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		List<User> users =new ArrayList();
		Map map=new HashMap();
		String[][] orders={{ "userid", "asc" }};
		if(user.getUsertype().equals("1")){
			//如果是管理员可以查看所有用户
		}else{
			map.put("userid", user.getUserid());
		}
		users = sqlDao_h.getRecordList(User.class, map, orders, false);
		List<User> user1 = new ArrayList<User>();
		for(int i=0;i<users.size();i++){
			User u0 = users.get(i);
			User ut = new User();
			ut.setUnitname(u0.getUnitid()+"  "+u0.getUsername());
			user1.add(ut);
		}
		List<User> uts = new ArrayList<User>();
		User u = new User();
		if(user.getUsertype().equals("1")){
			u.setUsername(""+""+"全部");
			uts.add(u);
		}
		for(int i=0;i<users.size();i++){
			User u1 = (User)users.get(i);
			Unit unit = u1.getUnit();
			if(Integer.parseInt(unit.getUnitid())!=1){
				u1.setUnitname(unit.getUnitid()+""+unit.getUnitname());
			}
			uts.add(u1);
		}
		ret.put(ServiceReturn.FIELD1, uts);
		ret.put(ServiceReturn.FIELD2, user1);
		
		return ret;

	}

	/**
	 * <p>Title: findLog</p> 
	 * <p>Description: 分页查询日志信息</p> 
	 * @param map
	 * @param orders
	 * @param pageInfo
	 * @return
	 * @throws Exception 
	 * @see com.agree.framework.web.service.administration.IAdministrationService#findLog(java.util.Map, java.lang.String[][], com.agree.framework.dao.entity.Page) 
	 */ 
	public List findLog(Map map, Page pageInfo)	throws Exception {
		//获得今年的年份和输入的起始年份坐对比，如果相同查询mnglog日志表    2013-2-21修改
//		Date now = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
//		String nowyear =sdf.format(now);
//		if(map.get("startDate").toString().substring(0,4).equals(nowyear) ){
			StringBuffer sql = new StringBuffer();
			sql.append("from BsmsMnguseroprlog f where f.unitid in (:units) ");

			if(StringUtils.isNotEmpty((String)map.get("username"))){
				sql.append(" and f.username like :username");
			}
			//hql+="and unitid in (:units) ";
			//String username=(String)map.get("username");
			if(StringUtils.isNotEmpty((String)map.get("startDate"))){
				sql.append(" and f.logdate >= :startDate ");
			}
			if(StringUtils.isNotEmpty((String)map.get("endDate"))){
				sql.append("and f.logdate <= :endDate ");
			}
			if(StringUtils.isNotEmpty((String)map.get("operation"))){
				sql.append("and f.operation like :operation ");
			}
			sql.append("order by f.logdate desc,f.logtime desc");
			List list = sqlDao_h.queryPage(sql.toString(),map,pageInfo,true);

			return list;
//		}else{
//			StringBuffer sql = new StringBuffer();
//			sql.append("from THisMnguseroprlog f where f.unitid in (:units) ");
//
//			if(StringUtils.isNotEmpty((String)map.get("username"))){
//				sql.append(" and f.username like :username");
//			}
//			//hql+="and unitid in (:units) ";
//			//String username=(String)map.get("username");
//			if(StringUtils.isNotEmpty((String)map.get("startDate"))){
//				sql.append(" and f.logdate >= :startDate ");
//			}
//			if(StringUtils.isNotEmpty((String)map.get("endDate"))){
//				sql.append("and f.logdate <= :endDate ");
//			}
//			if(StringUtils.isNotEmpty((String)map.get("operation"))){
//				sql.append("and f.operation like :operation ");
//			}
//			sql.append("order by f.logdate desc,f.logtime desc");
//			List list = sqlDao_h.queryPage(sql.toString(),map,pageInfo,true);
//			return list;
//		}
	}
	
	/**
	 * @Title: findLogDoExcel 
	 * @Description: 操作记录查询，不分页，提供Excel下载数据
	 * @param @param map
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return List    返回类型 
	 * @throws
	 */
	public List findLogDoExcel(Map map) throws Exception {
		//获得今年的年份和输入的起始年份坐对比，如果相同查询mnglog日志表    2013-2-21修改
//		Date now = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
//		String nowyear =sdf.format(now);
//		if(map.get("startDate").toString().substring(0,4).equals(nowyear) ){
			StringBuffer sql = new StringBuffer();
			sql.append("from BsmsMnguseroprlog f where f.unitid in (:units) ");
			
			if(StringUtils.isNotEmpty((String)map.get("username"))){
				sql.append(" and f.username like :username");
			}
			//hql+="and unitid in (:units) ";
			//String username=(String)map.get("username");
			if(StringUtils.isNotEmpty((String)map.get("startDate"))){
				sql.append(" and f.logdate >= :startDate ");
			}
			if(StringUtils.isNotEmpty((String)map.get("endDate"))){
				sql.append("and f.logdate <= :endDate ");
			}
			if(StringUtils.isNotEmpty((String)map.get("operation"))){
				sql.append("and f.operation like :operation ");
			}
			sql.append("order by f.logdate desc,f.logtime desc");
			List list = sqlDao_h.getRecordList(sql.toString(), map, false);
			return list;
//		}else{
//			StringBuffer sql = new StringBuffer();
//			sql.append("from THisMnguseroprlog f where f.unitid in (:units) ");
//			
//			if(StringUtils.isNotEmpty((String)map.get("username"))){
//				sql.append(" and f.username like :username");
//			}
//			//hql+="and unitid in (:units) ";
//			//String username=(String)map.get("username");
//			if(StringUtils.isNotEmpty((String)map.get("startDate"))){
//				sql.append(" and f.logdate >= :startDate ");
//			}
//			if(StringUtils.isNotEmpty((String)map.get("endDate"))){
//				sql.append("and f.logdate <= :endDate ");
//			}
//			if(StringUtils.isNotEmpty((String)map.get("operation"))){
//				sql.append("and f.operation like :operation ");
//			}
//			sql.append("order by f.logdate desc,f.logtime desc");
//			List list = sqlDao_h.getRecordList(sql.toString(), map, false);
//			return list;
//		}
	}
	
	/** 
	 * @Title: assignQuarters 
	 * @Description: 分配用户岗位
	 * @return
	 * @throws Exception    
	 */ 
	public ServiceReturn assignQuarters(Map map) throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String hql = "update User set infoex3=? where userid=?";
		Object[] params = {map.get("infoex3"),map.get("userid").toString()};
		int updated = sqlDao_h.excuteHql(hql,params);
		if(updated != 1){
			ret.setSuccess(false);
			ret.setErrmsg("分配岗位失败");
			return ret;
		}
		return ret;
	}
	/** 
	 * @Title: cancelQuarters 
	 * @Description: 取消用户岗位
	 * @return
	 * @throws Exception    
	 */ 
	public ServiceReturn cancelQuarters(List users) throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		for(int i=0;i<users.size();i++){
			String hql = "update User set infoex3='' where userid=?";
			User user = (User)users.get(i);
			Object[] params = {user.getUserid()};
			int updated = sqlDao_h.excuteHql(hql,params);
			if(updated != 1){
				ret.setSuccess(false);
				ret.setErrmsg("取消岗位失败");
				return ret;
			}
		}
		return ret;
	}

	/** 
	 * @Title: isInUnit 
	 * @Description: 判断用户是否在这个部门
	 * @return
	 * @throws Exception    
	 */ 
	public ServiceReturn  isInSameUnit(User user,Map map ) throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String unitid=user.getUnit().getUnitid();
		Map para=new HashMap();
		para.put("author", map.get("author"));
		//判断其与当前用户是否属于同一部门
		String hql = "select count(*) from User where usercode=:author and unitid='"+unitid + "'";
		Long num=sqlDao_h.getRecordCount(hql,para, false);
		if(num==0){
			ret.setErrmsg("授权用户不存在或与当前用户不属于同一部门,不可授权");
			return ret;
		}
		//判断用户是否可以授权
		hql = "select count(*) from User where usercode=:author and infoex1='1'";
		num=sqlDao_h.getRecordCount(hql,para, false);
		if(num==0){
			ret.setErrmsg("该用户不可授权，请用其他授权用户再试！");
			return ret;
		}
		String userid=user.getUserid();
		para.put("userid", userid);
		logger.info("username="+userid);
		logger.info("author="+map.get("author"));
		hql = "select count(*) from User where usercode=:author and infoex1='1' and userid<>:userid";
		num=sqlDao_h.getRecordCount(hql,para, false);
		if(num==0){
			ret.setErrmsg("交易用户不能给自己授权，请用其他授权用户再试！");
			return ret;
		}
		
		MD5 md5 = new MD5();
		String password = (String) map.get("passwd");
		map.put("passwd",md5.getMD5String(password));
		
		hql = "select count(*) from User where usercode=:author and password=:passwd";
		num=sqlDao_h.getRecordCount(hql,map, false);
		if(num==0){
			ret.setErrmsg("授权用户验证失败！");
			return ret;
		}
		return ret;
		
		
	}
	
}
