package com.agree.abt.action.configManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;

/**
 * 叫号规则配置
 * @ClassName: QueueingRulesAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2013-9-13
 */
public class QueueingRulesAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private Log logger = LogFactory.getLog(QueueingRulesAction.class);	
	private ABTComunicateNatp cona;
	
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		if((Boolean)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.QUEUERULESFLAG)){
			return "success2";
		}else {
			return SUCCESS;
		}
	}
	
	/**
	 * 通过叫号机编号查询窗口列表
	 * @throws Exception
	 */
	public void queryWindowByQMNum() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		 
		
		cona.setBMSHeader("ibp.bms.b117_2.01", user);
		
		cona.set(IDictABT.BRANCH, user.getUnitid());
		String qm_num = req.getParameter("value");
		cona.set("query_rules", "2");
		cona.set(IDictABT.QM_NUM, qm_num);
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		logger.info(map);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("winsize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.WIN_NUM, map.get("win_num").get(i));
			hld.put("win_name", map.get("win_num").get(i) + "号窗口");
			list.add(hld);
		}
		
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	/**
	 * 查询已配置的叫号规则
	 */
	public String queryQueueingRules() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		//查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b119_1.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.WIN_NUM, obj.getString("win_num"));
		cona.set(IDictABT.QM_NUM, obj.getString("qm_num"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		String call_rule = (String) map.get("call_rule").get(0);
		//call_rule = "".equals(map.get("call_rule").get(0))?"1":map.get("call_rule").get(0);
		
		if("".equals(call_rule)){
			call_rule = "0";
		}else if("1".equals(call_rule)){
			call_rule = "0";
		}else{
			call_rule = "1";
		}

		List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("win_bssize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BS_ID, map.get("bs_id").get(i));
			hld.put(IDictABT.BS_NAME_CH, map.get("bs_name_ch").get(i));
			hld.put("bs_level", map.get("bs_level").get(i));
			hld.put("shareqm_flag", map.get("shareqm_flag").get(i));
			list1.add(hld);
		}
		
		List<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
		loopNum = (String) map.get("win_qmsize").get(0);
		num = Integer.parseInt(loopNum);
		for (int j = 0; j < num; j++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.QUEUETYPE_ID, map.get("queuetype_id").get(j));
			hld.put(IDictABT.QUEUETYPE_NAME, map.get("queuetype_name").get(j));
			hld.put(IDictABT.QUEUETYPE_LEVEL, map.get("queuetype_level").get(j));
			list2.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		
		ret.put("call_rule", call_rule);
		ret.put("win_bs", list1);
		ret.put("win_qm", list2);
		//ServiceReturn retn = new ServiceReturn(true, "");
		//ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		//ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
		super.setActionResult(pageInfo, null, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 修改叫号规则
	 */
	public String changeCallRules() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b119_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.WIN_NUM, obj.getString("win_num"));
		cona.set(IDictABT.QM_NUM, obj.getString("qm_num"));
		cona.set(IDictABT.CALL_RULE, obj.getString("call_rule"));
		//判断afa的返回结果,是否成功
		cona.exchange();
		Page pageInfo =new Page();
		ServiceReturn ret = new ServiceReturn(true, "");
		super.setActionResult(pageInfo, null, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 添加窗口业务绑定
	 * @return
	 * @throws Exception
	 */
	public String addWinBusiness() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b119_3.01", user);
		cona.set(IDictABT.QM_NUM, obj.getString(IDictABT.QM_NUM));
		cona.set(IDictABT.WIN_NUM, obj.getString(IDictABT.WIN_NUM));
		cona.set(IDictABT.BRANCH, user.getUnitid());
		JSONArray array = JSONArray.fromObject(obj.get("listData"));
		int num = array.size();
		cona.set("bs_id_size", String.valueOf(num));
		//拼接循环报文
		StringBuffer final_bs_id = new StringBuffer("[");
		StringBuffer final_bs_level = new StringBuffer("[");
		StringBuffer final_shareqm_flag = new StringBuffer("[");
		for(int i = 0 ; i<num ; i++){
			JSONObject map = JSONObject.fromObject(array.get(i));
			final_bs_id.append("'" + map.getString("bs_id") + "', ");
			final_bs_level.append("'" + map.getString("bs_level") + "', ");
			String shareqm_flag = "true".equals(map.getString("shareqm_flag"))?"1":"0";
			final_shareqm_flag.append("'" + shareqm_flag + "', ");
		}
		
		if(num!=0){
			String bs_ids = final_bs_id.substring(0, final_bs_id.length()-2) + "]";
			String bs_levels = final_bs_level.substring(0, final_bs_level.length()-2) + "]";
			String shareqm_flags = final_shareqm_flag.substring(0, final_shareqm_flag.length()-2) + "]";
			cona.set(IDictABT.BS_ID, bs_ids);
			cona.set("bs_level", bs_levels);
			cona.set("shareqm_flag", shareqm_flags);
		}else{
			cona.set(IDictABT.BS_ID, "[]");
			cona.set("bs_level", "[]");
			cona.set("shareqm_flag", "[]");
		}
		//判断afa的返回结果,是否成功
		cona.exchange();

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 添加窗口队列绑定
	 * @return String
	 * @throws Exception
	 */
	public String addWinQueue() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b119_5.01", user);
		
		StringBuffer final_queuetype_id = new StringBuffer("[");
		StringBuffer final_queuetype_level = new StringBuffer("[");
		JSONArray array = JSONArray.fromObject(obj.get("listData"));
		int num = array.size();
		for(int i = 0 ; i<num ; i++){
			JSONObject map = JSONObject.fromObject(array.get(i));
			final_queuetype_id.append("'" + map.getString("queuetype_id") + "', ");
			if ("".equals(map.getString(IDictABT.QUEUETYPE_LEVEL))) {
				final_queuetype_level.append("'0', ");
			} else {
				final_queuetype_level.append("'" + map.getString("queuetype_level") + "', ");
			}
		}
		
		cona.set("queuetype_id_size", String.valueOf(num));
		if(num!=0){
			String queuetype_ids = final_queuetype_id.substring(0, final_queuetype_id.length()-2) + "]";
			cona.set(IDictABT.QUEUETYPE_ID, queuetype_ids);
		}else{
			cona.set(IDictABT.QUEUETYPE_ID, "[]");
		}
		if(num!=0){
			String queuetype_levels = final_queuetype_level.substring(0, final_queuetype_level.length()-2) + "]";
			cona.set(IDictABT.QUEUETYPE_LEVEL, queuetype_levels);
		}else{
			cona.set(IDictABT.QUEUETYPE_LEVEL, "[]");
		}

		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.QM_NUM, obj.getString(IDictABT.QM_NUM));
		cona.set(IDictABT.WIN_NUM, obj.getString(IDictABT.WIN_NUM));
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
//TODO参数化---------------------------------------------------------------------------------------
	
	/**
	 * 查询已配置的叫号规则
	 */
	public String queryQueueingRulesById() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		//查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b119_1.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set("parameter_id", obj.getString("parameter_id"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
//		String call_rule = map.get("call_rule").get(0);
//		
//		if("".equals(call_rule)){
//			call_rule = "0";
//		}else if("1".equals(call_rule)){
//			call_rule = "0";
//		}else{
//			call_rule = "1";
//		}
		
		List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("win_bssize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BS_ID, map.get("bs_id").get(i));
			hld.put(IDictABT.BS_NAME_CH, map.get("bs_name_ch").get(i));
			hld.put("bs_level", map.get("bs_level").get(i));
			hld.put("shareqm_flag", map.get("shareqm_flag").get(i));
			list1.add(hld);
		}
		
		List<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
		loopNum = (String) map.get("win_qmsize").get(0);
		num = Integer.parseInt(loopNum);
		for (int j = 0; j < num; j++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.QUEUETYPE_ID, map.get("queuetype_id").get(j));
			hld.put(IDictABT.QUEUETYPE_NAME, map.get("queuetype_name").get(j));
			hld.put(IDictABT.QUEUETYPE_LEVEL, map.get("queuetype_level").get(j));
			list2.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		
		//ret.put("call_rule", call_rule);
		ret.put("win_bs", list1);
		ret.put("win_qm", list2);
		super.setActionResult(pageInfo, null, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 添加窗口业务绑定
	 * @return
	 * @throws Exception
	 */
	public String addWinBusinessById() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b119_3.01", user);
		cona.set("parameter_id", obj.getString("parameter_id"));
		cona.set(IDictABT.BRANCH, user.getUnitid());
		JSONArray array = JSONArray.fromObject(obj.get("listData"));
		int num = array.size();
		cona.set("bs_id_size", String.valueOf(num));
		//拼接循环报文
		StringBuffer final_bs_id = new StringBuffer("[");
		StringBuffer final_bs_level = new StringBuffer("[");
		StringBuffer final_shareqm_flag = new StringBuffer("[");
		for(int i = 0 ; i<num ; i++){
			JSONObject map = JSONObject.fromObject(array.get(i));
			final_bs_id.append("'" + map.getString("bs_id") + "', ");
			final_bs_level.append("'" + map.getString("bs_level") + "', ");
			String shareqm_flag = "true".equals(map.getString("shareqm_flag"))?"1":"0";
			final_shareqm_flag.append("'" + shareqm_flag + "', ");
		}
		
		if(num!=0){
			String bs_ids = final_bs_id.substring(0, final_bs_id.length()-2) + "]";
			String bs_levels = final_bs_level.substring(0, final_bs_level.length()-2) + "]";
			String shareqm_flags = final_shareqm_flag.substring(0, final_shareqm_flag.length()-2) + "]";
			cona.set(IDictABT.BS_ID, bs_ids);
			cona.set("bs_level", bs_levels);
			cona.set("shareqm_flag", shareqm_flags);
		}else{
			cona.set(IDictABT.BS_ID, "[]");
			cona.set("bs_level", "[]");
			cona.set("shareqm_flag", "[]");
		}
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 添加窗口队列绑定
	 * @return String
	 * @throws Exception
	 */
	public String addWinQueueById() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b119_5.01", user);
		
		StringBuffer final_queuetype_id = new StringBuffer("[");
		StringBuffer final_queuetype_level = new StringBuffer("[");
		JSONArray array = JSONArray.fromObject(obj.get("listData"));
		int num = array.size();
		for(int i = 0 ; i<num ; i++){
			JSONObject map = JSONObject.fromObject(array.get(i));
			final_queuetype_id.append("'" + map.getString("queuetype_id") + "', ");
			if ("".equals(map.getString(IDictABT.QUEUETYPE_LEVEL))) {
				final_queuetype_level.append("'0', ");
			} else {
				final_queuetype_level.append("'" + map.getString("queuetype_level") + "', ");
			}
		}
		
		cona.set("queuetype_id_size", String.valueOf(num));
		if(num!=0){
			String queuetype_ids = final_queuetype_id.substring(0, final_queuetype_id.length()-2) + "]";
			cona.set(IDictABT.QUEUETYPE_ID, queuetype_ids);
		}else{
			cona.set(IDictABT.QUEUETYPE_ID, "[]");
		}
		if(num!=0){
			String queuetype_levels = final_queuetype_level.substring(0, final_queuetype_level.length()-2) + "]";
			cona.set(IDictABT.QUEUETYPE_LEVEL, queuetype_levels);
		}else{
			cona.set(IDictABT.QUEUETYPE_LEVEL, "[]");
		}
		
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set("parameter_id", obj.getString("parameter_id"));
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
}
