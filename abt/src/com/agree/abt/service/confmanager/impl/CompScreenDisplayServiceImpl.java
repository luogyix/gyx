package com.agree.abt.service.confmanager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.confManager.CompScreenDisplay;
import com.agree.abt.service.confmanager.ICompScreenDisplayService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.base.BaseService;

public class CompScreenDisplayServiceImpl extends BaseService implements ICompScreenDisplayService{

	/**
	 * 查询
	 */
	@SuppressWarnings("unchecked")
	public List<CompScreenDisplay> queryCompScreenInfo(String branch) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("branch", branch);
		String hql="from CompScreenDisplay  where branch=:branch";
		return sqlDao_h.getRecordList(hql,map,false);
	}

	/**
	 * 分页查询
	 */
	@SuppressWarnings("unchecked")
	public List<CompScreenDisplay> queryCompScreen4Page(
			net.sf.json.JSONObject jsonObj, Page pageInfo, String branch)
			throws Exception {
		Map<String,String> map=new HashMap<String,String>();
		map.put("branch", branch);
		String hql="from CompScreenDisplay  where branch=:branch";
		List<?> list = sqlDao_h.queryPage(hql,map,pageInfo,false);//此方法传入的hql为完整的hql，不需传入排序参数
		return (List<CompScreenDisplay>) list;
	}
	/**
	 * 添加综合屏信息
	 */
	public ServiceReturn addCompScreenInfo(String jsonString)
			throws Exception {
		
		JSONObject obj = JSONObject.fromObject(jsonString);
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		CompScreenDisplay compScreen= (CompScreenDisplay)JSONObject.toBean(obj, CompScreenDisplay.class);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		compScreen.setBranch(user.getUnitid());
		Map<String,String> map1=new HashMap<String,String>();
		map1.put("branch",compScreen.getBranch());
		String sql="select count(*) from CompScreenDisplay where branch=:branch";
		Long sum=sqlDao_h.getRecordCount(sql,map1);

		if(sum>0){//已经有屏
			String sql1="select max(comp_screen_id) from CompScreenDisplay where branch= "+compScreen.getBranch();		
			String max= sqlDao_h.getRecord(sql1).toString();
			
			max=String.valueOf(Long.parseLong(max)+1);
			compScreen.setComp_screen_id(max);
		}else{
			compScreen.setComp_screen_id(compScreen.getBranch()+"00001");
		}
		compScreen.setDefault_flag("on".equals(obj.getString("default_flag"))?"1":"0");
		  if("1".equals(compScreen.getDefault_flag())){
			  Map<String,String> map=new HashMap<String,String>();
				map.put("branch",compScreen.getBranch());
			Long count =sqlDao_h.getRecordCount(
				"select count(*) from CompScreenDisplay where branch=:branch and default_flag='1'",map);
			if(count>0)
			{
				sRet.setSuccess(false);
				sRet.setOperaterResult(false);
				sRet.setErrmsg("已有默认综合屏参数，无法添加！");
				return sRet;
			}
			compScreen.setDefault_flag("1");
		}
		  else{
			  compScreen.setDefault_flag("0");
		  }
		  sqlDao_h.saveRecord(compScreen);
		return sRet;
	}

     /**
      * 修改综合屏信息
      */
	
	public ServiceReturn editCompScreenInfo(String jsonString)
			throws Exception {
		JSONObject obj = JSONObject.fromObject(jsonString);
		CompScreenDisplay compScreen= (CompScreenDisplay)JSONObject.toBean(obj, CompScreenDisplay.class);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");	
		compScreen.setDefault_flag("on".equals(obj.getString("default_flag"))?"1":"0");
		if("1".equals(compScreen.getDefault_flag())){
			 Map<String,String> map=new HashMap<String,String>();
				map.put("branch",compScreen.getBranch());
				map.put("comp_screen_id",compScreen.getComp_screen_id());
			Long count =sqlDao_h.getRecordCount(
				"select count(*) from CompScreenDisplay where " +
				"branch=:branch "+
				"and comp_screen_id!=:comp_screen_id "+ 
				"and default_flag='1'",map);
			if(count>0)
			{
				sRet.setSuccess(false);
				sRet.setOperaterResult(false);
				sRet.setErrmsg("已有默认综合屏参数，无法修改！");
				return sRet;
			}compScreen.setDefault_flag("1");
		}
		else{
			compScreen.setDefault_flag("0");
		}
		sqlDao_h.updateRecord(compScreen);
		return sRet;
	}
	
	/**
	 * 删除综合屏信息
	 */
	public ServiceReturn delCompScreenInfo(String inputJsonStr)
			throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		String comp_screen_ids = "";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = JSONObject.fromObject(jsonArray.getString(i));
			Object obj[] = jsonObj.values().toArray();
			comp_screen_ids = comp_screen_ids + "," + obj[1].toString();
		}
		String[] comp_screen_id_list = comp_screen_ids.substring(1).split(",");		
		String comp_screen_idss = "";
		for(int i=0;i < comp_screen_id_list.length;i++){
			Map<String,String> map=new HashMap<String,String>();
			map.put("comp_screen_id", comp_screen_id_list[i]);
			String id[]={"comp_screen_id"};
			String sql="select * from BT_QM_WIN where comp_screen_id =:comp_screen_id";
			List<Object> count =sqlDao_h.getRecordListBySql(sql,map,id);
			if(count.size()!=0){
				comp_screen_idss=comp_screen_idss+comp_screen_id_list[i]+"  ";
			}
			else{
				sqlDao_h.deleteById(CompScreenDisplay.class, comp_screen_id_list[i]);
			}
		}
		if(comp_screen_idss!=""){
			ret.put("resultmsg", "以下综合屏参数正在使用，无法删除！参数id:"+comp_screen_idss);
		}else{
			ret.put("resultmsg", "删除成功");
		}
		return ret;
	}
}
