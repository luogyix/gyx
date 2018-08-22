package com.agree.abt.action.dataAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.dataAnalysis.QueueNumFlowLine;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.DateUtil;

/**
 * 排队号流水查询
 * @ClassName: QueueNumMonitorAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2014-1-18
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class QueueNumMonitorAction extends BaseAction {
	private static final long serialVersionUID = 2141897762615781445L;
	private ABTComunicateNatp cona;
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());//获取部门集合
		sRet.put(ServiceReturn.FIELD2, super.getLogonUser(false));//获取登录用户信息
		sRet.put(ServiceReturn.FIELD6, ApplicationConstants.RESETPASSWORD);//获取默认密码
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 查询排队机当日流水
	 * @return
	 * @throws Exception
	 */
	public String queryQueueNumMonitor() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		List list = new ArrayList();
		Page pageInfo = this.getPage(jsonObj,"");
		User user = super.getLogonUser(false);

		//正常使用时将一下注释的打开
		 
		
		cona.setBMSHeader("ibp.bms.b208.01", user);
		
		cona.set("branch",jsonObj.getString("branch"));
		cona.set("queue_num",jsonObj.getString("queue_num").trim());
		cona.set("queuenum_type",jsonObj.getString("queuenumType"));
		cona.set("queue_status",jsonObj.getString("queueStatus"));
		cona.set("bs_id",jsonObj.getString("business"));
		cona.set("custtype_i", jsonObj.getString("custtype"));
		cona.set("queuetype_id",jsonObj.getString("queuetype"));
		//分页专用
		cona.set("needpage","1");
		cona.set("selflag","2");
		cona.set("pageflag",pageInfo.getPageflag().toString());//分页标识
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
		cona.set("currentpage",currentpage);//当前页码
		cona.set("count",pageInfo.getLimit().toString());//每页记录数
		
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		if(!"0".equals(map.get("queuesize").get(0))){
			pageInfo.setTotal(Integer.parseInt(map.get("rcdsumnum").get(0)));//总条数	
			Integer pageNo = Integer.parseInt(map.get("totalpage").get(0));//总页数
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
			String loopNum = map.get("queuesize").get(0);
			int num = Integer.parseInt(loopNum);
			for (int i = 0; i < num; i++) {
				QueueNumFlowLine numflow = new QueueNumFlowLine();
				//正常使用时将一下注释的打开
				numflow.setWork_date(map.get("work_date").get(i).toString());
				numflow.setQueue_num(map.get("queue_num").get(i).toString());
				numflow.setBranch(map.get("branch").get(i).toString());
				numflow.setBranch_name(map.get("branch_name").get(i).toString());
				numflow.setTransfer_num(map.get("transfer_num").get(i).toString());
				numflow.setQm_num(map.get("qm_num").get(i).toString());
				numflow.setQm_ip(map.get("qm_ip").get(i).toString());
				numflow.setQueue_seq(map.get("queue_seq").get(i).toString());
				numflow.setSoftcall_teller(map.get("softcall_teller").get(i).toString());
				numflow.setSoftcall_teller_name(map.get("softcall_teller_name").get(i).toString());
				numflow.setSoftcall_seq(map.get("softcall_seq").get(i).toString());
				numflow.setBs_id(map.get("bs_id").get(i).toString());
				numflow.setBs_name_ch(map.get("bs_name_ch").get(i).toString());
				numflow.setQueuetype_id(map.get("queuetype_id").get(i).toString());
				numflow.setQueuetype_name(map.get("queuetype_name").get(i).toString());
				numflow.setCusttype_i(map.get("custtype_i").get(i).toString());
				numflow.setCusttype_name(map.get("custtype_name").get(i).toString());
				numflow.setNode_id(map.get("node_id").get(i).toString());
				numflow.setWin_num(map.get("win_num").get(i).toString());
				numflow.setWindow_ip(map.get("window_ip").get(i).toString());
				numflow.setQueuenum_type(map.get("queuenum_type").get(i).toString());
				numflow.setCustinfo_type(map.get("custinfo_type").get(i).toString());
				numflow.setCustinfo_num(map.get("custinfo_num").get(i).toString());
				numflow.setQueue_status(map.get("queue_status").get(i).toString());
				
				String en_queue_time = map.get("en_queue_time").get(i).toString();
				numflow.setEn_queue_time("".equals(en_queue_time)?"":DateUtil.formatTime(en_queue_time));
				String de_queue_time = map.get("de_queue_time").get(i).toString();
				numflow.setDe_queue_time("".equals(de_queue_time)?"":DateUtil.formatTime(de_queue_time));
				
				long waittime = 0;
				if("".equals(map.get("de_queue_time").get(i))){//弃号
					waittime = DateUtil.diffTime(en_queue_time);
				}else{
					waittime = DateUtil.diffTime2(map.get("en_queue_time").get(i), map.get("de_queue_time").get(i));
					
				}
				numflow.setWait_time(String.valueOf(waittime/60==0?1:waittime/60)+"分钟");
				
				String start_serv_time = map.get("start_serv_time").get(i).toString();
				numflow.setStart_serv_time("".equals(start_serv_time)?"":DateUtil.formatTime(start_serv_time));
				String finish_serv_time = map.get("finish_serv_time").get(i).toString();
				numflow.setFinish_serv_time("".equals(finish_serv_time)?"":DateUtil.formatTime(finish_serv_time));
				long servtime = 0;
				if("".equals(map.get("finish_serv_time").get(i))){
					numflow.setServ_time("");
				}else{
					servtime = DateUtil.diffTime2(map.get("start_serv_time").get(i), map.get("finish_serv_time").get(i));
					numflow.setServ_time(String.valueOf(servtime/60==0?1:servtime/60) + "分钟");
				}
				
				numflow.setAssess_status(map.get("assess_status").get(i).toString());
				numflow.setReserv_flag(map.get("reserv_flag").get(i).toString());
				numflow.setReserv_id(map.get("reserv_id").get(i).toString());
				numflow.setRemaind_flag(map.get("remaind_flag").get(i).toString());
				numflow.setRemaind_phone(map.get("remaind_phone").get(i).toString());
				numflow.setNoti_waitnum(map.get("noti_waitnum").get(i).toString());
				numflow.setNoti_setnum(map.get("noti_setnum").get(i).toString());
				numflow.setIsnotify(map.get("isnotify").get(i).toString());
				numflow.setIsbefore(map.get("isbefore").get(i).toString());
				numflow.setBeforestatus(map.get("beforestatus").get(i).toString());
				String vcalltime = map.get("vcalltime").get(i).toString();
				numflow.setVcalltime("".equals(vcalltime)?"":DateUtil.formatTime(vcalltime));
				String vcalltime_2 = map.get("vcalltime_2").get(i).toString();
				numflow.setVcalltime_2("".equals(vcalltime_2)?"":DateUtil.formatTime(vcalltime_2));
				numflow.setCheck_queue_value(map.get("check_queue_value").get(i).toString());
				list.add(numflow);
			}
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);

		return AJAX_SUCCESS;
	}

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
}
