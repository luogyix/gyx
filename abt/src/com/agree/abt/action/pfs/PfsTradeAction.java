package com.agree.abt.action.pfs;

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
import com.agree.util.IDictABT;

/**
 * 预填单业务配置
 * @ClassName:PfsTradeAction.java
 * @company 赞同科技
 * @author xhc
 * @date 2014-3-12
 */
public class PfsTradeAction extends BaseAction {
	
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
	 * 
	 * @Title: queryQue
	 * @Description: 预填单查询业务信息
	 * @return String
	 * @throws Exception
	 */
	public String queryTrade() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo = new Page();
		 
		
		cona.setBMSHeader("ibp.bms.b133_1.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona.set("query_rules", obj.getString("query_rules"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("tradesize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.TRADE_ID, map.get("trade_id").get(i));
			hld.put(IDictABT.TRADE_NAME_CH, map.get("trade_name_ch").get(i));
			hld.put(IDictABT.TRADE_NAME_EN, map.get("trade_name_en").get(i));
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.TRADE_CODE_FR, map.get("trade_code_fr").get(i));
			hld.put(IDictABT.TRADE_TYPE, map.get("trade_type").get(i));
			hld.put(IDictABT.BRANCH_LEVEL, map.get("branch_level").get(i));
			hld.put(IDictABT.STATUS, map.get("status").get(i));
			hld.put(IDictABT.NOTE, map.get("notea").get(i));
			list.add(hld);

		}
		pageInfo.setTotal(num);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 
	 * @Title: queryQue
	 * @Description: 查询业务信息
	 * @return String
	 * @throws Exception
	 */
	public String queryTradePage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		 
		
		cona.setBMSHeader("ibp.bms.b133_1.01", user);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set("query_rules", obj.getString("query_rules"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("tradesize").get(0);
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
			hld.put(IDictABT.TRADE_ID, map.get("trade_id").get(i));
			hld.put(IDictABT.TRADE_NAME_CH, map.get("trade_name_ch").get(i));
			hld.put(IDictABT.TRADE_NAME_EN, map.get("trade_name_en").get(i));
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.TRADE_CODE_FR, map.get("trade_code_fr").get(i));
			hld.put(IDictABT.TRADE_TYPE, map.get("trade_type").get(i));
			hld.put(IDictABT.BRANCH_LEVEL, map.get("branch_level").get(i));
			hld.put(IDictABT.STATUS, map.get("status").get(i));
			hld.put(IDictABT.NOTE, map.get("note").get(i));
			list.add(hld);
		}
		
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	

	/**
	 * 
	 * @Description: 预填单查询业务下拉框
	 * @throws Exception
	 */
	public void queryTradeSmall() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		 
		
		cona.setBMSHeader("ibp.bms.b133_1.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set("query_rules", req.getParameter("query_rules"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("tradesize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.TRADE_ID, map.get("trade_id").get(i));
			hld.put(IDictABT.TRADE_NAME_CH, map.get("trade_name_ch").get(i));			
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}

	/**
	 * 
	 * @Description: 添加业务信息
	 * @return String
	 * @throws Exception
	 */
//	public String addBusiness() throws Exception {
//		HttpServletRequest req = ServletActionContext.getRequest();
//		User user = (User) req.getSession().getAttribute(
//				ApplicationConstants.LOGONUSER);
//		String inputJsonStr = super.getJsonString();
//		JSONObject obj = JSONObject.fromObject(inputJsonStr);
//		 
//		cona.setHeader("ibp.bms.b103_1.01", user.getUnitid(), user.getUsercode(), "03");
//		cona.set(IDictABT.BRANCH, user.getUnitid());
//		cona.set(IDictABT.BS_NAME_CH, obj.getString(IDictABT.BS_NAME_CH));
//		cona.set(IDictABT.BS_NAME_EN, obj.getString(IDictABT.BS_NAME_EN));
//		cona.set(IDictABT.BS_TYPE, obj.getString(IDictABT.BS_TYPE));
//		cona.set(IDictABT.BRANCH_LEVEL, obj.getString(IDictABT.BRANCH_LEVEL));
//		cona.set(IDictABT.CARD_FLAG, obj.getString(IDictABT.CARD_FLAG));
//		cona.set("bs_level", obj.getString("bs_level"));
//		cona.set(IDictABT.STATUS, "on".equals(obj.getString(IDictABT.STATUS))?"1":"0");
//		cona.set("notex", obj.getString(IDictABT.NOTE));
//		
//		HashMap map = (HashMap) cona.exchange();
//		String validate="";
//		validate=cona.validMap(map);
//		if(validate!=null&&validate.trim().length()>0){
//			throw new AppException(validate);
//		}
//
//		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
//		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
//		super.setActionresult(returnJson.toString());
//		return AJAX_SUCCESS;
//	}

	/**
	 * 
	 * @Description: 修改业务信息
	 * @return String
	 * @throws Exception
	 */
	public String editTrade() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b133_2.01", user);
		cona.set(IDictABT.BRANCH, obj.getString(IDictABT.BRANCH));
		cona.set(IDictABT.TRADE_ID, obj.getString(IDictABT.TRADE_ID));
		cona.set(IDictABT.TRADE_NAME_CH, obj.getString(IDictABT.TRADE_NAME_CH));
		cona.set(IDictABT.TRADE_NAME_EN, obj.getString(IDictABT.TRADE_NAME_EN));
		cona.set(IDictABT.TRADE_TYPE, obj.getString(IDictABT.TRADE_TYPE));
		cona.set("notea", obj.getString(IDictABT.NOTE));
		cona.set(IDictABT.STATUS, "on".equals(obj.getString(IDictABT.STATUS))?"1":"0");
		//判断afa的返回结果,是否成功
		cona.exchange();

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 
	 * @Description: 删除业务信息
	 * @return String
	 * @throws Exception
	 */

//	public String delBusiness() throws Exception {
//		HttpServletRequest req = ServletActionContext.getRequest();
//		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
//		String inputJsonStr = super.getJsonString();
//		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
//		JsonConfig config = new JsonConfig();
//		config.setArrayMode(JsonConfig.MODE_LIST);
//		config.setCollectionType(List.class);
//		//TODO 群体删除修改
//		for (int i = 0; i < jsonArray.size(); i++) {
//			JSONObject jsonObj = JSONObject.fromObject(jsonArray.getString(i));
//			Object obj[] = jsonObj.values().toArray();
//			//初始化NATP通讯
//			cona.reInit(); 
//			cona.setHeader("ibp.bms.b103_4.01", user.getUnitid(), user.getUsercode(), "03");
//			cona.set(IDictABT.BS_ID, obj[0].toString());
//			cona.set(IDictABT.BRANCH, obj[1].toString());
//			HashMap map = (HashMap) cona.exchange();
//			String validate="";
//			validate=cona.validMap(map);
//			if(validate!=null&&validate.trim().length()>0){
//				throw new AppException(validate);
//			}
//		}
//
//		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
//		String string = super.convertServiceReturnToJson(ret).toString();
//		super.setActionresult(string);
//		return AJAX_SUCCESS;
//	}

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}

}
