package com.agree.abt.service.confmanager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import com.agree.abt.model.confManager.ThemeInfo;
import com.agree.abt.service.confmanager.IThemeManagerService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.base.BaseService;

public class ThemeManagerServiceImpl extends BaseService implements IThemeManagerService {
	
	@SuppressWarnings("unchecked")
	@Transactional  
	public List<ThemeInfo> queryThemeInfo(String branch) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("branch", branch);
		String hql ="from ThemeInfo " +
					"where branch = :branch or branch in( " +
					"	select parentunitid from Unit " +
					"	where unitid in (" +
					"		select parentunitid from Unit " +
					"		where unitid in (" +
					"			select parentunitid from Unit " +
					"			where unitid in ( " +
					"				select parentunitid from Unit " +
					"				where unitid = :branch" +
					"			)or unitid = :branch " +
					"		) or unitid = :branch " +
					"	) or unitid = :branch" +
					")";
		return sqlDao_h.getRecordList(hql,map,false);
	}

	@SuppressWarnings("unchecked")
	@Transactional  
	public List<ThemeInfo> queryTheme4Page(JSONObject jsonObj, Page pageInfo, String branch) throws Exception {
		Map<String,String> map=new HashMap<String,String>();
		map.put("branch", branch);
		String hql ="from ThemeInfo " +
					"where branch = :branch or branch in( " +
					"	select parentunitid from Unit " +
					"	where unitid in (" +
					"		select parentunitid from Unit " +
					"		where unitid in (" +
					"			select parentunitid from Unit " +
					"			where unitid in ( " +
					"				select parentunitid from Unit " +
					"				where unitid = :branch" +
					"			)or unitid = :branch " +
					"		) or unitid = :branch " +
					"	) or unitid = :branch" +
					")";
		List<?> list = sqlDao_h.queryPage(hql,map,pageInfo,false);//此方法传入的hql为完整的hql，不需传入排序参数
		return (List<ThemeInfo>) list;
	}
	@Transactional  
	public ServiceReturn addThemeInfo(String jsonString) throws Exception {
		JSONObject obj = JSONObject.fromObject(jsonString);
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		//BtSysParameter systemParameter = (BtSysParameter)JSONObject.fromObject(strJson);
		ThemeInfo theme = (ThemeInfo)JSONObject.toBean(obj, ThemeInfo.class);
		theme.setTheme_id(UUID.randomUUID().toString().substring(0, 8));
		theme.setBranch(user.getUnitid());
		if("on".equals(obj.getString("default_flag"))||"0".equals(obj.getString("default_flag"))){
			Map<String,String> map=new HashMap<String,String>();
			map.put("branch", user.getUnitid());
			Long count = sqlDao_h.getRecordCount("select count(*) from ThemeInfo where branch=:branch and default_flag='0'",map);
			if(count > 0){//默认标志已存在
				ret.setSuccess(false);
				ret.setOperaterResult(false);
				ret.setErrmsg("此机构已存在默认的主题！");
				return ret;
			}
			theme.setDefault_flag("0");
		}else{
			theme.setDefault_flag("1");
		}
		sqlDao_h.saveRecord(theme);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		return sRet;
	}
	@Transactional  
	public ServiceReturn editThemeInfo(String jsonString) throws Exception {
		JSONObject obj = JSONObject.fromObject(jsonString);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		ThemeInfo theme = (ThemeInfo)JSONObject.toBean(obj, ThemeInfo.class);
		if("on".equals(obj.getString("default_flag"))||"1".equals(obj.getString("default_flag"))){
			Map<String,String> map=new HashMap<String,String>();
			map.put("branch", theme.getBranch());
			map.put("theme_id", theme.getTheme_id());
			Long count = sqlDao_h.getRecordCount("select count(*) from ThemeInfo where branch=:branch and default_flag='0' and theme_id!=:theme_id",map);
			if(count > 0){//默认标志已存在
				ret.setSuccess(false);
				ret.setOperaterResult(false);
				ret.setErrmsg("此机构已存在默认的主题！");
				return ret;
			}
			theme.setDefault_flag("0");
		}else{
			theme.setDefault_flag("1");
		}
		sqlDao_h.updateRecord(theme);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		return sRet;
	}
	@Transactional  
	public ServiceReturn delThemeInfo(String theme_ids) throws Exception {
		String[] theme_id_list = theme_ids.substring(1).split(",");
		for (int i = 0; i < theme_id_list.length; i++) {
			sqlDao_h.deleteById(ThemeInfo.class, theme_id_list[i]);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		return ret;
	}

}
