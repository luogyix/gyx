package com.agree.framework.web.service.administration;

import com.agree.framework.web.service.base.IBaseService;

public interface IClearHisLogServer extends IBaseService {
	/**
	 * 
	 * @Title: clearHisLogData 
	 * @Description: 到1月1号清楚原表数据备份到t_BSMS_mnguseroprlog
	 * @param 
	 * @throws Exception
	 */
	public void clearHisLogData() throws Exception;
}
