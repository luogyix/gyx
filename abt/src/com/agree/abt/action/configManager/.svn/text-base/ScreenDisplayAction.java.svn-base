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
 * 条屏信息配置
 * @ClassName: QueueMachineInfoAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2013-8-12
 */
public class ScreenDisplayAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	private Log logger = LogFactory.getLog(ScreenDisplayAction.class);	
	/**
	 * 加载主页面
	 * @return
	 * @throws Exception
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		logger.info("已注入:" + cona.getIp() + ":" + cona.getPort());//已注入:192.9.202.224:9012
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 查询条屏信息
	 * @return
	 * @throws Exception
	 */
	public String queryScreen() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b111_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona.set("query_rules", obj.getString("query_rules"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("screensize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.SCREEN_ID, map.get("screen_id").get(i));
			hld.put(IDictABT.DEFAULT_FLAG, "1".equals(map.get("default_flag").get(i))?"0":"1");
			hld.put(IDictABT.FREE_CONTENT, map.get("free_content").get(i));
			hld.put(IDictABT.FREE_STYLE, map.get("free_style").get(i));
			hld.put(IDictABT.FREE_SPEED, map.get("free_speed").get(i));
			hld.put(IDictABT.CALL_CONTENT, map.get("call_content").get(i));
			hld.put(IDictABT.CALL_STYLE, map.get("call_style").get(i));
			hld.put(IDictABT.CALL_SPEED, map.get("call_speed").get(i));
			hld.put(IDictABT.PAUSE_CONTENT, map.get("pause_content").get(i));
			hld.put(IDictABT.PAUSE_STYLE, map.get("pause_style").get(i));
			hld.put(IDictABT.PAUSE_SPEED, map.get("pause_speed").get(i));
			hld.put(IDictABT.SERVE_CONTENT, map.get("serve_content").get(i));
			hld.put(IDictABT.SERVE_STYLE, map.get("serve_style").get(i));
			hld.put(IDictABT.SERVE_SPEED, map.get("serve_speed").get(i));
			list.add(hld);
		}
		pageInfo.setTotal(num);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 查询条屏信息
	 * @return
	 * @throws Exception
	 */
	public String queryScreenPage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b111_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona.set("query_rules", obj.getString("query_rules"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("screensize").get(0);
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
			hld.put(IDictABT.SCREEN_ID, map.get("screen_id").get(i));
			hld.put(IDictABT.DEFAULT_FLAG, "1".equals(map.get("default_flag").get(i))?"0":"1");
			hld.put(IDictABT.FREE_CONTENT, map.get("free_content").get(i));
			hld.put(IDictABT.FREE_STYLE, map.get("free_style").get(i));
			hld.put(IDictABT.FREE_SPEED, map.get("free_speed").get(i));
			hld.put(IDictABT.CALL_CONTENT, map.get("call_content").get(i));
			hld.put(IDictABT.CALL_STYLE, map.get("call_style").get(i));
			hld.put(IDictABT.CALL_SPEED, map.get("call_speed").get(i));
			hld.put(IDictABT.PAUSE_CONTENT, map.get("pause_content").get(i));
			hld.put(IDictABT.PAUSE_STYLE, map.get("pause_style").get(i));
			hld.put(IDictABT.PAUSE_SPEED, map.get("pause_speed").get(i));
			hld.put(IDictABT.SERVE_CONTENT, map.get("serve_content").get(i));
			hld.put(IDictABT.SERVE_STYLE, map.get("serve_style").get(i));
			hld.put(IDictABT.SERVE_SPEED, map.get("serve_speed").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 添加条屏信息
	 * @return String
	 * @throws Exception
	 */
	public String addScreen() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b111_1.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		//conaV2.set(IDictABT.SCREEN_ID, obj.getString("screen_id"));
		cona.set(IDictABT.DEFAULT_FLAG, "on".equals(obj.getString("default_flag"))?"0":"1");
		cona.set(IDictABT.FREE_CONTENT, obj.getString("free_content"));
		cona.set(IDictABT.FREE_STYLE, obj.getString("free_style"));
		cona.set(IDictABT.FREE_SPEED, obj.getString("free_speed"));
		cona.set(IDictABT.CALL_CONTENT, obj.getString("call_content"));
		cona.set(IDictABT.CALL_STYLE, obj.getString("call_style"));
		cona.set(IDictABT.CALL_SPEED, obj.getString("call_speed"));
		cona.set(IDictABT.PAUSE_CONTENT, obj.getString("pause_content"));
		cona.set(IDictABT.PAUSE_STYLE, obj.getString("pause_style"));
		cona.set(IDictABT.PAUSE_SPEED, obj.getString("pause_speed"));
		cona.set(IDictABT.SERVE_CONTENT, "@@@@@号正在办理");
		cona.set(IDictABT.SERVE_STYLE, "0");
		cona.set(IDictABT.SERVE_SPEED, "5");
		
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn tet=new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 修改条屏信息
	 * @return String
	 * @throws Exception
	 */
	public String editScreen() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b111_3.01", user);
		cona.set(IDictABT.BRANCH, obj.getString("branch"));
		cona.set(IDictABT.SCREEN_ID, obj.getString("screen_id"));
		cona.set(IDictABT.DEFAULT_FLAG, "on".equals(obj.getString("default_flag"))?"0":"1");
		cona.set(IDictABT.FREE_CONTENT, obj.getString("free_content"));
		cona.set(IDictABT.FREE_STYLE, obj.getString("free_style"));
		cona.set(IDictABT.FREE_SPEED, obj.getString("free_speed"));
		cona.set(IDictABT.CALL_CONTENT, obj.getString("call_content"));
		cona.set(IDictABT.CALL_STYLE, obj.getString("call_style"));
		cona.set(IDictABT.CALL_SPEED, obj.getString("call_speed"));
		cona.set(IDictABT.PAUSE_CONTENT, obj.getString("pause_content"));
		cona.set(IDictABT.PAUSE_STYLE, obj.getString("pause_style"));
		cona.set(IDictABT.PAUSE_SPEED, obj.getString("pause_speed"));
		cona.set(IDictABT.SERVE_CONTENT, obj.getString("serve_content"));
		cona.set(IDictABT.SERVE_STYLE, obj.getString("serve_style"));
		cona.set(IDictABT.SERVE_SPEED, obj.getString("serve_speed"));
		//判断afa的返回结果,是否成功
		cona.exchange();
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 删除条屏信息
	 * @return String
	 * @throws Exception
	 */

	public String delScreen() throws Exception {
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
			cona.setBMSHeader("ibp.bms.b111_4.01", user);
			cona.set(IDictABT.BRANCH, obj[0].toString());
			cona.set(IDictABT.SCREEN_ID, obj[1].toString());
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
