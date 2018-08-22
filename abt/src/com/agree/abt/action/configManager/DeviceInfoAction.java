package com.agree.abt.action.configManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.agree.util.DateUtil;
import com.agree.util.IDictABT;

/**
 * 设备信息配置
 * 
 * @ClassName: PfsMsgInfoAction.java
 * @company 赞同科技
 * @author 赵明
 * @date 2014-3-3
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DeviceInfoAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;

	/**
	 * 加载主页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(
				ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}

	/**
	 * 查询设备信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryDeviceInfo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		Page pageInfo = new Page();
		// 查询交易接口

		cona.setBMSHeader("ibp.bms.b131_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		// 判断afa的返回结果,是否成功
		Map<String, List<String>> map = cona.exchange();
		if (map.get("H_ret_code") == null) {
			throw new AppException("与后台服务通讯失败,没有返回状态码(H_ret_code)和状态信息");
		}
		String H_ret_code = (String) map.get("H_ret_code").get(0);
		if (!H_ret_code.equals(IDictABT.AFARESULTSTATUS_SUCC)) {
			throw new AppException("错误码:" + H_ret_code + "," + "错误信息:"
					+ map.get("H_ret_desc").get(0));
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String loopNum = (String) map.get("devicesize").get(0);
		pageInfo.setTotal(Integer.parseInt(loopNum));
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			Set keyset = map.keySet();
			Iterator<String> it = keyset.iterator();
			while (it.hasNext()) {
				String key = it.next();
				List<String> tmep = (List<String>) map.get(key);
				if (tmep.size() < num) {
					continue;
				} else {
					hld.put(key, tmep.get(i));
				}
			}
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}

	/**
	 * 查询设备信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryDeviceInfoPage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		// 查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		Page pageInfo = this.getPage(obj);

		cona.setBMSHeader("ibp.bms.b131_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.DEVICETYPE, obj.getString("devicetype"));

		// 分页专用
		cona.set("pageflag", pageInfo.getPageflag().toString());// 分页标识
		String currentpage = "";
		switch (pageInfo.getPageflag()) {
		case 0:
			currentpage = "1";
			pageInfo.setStart(1);
			break;
		case 1:
			currentpage = "1";
			break;
		case 2:
			currentpage = String.valueOf(pageInfo.getStart() + 1);
			break;
		case 3:
			currentpage = String.valueOf(pageInfo.getStart() - 1);
			break;
		case 4:
			currentpage = String.valueOf(pageInfo.getStart());
			pageInfo.setStart(pageInfo.getStart());
			break;
		}
		cona.set("currentpage", currentpage);// 当前页码
		cona.set("count", pageInfo.getLimit().toString());// 每页记录数
		// 判断afa的返回结果,是否成功
		Map<String, List<String>> map = cona.exchange();
		String loopNum = (String) map.get("devicesize").get(0);
		int num = Integer.parseInt(loopNum);
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

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = pageInfo.getRowStart(); i < pageInfo.getRowEnd(); i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			Set keyset = map.keySet();
			Iterator<String> it = keyset.iterator();
			while (it.hasNext()) {
				String key = it.next();
				List<String> tmep = (List<String>) map.get(key);
				if (tmep.size() < pageInfo.getRowEnd() - pageInfo.getRowStart()) {
					continue;
				} else
					hld.put(key, tmep.get(i));

			}
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}

	/**
	 * 添加设备信息
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String addDeviceInfo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);

		cona.setBMSHeader("ibp.bms.b131_1.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set("device_ip", "");//设备ip
		cona.set("device_oid", "");//设备唯一标识
		//cona.set("check_code", "");
		Iterator keys = obj.keys();
		while (keys.hasNext()) {
			String key = keys.next().toString();
			if (obj.getString(key).equalsIgnoreCase("on")) {
				String a = obj.getString(key).replaceAll("on", "1");
				cona.set(key, a);
			} else if (obj.getString(key).equalsIgnoreCase("off")) {
				String a = obj.getString(key).replaceAll("off", "0");
				cona.set(key, a);
			} else
				cona.set(key, obj.getString(key));
		}
		// 生成checkcode
		if ("10".equals(obj.getString("devicetype"))
				|| "11".equals(obj.getString("devicetype"))) {
			// branch+device_num+hhmmss
			String num = user.getUnitid() + obj.getString("device_num")
					+ DateUtil.formatDate(new Date(), "HHmmss");
			cona.set("check_code", num);
		}
		// 判断afa的返回结果,是否成功
		cona.exchange();

		ServiceReturn tet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 修改设备信息
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String editDeviceInfo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);

		cona.setBMSHeader("ibp.bms.b131_3.01", user);
		Iterator keys = obj.keys();
		while (keys.hasNext()) {
			String key = keys.next().toString();
			if (obj.getString(key).equalsIgnoreCase("on")) {
				String a = obj.getString(key).replaceAll("on", "1");
				cona.set(key, a);
			} else if (obj.getString(key).equalsIgnoreCase("off")) {
				String a = obj.getString(key).replaceAll("off", "0");
				cona.set(key, a);
			} else
				cona.set(key, obj.getString(key));
		}

		cona.exchange();

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 删除设备信息
	 * 
	 * @return String
	 * @throws Exception
	 */

	public String delDeviceInfo() throws Exception {
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
			cona.setBMSHeader("ibp.bms.b131_4.01", user);
			cona.set(IDictABT.DEVICE_NUM, obj[0].toString());
			cona.set(IDictABT.BRANCH, obj[1].toString());
			cona.set(IDictABT.DEVICETYPE, obj[2].toString());
			// 判断afa的返回结果,是否成功
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
