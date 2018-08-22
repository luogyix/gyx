package com.agree.abt.service.confmanager;

import net.sf.json.JSONObject;

import com.agree.abt.model.confManager.BtSysParameter;
import com.agree.framework.web.common.ServiceReturn;

/**
 * 排队机界面-叫号规则参数配置接口
 * @ClassName: ISystemParameterService.java
 * @company 赞同科技
 * @author XiWang
 * @date 2014-4-25
 */
public interface ISystemParameterService {
	/**
	 * 查询系统参数
	 * @param branch 机构号
	 * @param parameter_flag 参数类型
	 * @throws Exception
	 */
	public ServiceReturn copysystemParameter(JSONObject obj,String template_id)throws Exception ;
	public ServiceReturn copysystemParameter(JSONObject obj)throws Exception;
	public BtSysParameter querySystemParameterById(String parameter_id)throws Exception;
	public ServiceReturn querySystemParameterByBranch(String branch) throws Exception; 
	public ServiceReturn querySystemParameter(String branch,String parameter_flag) throws Exception;
	public boolean flagSystemParameter(String id, String parameter_flag) throws Exception;
	public ServiceReturn addSystemParameter(JSONObject obj) throws Exception;
	public ServiceReturn addSystemParameter(JSONObject obj,String par_id) throws Exception;
	public ServiceReturn editSystemParameter(JSONObject obj) throws Exception;
	public ServiceReturn editSystemParameter(JSONObject obj,String par_id) throws Exception;
	public ServiceReturn delSystemParameter(String id) throws Exception;
	public boolean isExist(ServiceReturn ret,String parameter_name,String parameter_flag) throws Exception;
}
