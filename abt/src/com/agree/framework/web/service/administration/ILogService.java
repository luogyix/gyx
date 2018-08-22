package com.agree.framework.web.service.administration;

/*import com.agree.framework.web.form.administration.Module;
import com.agree.framework.web.form.administration.Role;*/
import com.agree.framework.web.form.administration.BsmsMnguseroprlog;
import com.agree.framework.web.service.base.IBaseService;

public interface ILogService extends IBaseService {
	/**
	 * 
	 * @Title: insertUserOprLog_itransc 
	 * @Description: 用户操作日志查询
	 * @param useroprlog
	 * @throws Exception
	 */
	public void insertUserOprLog_itransc(BsmsMnguseroprlog useroprlog) throws Exception;
	
	//public Module getModuleByAction(Role role);
}
