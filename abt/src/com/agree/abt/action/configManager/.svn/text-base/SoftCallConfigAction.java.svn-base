package com.agree.abt.action.configManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;

public class SoftCallConfigAction extends BaseAction {
	
	private ABTComunicateNatp cona;
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(SoftCallConfigAction.class);
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
	 * 查询SoftCall信息
	 * @return
	 * @throws Exception
	 */
	public String querySoftCall() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b107_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona.set("query_rules", obj.getString("query_rules"));
		
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		logger.info("map:" + map);
		String loopNum = (String) map.get("softcallsize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.SOFTCALL_ID, map.get("softcall_id").get(i));
			hld.put(IDictABT.DEFAULT_FLAG, "1".equals(map.get("default_flag").get(i))?"0":"1");
			hld.put(IDictABT.STATUS, map.get("status").get(i));
			hld.put(IDictABT.RECALL_LIMIT, map.get("recall_limit").get(i));
			hld.put(IDictABT.CALL_NEXT_LIMIT, map.get("call_next_limit").get(i));
			hld.put(IDictABT.NOTIFY_CUSTOMER, map.get("notify_customer").get(i));
			list.add(hld);
		}
		pageInfo.setTotal(num);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	public String querySoftCallPage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b107_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona.set("query_rules", obj.getString("query_rules"));
		
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("softcallsize").get(0);
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
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.SOFTCALL_ID, map.get("softcall_id").get(i));
			hld.put(IDictABT.DEFAULT_FLAG, "1".equals(map.get("default_flag").get(i))?"0":"1");
			hld.put(IDictABT.STATUS, map.get("status").get(i));
			hld.put(IDictABT.RECALL_LIMIT, map.get("recall_limit").get(i));
			hld.put(IDictABT.CALL_NEXT_LIMIT, map.get("call_next_limit").get(i));
			hld.put(IDictABT.NOTIFY_CUSTOMER, map.get("notify_customer").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 添加SoftCall信息
	 * @return String
	 * @throws Exception
	 */
	public String addSoftCall() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);

		 
		
		cona.setBMSHeader("ibp.bms.b107_1.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.DEFAULT_FLAG, "on".equals(obj.getString("default_flag"))?"0":"1");
		cona.set(IDictABT.STATUS, "on".equals(obj.getString("status"))?"1":"0");
		cona.set(IDictABT.RECALL_LIMIT, obj.getString("recall_limit"));
		cona.set(IDictABT.CALL_NEXT_LIMIT, obj.getString("call_next_limit"));
		//conaV2.set(IDictABT.NOTIFY_CUSTOMER, obj.getString("notify_customer"));
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn tet=new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 修改SoftCall信息
	 * @return String
	 * @throws Exception
	 */
	public String editSoftCall() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b107_3.01", user);
		cona.set(IDictABT.BRANCH, obj.getString("branch"));
		cona.set(IDictABT.SOFTCALL_ID, obj.getString("softcall_id"));
		cona.set(IDictABT.DEFAULT_FLAG, "on".equals(obj.getString("default_flag"))?"0":"1");
		cona.set(IDictABT.STATUS, "on".equals(obj.getString("status"))?"1":"0");
		cona.set(IDictABT.RECALL_LIMIT, obj.getString("recall_limit"));
		cona.set(IDictABT.CALL_NEXT_LIMIT, obj.getString("call_next_limit"));
		//conaV2.set(IDictABT.NOTIFY_CUSTOMER, obj.getString("notify_customer"));
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 删除SoftCall信息
	 * @return String
	 * @throws Exception
	 */

	public String delSoftCall() throws Exception {
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
			cona.setBMSHeader("ibp.bms.b107_4.01", user);
			cona.set(IDictABT.BRANCH, obj[0].toString());
			cona.set(IDictABT.SOFTCALL_ID, obj[1].toString());
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
