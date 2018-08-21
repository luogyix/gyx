package com.gyx.service.framework.welcome.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gyx.administration.Role;
import com.gyx.administration.User;
import com.gyx.exception.AppException;
import com.gyx.mapper.administration.ModuleMapper;
import com.gyx.mapper.administration.UserMapper;
import com.gyx.pubutil.MD5;
import com.gyx.service.framework.welcome.ILogonService;

@Service
public class LogonServiceImpl implements ILogonService {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ModuleMapper moduleMapper;

	/**
	 * @throws Exception 
	 * 校验用户信息
	 * @throws  
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getLogonUser_itransc(User user) throws Exception{
		MD5 md5 = new MD5();
		String password = user.getPassword();
		user.setPassword(md5.getMD5String(password));
		User result_user = null;
		try {
			result_user = userMapper.selectUserAllDataByUserCode(user.getUsercode());
		} catch (Exception e) {
			throw new AppException("您好，用户出现异常:您所在的机构或分配的角色不存在，请联系管理员！");
		}
		
		if(result_user!=null && user.getPassword().equals(result_user.getPassword())){
			result_user.setPasswd(password);
			if(!result_user.getState().equals("1")){
				throw new AppException("");
			}
			//获得用户角色集合
			Set<Role> roles = result_user.getRoles();
			List roleids = new ArrayList<String>();
			for (Role role : roles) {
				if(role.getEnabled().toString().equals("1")
						&& role.getRoletype().equals("1")){
					roleids.add(role.getRoleid());
				}
			}
			if(roleids.size() == 0){
				throw new AppException("抱歉，您的账户未被授权，请联系管理员");
			}
			Map paraMap = new HashMap();
			paraMap.put("roleIds", roleids);
			List privileges1 = moduleMapper.selectModuleInRoleIdList(paraMap);
			Map map = new HashMap();
			map.put("user", result_user);
			map.put("privileges", privileges1);
			/**
			 * 用户拥有的菜单直接可直接从user对象中获取
			 * map.put("privileges", "");
			 */
			return map;
		}else {
			if(result_user != null){
				throw new AppException("用户名或密码错误");
			}else{
				throw new AppException("exception.invaliduser");
			}
		}
	}

}
