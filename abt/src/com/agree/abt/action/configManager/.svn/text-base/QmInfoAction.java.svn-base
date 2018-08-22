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
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;



/**
 * 排队机信息配置
 * @ClassName: QueueMachineInfoAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2013-8-12
 */
public class QmInfoAction extends BaseAction {
	
	private static final Logger logger = LoggerFactory.getLogger(QmInfoAction.class);
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;

	/**
	 * 加载主页面
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
	 * 查询排队机信息
	 * @return
	 * @throws Exception
	 */
	public String queryQMInfo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b109_2.01", user);
		
		cona.set(IDictABT.BRANCH, user.getUnitid());
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("qmsize").get(0);
		pageInfo.setTotal(Integer.parseInt(loopNum));
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.QM_NUM, map.get("qm_num").get(i));
			hld.put(IDictABT.QM_NAME, map.get("qm_name").get(i));
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.QM_IP, map.get("qm_ip").get(i));
			hld.put(IDictABT.STATUS, map.get("status").get(i));
			hld.put(IDictABT.QM_PARAM_ID, map.get("qm_param_id").get(i));
			if((Boolean)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.MACHINEVIEWFLAG)){
				hld.put("parameter_id", map.get("parameter_id").get(i));
			}
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 查询排队机信息
	 * @return
	 * @throws Exception
	 */
	public String queryQMInfoPage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b109_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("qmsize").get(0);
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
			hld.put(IDictABT.QM_NUM, map.get("qm_num").get(i));
			hld.put(IDictABT.QM_NAME, map.get("qm_name").get(i));
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.QM_IP, map.get("qm_ip").get(i));
			hld.put("qm_oid", map.get("qm_oid").get(i));
			hld.put(IDictABT.STATUS, map.get("status").get(i));
			hld.put(IDictABT.QM_PARAM_ID, map.get("qm_param_id").get(i));
			//虚拟柜员号
			hld.put("deviceadm_num", map.get("deviceadm_num").get(i));
			//资产编号
			hld.put("propertynum", map.get("propertynum").get(i));
			if((Boolean)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.MACHINEVIEWFLAG)){
				hld.put("parameter_id", map.get("parameter_id").get(i));
			}
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 查询排队机下拉框
	 * @throws Exception
	 */
	public void queryQMInfoSmall() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b109_2.01", user);
		cona.set("branch", user.getUnitid()); //查询条件.待确认
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		logger.info("---------------------------map : " + map);
		String loopNum = (String) map.get("qmsize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.QM_NUM, map.get("qm_num").get(i));
			hld.put(IDictABT.QM_NAME, map.get("qm_name").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	/**
	 * 添加排队机信息
	 * @return String
	 * @throws Exception
	 */
	public String addQMInfo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b109_1.01", user);
		cona.set(IDictABT.QM_NUM, obj.getString("qm_num"));
		cona.set(IDictABT.QM_NAME, obj.getString("qm_name"));
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.STATUS, "on".equals(obj.getString("status"))?"1":"0");
		cona.set(IDictABT.QM_PARAM_ID, obj.getString("qm_param_id"));
		if((Boolean)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.MACHINEVIEWFLAG)){
			cona.set("parameter_id", obj.getString("parameter_id"));
		}
		//虚拟柜员号
		cona.set("deviceadm_num", obj.getString("deviceadm_num"));
		//资产编码
		cona.set("propertynum", obj.getString("propertynum"));
		
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn tet=new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 修改排队机信息
	 * @return String
	 * @throws Exception
	 */
	public String editQMInfo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b109_3.01", user);
		cona.set(IDictABT.QM_NUM, obj.getString("qm_num_old"));
		cona.set("qm_num_new", obj.getString("qm_num"));//新排队机编号
		cona.set(IDictABT.QM_NAME, obj.getString("qm_name"));
		cona.set(IDictABT.BRANCH, obj.getString("branch"));
		cona.set(IDictABT.QM_IP, obj.getString("qm_ip"));
		cona.set(IDictABT.STATUS, "on".equals(obj.getString("status"))?"1":"0");
		cona.set(IDictABT.QM_PARAM_ID, obj.getString("qm_param_id"));
		if((Boolean)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.MACHINEVIEWFLAG)){
			cona.set("parameter_id", obj.getString("parameter_id"));
		}
		
		//虚拟柜员号
		cona.set("deviceadm_num", obj.getString("deviceadm_num"));
		//资产编码
		cona.set("propertynum", obj.getString("propertynum"));
		
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 删除排队机信息
	 * @return String
	 * @throws Exception
	 */

	public String delQMInfo() throws Exception {
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
			cona.setBMSHeader("ibp.bms.b109_4.01", user);
			cona.set(IDictABT.QM_NUM, obj[0].toString());
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
