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
 * 消息推送规则配置
 * @ClassName: MsgPushRuleAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2014-4-11
 */
public class MsgPushRuleAction extends BaseAction {

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
	 * 查询消息推送规则
	 */
	public String queryMsgPushRule() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b403_2.01", user);
		if(!"".equals(obj.getString("msgtype"))){
			cona.set("msgtype", obj.getString("msgtype"));
		}
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
			hld.put("msgtype",map.get("msgtype").get(i));
			hld.put("devicetype",map.get("devicetype").get(i));
			hld.put("pushtype",map.get("pushtype").get(i));
			hld.put("delaytime",map.get("delaytime").get(i));
			hld.put("retrytime",map.get("retrytime").get(i));
			hld.put("retrynum",map.get("retrynum").get(i));
			hld.put("outmode",map.get("outmode").get(i));
			hld.put("msgaddition",map.get("msgaddition").get(i));
			hld.put("pushmode",map.get("pushmode").get(i));
			hld.put("pushcondition",map.get("pushcondition").get(i));
			hld.put("conditiondesc",map.get("conditiondesc").get(i));
			list.add(hld);
		}
		
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 添加消息推送规则
	 */
	public String addMsgPushRule() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b403_1.01", user);
		cona.set("msgtype",obj.getString("msgtype"));
		cona.set("devicetype",obj.getString("devicetype"));
		cona.set("pushtype",obj.getString("pushtype"));
		cona.set("delaytime",obj.getString("delaytime"));
		cona.set("retrytime",obj.getString("retrytime"));
		cona.set("retrynum",obj.getString("retrynum"));
		cona.set("outmode",obj.getString("outmode"));
		cona.set("msgaddition",obj.getString("msgaddition"));
		cona.set("pushmode",obj.getString("pushmode"));
		cona.set("pushcondition",obj.getString("pushcondition"));
		cona.set("conditiondesc",obj.getString("conditiondesc"));
		//判断afa的返回结果,是否成功
		cona.exchange();

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 修改消息推送规则
	 */
	public String editMsgPushRule() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b403_3.01", user);
		cona.set("msgtype_old",obj.getString("msgtype_old"));
		cona.set("devicetype_old",obj.getString("devicetype_old"));
		cona.set("msgtype",obj.getString("msgtype"));
		cona.set("devicetype",obj.getString("devicetype"));
		cona.set("pushtype",obj.getString("pushtype"));
		cona.set("delaytime",obj.getString("delaytime"));
		cona.set("retrytime",obj.getString("retrytime"));
		cona.set("retrynum",obj.getString("retrynum"));
		cona.set("outmode",obj.getString("outmode"));
		cona.set("msgaddition",obj.getString("msgaddition"));
		cona.set("pushmode",obj.getString("pushmode"));
		cona.set("pushcondition",obj.getString("pushcondition"));
		cona.set("conditiondesc",obj.getString("conditiondesc"));
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 删除消息推送规则
	 */
	public String delMsgPushRule() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = JSONObject.fromObject(jsonArray.getString(i));
			 
			//初始化NATP通讯
			cona.reInit();
			cona.setBMSHeader("ibp.bms.b403_4.01", user);
			cona.set("msgtype",obj.getString("msgtype"));
			cona.set("devicetype",obj.getString("devicetype"));
			//判断afa的返回结果,是否成功
			cona.exchange();
			
		}
		
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
}
