package com.agree.abt.action.configManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;

/**
 * 机构参数配置
 * @company 赞同科技
 * @author XiWang
 * @date 2013-9-22
 */
public class BranchParamAction extends BaseAction {
	
	private ABTComunicateNatp cona;
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(BranchParamAction.class);
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	public String saveConf() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);

		cona.setBMSHeader("ibp.bms.b205_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.DEFAULT_FLAG, "0");
		cona.set(IDictABT.RESERV_STATUS, obj.getString(IDictABT.RESERV_STATUS));
		//启用预约提前通知
		cona.set(IDictABT.RESERV_ADVANCE_STATUS, obj.getString(IDictABT.RESERV_ADVANCE_STATUS));
		//预约提前通知分钟
		cona.set_cover("reserv_advance_before", obj.getString("reserv_advance_before"));
		
		cona.set("reserv_max_break", obj.getString("reserv_max_break"));
		cona.set("reserv_max_time", obj.getString("reserv_max_time"));
		cona.set("reserv_maxdays_before", obj.getString("reserv_maxdays_before"));
		cona.set("reserv_minmin_before", obj.getString("reserv_minmin_before"));
		cona.set("reserv_min_active", obj.getString("reserv_min_active"));
		cona.set("reserv_max_active", obj.getString("reserv_max_active"));
		cona.set("reserv_prompt", obj.getString("reserv_prompt"));
		cona.set("negative_monitor", obj.getString("negative_monitor"));
		cona.set("remaind_flag", obj.getString("remaind_flag"));
		cona.set("customer_visit", obj.getString("customer_visit"));
		cona.set("negative_noti_flag", obj.getString("negative_noti_flag"));
		cona.set("def_waitnum_threshold", obj.getString("def_waitnum_threshold"));
		cona.set("def_waittime_threshold", obj.getString("def_waittime_threshold"));
		cona.set("wake_sleeptime", obj.getString("wake_sleeptime"));
		cona.set("def_notify_threshold", obj.getString("def_notify_threshold"));
		cona.set("def_show_notify_threshold", obj.getString("def_show_notify_threshold"));
		cona.set("ticket_fstip", obj.getString("ticket_fstip"));
		cona.set("queue_applytime", obj.getString("queue_applytime"));
		cona.exchange();
		
		Page pageInfo =new Page();
		ServiceReturn ret = new ServiceReturn(true, "");
		super.setActionResult(pageInfo,null, ret);
		return AJAX_SUCCESS;
	}
	
	public void loadConf() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		cona.setBMSHeader("ibp.bms.b205_1.01", user);
		cona.set("branch", user.getUnitid()); //查询条件.待确认
		cona.set("query_rules", req.getParameter("query_rules"));
		Map<String,List<String>> map =  cona.exchange();
		String H_ret_code = (String) map.get("H_ret_code").get(0);
		if(!H_ret_code.equals(IDictABT.AFARESULTSTATUS_SUCC)){
			throw  new AppException((String) map.get("H_ret_desc").get(0));
		};
		ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map1 = new HashMap<String,Object>();
		if(!"0".equals(map.get("datasize").get(0))){
			map1.put(IDictABT.BRANCH, map.get(IDictABT.BRANCH).get(0));
			//map1.put(IDictABT.DEFAULT_FLAG, "0".equals(map.get(IDictABT.DEFAULT_FLAG).get(0))?"1":"0");
			map1.put(IDictABT.RESERV_STATUS, map.get(IDictABT.RESERV_STATUS).get(0));
			//启用预约提前通知
			map1.put(IDictABT.RESERV_ADVANCE_STATUS, map.get(IDictABT.RESERV_ADVANCE_STATUS).get(0));
			//预约提前分钟
			map1.put("reserv_advance_before", map.get("reserv_advance_before").get(0));
			
			map1.put("reserv_max_break", map.get("reserv_max_break").get(0));
			map1.put("reserv_max_time", map.get("reserv_max_time").get(0));
			map1.put("reserv_maxdays_before", map.get("reserv_maxdays_before").get(0));
			map1.put("reserv_minmin_before", map.get("reserv_minmin_before").get(0));
			map1.put("reserv_min_active", map.get("reserv_min_active").get(0));
			map1.put("reserv_max_active", map.get("reserv_max_active").get(0));
			map1.put("reserv_prompt", map.get("reserv_prompt").get(0));
			map1.put("negative_monitor", map.get("negative_monitor").get(0));
			map1.put("remaind_flag", map.get("remaind_flag").get(0));
			map1.put("customer_visit", map.get("customer_visit").get(0));
			map1.put("negative_noti_flag", map.get("negative_noti_flag").get(0));
			map1.put("def_waitnum_threshold", map.get("def_waitnum_threshold").get(0));
			map1.put("def_waittime_threshold", map.get("def_waittime_threshold").get(0));
			map1.put("def_notify_threshold", map.get("def_notify_threshold").get(0));
			map1.put("def_show_notify_threshold", map.get("def_show_notify_threshold").get(0));
			map1.put("wake_sleeptime", map.get("wake_sleeptime").get(0));
			map1.put("ticket_fstip", map.get("ticket_fstip").get(0));
			map1.put("queue_applytime", map.get("queue_applytime").get(0));
			list.add(map1);
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	public void loadConfByBranch() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		cona.setBMSHeader("ibp.bms.b205_1.01", user);
		cona.set("branch", req.getParameter("branch")); //查询条件.待确认
		cona.set("query_rules", req.getParameter("query_rules"));
		Map<String,List<String>> map = cona.exchange();
		log.info("---------------------------map : " + map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(!"0".equals(map.get("datasize").get(0))){
			map1.put(IDictABT.BRANCH, map.get(IDictABT.BRANCH).get(0));
			//map1.put(IDictABT.DEFAULT_FLAG, "0".equals(map.get(IDictABT.DEFAULT_FLAG).get(0))?"1":"0");
			map1.put(IDictABT.RESERV_STATUS, map.get(IDictABT.RESERV_STATUS).get(0));
			//启用预约提前通知
			map1.put(IDictABT.RESERV_ADVANCE_STATUS, map.get(IDictABT.RESERV_ADVANCE_STATUS).get(0));
			//预约提前分钟
			map1.put("reserv_advance_before", map.get("reserv_advance_before").get(0));
			
			map1.put("reserv_max_break", map.containsKey("reserv_max_break")?map.get("reserv_max_break").get(0):"");
			map1.put("reserv_max_time", map.get("reserv_max_time").get(0));
			map1.put("reserv_maxdays_before", map.get("reserv_maxdays_before").get(0));
			map1.put("reserv_minmin_before", map.get("reserv_minmin_before").get(0));
			map1.put("reserv_min_active", map.get("reserv_min_active").get(0));
			map1.put("reserv_max_active", map.get("reserv_max_active").get(0));
			map1.put("reserv_prompt", map.get("reserv_prompt").get(0));
			map1.put("negative_monitor", map.get("negative_monitor").get(0));
			map1.put("remaind_flag", map.get("remaind_flag").get(0));
			map1.put("customer_visit", map.get("customer_visit").get(0));
			map1.put("negative_noti_flag", map.get("negative_noti_flag").get(0));
			map1.put("def_waitnum_threshold", map.get("def_waitnum_threshold").get(0));
			map1.put("def_waittime_threshold", map.get("def_waittime_threshold").get(0));
			map1.put("wake_sleeptime", map.get("wake_sleeptime").get(0));
			map1.put("def_notify_threshold", map.get("def_notify_threshold").get(0));
			map1.put("def_show_notify_threshold", map.get("def_show_notify_threshold").get(0));
			list.add(map1);
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
}
