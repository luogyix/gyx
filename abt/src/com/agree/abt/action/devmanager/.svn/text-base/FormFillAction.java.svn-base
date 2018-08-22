package com.agree.abt.action.devmanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;

public class FormFillAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	private ABTComunicateNatp cona;

	public String loadPage(){
		
		return SUCCESS;
	}
	
	
	public String queryParam() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
//		cona = new ABTComunicateNatp();
		
		cona.setBMSHeader("ibp.bms.b212.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum;
		try {
			loopNum = (String) map.get("paramsize").get(0);
		} catch (Exception e) {
			loopNum = "0";
		}
		int num = Integer.parseInt(loopNum);
		Page pageInfo = this.getPage(obj);
		pageInfo.setTotal(num);//总条数		
		Integer pageNo = (pageInfo.getTotal() % pageInfo.getLimit() == 0) ? pageInfo.getTotal() / pageInfo.getLimit() : pageInfo.getTotal()
				/ pageInfo.getLimit() + 1;// 得到总页数
		if (pageInfo.getStart() == -1) {// 查询最后一页
			pageInfo.setRowStart((pageNo - 1) * pageInfo.getLimit());
			pageInfo.setRowEnd(pageInfo.getRowStart()
					+ (pageInfo.getTotal() % pageInfo.getLimit() == 0 ? pageInfo.getLimit() : pageInfo.getTotal() % pageInfo.getLimit()));
			pageInfo.setPage(pageNo);
			pageInfo.setTotal(pageInfo.getTotal());
		} else {
			pageInfo.setRowStart((pageInfo.getStart() - 1) * pageInfo.getLimit() );
			pageInfo.setRowEnd( (pageInfo.getRowStart() + pageInfo.getLimit()) <= pageInfo.getTotal() ? (pageInfo.getRowStart() + pageInfo.getLimit())
					: pageInfo.getTotal() );
			pageInfo.setPage(pageNo);
			pageInfo.setTotal(pageInfo.getTotal());
		}
		for (int i=pageInfo.getRowStart();i<pageInfo.getRowEnd();i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put("branch", map.get("branch").get(i));
			hld.put("param_id", map.get("param_id").get(i));
			hld.put("messageoutlay", map.get("messageoutlay").get(i));
			hld.put("create_date", map.get("create_date").get(i));
			hld.put("explain", map.get("explain").get(i));
			hld.put("queuetype", map.get("queuetype").get(i));
			hld.put("pro_save_hint", map.get("pro_save_hint").get(i));
			hld.put("pro_drow_hint", map.get("pro_drow_hint").get(i));
			hld.put("trapro_drow_hint", map.get("trapro_drow_hint").get(i));
			hld.put("pfs_password", map.get("pfs_password").get(i));
			hld.put("shutdown_time", map.get("shutdown_time").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	public String queryParamAll() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo = new Page();
		//查询交易接口
		cona.setBMSHeader("ibp.bms.b212.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String loopNum;
		try {
			loopNum = (String) map.get("paramsize").get(0);
		} catch (Exception e) {
			loopNum = "0";
		}
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, String> hld = new HashMap<String, String>();
			hld.put("branch", map.get("branch").get(i));
			hld.put("param_id", map.get("param_id").get(i));
			hld.put("messageoutlay", map.get("messageoutlay").get(i));
			hld.put("create_date", map.get("create_date").get(i));
			hld.put("explain", map.get("explain").get(i));
			hld.put("queuetype", map.get("queuetype").get(i));
			hld.put("pro_save_hint", map.get("pro_save_hint").get(i));
			hld.put("pro_drow_hint", map.get("pro_drow_hint").get(i));
			hld.put("trapro_drow_hint", map.get("trapro_drow_hint").get(i));
			hld.put("pfs_password", map.get("pfs_password").get(i));
			hld.put("shutdown_time", map.get("shutdown_time").get(i));
			list.add(hld);
		}
		pageInfo.setTotal(num);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	public String addAdvertParam() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		
		
		cona.reInit();
		cona.setBMSHeader("ibp.bms.b212_1.01", user);
		//机构号
		cona.set(IDictABT.BRANCH, user.getUnitid());
		//参数id
		String param_id = UUID.randomUUID().toString().substring(0, 20);
		cona.set("param_id", param_id);
		//收费标准
		if(obj.getString("messageoutlay").equals("免费")){
			cona.set("messageoutlay","0");
		}else if(obj.getString("messageoutlay").equals("包月")){
			cona.set("messageoutlay","1");
		}else{
			cona.set("messageoutlay", obj.getString("messageoutlay").equals("包年") ? "2" : "1,2");
		}
		//创建时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		cona.set("create_date", sdf.format(new Date()));
		//备注
		cona.set("explain", obj.getString("explain"));
		//排队方式
		if(obj.getString("queuetype").equals("任意方式")){
			cona.set("queuetype","0");
		}else{
			cona.set("queuetype",obj.getString("queuetype").equals("先填单") ? "1" : "2");
		}
		//省内存款提示信息
		cona.set("pro_save_hint", obj.getString("pro_save_hint"));
		//省内取款提示信息
		cona.set("pro_drow_hint", obj.getString("pro_drow_hint"));
		//跨省取款提示信息
		cona.set("trapro_drow_hint", obj.getString("trapro_drow_hint"));
		//填单机管理密码
		cona.set("pfs_password", obj.getString("pfs_password"));
		//填单机关机时间
		cona.set("shutdown_time", obj.getString("shutdown_time"));
		//判断afa的返回结果,是否成功
		cona.exchange();
		ServiceReturn tet=new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		
		return AJAX_SUCCESS;
	}

	
	public String editAdvertParam() throws Exception{
		
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b212_3.01", user);
		//机构号
		cona.set(IDictABT.BRANCH, user.getUnitid());
		//参数id
		cona.set("param_id", obj.getString("param_id"));
		//收费标准
		if(obj.getString("messageoutlay").equals("免费")){
			cona.set("messageoutlay","0");
		}else if(obj.getString("messageoutlay").equals("包月")){
			cona.set("messageoutlay","1");
		}else{
			cona.set("messageoutlay", obj.getString("messageoutlay").equals("包年") ? "2" : "1,2");
		}
		//备注
		cona.set("explain", obj.getString("explain"));
		//排队方式
		if(obj.getString("queuetype").equals("任意方式")){
			cona.set("queuetype","0");
		}else{
			cona.set("queuetype",obj.getString("queuetype").equals("先填单") ? "1" : "2");
		}
		//省内存款提示信息
		cona.set("pro_save_hint", obj.getString("pro_save_hint"));
		//省内取款提示信息
		cona.set("pro_drow_hint", obj.getString("pro_drow_hint"));
		//跨省取款提示信息
		cona.set("trapro_drow_hint", obj.getString("trapro_drow_hint"));
		//填单机管理密码
		cona.set("pfs_password", obj.getString("pfs_password"));
		//填单机关机时间
		cona.set("shutdown_time", obj.getString("shutdown_time"));
		cona.exchange();
		ServiceReturn tet=new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		
		return AJAX_SUCCESS;
	}
	
	
	public String deleteAdvertParam() throws Exception{
		
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = JSONObject.fromObject(jsonArray.getString(i));
			Object obj[] = jsonObj.values().toArray();
			 
			//初始化NATP通讯
			cona.reInit();
			cona.setBMSHeader("ibp.bms.b212_4.01", user);
			cona.set("branch", user.getUnitid());
			cona.set("param_id", obj[0].toString());
			//判断afa的返回结果,是否成功
			cona.exchange();
		}
		
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		
		return AJAX_SUCCESS;
	}
	
	
	
	
	
	public ABTComunicateNatp getCona() {
		return cona;
	}


	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
