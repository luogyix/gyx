package com.agree.abt.service.pfs;

import com.agree.framework.web.common.ServiceReturn;

public interface ICustRateService 
{
	/**
	 * 导入费率信息
	 * @param jsonString
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn importCustRate(String jsonString) throws Exception;
}
