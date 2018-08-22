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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;


/**
 * 客户类型配置
 * @ClassName: CustTypeConfigAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2013-7-29
 */
public class CustTypeConfigAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	private static final Logger log = LoggerFactory.getLogger(CustTypeConfigAction.class);	
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
	 * 查询客户类型
	 * @return
	 * @throws Exception
	 */
	public String queryCustType() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b108_2.01", user);
		
		cona.set("branch", user.getUnitid()); //查询条件.待确认
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		log.info("---------------------------map : " + map);
		String loopNum = (String) map.get("custtypesize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.CUSTTYPE_I, map.get("custtype_i").get(i));
			hld.put(IDictABT.CUSTTYPE_E, map.get("custtype_e").get(i));
			hld.put(IDictABT.CUSTTYPE_NAME, map.get("custtype_name").get(i));
			hld.put("custtype_waittime", map.get("custtype_waittime").get(i));
			//hld.put("custreporttype", map.get("custreporttype").get(i));
			hld.put(IDictABT.ISVIP, map.get("isvip").get(i));
			list.add(hld);
		}
		pageInfo.setTotal(num);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		ret.put(ServiceReturn.FIELD1, ret);
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 查询客户类型
	 * @return
	 * @throws Exception
	 */
	public String queryCustTypePage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b108_2.01", user);
		cona.set("branch", user.getUnitid()); //查询条件.待确认
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map =  cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = "";
		if(map.containsKey("custtypesize")){
			loopNum = (String) map.get("custtypesize").get(0);
		}else{
			loopNum = "0";
		}
		int num = Integer.parseInt(loopNum);
		
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
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
			hld.put(IDictABT.CUSTTYPE_I, map.get("custtype_i").get(i));
			hld.put(IDictABT.CUSTTYPE_E, map.get("custtype_e").get(i));
			hld.put(IDictABT.CUSTTYPE_NAME, map.get("custtype_name").get(i));
			hld.put("custtype_waittime", map.get("custtype_waittime").get(i));
			//hld.put("custreporttype", map.get("custreporttype").get(i));
			hld.put(IDictABT.ISVIP, map.get("isvip").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		ret.put(ServiceReturn.FIELD1, ret);
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 查询简单数据 客户类型
	 * @throws Exception
	 */
	public void queryCustTypeSmall() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b108_2.01", user);
		cona.set("branch", user.getUnitid()); //查询条件.待确认
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		if(map.get("H_ret_code") == null){
			throw new AppException("与后台服务通讯失败,没有返回状态码(H_ret_code)和状态信息");
		}
		String H_ret_code = (String) map.get("H_ret_code").get(0);
		if(!H_ret_code.equals(IDictABT.AFARESULTSTATUS_SUCC)){
			throw  new AppException("错误码:"+H_ret_code+","+"错误信息"+map.get("H_ret_desc").get(0));
		}
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		log.info("---------------------------map : " + map);
		String loopNum = (String) map.get("custtypesize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.CUSTTYPE_I, map.get("custtype_i").get(i));
			hld.put(IDictABT.CUSTTYPE_E, map.get("custtype_e").get(i));
			hld.put(IDictABT.CUSTTYPE_NAME, map.get("custtype_name").get(i));
			hld.put("custtype_waittime", map.get("custtype_waittime").get(i));
			//hld.put("custreporttype", map.get("custreporttype").get(i));
			hld.put(IDictABT.ISVIP, map.get("isvip").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	/**
	 * 
	 * @Description: 添加客户类型信息
	 * @return String
	 * @throws Exception
	 */
	public String addCustType() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b108_1.01", user);
//		int binStart=Integer.parseInt(obj.getString("binStart"));
		
//		if(binEnd >dataEnd){
//			throw new AppException("卡Bin验证的结束位置不能大于数据的结束位置，请核对" );
//		}
		cona.set(IDictABT.CUSTTYPE_I, obj.getString("custtype_i"));
		cona.set(IDictABT.CUSTTYPE_E, obj.getString("custtype_e"));
		cona.set(IDictABT.CUSTTYPE_NAME, obj.getString("custtype_name"));
		cona.set("custtype_waittime", "0");
		//cona.set("custtype_waittime", obj.getString("custtype_waittime"));
		//cona.set("custreporttype", obj.getString("custreporttype"));
		cona.set(IDictABT.ISVIP, "on".equals(obj.getString("isvip"))?"1":"0");
		//判断afa的返回结果,是否成功
		cona.exchange();
		ServiceReturn tet=new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 
	 * @Title: editCustType
	 * @Description: 修改客户信息
	 * @return String
	 * @throws Exception
	 */
	public String editCustType() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b108_3.01", user);
		cona.set("custtype_i_new", obj.getString("custtype_i"));
		cona.set("custtype_e_new", obj.getString("custtype_e"));
		cona.set(IDictABT.CUSTTYPE_I, obj.getString("custtype_i_old"));
		cona.set(IDictABT.CUSTTYPE_E, obj.getString("custtype_e_old"));
		cona.set(IDictABT.CUSTTYPE_NAME, obj.getString("custtype_name"));
		cona.set("custtype_waittime", "0");
		//cona.set("custtype_waittime", obj.getString("custtype_waittime"));
		//cona.set("custreporttype", obj.getString("custreporttype"));
		cona.set(IDictABT.ISVIP, "on".equals(obj.getString("isvip"))?"1":"0");
//		if(Integer.parseInt(dataEnd)-Integer.parseInt(dataStart)<0){
//			throw new AppException("数据的结束位置不能小于数据的开始位置，请核对" );
//		}
		
		//判断afa的返回结果,是否成功
		cona.exchange();
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 
	 * @Title: delCustType
	 * @Description: 删除客户类型信息
	 * @return String
	 * @throws Exception
	 */
	public String delCustType() throws Exception {
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
			cona.setBMSHeader("ibp.bms.b108_4.01", user);
			cona.set(IDictABT.CUSTTYPE_I, obj[0].toString());
			cona.set(IDictABT.CUSTTYPE_E, obj[1].toString());
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
