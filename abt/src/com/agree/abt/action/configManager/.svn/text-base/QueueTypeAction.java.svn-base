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

import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;

/**
 * 队列配置
 * @ClassName: QueueTypeAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2013-9-8
 */
public class QueueTypeAction extends BaseAction{
	
	private ABTComunicateNatp cona;
	private static final long serialVersionUID = 1L;
	
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD2,super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 查询队列
	 * @return
	 * @throws Exception
	 */
	public String queryQueue() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		Page pageInfo = new Page();
		 
		
		cona.setBMSHeader("ibp.bms.b110_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona.set("query_rules", obj.getString("query_rules"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("queuetypesize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BRANCH, map.get(IDictABT.BRANCH).get(i));
			hld.put(IDictABT.QUEUETYPE_ID, map.get(IDictABT.QUEUETYPE_ID).get(i));
			hld.put(IDictABT.QUEUETYPE_NAME, map.get(IDictABT.QUEUETYPE_NAME).get(i));
			hld.put(IDictABT.QUEUETYPE_PREFIX, map.get(IDictABT.QUEUETYPE_PREFIX).get(i));
			hld.put(IDictABT.QUEUETYPE_PREFIX_NUM, map.get(IDictABT.QUEUETYPE_PREFIX_NUM).get(i));
			hld.put(IDictABT.QUEUETYPE_LEVEL, map.get(IDictABT.QUEUETYPE_LEVEL).get(i));
			hld.put("queuetype_trans", map.get("queuetype_trans").get(i));
			hld.put(IDictABT.STATUS, map.get(IDictABT.STATUS).get(i));
			list.add(hld);
		}
		pageInfo.setTotal(num);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 查询队列
	 * @return
	 * @throws Exception
	 */
	public String queryQueuePage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b110_2.01", user);
		//cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.BRANCH, obj.getString("branch"));
		cona.set("query_rules", obj.getString("query_rules"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("queuetypesize").get(0);
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
			hld.put(IDictABT.BRANCH, map.get(IDictABT.BRANCH).get(i));
			hld.put(IDictABT.QUEUETYPE_ID, map.get(IDictABT.QUEUETYPE_ID).get(i));
			hld.put(IDictABT.QUEUETYPE_NAME, map.get(IDictABT.QUEUETYPE_NAME).get(i));
			hld.put(IDictABT.QUEUETYPE_PREFIX, map.get(IDictABT.QUEUETYPE_PREFIX).get(i));
			hld.put(IDictABT.QUEUETYPE_PREFIX_NUM, map.get(IDictABT.QUEUETYPE_PREFIX_NUM).get(i));
			hld.put(IDictABT.QUEUETYPE_LEVEL, map.get(IDictABT.QUEUETYPE_LEVEL).get(i));
			hld.put("queuetype_trans", map.get("queuetype_trans").get(i));
			hld.put(IDictABT.STATUS, map.get(IDictABT.STATUS).get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 查询队列下拉框
	 * @throws Exception
	 */
	public void queryQueueTypeSmall() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b110_2.01", user);
		cona.set("branch", user.getUnitid()); //查询条件.待确认
		cona.set("query_rules", req.getParameter("query_rules"));
		
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("queuetypesize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BRANCH, map.get(IDictABT.BRANCH).get(i));
			hld.put(IDictABT.QUEUETYPE_ID, map.get(IDictABT.QUEUETYPE_ID).get(i));
			hld.put(IDictABT.QUEUETYPE_NAME, map.get(IDictABT.QUEUETYPE_NAME).get(i));
			hld.put(IDictABT.QUEUETYPE_PREFIX, map.get(IDictABT.QUEUETYPE_PREFIX).get(i));
			hld.put(IDictABT.QUEUETYPE_PREFIX_NUM, map.get(IDictABT.QUEUETYPE_PREFIX_NUM).get(i));
			hld.put(IDictABT.QUEUETYPE_LEVEL, map.get(IDictABT.QUEUETYPE_LEVEL).get(i));
			hld.put("queuetype_trans", map.get("queuetype_trans").get(i));
			hld.put(IDictABT.STATUS, map.get(IDictABT.STATUS).get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		//List<Map<String,String>> retn=new ArrayList<Map<String,String>> ();
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	/**
	 * 添加队列
	 * @return
	 * @throws Exception
	 */
	public String addQueue() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b110_1.01", user);
		//cona.set(IDictABT.BRANCH, user.getUnitid());
		if ("1".equals(user.getUnit().getBank_level())) {//总行
			if(obj.containsKey("branch")){
				cona.set(IDictABT.BRANCH, obj.getString(IDictABT.BRANCH));
			}
		} else {
			cona.set(IDictABT.BRANCH, user.getUnitid());
		}
		cona.set(IDictABT.QUEUETYPE_NAME, obj.getString(IDictABT.QUEUETYPE_NAME));
		cona.set(IDictABT.QUEUETYPE_PREFIX, obj.getString(IDictABT.QUEUETYPE_PREFIX).toUpperCase());
		cona.set(IDictABT.QUEUETYPE_PREFIX_NUM, obj.getString(IDictABT.QUEUETYPE_PREFIX_NUM));
		cona.set(IDictABT.QUEUETYPE_LEVEL, "".equals(obj.getString(IDictABT.QUEUETYPE_LEVEL))?"1":obj.getString(IDictABT.QUEUETYPE_LEVEL));
		cona.set("queuetype_trans", "on".equals(obj.getString("queuetype_trans"))?"1":"0");
		cona.set(IDictABT.STATUS, "on".equals(obj.getString("status"))?"1":"0");
		//判断afa的返回结果,是否成功
		cona.exchange();

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 修改队列
	 * @return
	 * @throws Exception
	 */
	public String editQueue() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b110_3.01", user);
		cona.set(IDictABT.QUEUETYPE_ID, obj.getString(IDictABT.QUEUETYPE_ID));
		cona.set(IDictABT.BRANCH, obj.getString(IDictABT.BRANCH));
		cona.set(IDictABT.QUEUETYPE_NAME, obj.getString(IDictABT.QUEUETYPE_NAME));
		cona.set(IDictABT.QUEUETYPE_PREFIX, obj.getString(IDictABT.QUEUETYPE_PREFIX).toUpperCase());
		cona.set(IDictABT.QUEUETYPE_PREFIX_NUM, obj.getString(IDictABT.QUEUETYPE_PREFIX_NUM));
		cona.set(IDictABT.QUEUETYPE_LEVEL, "".equals(obj.getString(IDictABT.QUEUETYPE_LEVEL))?"1":obj.getString(IDictABT.QUEUETYPE_LEVEL));
		cona.set("queuetype_trans", "on".equals(obj.getString("queuetype_trans"))?"1":"0");
		cona.set(IDictABT.STATUS, "on".equals(obj.getString("status"))?"1":"0");
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 删除队列
	 * @return
	 * @throws Exception
	 */
	public String delQueue() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
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
			cona.setBMSHeader("ibp.bms.b110_4.01", user);
			cona.set(IDictABT.QUEUETYPE_ID, obj[0].toString());
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
