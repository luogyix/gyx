package com.gyx.service.framework.welcome;


import java.util.Map;

import com.gyx.administration.User;

public interface ILogonService {
	
	
	@SuppressWarnings("rawtypes")
	public Map getLogonUser_itransc(User user)throws Exception;
}
