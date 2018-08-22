package com.agree.framework.web.service.administration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.agree.framework.exception.AppErrorcode;
import com.agree.framework.exception.AppException;
import com.agree.framework.web.form.administration.Role;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.base.BaseService;
import com.agree.util.MD5;
@SuppressWarnings({"unchecked","rawtypes"})
public class LogonService extends BaseService implements ILogonService {
	
	/**
 * 根据用户名和密码获取用户信息
 */
	public Map getLogonUser_itransc(User user) throws Exception {
		MD5 md5 = new MD5();
		String password = user.getPassword();
		
		//String prcid = user.getPrcid();
		
		user.setPassword(md5.getMD5String(password));
		User result_user = null;
//		Long state = (Long)sqlDao_h.getRecord("select state from User u where usercode='" + user.getUsercode()
//				+ "' and password='"+user.getPassword()+"'");
//		if(state!=null&&state==2){
//			throw new AppException("您好，此用户已被禁用，请联系管理员！");
//		}
		try {
			result_user = (User) sqlDao_h.getRecord("from User u where usercode='" + user.getUsercode()+"'");
		} catch (HibernateObjectRetrievalFailureException e) {
			throw new AppException("您好，用户出现异常:您所在的机构或分配的角色不存在，请联系管理员！");
		}
		
		if (result_user!=null&&user.getPassword().equals(result_user.getPassword())) {
			result_user.setPasswd(password);
			//if (!(result_user.getState().equals(new Long(1)))) {// 用户禁用
			if (!result_user.getState().equals("1")) {// 用户禁用
				throw new AppException(AppErrorcode.USERPROHIBITED);
			}
			//获得用户角色集合
			Set<Role> roles = result_user.getRoles();
			
			List roleids=new ArrayList();
			for(Role role:roles){
				if("1".equals(role.getEnabled().toString())&&"1".equals(role.getRoletype()))
				{
					roleids.add(role.getRoleid());
				}
			}
			
			//----判断用户被赋予的角色是否全部禁用-----丅
//			for(Role role:roles){
//				if("1".equals(role.getEnabled().toString())){
//					continue;
//				}else{
//					roleids.remove(role.getRoleid());//去除用户角色为禁用的角色id
//				}
//			}
			//----判断管理台角色-----丅
//			for(Role role:roles){
//				if("1".equals(role.getRoletype())){
//					continue;
//				}else{
//					roleids.remove(role.getRoleid());//去除非管理台角色
//				}
//			}
			//-----------------------------------丄
			
			//如果用户没有权限
			if (roleids.size() == 0) {
				throw new AppException(AppErrorcode.USERNOPRIVILIDGE);
			}
			
			Map paraMap=new HashMap();
			paraMap.put("roleids", roleids);
			//获得用户权限
			String hql="select distinct new Module(m.moduleid,  m.modulename,  m.parentmoduleid,"+
								"m.moduletype,  m.moduleaction,  m.moduleorder,"+
								" m.modulelevel,  m.logflag,  m.privilegeType, m.createuser,"+
								" m.createdate,  m.lastmoduser,  m.lastmoddate,"+
								 "m.moduleImg,  m.authflag,  m.remark1,  m.remark2,m.remark3) "+
								" from BsmsMngrolemodule r,Module m "+
								" where r.roleid in(:roleids) and r.moduleid=m.moduleid  "+
								" order by modulelevel,moduleorder asc ";
			
			List privileges1=this.sqlDao_h.getRecordList(hql, paraMap, false);
			
			Map map=new HashMap();
			map.put("user", result_user);
			map.put("privileges", privileges1);
			return map;
		}else{
			if (result_user!=null) {
				throw new AppException("用户名或密码错误");
			}else{
				throw new AppException(AppErrorcode.INVALIDUSER);
			}
		}
	}
	/**
	 * 验证柜员的登录
	 */
	public Map getLogonUser_teller(User user) throws Exception {
		User result_user = new User();
		Object obj=sqlDao_h.getRecord("select state from User u where usercode='" + user.getUsercode()
				+"'");
		Long state=Long.valueOf(String.valueOf(obj));
		if(state!=null&&state==2){
			throw new AppException("您好，此用户已被禁用，请联系管理员！");
		}
		try {
			result_user = (User) sqlDao_h.getRecord("from User u where usercode='" + user.getUsercode()
					+"'");
		} catch (HibernateObjectRetrievalFailureException e) {
			throw new AppException("您好，用户出现异常:您所在的机构或分配的角色不存在，请联系管理员！");
		}

		if (result_user != null) {
			if (!(result_user.getState().equals(new String("1")))) {// 用户禁用
				throw new AppException(AppErrorcode.USERPROHIBITED);
			}
			//获得用户角色集合
			Set<Role> roles = result_user.getRoles();
			
			List roleids=new ArrayList();
			for(Role role:roles){
				roleids.add(role.getRoleid());
			}
			
			//----判断用户被赋予的角色是否全部禁用-----丅
			
			for(Role role:roles){
				if("1".equals(role.getEnabled().toString())){
					continue;
				}else{
					roleids.remove(role.getRoleid());//去除用户角色为禁用的角色id
				}
			}
			//-----------------------------------丄
			
			//如果用户没有权限
			if (roleids.size() == 0) {
				throw new AppException(AppErrorcode.USERNOPRIVILIDGE);
			}
			
			Map paraMap=new HashMap();
			paraMap.put("roleids", roleids);
			//获得用户权限
			String hql="select distinct new Module(m.moduleid,  m.modulename,  m.parentmoduleid,"+
								"m.moduletype,  m.moduleaction,  m.moduleorder,"+
								" m.modulelevel,  m.logflag,  m.privilegeType, m.createuser,"+
								" m.createdate,  m.lastmoduser,  m.lastmoddate,"+
								 "m.moduleImg,  m.authflag,  m.remark1,  m.remark2,m.remark3) "+
								" from BsmsMngrolemodule r,Module m "+
								" where r.roleid in(:roleids) and r.moduleid=m.moduleid  "+
								" order by modulelevel,moduleorder asc ";
			
			List privileges1=this.sqlDao_h.getRecordList(hql, paraMap, false);
			
			Map map=new HashMap();
			map.put("user", result_user);
			map.put("privileges", privileges1);
			return map;
		}else{
			throw new AppException(AppErrorcode.INVALIDUSER);
		}
	}
}
