package com.agree.abt.service.confmanager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import com.agree.abt.model.confManager.BtSysParameter;
import com.agree.abt.service.confmanager.ISystemParameterService;
import com.agree.framework.exception.AppException;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.base.BaseService;

/**
 * 排队机界面-叫号规则参数配置实现类
 * @ClassName: SystemParameterServiceImpl.java
 * @company 赞同科技
 * @author XiWang
 * @date 2014-4-25
 */
public class SystemParameterServiceImpl extends BaseService implements ISystemParameterService {

	@Transactional
	public BtSysParameter querySystemParameterById(String parameter_id)throws Exception{
		Map<String,String> map=new HashMap<String,String>();
		map.put("parameter_id", parameter_id);
		String hql="from BtSysParameter where parameter_id=:parameter_id";
		BtSysParameter sysParameter=(BtSysParameter) sqlDao_h.getRecord(hql, map, false);
		return sysParameter;
	}
	
	@Transactional
	public ServiceReturn querySystemParameterByBranch(String branch) throws Exception{
		Map<String,String> map=new HashMap<String,String>();
		map.put("branch", branch);
		String hql ="from BtSysParameter " +
					"where (branch=:branch or branch in ( " +
					"	select parentunitid from Unit " +
					"	where unitid = :branch or unitid in ( " +
					"		select parentunitid from Unit " +
					"		where unitid = :branch or unitid in ( " +
					"			select parentunitid from Unit " +
					"			where unitid = :branch or unitid in ( " +
					"				select parentunitid from Unit " +
					"				where unitid = :branch or unitid in ( " +
					"					select parentunitid from Unit " +
					"					where unitid = :branch or unitid in ( " +
					"						select parentunitid from Unit " +
					"						where unitid = :branch" +
					"					) " +
					"				) " +
					"			) " +
					"		) " +
					"	)" +
					"))  order by branch";
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		@SuppressWarnings("unchecked")
		List<BtSysParameter> list = sqlDao_h.getRecordList(hql,map,false);
		sRet.put(ServiceReturn.FIELD1, list);
		return sRet;
	}
	
	
	@Transactional
	public ServiceReturn querySystemParameter(String branch,String parameter_flag) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("branch", branch);
		map.put("parameter_flag", parameter_flag);
		String hql ="from BtSysParameter " +
					"where parameter_flag=:parameter_flag and (branch=:branch or branch in ( " +
					"	select parentunitid from Unit " +
					"	where unitid = :branch or unitid in ( " +
					"		select parentunitid from Unit " +
					"		where unitid = :branch or unitid in ( " +
					"			select parentunitid from Unit " +
					"			where unitid = :branch or unitid in ( " +
					"				select parentunitid from Unit " +
					"				where unitid = :branch or unitid in ( " +
					"					select parentunitid from Unit " +
					"					where unitid = :branch or unitid in ( " +
					"						select parentunitid from Unit " +
					"						where unitid = :branch" +
					"					) " +
					"				) " +
					"			) " +
					"		) " +
					"	)" +
					")) order by branch";
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		@SuppressWarnings("unchecked")
		List<BtSysParameter> list = sqlDao_h.getRecordList(hql,map,false);
		sRet.put(ServiceReturn.FIELD1, list);
		return sRet;
	}

	/**
	 * 2016年5月6日修改部分==================================================start========================
	 * 判断叫号规则参数名称是否已存在 */
	public boolean isExist(ServiceReturn ret,String parameter_name,String parameter_flag) throws Exception{
			Map<String,String> map=new HashMap<String,String>();
			map.put("parameter_name", parameter_name);
			map.put("parameter_flag", parameter_flag);
			Long count = sqlDao_h.getRecordCount("select count(*) from BtSysParameter where parameter_name=:parameter_name and parameter_flag=:parameter_flag",map);
			if(count > 0){//默认标志已存在
				ret.setSuccess(false);
				ret.setOperaterResult(false);
				ret.setErrmsg("已存在该名字的参数，请修改参数名称！");
				return true;
			}
		    return false;
		
	}
    //2016年5月6日修改部分==================================================end========================



	public ServiceReturn addSystemParameter(JSONObject obj) throws Exception {
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		//BtSysParameter systemParameter = (BtSysParameter)JSONObject.fromObject(strJson);
		BtSysParameter systemParameter = (BtSysParameter)JSONObject.toBean(obj, BtSysParameter.class);
		systemParameter.setParameter_id(UUID.randomUUID().toString().substring(0, 8));
		systemParameter.setBranch(user.getUnitid());
		if("true".equals(obj.getString("default_flag"))||"1".equals(obj.getString("default_flag"))){
			Map<String,String> map=new HashMap<String,String>();
			map.put("branch", user.getUnitid());
			map.put("parameter_flag", obj.getString("parameter_flag"));
			Long count = sqlDao_h.getRecordCount("select count(*) from BtSysParameter where branch=:branch and parameter_flag=:parameter_flag and default_flag='0'",map);
			if(count > 0){//默认标志已存在
//===2017-01-11 高艺祥修改部分 。修改内容：注释区域内代码，并添加向页面报错语句===============start===============================================================				
				/*ret.setSuccess(false);
				ret.setOperaterResult(false);
				ret.setErrmsg("此机构已存在默认的参数！");
				return ret;*/
				throw new AppException("此机构已存在默认参数");
//===========================================end====================================================================				
			}
			systemParameter.setDefault_flag("0");
		}else{
			systemParameter.setDefault_flag("1");
		}


		//2016年5月6日修改部分==================================================start========================
		String parameter_name=obj.getString("parameter_name");
		String parameter_flag=obj.getString("parameter_flag");
		if(isExist( ret,parameter_name,parameter_flag)){
			return ret;
		}
		//2016年5月6日修改部分==================================================end========================
		

		sqlDao_h.saveRecord(systemParameter);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		return sRet;
	}
	
	public boolean flagSystemParameter(String id, String parameter_flag) throws Exception {
		Map<String,String> map=new HashMap<String,String>();
		map.put("branch", id);
		map.put("parameter_flag", parameter_flag);
		Long count = sqlDao_h.getRecordCount("select count(*) from BtSysParameter where branch=:branch and parameter_flag=:parameter_flag and default_flag='0'",map);
		if(count > 0){//默认标志已存在
			return false;
		}else{
			return true;
		}
	}

	public ServiceReturn editSystemParameter(JSONObject obj) throws Exception {
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		BtSysParameter systemParameter = (BtSysParameter)JSONObject.toBean(obj, BtSysParameter.class);
		if("true".equals(obj.getString("default_flag"))||"1".equals(obj.getString("default_flag"))){
			Map<String,String> map=new HashMap<String,String>();
			map.put("branch", user.getUnitid());
			map.put("parameter_flag", obj.getString("parameter_flag"));
			//map.put("parameter_id", systemParameter.getParameter_id());
			Long count = sqlDao_h.getRecordCount("select count(*) from BtSysParameter where branch=:branch and parameter_flag=:parameter_flag and default_flag='0'",map);
			if(count > 0){//默认标志已存在
//===2017-01-11 高艺祥修改部分 。修改内容：注释区域内代码，并添加向页面报错语句===============start===============================================================				
				/*ret.setSuccess(false);
				ret.setOperaterResult(false);
				ret.setErrmsg("此机构已存在默认的参数！");
				return ret;*/
				throw new AppException("此机构已存在默认参数");
//===========================================end====================================================================
			}
			systemParameter.setDefault_flag("0");
		}else{
			systemParameter.setDefault_flag("1");
		}

		//2016年5月6日修改部分==================================================start========================
		String parameter_name=obj.getString("parameter_name");
		String parameter_flag=obj.getString("parameter_flag");
		Map<String,String> map=new HashMap<String,String>();
		map.put("parameter_name", parameter_name);
		map.put("parameter_id", obj.getString("parameter_id"));
		Long count = sqlDao_h.getRecordCount("select count(*) from BtSysParameter where parameter_name=:parameter_name and parameter_id=:parameter_id",map);
		if(count!=1){
			if(isExist( ret,parameter_name,parameter_flag)){
				return ret;
			}
		}
		//2016年5月6日修改部分==================================================end========================
		

		sqlDao_h.updateRecord(systemParameter);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		return sRet;
	}

	public ServiceReturn delSystemParameter(String id) throws Exception {
//		HttpServletRequest req = ServletActionContext.getRequest();
//		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
//		cona.reInit();
//		cona.setHeader("ibp.bms.b209_4.01", user.getUnitid(), user.getUsercode(),"03");
//		cona.set("parameter_id", id);
//		HashMap map = (HashMap) cona.exchange();
//		String validate="";
//		validate=cona.validMap(map);
//		if(validate!=null&&validate.trim().length()>0){
//			throw new AppException(validate);
//		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		return ret;
	}


	
	public ServiceReturn addSystemParameter(JSONObject obj, String par_id)
			throws Exception {
		//User user = (User) ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		//BtSysParameter systemParameter = (BtSysParameter)JSONObject.fromObject(strJson);
		BtSysParameter systemParameter = (BtSysParameter)JSONObject.toBean(obj, BtSysParameter.class);
		systemParameter.setParameter_id(UUID.randomUUID().toString().substring(0, 8));
		systemParameter.setBranch(par_id);
		if("true".equals(obj.getString("default_flag"))||"1".equals(obj.getString("default_flag"))){
			Map<String,String> map=new HashMap<String,String>();
			map.put("branch", par_id);
			map.put("parameter_flag", obj.getString("parameter_flag"));
			Long count = sqlDao_h.getRecordCount("select count(*) from BtSysParameter where branch=:branch and parameter_flag=:parameter_flag and default_flag='0'",map);
			if(count > 0){//默认标志已存在
				ret.setSuccess(false);
				ret.setOperaterResult(false);
				throw new AppException("此机构已存在默认参数");
/*************************************2017-01-11 18:51/高艺祥修改 。修改内容：注释以下代码 ********************************************************/
				/*ret.setErrmsg("此机构已存在默认的参数！");
				return ret;*/
/*************************************2017-01-11 18:51/高艺祥修改 ********************************************************/
			}
			systemParameter.setDefault_flag("0");
		}else{
			systemParameter.setDefault_flag("1");
		}



		//2016年5月6日修改部分==================================================start========================
		String parameter_name=obj.getString("parameter_name");
		String parameter_flag=obj.getString("parameter_flag");
		if(isExist( ret,parameter_name,parameter_flag)){
			return ret;
		}
		//2016年5月6日修改部分==================================================end========================
		


		sqlDao_h.saveRecord(systemParameter);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		return sRet;
	}

	public ServiceReturn editSystemParameter(JSONObject obj, String par_id)
			throws Exception {
		//User user = (User) ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		BtSysParameter systemParameter = (BtSysParameter)JSONObject.toBean(obj, BtSysParameter.class);
		if("true".equals(obj.getString("default_flag"))||"1".equals(obj.getString("default_flag"))){
			Map<String,String> map=new HashMap<String,String>();
			map.put("branch", par_id);
			map.put("parameter_flag", obj.getString("parameter_flag"));
			map.put("parameter_id", systemParameter.getParameter_id());
			Long count = sqlDao_h.getRecordCount("select count(*) from BtSysParameter where branch=:branch and parameter_flag=:parameter_flag and default_flag='0' and parameter_id!=:parameter_id",map);
			if(count > 0){//默认标志已存在
//===2017-01-11 高艺祥修改部分 。修改内容：注释区域内代码，并添加向页面报错语句===============start===============================================================				
				/*ret.setSuccess(false);
				ret.setOperaterResult(false);
				ret.setErrmsg("此机构已存在默认的参数！");
				return ret;*/
				throw new AppException("此机构已存在默认参数");
//===========================================end====================================================================
			}
			systemParameter.setDefault_flag("0");
		}else{
			systemParameter.setDefault_flag("1");
		}


		//2016年5月6日修改部分==================================================start========================
		String parameter_name=obj.getString("parameter_name");
		String parameter_flag=obj.getString("parameter_flag");
		Map<String,String> map=new HashMap<String,String>();
		map.put("parameter_name", parameter_name);
		map.put("parameter_id", obj.getString("parameter_id"));
		Long count = sqlDao_h.getRecordCount("select count(*) from BtSysParameter where parameter_name=:parameter_name and parameter_id=:parameter_id",map);
		if(count!=1){
			if(isExist( ret,parameter_name,parameter_flag)){
				return ret;
			}
		}
		//2016年5月6日修改部分==================================================end========================



				
		sqlDao_h.updateRecord(systemParameter);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		return sRet;
	}
	
	/**
	 * 复制菜单
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn copysystemParameter(JSONObject obj,String template_id)throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		//User user = (User) ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String parameter_id=obj.getString("parameter_id");
		//BtSysParameter systemParameter = (BtSysParameter)JSONObject.fromObject(strJson);
		BtSysParameter systemParameter = (BtSysParameter)JSONObject.toBean(obj, BtSysParameter.class);
		String interface_id=UUID.randomUUID().toString().substring(0, 8);
		systemParameter.setParameter_id(interface_id);
		systemParameter.setBranch(template_id);
		if("true".equals(obj.getString("default_flag"))||"1".equals(obj.getString("default_flag"))){
			Map<String,String> map=new HashMap<String,String>();
			map.put("branch", template_id);
			map.put("parameter_flag", obj.getString("parameter_flag"));
			Long count = sqlDao_h.getRecordCount("select count(*) from BtSysParameter where branch=:branch and parameter_flag=:parameter_flag and default_flag='0'",map);
			if(count > 0){//默认标志已存在
//===2017-01-11 高艺祥修改部分 。修改内容：注释区域内代码，并添加向页面报错语句===============start===============================================================				
				/*ret.setSuccess(false);
				ret.setOperaterResult(false);
				ret.setErrmsg("此机构已存在默认的参数！");
				return ret;*/
				throw new AppException("此机构已存在默认参数");
