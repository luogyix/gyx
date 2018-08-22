package com.agree.abt.action.configManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

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
 * 排队机参数配置
 * @ClassName: QueueMachineParamAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2013-8-24
 */
public class QueueMachineParamAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	private Log logger = LogFactory.getLog(QueueMachineParamAction.class);	
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		logger.info("已注入:" + cona.getIp() + ":" + cona.getPort());//已注入:192.9.202.224:9012
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 查询排队机参数
	 * @return
	 * @throws Exception
	 */
	public String queryQMParam() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		//查询交易接口
		 
		cona.setBMSHeader("ibp.bms.b105_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona.set("query_rules", obj.getString("query_rules"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = map.get("paramsize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.QM_PARAM_ID, map.get("qm_param_id").get(i));
			hld.put(IDictABT.DEFAULT_FLAG, "1".equals(map.get("default_flag").get(i))?"0":"1");
			hld.put(IDictABT.THEME_ID, map.get("theme_id").get(i));
			//hld.put("doublescreen_id", map.get("doublescreen_id").get(i));
			hld.put(IDictABT.TICKET_STYLE_ID, map.get("ticket_style_id").get(i));
			String timeing_shutdown = map.get("timeing_shutdown").get(i);
			if(!"0".equals(map.get("timeing_shutdown").get(i))){
				timeing_shutdown = timeing_shutdown.substring(0,2) + ":" + timeing_shutdown.substring(2);
			}
			hld.put(IDictABT.TIMEING_SHUTDOWN, timeing_shutdown);
			hld.put(IDictABT.CFG_PWD, map.get("cfg_pwd").get(i));
			hld.put(IDictABT.CALL_VOICE, map.get("call_voice").get(i));
			hld.put(IDictABT.SHOW_BS_WAITNUM, map.get("show_bs_waitnum").get(i));
			//hld.put(IDictABT.SHOW_DIR_WAITNUM, map.get("show_dir_waitnum").get(i));//是否显示目录等待人数
			hld.put(IDictABT.BS_SERVICETIME_STATUS, map.get("bs_servicetime_status").get(i));
			//hld.put(IDictABT.THEME_STATUS, map.get("theme_status").get(i));
			hld.put(IDictABT.SHOW_CLOCK, map.get("show_clock").get(i));
			//hld.put(IDictABT.SMS_CUSTOMER, map.get("sms_customer").get(i));
			hld.put("label_content", map.get("label_content").get(i));
			hld.put("label_text_size", map.get("label_text_size").get(i));
			hld.put("label_text_color", map.get("label_text_color").get(i));
			hld.put("label_text_style", map.get("label_text_style").get(i));
			hld.put("label_text_font", map.get("label_text_font").get(i));
			//hld.put(IDictABT.ISPRECONTRACT, map.get("isprecontract").get(i));
			//hld.put(IDictABT.SOUNDBOX_STATUS, map.get("soundbox_status").get(i));//是否启用无线音箱
			list.add(hld);
		}
		pageInfo.setTotal(num);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 查询排队机参数
	 * @return
	 * @throws Exception
	 */
	public String queryQMParamPage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b105_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona.set("query_rules", obj.getString("query_rules"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = map.get("paramsize").get(0);
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
			hld.put(IDictABT.QM_PARAM_ID, map.get("qm_param_id").get(i));
			hld.put(IDictABT.DEFAULT_FLAG, "1".equals(map.get("default_flag").get(i))?"0":"1");
			hld.put(IDictABT.THEME_ID, map.get("theme_id").get(i));
			//hld.put("doublescreen_id", map.get("doublescreen_id").get(i));
			hld.put(IDictABT.TICKET_STYLE_ID, map.get("ticket_style_id").get(i));
			String timeing_shutdown = map.get("timeing_shutdown").get(i);
			if(!"0".equals(map.get("timeing_shutdown").get(i))){
				timeing_shutdown = timeing_shutdown.substring(0,2) + ":" + timeing_shutdown.substring(2);
			}
			hld.put(IDictABT.TIMEING_SHUTDOWN, timeing_shutdown);
			hld.put(IDictABT.CFG_PWD, map.get("cfg_pwd").get(i));
			hld.put(IDictABT.CALL_VOICE, map.get("call_voice").get(i));
			hld.put(IDictABT.SHOW_BS_WAITNUM, map.get("show_bs_waitnum").get(i));
			//hld.put(IDictABT.SHOW_DIR_WAITNUM, map.get("show_dir_waitnum").get(i));//是否显示目录等待人数
			hld.put(IDictABT.BS_SERVICETIME_STATUS, map.get("bs_servicetime_status").get(i));
			//hld.put(IDictABT.THEME_STATUS, map.get("theme_status").get(i));
			hld.put(IDictABT.SHOW_CLOCK, map.get("show_clock").get(i));
			//hld.put(IDictABT.SMS_CUSTOMER, map.get("sms_customer").get(i));
			
			/*
			 * 去除排队机界面提示标签
			hld.put("label_content", map.get("label_content").get(i));
			hld.put("label_text_size", map.get("label_text_size").get(i));
			hld.put("label_text_color", map.get("label_text_color").get(i));
			hld.put("label_text_style", map.get("label_text_style").get(i));
			hld.put("label_text_font", map.get("label_text_font").get(i));
			*/
			
			//hld.put(IDictABT.ISPRECONTRACT, map.get("isprecontract").get(i));
			//hld.put(IDictABT.SOUNDBOX_STATUS, map.get("soundbox_status").get(i));//是否启用无线音箱
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 添加排队机参数
	 * @return
	 * @throws Exception
	 */
	public String addQMParam() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b105_1.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		//conaV2.set(IDictABT.QM_PARAM_ID, obj.getString("qm_param_id"));
		cona.set(IDictABT.DEFAULT_FLAG, "on".equals(obj.getString("default_flag"))?"0":"1");
		cona.set(IDictABT.THEME_ID, obj.getString("theme_id"));
		cona.set(IDictABT.TICKET_STYLE_ID, obj.getString("ticket_style_id"));
		//cona.set("doublescreen_id", obj.getString("doublescreen_id"));
		cona.set(IDictABT.TIMEING_SHUTDOWN, obj.getString("timeing_shutdown").replace(":",""));
		cona.set(IDictABT.CFG_PWD, obj.getString("cfg_pwd"));
		cona.set(IDictABT.CALL_VOICE, obj.getString("call_voice"));
		cona.set(IDictABT.SHOW_BS_WAITNUM, "on".equals(obj.getString("show_bs_waitnum"))?"1":"0");
		cona.set(IDictABT.BS_SERVICETIME_STATUS, "on".equals(obj.getString("bs_servicetime_status"))?"1":"0");
		//conaV2.set(IDictABT.THEME_STATUS, "on".equals(obj.getString("theme_status"))?"1":"0");
		cona.set(IDictABT.SHOW_CLOCK, "on".equals(obj.getString("show_clock"))?"1":"0");
		//conaV2.set(IDictABT.SMS_CUSTOMER, "on".equals(obj.getString("sms_customer"))?"1":"0");
		
		/*
		 * 去除排队机界面提示标签
		 * 
		 * cona.set("label_content",obj.getString("label_content"));
		cona.set("label_text_size",obj.getString("label_text_size"));
		cona.set("label_text_color",obj.getString("label_text_color"));
		cona.set("label_text_style",obj.getString("label_text_style"));
		cona.set("label_text_font",obj.getString("label_text_font"));
		*/
		
		//conaV2.set(IDictABT.ISPRECONTRACT, "on".equals(obj.getString("isprecontract"))?"1":"0");
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn tet=new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		
		return AJAX_SUCCESS;
	}
	
	/**
	 * 修改排队机参数
	 * @return
	 * @throws Exception
	 */
	public String editQMParam() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b105_3.01", user);
		cona.set(IDictABT.BRANCH, obj.getString("branch"));
		cona.set(IDictABT.QM_PARAM_ID, obj.getString("qm_param_id"));
		cona.set(IDictABT.DEFAULT_FLAG, "on".equals(obj.getString("default_flag"))?"0":"1");
		cona.set(IDictABT.THEME_ID, obj.getString("theme_id"));
		cona.set(IDictABT.TICKET_STYLE_ID, obj.getString("ticket_style_id"));
		//cona.set("doublescreen_id", obj.getString("doublescreen_id"));
		cona.set(IDictABT.TIMEING_SHUTDOWN, obj.getString("timeing_shutdown").replace(":",""));
		cona.set(IDictABT.CFG_PWD, obj.getString("cfg_pwd"));
		cona.set(IDictABT.CALL_VOICE, obj.getString("call_voice"));
		cona.set(IDictABT.SHOW_BS_WAITNUM, "on".equals(obj.getString("show_bs_waitnum"))?"1":"0");
		cona.set(IDictABT.BS_SERVICETIME_STATUS, "on".equals(obj.getString("bs_servicetime_status"))?"1":"0");
		//conaV2.set(IDictABT.THEME_STATUS, "on".equals(obj.getString("theme_status"))?"1":"0");
		cona.set(IDictABT.SHOW_CLOCK, "on".equals(obj.getString("show_clock"))?"1":"0");
		//conaV2.set(IDictABT.SMS_CUSTOMER, "on".equals(obj.getString("sms_customer"))?"1":"0");
		
		/*
		 * 去除修改弹窗中的 排队机界面提示标签
		 * 
		cona.set("label_content",obj.getString("label_content"));
		cona.set("label_text_size",obj.getString("label_text_size"));
		cona.set("label_text_color",obj.getString("label_text_color"));
		cona.set("label_text_style",obj.getString("label_text_style"));
		cona.set("label_text_font",obj.getString("label_text_font"));
		*/
		
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 删除排队机参数
	 * @return
	 * @throws Exception
	 */
	public String delQMParam() throws Exception {
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
			cona.setBMSHeader("ibp.bms.b105_4.01", user);
			cona.set(IDictABT.QM_PARAM_ID, obj[0].toString());
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