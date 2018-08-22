package com.agree.abt.action.dataAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.dataAnalysis.QueueNumFlowLine;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.DateUtil;

/**
 * 排队号流水查询
 * @ClassName: QueueFlowLineAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2013-9-20
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class QueueNumFlowLineAction extends BaseAction {
	
	private static final long serialVersionUID = 2141897762615781445L;
	private Log logger = LogFactory.getLog(QueueNumFlowLineAction.class);	
	private String STYLE_PAGE = "page";//分页标识
	private String STYLE_EXCEL = "excel";//导出Excel
	
	
	
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
	 * @Title: queryQueueNum 
	 * @Description: 
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws 
	 */ 
	public String queryQueueNum() throws Exception {
		String jsonString = super.getJsonString();
		int totalSum = 0;
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		List list = new ArrayList();
		Page pageInfo = this.getPage(jsonObj,"");
		User user = super.getLogonUser(false);
		
		String dateStart = jsonObj.getString("startDate").replace("-", "");
		String dateEnd = jsonObj.getString("endDate").replace("-", "");
		int sYear = Integer.parseInt(dateStart.substring(0,4));
		int eYear = Integer.parseInt(dateEnd.substring(0,4));
		int sMonth = Integer.parseInt(dateStart.substring(4,6));
		int eMonth = Integer.parseInt(dateEnd.substring(4,6));
		int sDay = Integer.parseInt(dateStart.substring(6));
		int eDay = Integer.parseInt(dateEnd.substring(6));
		if(sYear!=eYear){
			eMonth += 12;
		}
		if(eDay-sDay>0){
			eMonth += 1;
		}
		if(eMonth-sMonth>3){
			throw new AppException("请确认查询时间段不超过3个月!");
		}
		natp(jsonObj, list, pageInfo, user, this.STYLE_PAGE, totalSum);

		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);

		return AJAX_SUCCESS;
	}
	
	public void exportExcel()throws Exception{
		int totalSum = 0;
		String jsonString = ServletActionContext.getRequest().getParameter("querycondition_str");
		logger.info("jsonString====="+jsonString);
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		List list = new ArrayList();
		Page pageInfo = new Page();
		pageInfo.setStart(1);
		pageInfo.setPageflag(4);
		pageInfo.setLimit(totalSum);
		User user = super.getLogonUser(false);
		 
		natp(jsonObj, list, pageInfo, user, this.STYLE_EXCEL, totalSum);
		//证件类型
		Map map2 = new HashMap();
		map2.put("0", "0-身份证");
		map2.put("1", "1-银行卡卡号");
		map2.put("2", "2-客户号");
		map2.put("3", "3-护照号码");
		//排队号类型
		Map map3 = new HashMap();
		map3.put("1", "1-普通");
		map3.put("2", "2-转移");
		map3.put("3", "3-预约");
		map3.put("4", "4-填单");
		//排队状态
		Map map4 = new HashMap();
		map4.put("0", "0-取号");
		map4.put("1", "1-业务办理中");
		map4.put("2", "2-业务办理结束");
		map4.put("3", "3-弃号");
		map4.put("4", "4-转移");
		map4.put("5", "5-填单");
		//提醒标志
		Map map1 = new HashMap();
		map1.put("0", "0-无");
		map1.put("1", "1-有");
		//提醒标志
		Map map5 = new HashMap();
		map5.put("0", "0-否");
		map5.put("1", "1-是");
		//通知标识
		Map map6 = new HashMap();
		map6.put("0", "0-未通知");
		map6.put("1", "1-已通知");
		//是否御填单
		Map map7 = new HashMap();
		map7.put("0", "0-否");
		map7.put("1", "1-是");
		//填单状态
		Map map8 = new HashMap();
		map8.put("0", "0-未完成");
		map8.put("1", "1-已完成");
		map8.put("2", "2-取消");
		map8.put("3", "3-正在处理");
		map8.put("4", "4-处理完毕");
		//客户评价结果
		Map map9 = new HashMap();
		map9.put("-1", "客户未评价");
		map9.put("0", "柜员未发起评价");
		map9.put("1", "不满意");
		map9.put("2", "一般");
		map9.put("3", "满意");
		map9.put("4", "非常满意");
		
		for(int i=0; i<list.size(); i++){//循环翻译要导出到excel中的需要翻译的字段
			if(!"".equals(((QueueNumFlowLine)list.get(i)).getCustinfo_type())&&map2.containsKey(((QueueNumFlowLine)list.get(i)).getCustinfo_type().toString())){
				//证件类型
				((QueueNumFlowLine) list.get(i)).setCustinfo_type( map2.get(((QueueNumFlowLine)list.get(i)).getCustinfo_type()).toString());
			}
			if(!"".equals(((QueueNumFlowLine)list.get(i)).getQueuenum_type())&&map3.containsKey(((QueueNumFlowLine)list.get(i)).getQueuenum_type().toString())){
				//排队号类型
				((QueueNumFlowLine) list.get(i)).setQueuenum_type( map3.get(((QueueNumFlowLine)list.get(i)).getQueuenum_type()).toString());
			}
			if(!"".equals(((QueueNumFlowLine)list.get(i)).getQueue_status())&&map4.containsKey(((QueueNumFlowLine)list.get(i)).getQueue_status().toString())){
				//排队状态
				((QueueNumFlowLine) list.get(i)).setQueue_status( map4.get(((QueueNumFlowLine)list.get(i)).getQueue_status()).toString());
			}
			if(!"".equals(((QueueNumFlowLine)list.get(i)).getReserv_flag())&&map1.containsKey(((QueueNumFlowLine)list.get(i)).getReserv_flag().toString())){
				//提醒标志
				((QueueNumFlowLine) list.get(i)).setReserv_flag( map1.get(((QueueNumFlowLine)list.get(i)).getReserv_flag()).toString());
			}
			if(!"".equals(((QueueNumFlowLine)list.get(i)).getRemaind_flag())&&map5.containsKey(((QueueNumFlowLine)list.get(i)).getRemaind_flag().toString())){
				((QueueNumFlowLine) list.get(i)).setRemaind_flag( map5.get(((QueueNumFlowLine)list.get(i)).getRemaind_flag()).toString());
			}
			if(!"".equals(((QueueNumFlowLine)list.get(i)).getIsnotify())&&map6.containsKey(((QueueNumFlowLine)list.get(i)).getIsnotify().toString())){
				//通知标志
				((QueueNumFlowLine) list.get(i)).setIsnotify( map6.get(((QueueNumFlowLine)list.get(i)).getIsnotify()).toString());
			}
			if(!"".equals(((QueueNumFlowLine)list.get(i)).getIsbefore())&&map7.containsKey(((QueueNumFlowLine)list.get(i)).getIsbefore().toString())){
				//是否御填单
				((QueueNumFlowLine) list.get(i)).setIsbefore( map7.get(((QueueNumFlowLine)list.get(i)).getIsbefore()).toString());
			}
			if(!"".equals(((QueueNumFlowLine)list.get(i)).getBeforestatus())&&map8.containsKey(((QueueNumFlowLine)list.get(i)).getBeforestatus().toString())){
				//填单状态
				((QueueNumFlowLine) list.get(i)).setBeforestatus( map8.get(((QueueNumFlowLine)list.get(i)).getBeforestatus()).toString());
			}
			if(!"".equals(((QueueNumFlowLine)list.get(i)).getAssess_status())&&map9.containsKey(((QueueNumFlowLine)list.get(i)).getAssess_status().toString())){
				//填单状态
				((QueueNumFlowLine) list.get(i)).setAssess_status( map9.get(((QueueNumFlowLine)list.get(i)).getAssess_status()).toString());
			}
		}
		String path="QueueNumFlowLineBook.xls";		
		String file = "历史流水明细统计";
		super.doExcel(path, list, jsonObj, file);
	}
	
	/** 
	 * @Title: natp 
	 * @Description:
	 * @param @param jsonObj
	 * @param @param list
	 * @param @param pageInfo
	 * @param @param user
	 * @param @param selfStyle
	 * @param @throws Exception    参数 
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public void natp( JSONObject jsonObj, List list, Page pageInfo, User user,  String selfStyle,  int totalSum) throws Exception{
		//正常使用时将一下注释的打开
		String branch=jsonObj.getString("branch");//机构号
		String business=jsonObj.getString("business");//业务类型
		String custtype = jsonObj.getString("custtype");//客户类型
		String queuetype=jsonObj.getString("queuetype");//队列名称
		String startdate=jsonObj.getString("startDate").replace("-", "");
		String enddate=jsonObj.getString("endDate").replace("-", "");
		String softcall_teller_name=jsonObj.getString("softcall_teller_name").trim();
		String queue_time=jsonObj.getString("queue_time");
		String serv_time=jsonObj.getString("serv_time");
		String serv_time_2=jsonObj.getString("serv_time_2");
		
		cona.setBMSHeader("ibp.bms.b310.01", user);
		
		cona.set("branch",branch);
		cona.set("bs_id",business);
		cona.set("custtype_i", custtype);
		cona.set("queuetype_id",queuetype);
		cona.set("startdate",startdate);
		cona.set("enddate",enddate);
		cona.set("softcall_teller_name",softcall_teller_name);
		cona.set("queue_time",queue_time);
		cona.set("serv_time",serv_time);
		cona.set("serv_time_2",serv_time_2);
		boolean blan;
		if(selfStyle.equals(QueueNumFlowLineAction.this.STYLE_PAGE)){
			cona.set("needpage","1");
			blan = true;
		}else if(selfStyle.equals(QueueNumFlowLineAction.this.STYLE_EXCEL)){
			cona.set("needpage","0");
			blan = false;
		}else{
			throw new AppException("代码中(SELFSTYLE)值错误！联系技术人员解决！");
		}
		cona.set("selflag","2");
		
		//分页专用
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
		
		if(!"0".equals(map.get("btqueuehisnum").get(0))){
			if(blan){
				pageInfo.setTotal(Integer.parseInt(map.get("rcdsumnum").get(0)));//总条数	
				totalSum = Integer.parseInt(map.get("rcdsumnum").get(0));
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
			}
			String loopNum = map.get("btqueuehisnum").get(0);
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
					numflow.setWait_time("");
				}else{
					waittime = DateUtil.diffTime2(map.get("en_queue_time").get(i), map.get("de_queue_time").get(i));
					numflow.setWait_time(String.valueOf(waittime/60==0?1:waittime/60) + "分钟");
				}
				
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
				//String vcalltime_2 = map.get("vcalltime_2").get(i).toString();
				//numflow.setVcalltime_2("".equals(vcalltime_2)?"":DateUtil.formatTime(vcalltime_2));
				list.add(numflow);
				}	
			}
		}
	

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
}
