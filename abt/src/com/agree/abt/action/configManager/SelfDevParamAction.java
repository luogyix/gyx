package com.agree.abt.action.configManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * 设备参数管理
 * 
 * @ClassName: DevParamManager.java
 * @company 赞同科技
 * @author Hujuqiang
 * @date 2016-8-16
 */

public class SelfDevParamAction extends BaseAction {

	private ABTComunicateNatp cona;
	private static final long serialVersionUID = 2141897762615781445L;

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}

	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());// 获取部门集合
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(
				ApplicationConstants.ACTIONRESULT, retObj.toString());

		return SUCCESS;
	}

	public String queryDevParam() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		Page pageInfo = new Page();

		cona.setBMSHeader("ibp.bms.b136_2.01", user);
		cona.set("branch", user.getUnitid());
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona.set("query_rules", obj.getString("query_rules"));
		Map<String, List<String>> map = cona.exchange();
		String validate = "";
		validate = cona.validMap(map);
		if (validate != null && validate.trim().length() > 0) {
			throw new AppException(validate);
		}
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String loopNum = map.get("selfdevparamsize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, String> hld = new HashMap<String, String>();
			hld.put(IDictABT.PARAMETER_ID, map.get("parameter_id").get(i));
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));

			hld.put(IDictABT.DEFAULT_FLAG,"1".equals(map.get("default_flag").get(i)) ? "0" : "1");
			hld.put(IDictABT.TIME_SHUTDOWN, map.get("time_shutdown").get(i));
			hld.put(IDictABT.TIME_PAGEOUT, map.get("time_pageout").get(i));

			hld.put(IDictABT.TIME_MAINSHOW, map.get("time_mainshow").get(i));

			list.add(hld);
		}
		pageInfo.setTotal(num);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}

	public String queryDevParamPage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		
		cona.setBMSHeader("ibp.bms.b136_2.01", user);
		cona.set("branch", user.getUnitid());
		cona.set("query_rules", obj.getString("query_rules"));
		Map<String, List<String>> map = cona.exchange();
		String validate = "";
		validate = cona.validMap(map);
		if (validate != null && validate.trim().length() > 0) {
			throw new AppException(validate);
		}
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String loopNum = map.get("selfdevparamsize").get(0);
		int num = Integer.parseInt(loopNum);
		Page pageInfo = this.getPage(obj);
		pageInfo.setTotal(num);// 总条数
		Integer pageNo = (pageInfo.getTotal() % pageInfo.getLimit() == 0) ? pageInfo
				.getTotal() / pageInfo.getLimit()
				: pageInfo.getTotal() / pageInfo.getLimit() + 1;// 得到总页数
		if (pageInfo.getStart() == -1) {// 查询最后一页
			pageInfo.setRowStart((pageNo - 1) * pageInfo.getLimit());
			pageInfo.setRowEnd(pageInfo.getRowStart()
					+ (pageInfo.getTotal() % pageInfo.getLimit() == 0 ? pageInfo
							.getLimit() : pageInfo.getTotal()
							% pageInfo.getLimit()));
			pageInfo.setPage(pageNo);
			pageInfo.setTotal(pageInfo.getTotal());
		} else {
			pageInfo.setRowStart((pageInfo.getStart() - 1)
					* pageInfo.getLimit());
			pageInfo.setRowEnd((pageInfo.getRowStart() + pageInfo.getLimit()) <= pageInfo
					.getTotal() ? (pageInfo.getRowStart() + pageInfo.getLimit())
					: pageInfo.getTotal());
			pageInfo.setPage(pageNo);
			pageInfo.setTotal(pageInfo.getTotal());
		}
		for (int i = pageInfo.getRowStart(); i < pageInfo.getRowEnd(); i++) {
			Map<String, String> hld = new HashMap<String, String>();
			hld.put(IDictABT.PARAMETER_ID, map.get("parameter_id").get(i));
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));

			hld.put(IDictABT.DEFAULT_FLAG,
					"1".equals(map.get("default_flag").get(i)) ? "0" : "1");
			hld.put(IDictABT.TIME_SHUTDOWN, map.get("time_shutdown").get(i));
			hld.put(IDictABT.TIME_PAGEOUT, map.get("time_pageout").get(i));

			hld.put(IDictABT.TIME_ADDSHOW, map.get("time_addshow").get(i));
			hld.put(IDictABT.TIME_MAINSHOW, map.get("time_mainshow").get(i));

			list.add(hld);
		}
		pageInfo.setTotal(num);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}

	/**
	 * 添加设备参数信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addDevParam() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona.setBMSHeader("ibp.bms.b136_1.01", user);
		if ("on".equalsIgnoreCase(obj.getString("default_flag"))) {
			cona.set(IDictABT.DEFAULT_FLAG, "0");
		} else {
			cona.set(IDictABT.DEFAULT_FLAG, "1");
		}

		cona.set(IDictABT.BRANCH,user.getUnitid());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		cona.set(IDictABT.PARAMETER_ID,sdf.format(new Date()));

		cona.set(IDictABT.TIME_SHUTDOWN, obj.getString("time_shutdown"));

		cona.set(IDictABT.TIME_PAGEOUT, obj.getString("time_pageout"));
		cona.set(IDictABT.TIME_MAINSHOW, obj.getString("time_mainshow"));
		cona.exchange();
		ServiceReturn tet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 修改设备参数信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editDevParam() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona.setBMSHeader("ibp.bms.b136_3.01",user);
		if ("on".equalsIgnoreCase(obj.getString("default_flag"))) {
			cona.set(IDictABT.DEFAULT_FLAG, "0");
		} else {
			cona.set(IDictABT.DEFAULT_FLAG, "1");
		}
		cona.set(IDictABT.PARAMETER_ID, obj.getString("parameter_id"));
		cona.set(IDictABT.BRANCH, obj.getString("branch"));
		cona.set(IDictABT.TIME_SHUTDOWN, obj.getString("time_shutdown"));

		cona.set(IDictABT.TIME_PAGEOUT, obj.getString("time_pageout"));
		cona.set(IDictABT.TIME_MAINSHOW, obj.getString("time_mainshow"));

		cona.exchange();
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 删除设备信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delDevParam() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);

		// TODO 群体删除修改
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = JSONObject.fromObject(jsonArray.getString(i));
			Object obj[] = jsonObj.values().toArray();
			cona.reInit();
			cona.setBMSHeader("ibp.bms.b136_4.01", user);
			cona.set("parameter_id", obj[0].toString());
			cona.set("branch", obj[1].toString());
			Map<String, List<String>> map = cona.exchange();
			String validate = "";
			validate = cona.validMap(map);
			if (validate != null && validate.trim().length() > 0) {
				throw new AppException(validate);
			}
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
}
