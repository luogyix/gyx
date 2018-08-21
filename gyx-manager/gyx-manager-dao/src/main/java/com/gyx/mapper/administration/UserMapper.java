package com.gyx.mapper.administration;

import com.gyx.administration.User;


public interface UserMapper {
	
	
	/**
	 * 根据userCode获得User,包括用户的角色及拥有的菜单
	 * @param userCode
	 * @return
	 */
	public User selectUserAllDataByUserCode(String userCode);
}
