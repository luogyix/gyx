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
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;

/**
 * 节假日配置
 * @ClassName: HolidayConfigAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2013-8-20
 */
public class HolidayConfigAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	
	/**
	 *加载主页面
	 * @return
	 * @throws Exception
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: queryHoliday
	 * @Description: 查询节假日信息
	 * @return String
	 * @throws Exception
	 */
	public String queryHoliday() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		 
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		
		cona.setBMSHeader("ibp.bms.b104_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set("query_rules", obj.getString("query_rules"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("holidaysize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.HOLIDAY_ID, map.get("holiday_id").get(i));
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.HOLIDAY_NAME, map.get("holiday_name").get(i));
			hld.put(IDictABT.THEME_ID, map.get("theme_id").get(i));
			String start=(String) map.get("start_date").get(i);
			if(start.trim().length()>0){
				start=start.substring(0,4)+"-"+start.substring(4,6)+"-"+start.substring(6,8);
			}
			hld.put(IDictABT.START_DATE,start);
			String end=(String) map.get("end_date").get(i);
			if( end.trim().length()>0){
				 end= end.substring(0,4)+"-"+ end.substring(4,6)+"-"+ end.substring(6,8);
			}
			hld.put(IDictABT.END_DATE, end);
			hld.put("note", map.get("note").get(i));
			
			list.add(hld);
		}
		pageInfo.setTotal(num);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);

		return AJAX_SUCCESS;
	}
	/**
	 * 分页查询节假日信息
	 * @Description: 分页查询节假日信息
	 * @return String
	 */
	public String queryHolidayPage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b104_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set("query_rules", obj.getString("query_rules"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("holidaysize").get(0);
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
			hld.put(IDictABT.HOLIDAY_ID, map.get("holiday_id").get(i));
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.HOLIDAY_NAME, map.get("holiday_name").get(i));
			hld.put(IDictABT.THEME_ID, map.get("theme_id").get(i));
			String start=(String) map.get("start_date").get(i);
			if(start.trim().length()>0){
				start=start.substring(0,4)+"-"+start.substring(4,6)+"-"+start.substring(6,8);
			}
			hld.put(IDictABT.START_DATE,start);
			String end=(String) map.get("end_date").get(i);
			if( end.trim().length()>0){
				end= end.substring(0,4)+"-"+ end.substring(4,6)+"-"+ end.substring(6,8);
			}
			hld.put(IDictABT.END_DATE, end);
			hld.put("note", map.get("note").get(i));
			
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		
		return AJAX_SUCCESS;
	}

	/**
	 * 
	 * @Title: addHoliday
	 * @Description: 添加节假日信息
	 * @return String
	 * @throws Exception
	 */
	public String addHoliday() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b104_1.01", user);
		String start=obj.getString("start_date");
		String end=obj.getString("end_date");
		start=start.replaceAll("-","");
		end=end.replaceAll("-","");
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.HOLIDAY_NAME, obj.getString("holiday_name"));
		cona.set(IDictABT.START_DATE,start);
		cona.set(IDictABT.END_DATE, end);
		cona.set(IDictABT.THEME_ID, obj.getString("theme_id"));
		cona.set("notex", obj.getString("note"));
		if(Integer.parseInt(end)<Integer.parseInt(start)){
			throw new AppException("结束日期不能小于开始日期!");
		}
		//判断afa的返回结果,是否成功
		cona.exchange();

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 
	 * @Title:  editHoliday
	 * @Description: 修改节假日信息
	 * @return String
	 * @throws Exception
	 */
	public String editHoliday() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b104_3.01", user);
		String start=obj.getString("start_date");
		String end=obj.getString("end_date");
		start=start.replaceAll("-","");
		end=end.replaceAll("-","");
		cona.set(IDictABT.HOLIDAY_ID, obj.getString("holiday_id"));
		cona.set(IDictABT.BRANCH, obj.getString("branch"));
		cona.set(IDictABT.HOLIDAY_NAME, obj.getString("holiday_name"));
		cona.set(IDictABT.START_DATE,start);
		cona.set(IDictABT.END_DATE, end);
		cona.set(IDictABT.THEME_ID, obj.getString("theme_id"));
		cona.set("notex", obj.getString("note"));
		if(Integer.parseInt(end)<Integer.parseInt(start)){
			throw new AppException("结束日期不能小于开始日期!");
		}
		//判断afa的返回结果,是否成功
		cona.exchange();
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 
	 * @Title: delHoliday
	 * @Description: 删除节假日信息
	 * @return String
	 * @throws Exception
	 */

	public String delHoliday() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = JSONObject.fromObject(jsonArray.getString(i));
			 
			//初始化NATP通讯
			cona.reInit();
			cona.setBMSHeader("ibp.bms.b104_4.01", user);
			cona.set(IDictABT.HOLIDAY_ID, obj.getString("holiday_id"));
			cona.set(IDictABT.BRANCH, obj.getString("branch"));
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
