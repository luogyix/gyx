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
 * 业务配置
 * @ClassName: BussinessAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2013-8-8
 */
public class BussinessAction extends BaseAction {
	
	
	private ABTComunicateNatp cona;
	private static final long serialVersionUID = 1L;
	
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		//Set<String> priv=this.getUserInpageFunc();
		//sRet.put(ServiceReturn.FIELD2,priv);//获取登录用户信息 也就是利用这个获取到此部分功能
		sRet.put(ServiceReturn.FIELD2,super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: queryQue
	 * @Description: 查询业务信息
	 * @return String
	 * @throws Exception
	 */
	public String queryBusiness() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo = new Page();
		 
		cona.setBMSHeader("ibp.bms.b103_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona.set("query_rules", obj.getString("query_rules"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map =  cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("bssize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BS_ID, map.get("bs_id").get(i));
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.BS_NAME_CH, map.get("bs_name_ch").get(i));
			hld.put(IDictABT.BS_NAME_EN, map.get("bs_name_en").get(i));
			hld.put(IDictABT.BS_TYPE, map.get("bs_type").get(i));
			hld.put(IDictABT.BRANCH_LEVEL, map.get("branch_level").get(i));
			hld.put(IDictABT.CARD_FLAG, map.get("card_flag").get(i));
			hld.put(IDictABT.STATUS, map.get("status").get(i));
			hld.put("bs_prefix", map.get("bs_prefix").get(i));
			hld.put(IDictABT.BS_SIGNSTATUS, map.get("bs_signstatus").get(i));
			hld.put(IDictABT.NOTE, map.get("note").get(i));
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
	public String queryBusinessPage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		cona.setBMSHeader("ibp.bms.b103_2.01", user);
		
		//cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.BRANCH, obj.getString("branch"));
		cona.set("query_rules", obj.getString("query_rules"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map =  cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("bssize").get(0);
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
			hld.put(IDictABT.BS_ID, map.get("bs_id").get(i));
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.BS_NAME_CH, map.get("bs_name_ch").get(i));
			hld.put(IDictABT.BS_NAME_EN, map.get("bs_name_en").get(i));
			hld.put(IDictABT.BS_TYPE, map.get("bs_type").get(i));
			hld.put(IDictABT.BRANCH_LEVEL, map.get("branch_level").get(i));
			hld.put(IDictABT.CARD_FLAG, map.get("card_flag").get(i));
			hld.put(IDictABT.STATUS, map.get("status").get(i));
			hld.put("bs_prefix", map.get("bs_prefix").get(i));
			hld.put(IDictABT.BS_SIGNSTATUS, map.get("bs_signstatus").get(i));
			hld.put(IDictABT.NOTE, map.get("note").get(i));
			list.add(hld);
		}
		
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 查询业务下拉框
	 * @throws Exception
	 */
	public void queryBusinessSmall() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		 
		cona.setBMSHeader("ibp.bms.b103_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set("query_rules", req.getParameter("query_rules"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map =  cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("bssize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BS_ID, map.get("bs_id").get(i));
			hld.put(IDictABT.BS_NAME_CH, map.get("bs_name_ch").get(i));
			hld.put(IDictABT.CARD_FLAG, map.get("card_flag").get(i));//0不1随意2必
			hld.put(IDictABT.BS_TYPE, map.get("bs_type").get(i));//0个1公
			hld.put("bs_prefix", map.get("bs_prefix").get(i));
			hld.put(IDictABT.NOTE, map.get("note").get(i));
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
	public String addBusiness() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		cona.setBMSHeader("ibp.bms.b103_1.01", user);
		if ("1".equals(user.getUnit().getBank_level())) {//总行
			if(obj.containsKey("branch")){
				cona.set(IDictABT.BRANCH, obj.getString(IDictABT.BRANCH));
			}
		} else {
			cona.set(IDictABT.BRANCH, user.getUnitid());
		}
		cona.set(IDictABT.BS_NAME_CH, obj.getString(IDictABT.BS_NAME_CH));
		cona.set(IDictABT.BS_NAME_EN, obj.getString(IDictABT.BS_NAME_EN));
		cona.set(IDictABT.BS_TYPE, obj.getString(IDictABT.BS_TYPE));
		cona.set(IDictABT.BRANCH_LEVEL, obj.getString(IDictABT.BRANCH_LEVEL));
		cona.set(IDictABT.CARD_FLAG, obj.getString(IDictABT.CARD_FLAG));
		cona.set("bs_prefix", obj.getString("bs_prefix"));
		cona.set(IDictABT.STATUS, "on".equals(obj.getString(IDictABT.STATUS))?"1":"0");
		cona.set("notex", obj.getString(IDictABT.NOTE));
		//判断afa的返回结果,是否成功
		cona.exchange();
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 
	 * @Description: 修改业务信息
	 * @return String
	 * @throws Exception
	 */
	public String editBusiness() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		cona.setBMSHeader("ibp.bms.b103_3.01", user);
		cona.set(IDictABT.BRANCH, obj.getString(IDictABT.BRANCH));
		cona.set(IDictABT.BS_ID, obj.getString(IDictABT.BS_ID));
		cona.set(IDictABT.BS_NAME_CH, obj.getString(IDictABT.BS_NAME_CH));
		cona.set(IDictABT.BS_NAME_EN, obj.getString(IDictABT.BS_NAME_EN));
		cona.set(IDictABT.BS_TYPE, obj.getString(IDictABT.BS_TYPE));
		cona.set(IDictABT.BRANCH_LEVEL, obj.getString(IDictABT.BRANCH_LEVEL));
		cona.set(IDictABT.CARD_FLAG, obj.getString(IDictABT.CARD_FLAG));
		cona.set("bs_prefix", obj.getString("bs_prefix"));
		cona.set(IDictABT.STATUS, "on".equals(obj.getString(IDictABT.STATUS))?"1":"0");
		cona.set("notex", obj.getString(IDictABT.NOTE));
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

	public String delBusiness() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
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
			cona.setBMSHeader("ibp.bms.b103_4.01", user);
			cona.set(IDictABT.BS_ID, obj[0].toString());
			cona.set(IDictABT.BRANCH, obj[1].toString());
			//判断afa的返回结果,是否成功
			Map<String,List<String>> map = cona.exchange();
			if(map.get("H_ret_code") == null){
				throw new AppException("与后台服务通讯失败,没有返回状态码(H_ret_code)和状态信息");
			}
			String H_ret_code = (String) map.get("H_ret_code").get(0);
			if(!H_ret_code.equals(IDictABT.AFARESULTSTATUS_SUCC)){
				throw  new AppException("错误码:"+H_ret_code+","+"错误信息"+map.get("H_ret_desc").get(0));
			}
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