//===========================================end====================================================================				
			}
			systemParameter.setDefault_flag("0");
		}else{
			systemParameter.setDefault_flag("1");
		}

		//2016年5月6日修改部分==================================================start========================
		String parameter_name=obj.getString("parameter_name");
		String parameter_flag=obj.getString("parameter_flag");
		if(isExist( ret,parameter_name,parameter_flag)){
			return ret;
		}
		//2016年5月6日修改部分==================================================end========================
		sqlDao_h.saveRecord(systemParameter);
		ret.put("parameter_id", parameter_id);
		ret.put("interface_id", interface_id);
		return ret;
	}
	
	public ServiceReturn copysystemParameter(JSONObject obj)throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String parameter_id=obj.getString("parameter_id");
		//BtSysParameter systemParameter = (BtSysParameter)JSONObject.fromObject(strJson);
		BtSysParameter systemParameter = (BtSysParameter)JSONObject.toBean(obj, BtSysParameter.class);
		String interface_id=UUID.randomUUID().toString().substring(0, 8);
		systemParameter.setParameter_id(interface_id);
		if("true".equals(obj.getString("default_flag"))||"1".equals(obj.getString("default_flag"))){
			Map<String,String> map=new HashMap<String,String>();
			map.put("branch", user.getUnitid());
			map.put("parameter_flag", obj.getString("parameter_flag"));
			Long count = sqlDao_h.getRecordCount("select count(*) from BtSysParameter where branch=:branch and parameter_flag=:parameter_flag and default_flag='0'",map);
			if(count > 0){//默认标志已存在
//===2017-01-11 高艺祥修改部分 。修改内容：注释区域内代码，并添加向页面报错语句===============start===============================================================				
				/*ret.setSuccess(false);
				ret.setOperaterResult(false);
				ret.setErrmsg("此机构已存在默认的参数！");
				return ret;*/
				throw new AppException("此机构已存在默认参数");
//===========================================end====================================================================
			}
			systemParameter.setDefault_flag("0");
		}else{
			systemParameter.setDefault_flag("1");
		}

		//2016年5月6日修改部分==================================================start========================
		String parameter_name=obj.getString("parameter_name");
		String parameter_flag=obj.getString("parameter_flag");
		if(isExist( ret,parameter_name,parameter_flag)){
			return ret;
		}
		//2016年5月6日修改部分==================================================end========================
		sqlDao_h.saveRecord(systemParameter);
		ret.put("parameter_id", parameter_id);
		ret.put("interface_id", interface_id);
		return ret;
	}

}
