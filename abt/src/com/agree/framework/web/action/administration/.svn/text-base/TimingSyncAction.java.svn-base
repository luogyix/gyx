package com.agree.framework.web.action.administration;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

/**
 * Servlet implementation class TimingSyncAction
 */
public class TimingSyncAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;    
	
	
	private ABTComunicateNatp cona;
	
	
	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}

	/**
	 * 
	 * @Title: loadPage
	 * @Description: TODO
	 * @return: String
	 * @throws
	 * @user: zhangkeyan
	 * @data: 2016-7-12
	 */
	public String loadPage() throws Exception{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());//获取部门集合
		sRet.put(ServiceReturn.FIELD2, super.getLogonUser(true));//获取登录用户信息
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Title: timing_manage
	 * @Description: TODO
	 * @return: String
	 * @throws
	 * @user: zhangkeyan
	 * @data: 2016-7-6
	 */
	public String manage() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona = new ABTComunicateNatp();
		cona.setBMSHeader("ibp.bms.b210_1.01", user);
		cona.set("status", "1");
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = map.get("tradeSyncSize").get(0);
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
			hld.put("templatecode", map.get("templatecode").get(i));
			hld.put("transcode", map.get("transcode").get(i));
			hld.put("status", map.get("status").get(i));
			hld.put("skunumber", map.get("skunumber").get(i));
			hld.put("exec_start_datetime", map.get("exec_start_datetime").get(i));
			hld.put("exec_end_datetime", map.get("exec_end_datetime").get(i));
			hld.put("exec_status", map.get("exec_status").get(i));
			hld.put("success_times", map.get("success_times").get(i));
			hld.put("trade_name", map.get("trade_name").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 修改可执行次数
	 * @return
	 * @throws Exception
	 */
	public String editTradeStatus() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		
		cona.reInit();
		cona.setBMSHeader("ibp.bms.b210_2.01", user);
		cona.set("branch", user.getUnitid());
		cona.set("templatecodeA", obj.getString("templatecode"));
		cona.set("transcodeA",obj.getString("transcode"));
		cona.set("success_times",obj.getString("success_times"));
		cona.set("skunumber",obj.getString("skunumber"));
		
		
		//判断afa的返回结果,是否成功
		cona.exchange();
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

}
