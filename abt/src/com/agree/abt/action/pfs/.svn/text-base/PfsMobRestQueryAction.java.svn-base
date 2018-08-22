package com.agree.abt.action.pfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
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
public class PfsMobRestQueryAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	private Log logger = LogFactory.getLog(PfsMobRestQueryAction.class);	
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
	public String queryQMRestPage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		Page pageInfo = this.getPage(obj);
		 
		
		cona.setBMSHeader("ibp.bms.b208_1.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.QUEUE_NUM,obj.getString("queue_num"));
		cona.set(IDictABT.QUEUENUM_TYPE,obj.getString("queuenum_type"));
		cona.set(IDictABT.QUEUE_STATUS,"5");
		cona.set(IDictABT.QUEUETYPE_ID,obj.getString("queuetype_id"));
		cona.set(IDictABT.BS_ID,obj.getString("bs_id"));
		cona.set(IDictABT.CUSTTYPE_I,obj.getString("custtype_i"));
		
		//分页专用
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
			currentpage = String.valueOf(pageInfo.getStart()+1);
			break;
		case 3:
			currentpage = String.valueOf(pageInfo.getStart()-1);
			break;
		case 4:
			currentpage = String.valueOf(pageInfo.getStart());
			pageInfo.setStart(pageInfo.getStart());
			break;
		}
		cona.set("pageflag",pageInfo.getPageflag().toString());//分页标识
		cona.set("currentpage",currentpage);//当前页码
		cona.set("count",pageInfo.getLimit().toString());//每页记录数
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("queuesize").get(0);
		int num = Integer.parseInt(loopNum);	
		pageInfo.setTotal(num);
		if(num==0){
		 pageInfo.setPage(0);
		}else{
		 pageInfo.setPage(Integer.parseInt((String) map.get("totalpage").get(0)));
		}
		for (int i=0;i<num;i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.WORK_DATE, map.get("work_date").get(i));
			hld.put(IDictABT.QUEUE_NUM, map.get("queue_num").get(i));
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.BRANCH_NAME, map.get("branch_name").get(i));
			hld.put(IDictABT.TRANSFER_NUM, map.get("transfer_num").get(i));
			hld.put(IDictABT.QM_NUM, map.get("qm_num").get(i));
			hld.put(IDictABT.QM_IP, map.get("qm_ip").get(i));
			hld.put(IDictABT.QUEUE_SEQ, map.get("queue_seq").get(i));
			hld.put(IDictABT.SOFTCALL_TELLER, map.get("softcall_teller").get(i));
			hld.put(IDictABT.SOFTCALL_TELLER_NAME, map.get("softcall_teller_name").get(i));
			hld.put(IDictABT.SOFTCALL_SEQ, map.get("softcall_seq").get(i));
			hld.put(IDictABT.BS_ID, map.get("bs_id").get(i));
			hld.put(IDictABT.BS_NAME_CH, map.get("bs_name_ch").get(i));
			hld.put(IDictABT.QUEUETYPE_ID, map.get("queuetype_id").get(i));
			hld.put(IDictABT.QUEUETYPE_NAME, map.get("queuetype_name").get(i));
			hld.put(IDictABT.CUSTTYPE_I, map.get("custtype_i").get(i));
			hld.put(IDictABT.CUSTTYPE_NAME, map.get("custtype_name").get(i));
			hld.put(IDictABT.NODE_ID, map.get("node_id").get(i));
			hld.put(IDictABT.WIN_NUM, map.get("win_num").get(i));
			hld.put(IDictABT.WINDOW_IP, map.get("window_ip").get(i));
			hld.put(IDictABT.QUEUENUM_TYPE, map.get("queuenum_type").get(i));
			hld.put(IDictABT.CUSTINFO_TYPE, map.get("custinfo_type").get(i));
			hld.put(IDictABT.CUSTINFO_NUM, map.get("custinfo_num").get(i));
			hld.put(IDictABT.QUEUE_STATUS, map.get("queue_status").get(i));
			hld.put(IDictABT.EN_QUEUE_TIME, map.get("en_queue_time").get(i));
			hld.put(IDictABT.DE_QUEUE_TIME, map.get("de_queue_time").get(i));
			hld.put(IDictABT.START_SERV_TIME, map.get("start_serv_time").get(i));
			hld.put(IDictABT.FINISH_SERV_TIME, map.get("finish_serv_time").get(i));
			hld.put(IDictABT.ASSESS_STATUS, map.get("assess_status").get(i));
			hld.put(IDictABT.RESERV_FLAG, map.get("reserv_flag").get(i));
			hld.put(IDictABT.RESERV_ID, map.get("reserv_id").get(i));
			hld.put(IDictABT.REMAIND_FLAG, map.get("remaind_flag").get(i));
			hld.put(IDictABT.REMAIND_PHONE, map.get("remaind_phone").get(i));
			hld.put(IDictABT.NOTI_VAITNUM, map.get("noti_waitnum").get(i));
			hld.put(IDictABT.NOTI_SETNUM, map.get("noti_setnum").get(i));
			hld.put(IDictABT.ISNOTIFY, map.get("isnotify").get(i));
			hld.put(IDictABT.ISBEFORE, map.get("isbefore").get(i));
			hld.put(IDictABT.BEFORESTATUS, map.get("beforestatus").get(i));
			hld.put(IDictABT.VCALLTIME, map.get("vcalltime").get(i));
			hld.put(IDictABT.VCALLTIME_2, map.get("vcalltime_2").get(i));
			hld.put(IDictABT.CHECK_QUEUE_VALUE, map.get("check_queue_value").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 
	 * @Title: queryQue
	 * @Description: 查询排队机业务信息
	 * @return String
	 * @throws Exception
	 */	
	public String getSystemDictionaryItem() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		 
		
		cona.setBMSHeader("ibp.bms.b103_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		String query_rules = req.getParameter("query_rules");
		cona.set("query_rules", query_rules);
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("bssize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BS_ID, map.get("bs_id").get(i));
			hld.put(IDictABT.BS_NAME_CH, map.get("bs_name_ch").get(i));
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
	 * 恢复排队号
	 * @return
	 * @throws Exception
	 */
	public String renewQueueNum() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.iqs.q006.01", user);
		cona.set(IDictABT.BRANCH, obj.getString("branch"));
		cona.set(IDictABT.QUEUE_NUM, obj.getString("queue_num"));
		cona.set(IDictABT.CHECK_QUEUE_VALUE, obj.getString("check_queue_value"));
		cona.set(IDictABT.QUEUE_STATUS, "0");
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		sRet.put(ServiceReturn.FIELD2, "恢复"+map.get("H_ret_desc").get(0));
		String string = super.convertServiceReturnToJson(sRet).toString();		
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
