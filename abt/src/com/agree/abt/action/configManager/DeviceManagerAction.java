package com.agree.abt.action.configManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

/**
 * 设备类型配置
 * @ClassName: DeviceManagerAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2014-4-8
 */
public class DeviceManagerAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;

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
		return SUCCESS;
	}
	
	/**
	 * 查询设备类型
	 */
	public String queryDeviceManager() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b402_2.01", user);
		if(!"".equals(obj.getString("devicetype"))){
			cona.set("devicetype", obj.getString("devicetype"));
		}
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("devicetypesize").get(0);
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
			hld.put("devicetype",map.get("devicetype").get(i));
			hld.put("connecttype",map.get("connecttype").get(i));
			hld.put("relaydeviceip",map.get("relaydeviceip").get(i));
			hld.put("relaydeviceport",map.get("relaydeviceport").get(i));
			hld.put("msgprotocol",map.get("msgprotocol").get(i));
			hld.put("msgmode",map.get("msgmode").get(i));
			hld.put("deviceclass",map.get("deviceclass").get(i));
			hld.put("serviceno",map.get("serviceno").get(i));
			hld.put("servicecode",map.get("servicecode").get(i));
			hld.put("devicestatus",map.get("devicestatus").get(i));
			hld.put("msgrecvtype",map.get("msgrecvtype").get(i));
			list.add(hld);
		}
		
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 添加设备类型
	 */
	public String addDeviceManager() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b402_1.01", user);
		cona.set("devicetype",obj.getString("devicetype"));
		cona.set("connecttype",obj.getString("connecttype"));
		cona.set("relaydeviceip",obj.getString("relaydeviceip"));
		cona.set("relaydeviceport",obj.getString("relaydeviceport"));
		cona.set("msgprotocol",obj.getString("msgprotocol"));
		cona.set("msgmode",obj.getString("msgmode"));
		cona.set("deviceclass",obj.getString("deviceclass"));
		cona.set("serviceno",obj.getString("serviceno"));
		cona.set("servicecode",obj.getString("servicecode"));
		cona.set("devicestatus",obj.getString("devicestatus"));
		cona.set("msgrecvtype",obj.getString("msgrecvtype"));
		//判断afa的返回结果,是否成功
		cona.exchange();
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 修改设备类型
	 */
	public String editDeviceManager() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b402_3.01", user);
		cona.set("devicetype_old",obj.getString("devicetype_old"));
		cona.set("devicetype",obj.getString("devicetype"));
		cona.set("connecttype",obj.getString("connecttype"));
		cona.set("relaydeviceip",obj.getString("relaydeviceip"));
		cona.set("relaydeviceport",obj.getString("relaydeviceport"));
		cona.set("msgprotocol",obj.getString("msgprotocol"));
		cona.set("msgmode",obj.getString("msgmode"));
		cona.set("deviceclass",obj.getString("deviceclass"));
		cona.set("serviceno",obj.getString("serviceno"));
		cona.set("servicecode",obj.getString("servicecode"));
		cona.set("devicestatus",obj.getString("devicestatus"));
		cona.set("msgrecvtype",obj.getString("msgrecvtype"));
		//判断afa的返回结果,是否成功
		cona.exchange();
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 删除设备类型
	 */
	public String delDeviceManager() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = JSONObject.fromObject(jsonArray.getString(i));
			 
			//初始化NATP通讯
			cona.reInit();
			cona.setBMSHeader("ibp.bms.b402_4.01", user);
			cona.set("devicetype", obj.getString("devicetype"));
			//判断afa的返回结果,是否成功
			cona.exchange();
		}
		
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
}
