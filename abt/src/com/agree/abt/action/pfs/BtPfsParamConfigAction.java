package com.agree.abt.action.pfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * 填单机参数配置
 * @ClassName: QueueMachineParamAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2013-8-24
 */
public class BtPfsParamConfigAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 填单机机参数
	 * @return
	 * @throws Exception
	 */
	public String queryPfsParam() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b132_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona.set("query_rules", obj.getString("query_rules"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("paramsize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.MOB_PARAM_ID, map.get("mob_param_id").get(i));
			hld.put(IDictABT.DEFAULT_FLAG, "1".equals(map.get("default_flag").get(i))?"0":"1");
			String timeing_shutdown = (String) map.get("timeing_shutdown").get(i);
			if(!"0".equals(map.get("timeing_shutdown").get(i))){
				timeing_shutdown = timeing_shutdown.substring(0,2) + ":" + timeing_shutdown.substring(2);
			}
			hld.put(IDictABT.TIMEING_SHUTDOWN, timeing_shutdown);
			hld.put(IDictABT.TIMEING_TIMEOUT, map.get("timeing_timeout").get(i));
			hld.put(IDictABT.MOB_PWD, map.get("mob_pwd").get(i));
			hld.put("label_content", map.get("label_content").get(i));
			hld.put("label_text_size", map.get("label_text_size").get(i));
			hld.put("label_text_color", map.get("label_text_color").get(i));
			hld.put("label_text_style", map.get("label_text_style").get(i));
			hld.put("label_text_font", map.get("label_text_font").get(i));
			list.add(hld);
		}
		pageInfo.setTotal(num);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 填单机机参数
	 * @return
	 * @throws Exception
	 */
	public String pfsParamPage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b132_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona.set("query_rules", obj.getString("query_rules"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map =  cona.exchange();
		if(map.get("H_ret_code") == null){
			throw new AppException("与后台服务通讯失败,没有返回状态码(H_ret_code)和状态信息");
		}
		String H_ret_code = (String) map.get("H_ret_code").get(0);
		if(!H_ret_code.equals(IDictABT.AFARESULTSTATUS_SUCC)){
			throw new AppException("错误码:"+H_ret_code+","+"错误信息:"+map.get("H_ret_desc").get(0));
		}
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("paramsize").get(0);
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
			hld.put(IDictABT.MOB_PARAM_ID, map.get("mob_param_id").get(i));
			hld.put(IDictABT.DEFAULT_FLAG, "1".equals(map.get("default_flag").get(i))?"0":"1");
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			String timeing_shutdown = (String) map.get("timeing_shutdown").get(i);
			if(!"0".equals(map.get("timeing_shutdown").get(i))){
				timeing_shutdown = timeing_shutdown.substring(0,2) + ":" + timeing_shutdown.substring(2);
			}
			hld.put(IDictABT.TIMEING_SHUTDOWN, timeing_shutdown);
			hld.put(IDictABT.TIMEING_TIMEOUT, map.get("timeing_timeout").get(i));
			hld.put(IDictABT.MOB_PWD, map.get("mob_pwd").get(i));
			hld.put("label_content", map.get("label_content").get(i));
			hld.put("label_text_font", map.get("label_text_font").get(i));
			hld.put("label_text_style", map.get("label_text_style").get(i));
			hld.put("label_text_size", map.get("label_text_size").get(i));
			hld.put("label_text_color", map.get("label_text_color").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 填单机机参数
	 * @return
	 * @throws Exception
	 */
	public String addPfsParam() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b132_1.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.DEFAULT_FLAG, "on".equals(obj.getString("default_flag"))?"0":"1");
		cona.set(IDictABT.TIMEING_SHUTDOWN, obj.getString("timeing_shutdown").replace(":",""));
		cona.set(IDictABT.TIMEING_TIMEOUT, obj.getString("timeing_timeout"));
		cona.set(IDictABT.MOB_PWD, obj.getString("mob_pwd"));
		cona.set("label_content",obj.getString("label_content"));
		cona.set("label_text_font",obj.getString("label_text_font"));
		cona.set("label_text_style",obj.getString("label_text_style"));
		cona.set("label_text_size",obj.getString("label_text_size"));
		cona.set("label_text_color",obj.getString("label_text_color"));
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn tet=new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		
		return AJAX_SUCCESS;
	}
	
	/**
	 * 填单机机参数
	 * @return
	 * @throws Exception
	 */
	public String editPfsParam() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b132_3.01", user);
		cona.set(IDictABT.MOB_PARAM_ID, obj.getString("mob_param_id"));
		cona.set(IDictABT.BRANCH, obj.getString("branch"));
		cona.set(IDictABT.DEFAULT_FLAG, "on".equals(obj.getString("default_flag"))?"0":"1");
		cona.set(IDictABT.TIMEING_SHUTDOWN, obj.getString("timeing_shutdown").replace(":",""));
		cona.set(IDictABT.TIMEING_TIMEOUT,obj.getString("timeing_timeout"));
		cona.set(IDictABT.MOB_PWD, obj.getString("mob_pwd"));
		cona.set("label_content",obj.getString("label_content"));
		cona.set("label_text_font",obj.getString("label_text_font"));
		cona.set("label_text_style",obj.getString("label_text_style"));
		cona.set("label_text_size",obj.getString("label_text_size"));
		cona.set("label_text_color",obj.getString("label_text_color"));
		
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 填单机机参数
	 * @return
	 * @throws Exception
	 */
	public String delPfsParam() throws Exception {
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
			cona.setBMSHeader("ibp.bms.b132_4.01", user);
			cona.set(IDictABT.MOB_PARAM_ID, obj[0].toString());
			cona.set(IDictABT.BRANCH, obj[1].toString());
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
}
