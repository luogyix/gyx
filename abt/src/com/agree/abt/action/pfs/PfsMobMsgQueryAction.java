package com.agree.abt.action.pfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
 * 预填单信息查询
 * @ClassName: PfsMobMsgQueryAction.java
 * @company 赞同科技
 * @author 赵明
 * @date 2014-3-18
 */
public class PfsMobMsgQueryAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	private Log logger = LogFactory.getLog(PfsMobMsgQueryAction.class);	
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
	 * 查询填单机信息
	 * @return
	 * @throws Exception
	 */
	public String queryQMInfoPage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		Page pageInfo = this.getPage(obj);
		 
		
		cona.setBMSHeader("ibp.mob.p002.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
        String work_date = obj.getString("work_date").replaceAll("-", "");
		cona.set(IDictABT.WORK_DATE,work_date);

		cona.set(IDictABT.QUEUE_NUM,obj.getString("queue_num"));
		cona.set("count",pageInfo.getLimit().toString());//每页记录数
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("pfssize").get(0);
		int num = Integer.parseInt(loopNum);
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
			hld.put(IDictABT.WORK_DATE, map.get("work_date").get(i));
			hld.put(IDictABT.PFS_SEQ, map.get("pfs_seq").get(i));
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.RESERV_ID, map.get("reserv_id").get(i));
			hld.put(IDictABT.QUEUE_NUM, map.get("queue_num").get(i));
			hld.put(IDictABT.TRADE_ID, map.get("trade_id").get(i));
			hld.put(IDictABT.TRADE_NAME_CH, map.get("trade_name_ch").get(i));
			hld.put(IDictABT.TRADE_CODE_FR, map.get("trade_code_fr").get(i));
			hld.put(IDictABT.TRADE_TYPE, map.get("trade_type").get(i));
			hld.put(IDictABT.BRANCH_LEVEL, map.get("branch_level").get(i));
			hld.put(IDictABT.PER_NAME, map.get("per_name").get(i));
			hld.put(IDictABT.PER_TYPE, map.get("per_type").get(i));
			hld.put(IDictABT.PER_NUM, map.get("per_num").get(i));
			hld.put(IDictABT.PER_PHONE, map.get("per_phone").get(i));
			hld.put(IDictABT.AGENT_NAME, map.get("agent_name").get(i));
			hld.put(IDictABT.AGENT_TYPE, map.get("agent_type").get(i));
			hld.put(IDictABT.AGENT_NUM, map.get("agent_num").get(i));
			hld.put(IDictABT.AGENT_PHONE, map.get("agent_phone").get(i));
			hld.put(IDictABT.MEDIUM_TYPE, map.get("medium_type").get(i));
			hld.put(IDictABT.MEDIUM_NUM, map.get("medium_num").get(i));
			hld.put(IDictABT.PFS_CONTENT, map.get("pfs_content").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 预填单信息删除
	 * @return String
	 * @throws Exception
	 */

	public String delMsgInfo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONArray jsonarray = JSONArray.fromObject(inputJsonStr);
		for(int i=0;i<jsonarray.size();i++){
			JSONObject jsonObj = JSONObject.fromObject(jsonarray.getString(i));
			Object obj[] = jsonObj.values().toArray();
			 
			//初始化NATP通讯
			cona.reInit();
			cona.setBMSHeader("ibp.mob.p004.01", user);
			cona.set(IDictABT.WORK_DATE, obj[0].toString());
			cona.set(IDictABT.PFS_SEQ, obj[1].toString());
			//判断afa的返回结果,是否成功
			cona.exchange();
		}
	
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 
	 * @Title: queryQue
	 * @Description: 查询填单机业务信息
	 * @return String
	 * @throws Exception
	 */	
	public String getSystemDictionaryItem() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		 
		
		cona.setBMSHeader("ibp.bms.b133_1.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		String query_rules = req.getParameter("query_rules");
		cona.set("query_rules", query_rules);
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("tradesize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.TRADE_ID, (String) map.get("trade_id").get(i));
			list.add(hld);

		}
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		logger.info(super.convertServiceReturnToJson(ret));
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
		return null;
	}
	
	/**
	 * 预填单信息登记
	 * @return String
	 * @throws Exception
	 */
	public String addPfsInfo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		Page pageInfo =new Page();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.mob.p001.01", user);
		cona.set(IDictABT.RESERV_ID, obj.getString("reserv_id"));
		cona.set(IDictABT.QUEUE_NUM, obj.getString("queue_num"));
		cona.set(IDictABT.TRADE_ID, obj.getString("trade_id"));
		cona.set(IDictABT.PER_NAME, obj.getString("per_name"));	
		cona.set(IDictABT.PER_TYPE, obj.getString("per_type"));
		cona.set(IDictABT.PER_NUM, obj.getString("per_num"));
		cona.set(IDictABT.PER_PHONE, obj.getString("per_phone"));
		cona.set(IDictABT.AGENT_NANE, obj.getString("agent_nane"));
		cona.set(IDictABT.AGENT_TYPE, obj.getString("agent_type"));
		cona.set(IDictABT.AGENT_NUM, obj.getString("agent_num"));
		cona.set(IDictABT.AGENT_PHONE, obj.getString("agent_phone"));
		cona.set(IDictABT.MEDIUM_TYPE, obj.getString("medium_type"));
		cona.set(IDictABT.MEDIUM_NUM, obj.getString("medium_num"));
		logger.info(obj.getString("continue_flag"));
		cona.set(IDictABT.PFS_CONTENT, obj.getString("pfs_content"));
		cona.set(IDictABT.CONTINUE_FLAG, "on".equals(obj.getString("continue_flag"))?"1":"0");
		logger.info("on".equals(obj.getString("continue_flag"))?"1":"0");
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		pageInfo.setTotal(2);
		for (int i = 0; i < 2; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.WORK_DATE, map.get("work_date").get(i));
			hld.put(IDictABT.PFS_SEQ, map.get("pfs_seq").get(i));
			list.add(hld);
		}
		ServiceReturn tet=new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 预填单信息修改测试
	 * @return String
	 * @throws Exception
	 */
	public String editPfsInfo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		 
		
		cona.setBMSHeader("ibp.mob.p003.01", user);
		cona.set("work_date", "20140318");
		cona.set("pfs_seq", "101602266166");
		cona.set("per_name", "无名");
		cona.set("per_type", "1");
		cona.set("per_num", "22222222");
		cona.set("per_phone", "1388888888");
		cona.set("agent_name", "");
		cona.set("agent_type", "2");
		cona.set("agent_num", "");
		cona.set("agent_phone", "138882828282");
		cona.set("medium_type", "2");
		cona.set("medium_num", "");
		cona.set("pfs_content", "不知道");
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		if(map.get("H_ret_code") == null){
			throw new AppException("与后台服务通讯失败,没有返回状态码(H_ret_code)和状态信息");
		}
		String H_ret_code = (String) map.get("H_ret_code").get(0);
		if(!H_ret_code.equals(IDictABT.AFARESULTSTATUS_SUCC)){
			throw new AppException("错误码:"+H_ret_code+","+"错误信息:"+map.get("H_ret_desc").get(0));
		}
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
}
