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
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;

/**
 * 队列监控配置
 * 
 * @company 赞同科技
 * @author XiWang
 * @date 2013-9-21
 */
public class QMMonitorConfAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	private static final Log logger = LogFactory.getLog(QMMonitorConfAction.class);//日志
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);

		ServletActionContext.getRequest().setAttribute(
				ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}

	public String queryQMMonitorConf() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo = new Page();
		// 查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b112_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set("query_rules", "0");
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		logger.info(map);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("queuemonitorsize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.QUEUETYPE_ID, map.get("queuetype_id").get(i));
			hld.put("waitnum_threshold", map.get("waitnum_threshold").get(i));
			hld.put("waittime_threshold", map.get("waittime_threshold").get(i));
			hld.put("notify_threshold", map.get("notify_threshold").get(i));
			hld.put("show_notify_threshold", map.get("show_notify_threshold")
					.get(i));
			list.add(hld);
		}
		pageInfo.setTotal(num);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	public void queryQMMonitorConfByBranch() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		// 查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b112_2.01", user);
		cona.set(IDictABT.BRANCH, req.getParameter("branch"));
		cona.set("query_rules", "0");
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		logger.info(map);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("queuemonitorsize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.QUEUETYPE_ID, map.get("queuetype_id").get(i));
			hld.put("waitnum_threshold", map.get("waitnum_threshold").get(i));
			hld.put("waittime_threshold", map.get("waittime_threshold").get(i));
			hld.put("notify_threshold", map.get("notify_threshold").get(i));
			hld.put("show_notify_threshold", map.get("show_notify_threshold")
					.get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	public String queryQMMonitorConfPage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		// 查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b112_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set("query_rules", "0");
		Map<String, List<String>> map = cona.exchange();
		String validate = "";
		validate = cona.validMap(map);
		if (validate != null && validate.trim().length() > 0) {
			throw new AppException(validate);
		} else {
		}
		if(map.get("H_ret_code") == null){
			throw new AppException("与后台服务通讯失败,没有返回状态码(H_ret_code)和状态信息");
		}
		String loopNum = (String) map.get("queuemonitorsize").get(0);
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
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (int i=pageInfo.getRowStart();i<pageInfo.getRowEnd();i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.QUEUETYPE_ID, map.get("queuetype_id").get(i));
			hld.put("waitnum_threshold", map.get("waitnum_threshold").get(i));
			hld.put("waittime_threshold", map.get("waittime_threshold").get(i));
			hld.put("notify_threshold", map.get("notify_threshold").get(i));
			hld.put("show_notify_threshold", map.get("show_notify_threshold")
					.get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}

	public String addQMMonitorConf() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);

		 
		
		cona.setBMSHeader("ibp.bms.b112_1.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.QUEUETYPE_ID, obj.getString(IDictABT.QUEUETYPE_ID));
		cona.set("waitnum_threshold", obj.getString("waitnum_threshold"));
		cona.set("waittime_threshold", obj.getString("waittime_threshold"));
		cona.set("notify_threshold", obj.getString("notify_threshold"));
		cona.set("show_notify_threshold",
				obj.getString("show_notify_threshold"));

		cona.exchange();
		ServiceReturn tet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	public String editQMMonitorConf() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b112_3.01", user);
		cona.set(IDictABT.BRANCH, obj.getString(IDictABT.BRANCH));
		cona.set(IDictABT.QUEUETYPE_ID, obj.getString("queuetype_id_old"));
		cona.set("queuetype_id_new", obj.getString(IDictABT.QUEUETYPE_ID));
		cona.set("waitnum_threshold", obj.getString("waitnum_threshold"));
		cona.set("waittime_threshold", obj.getString("waittime_threshold"));
		cona.set("notify_threshold", obj.getString("notify_threshold"));
		cona.set("show_notify_threshold",obj.getString("show_notify_threshold"));

		// if(Integer.parseInt(dataEnd)-Integer.parseInt(dataStart)<0){
		// throw new AppException("数据的结束位置不能小于数据的开始位置，请核对" );
		// }

		cona.exchange();
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	public String delQMMonitorConf() throws Exception {
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
			cona.setBMSHeader("ibp.bms.b112_4.01", user);
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
