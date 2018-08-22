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

public class WindowConfigAction extends BaseAction {
	
	private ABTComunicateNatp cona;
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(WindowConfigAction.class);
	
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	public String queryWindow() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b117_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona.set("query_rules", obj.getString("query_rules"));
		
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		logger.info("map:" + map);
		String loopNum = (String) map.get("winsize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.WIN_NUM, map.get("win_num").get(i));
			hld.put(IDictABT.QM_NUM, map.get("qm_num").get(i));
			hld.put(IDictABT.IP, map.get("ip").get(i));
			hld.put(IDictABT.WIN_OID, map.get("win_oid").get(i));
			hld.put(IDictABT.CALL_RULE, map.get("call_rule").get(i));
			hld.put(IDictABT.STATUS, map.get("status").get(i));
			hld.put(IDictABT.SCREEN_ID, map.get("screen_id").get(i));
			hld.put(IDictABT.SOFTCALL_ID, map.get("softcall_id").get(i));
			hld.put("apprflag", map.get("apprflag").get(i));
			hld.put(IDictABT.WIN_SERVICE_STATUS, map.get("win_service_status").get(i));
			if((Boolean)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.QUEUERULESFLAG)){
				hld.put("parameter_id", map.get("parameter_id").get(i));
			}
			list.add(hld);
		}
		pageInfo.setTotal(num);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	public String queryWindowPage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b117_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona.set("query_rules", obj.getString("query_rules"));
		
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("winsize").get(0);
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
			hld.put(IDictABT.WIN_NUM, map.get("win_num").get(i));
			hld.put(IDictABT.QM_NUM, map.get("qm_num").get(i));
			hld.put(IDictABT.IP, map.get("ip").get(i));
			hld.put(IDictABT.WIN_OID, map.get("win_oid").get(i));
			hld.put(IDictABT.CALL_RULE, map.get("call_rule").get(i));
			hld.put(IDictABT.STATUS, map.get("status").get(i));
			hld.put("apprflag", map.get("apprflag").get(i));
			hld.put(IDictABT.SCREEN_ID, map.get("screen_id").get(i));
			hld.put(IDictABT.SOFTCALL_ID, map.get("softcall_id").get(i));
			hld.put(IDictABT.WIN_SERVICE_STATUS, map.get("win_service_status").get(i));
			if((Boolean)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.QUEUERULESFLAG)){
				hld.put("parameter_id", map.get("parameter_id").get(i));
			}
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 添加Window信息
	 * @return String
	 * @throws Exception
	 */
	public String addWindow() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);

		 
		
		cona.setBMSHeader("ibp.bms.b117_1.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.WIN_NUM, obj.getString("win_num"));
		cona.set(IDictABT.QM_NUM, obj.getString("qm_num"));
		//cona.set(IDictABT.IP, obj.getString("ip"));
		cona.set(IDictABT.STATUS, "on".equals(obj.getString("status"))?"1":"0");
		cona.set(IDictABT.SCREEN_ID, obj.getString("screen_id"));
		cona.set(IDictABT.CALL_RULE, obj.getString("call_rule"));
		cona.set(IDictABT.SOFTCALL_ID, obj.getString("softcall_id"));
		cona.set("apprflag", obj.getString("apprflag"));
		if((Boolean)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.QUEUERULESFLAG)){
			cona.set("parameter_id", obj.getString("parameter_id"));
		}
		//判断afa的返回结果,是否成功
		cona.exchange();
		ServiceReturn tet=new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 修改Window信息
	 * @return String
	 * @throws Exception
	 */
	public String editWindow() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b117_3.01", user);
		cona.set(IDictABT.BRANCH, obj.getString("branch"));
		cona.set(IDictABT.WIN_NUM, obj.getString("win_num_old"));
		cona.set(IDictABT.QM_NUM, obj.getString("qm_num_old"));
		//cona.set(IDictABT.IP, obj.getString("ip"));
		cona.set(IDictABT.STATUS, "on".equals(obj.getString("status"))?"1":"0");
		cona.set(IDictABT.SCREEN_ID, obj.getString("screen_id"));
		cona.set(IDictABT.SOFTCALL_ID, obj.getString("softcall_id"));
		cona.set(IDictABT.CALL_RULE, obj.getString("call_rule"));
		cona.set("win_num_new", obj.getString("win_num"));
		cona.set("qm_num_new", obj.getString("qm_num"));
		cona.set("apprflag", obj.getString("apprflag"));
		if((Boolean)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.QUEUERULESFLAG)){
			cona.set("parameter_id", obj.getString("parameter_id"));
		}
		//判断afa的返回结果,是否成功
		cona.exchange();
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 删除Window信息
	 * @return String
	 * @throws Exception
	 */
	public String delWindow() throws Exception {
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
			cona.setBMSHeader("ibp.bms.b117_4.01", user);
			cona.set(IDictABT.BRANCH, obj[0].toString());
			cona.set(IDictABT.WIN_NUM, obj[1].toString());
			cona.set(IDictABT.QM_NUM, obj[2].toString());
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
