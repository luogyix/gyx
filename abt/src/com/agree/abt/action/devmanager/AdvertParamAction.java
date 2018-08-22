package com.agree.abt.action.devmanager;

import java.util.ArrayList;
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
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;

public class AdvertParamAction extends BaseAction {
	
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
		cona = new ABTComunicateNatp();
		
		cona.setBMSHeader("ibp.bms.b211_1.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum;
		try {
			loopNum = (String) map.get("adParamNum").get(0);
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
			hld.put("device_type", map.get("device_type").get(i));
			hld.put("device_id", map.get("device_id").get(i));
			hld.put("param_id", map.get("param_id").get(i));
			hld.put("param_name", map.get("param_name").get(i));
			hld.put("param_key", map.get("param_key").get(i));
			hld.put("param_value", map.get("param_value").get(i));
			hld.put("create_date", map.get("create_date").get(i));
			hld.put("note", map.get("note").get(i));
			list.add(hld);
		}
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
		cona.setBMSHeader("ibp.bms.b211_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		
		String uuid = UUID.randomUUID().toString().substring(0, 10);
		cona.set("param_id", user.getUnitid()+uuid);
		
		cona.set("device_type",obj.getString("device_type"));
		cona.set("device_id",obj.getString("device_type"));
		//cona.set("device_type","02");
		//cona.set("device_id","02");
		cona.set("param_name","广告参数");
		//cona.set("param_key",obj.getString("param_key"));
		cona.set("param_key","广告轮播时间");
		cona.set("param_value",obj.getString("param_value"));
		cona.set("create_date",obj.getString("create_date"));
		cona.set("notea",obj.getString("note"));
		/*cona.set(IDictABT.DEFAULT_FLAG, "on".equals(obj.getString("default_flag"))?"0":"1");
		cona.set(IDictABT.TIMEING_SHUTDOWN, obj.getString("timeing_shutdown").replace(":",""));
		cona.set(IDictABT.TIMEING_TIMEOUT, obj.getString("timeing_timeout"));
		cona.set(IDictABT.MOB_PWD, obj.getString("mob_pwd"));*/
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
		 
		
		cona.setBMSHeader("ibp.bms.b211_3.01", user);
		cona.set("branch", obj.getString("branch"));
		cona.set("device_type",obj.getString("device_type"));
		cona.set("param_id",obj.getString("param_id"));
		//cona.set("param_name",obj.getString("param_name"));
		//cona.set("param_key",obj.getString("param_key"));
		cona.set("param_key","广告轮播时间");
		
		String param_value = obj.getString("param_value");
		if(param_value == null){
			param_value = " ";
		}
		cona.set("param_value",param_value);
		String note = obj.getString("note");
		if(note == null){
			note = " ";
		}
		cona.set("notea",note);
		//判断afa的返回结果,是否成功
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
			 
			cona.reInit();
			cona.setBMSHeader("ibp.bms.b211_4.01", user);
			cona.set("param_id", obj[0].toString());
			//判断afa的返回结果,是否成功
			Map<String,List<String>> map = cona.exchange();
			if(map.get("H_ret_code") == null){
				throw new AppException("与后台服务通讯失败,没有返回状态码(H_ret_code)和状态信息");
			}
			String H_ret_code = (String) map.get("H_ret_code").get(0);
			if(!H_ret_code.equals(IDictABT.AFARESULTSTATUS_SUCC)){
				throw new AppException("错误码:"+H_ret_code+","+"错误信息:"+map.get("H_ret_desc").get(0));
			}
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
