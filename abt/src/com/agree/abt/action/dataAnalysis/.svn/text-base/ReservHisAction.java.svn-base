package com.agree.abt.action.dataAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.dataAnalysis.ReservFlow;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.DateUtil;

/**
 * 预约流水-历史查询
 * @ClassName: ReservFlowAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2014-2-24
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ReservHisAction extends BaseAction {
	
	private static final long serialVersionUID = 2141897762615781445L;
	private ABTComunicateNatp cona;
	
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());//获取部门集合
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 查询预约历史
	 * @return
	 * @throws Exception
	 */
	public String queryReservHistory() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		List list = new ArrayList();
		Page pageInfo = this.getPage(jsonObj,"");
		User user = super.getLogonUser(false);

		 
		
		cona.setBMSHeader("ibp.bds.p208.01", user);
		
//		if(jsonObj.containsKey("bs_id")){
//			cona.set("bs_id",jsonObj.getString("bs_id"));
//		}
		cona.set("branch",jsonObj.getString("branch"));
		cona.set("bs_id",jsonObj.getString("bs_id"));
		cona.set("reserv_id",jsonObj.getString("reserv_id").trim());
		cona.set("startdate",jsonObj.getString("startdate").replace("-", ""));
		cona.set("enddate",jsonObj.getString("enddate").replace("-", ""));
		cona.set("reserv_begin_time",jsonObj.getString("reserv_begin_time").replace(":", ""));
		cona.set("reserv_end_time",jsonObj.getString("reserv_end_time").replace(":", ""));
		//分页专用
		cona.set("needpage","1");
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
		if(!"0".equals(map.get("btreservqueuenum").get(0))){
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
			String loopNum = map.get("btreservqueuenum").get(0);
			int num = Integer.parseInt(loopNum);
			for (int i = 0; i < num; i++) {
				ReservFlow rf = new ReservFlow();
				//正常使用时将一下注释的打开
				rf.setWork_date(map.get("work_date").get(i).toString());
				rf.setRequest_seq(map.get("request_seq").get(i).toString());
				rf.setRequest_channel(map.get("request_channel").get(i).toString());
				rf.setReserv_id(map.get("reserv_id").get(i).toString());
				rf.setReserv_bs_id(map.get("reserv_bs_id").get(i).toString());
				rf.setReserv_branch(map.get("reserv_branch").get(i).toString());
				rf.setReserv_status(map.get("reserv_status").get(i).toString());
				rf.setReserv_seq(map.get("reserv_seq").get(i).toString());
				rf.setQueue_seq(map.get("queue_seq").get(i).toString());
				rf.setService_seq(map.get("service_seq").get(i).toString());
				rf.setCustinfo_type(map.get("custinfo_type").get(i).toString());
				rf.setCustinfo_num(map.get("custinfo_num").get(i).toString());
				rf.setCustinfo_name(map.get("custinfo_name").get(i).toString());
				rf.setAccount(map.get("account").get(i).toString());
				rf.setSms_customer(map.get("sms_customer").get(i).toString());
				rf.setPhone_no(map.get("phone_no").get(i).toString());
				rf.setReserv_zone(map.get("reserv_zone").get(i).toString());
				rf.setCheck_reserv_value(map.get("check_reserv_value").get(i).toString());
				
				String reserv_modify_time = map.get("reserv_modify_time").get(i).toString();
				rf.setReserv_modify_time("".equals(reserv_modify_time)?"":DateUtil.formatTime(reserv_modify_time));
				String reserv_end_time = map.get("reserv_end_time").get(i).toString();
				rf.setReserv_end_time("".equals(reserv_end_time)?"":DateUtil.formatTime(reserv_end_time));
				String reserv_begin_time = map.get("reserv_begin_time").get(i).toString();
				rf.setReserv_begin_time("".equals(reserv_begin_time)?"":DateUtil.formatTime(reserv_begin_time));
				String reserv_record_time = map.get("reserv_record_time").get(i).toString();
				rf.setReserv_record_time("".equals(reserv_record_time)?"":DateUtil.formatTime(reserv_record_time));
				
				rf.setReserv_record_date(map.get("reserv_record_date").get(i).toString());
				rf.setReserv_begin_date(map.get("reserv_begin_date").get(i).toString());
				rf.setReserv_end_date(map.get("reserv_end_date").get(i).toString());
				rf.setBs_name_ch(map.get("bs_name_ch").get(i).toString());
				//list.add(map.get("bs_name_ch").get(i).toString());
				list.add(rf);
			}
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);

		return AJAX_SUCCESS;
	}
	
	/**
	 * 导出预约历史的EXCEL
	 * @return
	 * @throws Exception
	 */
	public String exportReservHistory() throws Exception {
		String jsonString = ServletActionContext.getRequest().getParameter("querycondition_str");
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		List list = new ArrayList();
		Page pageInfo = new Page();
		pageInfo.setStart(1);
		pageInfo.setPageflag(4);
		pageInfo.setLimit(99999);
		User user = super.getLogonUser(false);
		
		//正常使用时将一下注释的打开
		 
		
		cona.setBMSHeader("ibp.bds.p208.01", user);
		
		cona.set("branch",jsonObj.getString("branch"));
//		if(jsonObj.containsKey("bs_id")){
//			cona.set("bs_id",jsonObj.getString("bs_id"));
//		}
		cona.set("bs_id",jsonObj.getString("bs_id"));
		cona.set("reserv_id",jsonObj.getString("reserv_id").trim());
		cona.set("startdate",jsonObj.getString("startdate").replace("-", ""));
		cona.set("enddate",jsonObj.getString("enddate").replace("-", ""));
		cona.set("reserv_begin_time",jsonObj.getString("reserv_begin_time").replace(":", ""));
		cona.set("reserv_end_time",jsonObj.getString("reserv_end_time").replace(":", ""));
		//分页专用
		cona.set("needpage","0");
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
		if(!"0".equals(map.get("btreservqueuenum").get(0))){
			String loopNum = map.get("btreservqueuenum").get(0);
			int num = Integer.parseInt(loopNum);
			for (int i = 0; i < num; i++) {
				ReservFlow rf = new ReservFlow();
				rf.setWork_date(map.get("work_date").get(i).toString());
				rf.setRequest_seq(map.get("request_seq").get(i).toString());
				rf.setRequest_channel(map.get("request_channel").get(i).toString());
				rf.setReserv_id(map.get("reserv_id").get(i).toString());
				rf.setReserv_bs_id(map.get("reserv_bs_id").get(i).toString());
				rf.setReserv_branch(map.get("reserv_branch").get(i).toString());
				Map<String,String> map2 = new HashMap<String,String>();
				map2.put("0","0-已登记");
				map2.put("1","1-已激活");
				map2.put("2","2-已取消");
				map2.put("3","3-已过期");
				if(map2.containsKey(map.get("reserv_status").get(i).toString())){
					rf.setReserv_status(map2.get(map.get("reserv_status").get(i).toString()));
				}else {
					rf.setReserv_status(map.get("reserv_status").get(i).toString());
				}
				rf.setReserv_seq(map.get("reserv_seq").get(i).toString());
				rf.setQueue_seq(map.get("queue_seq").get(i).toString());
				
				rf.setService_seq(map.get("service_seq").get(i).toString());
				Map<String,String> map1 = new HashMap<String,String>();
				map1.put("0","0-身份证");
				map1.put("1","1-银行卡卡号");
				map1.put("2","2-客户号");
				map1.put("3","3-护照号码");
				map1.put("B","B-外国公民护照");
				map1.put("C","C-户口簿");
				map1.put("D","D-港澳居民通行证");
				map1.put("E","E-还乡证");
				map1.put("F","F-边民出入境通行证");
				map1.put("G","G-军官证");
				map1.put("H","H-士兵证");
				map1.put("I","I-军事院校学员证");
				map1.put("J","J-军队离休干部荣誉证");
				map1.put("K","K-军官退休证");
				map1.put("L","L-军人文职干部退休证");
				map1.put("M","M-营业执照");
				map1.put("N","N-批文");
				map1.put("O","O-开户证明");
				map1.put("P","P-其他");
				map1.put("Q","Q-武警身份证");
				map1.put("R","R-台湾居民通行证");
				map1.put("S","S-中国公民护照");
				map1.put("T","T-台湾居民往大陆通知证");
				map1.put("U","U-临时身份证");
				map1.put("X","X-事业单位登记证");
				map1.put("Y","Y-企业名称预先核准通知书");
				if(map1.containsKey(map.get("custinfo_type").get(i).toString())){
					rf.setCustinfo_type(map1.get(map.get("custinfo_type").get(i).toString()));
				}else {
					rf.setCustinfo_type(map.get("custinfo_type").get(i).toString());
				}
				rf.setCustinfo_num(map.get("custinfo_num").get(i).toString());
				rf.setCustinfo_name(map.get("custinfo_name").get(i).toString());
				rf.setAccount(map.get("account").get(i).toString());
				Map<String,String> map3 = new HashMap<String,String>();
				map3.put("0","0-未通知");
				map3.put("1","1-已通知");
				if(map3.containsKey(map.get("sms_customer").get(i).toString())){
					rf.setSms_customer(map3.get(map.get("sms_customer").get(i).toString()));
				}else {
					rf.setSms_customer(map.get("sms_customer").get(i).toString());
				}
				rf.setPhone_no(map.get("phone_no").get(i).toString());
				rf.setReserv_zone(map.get("reserv_zone").get(i).toString());
				rf.setCheck_reserv_value(map.get("check_reserv_value").get(i).toString());
				
				
				String reserv_modify_time = map.get("reserv_modify_time").get(i).toString();
				rf.setReserv_modify_time("".equals(reserv_modify_time)?"":DateUtil.formatTime(reserv_modify_time));
				String reserv_end_time = map.get("reserv_end_time").get(i).toString();
				rf.setReserv_end_time("".equals(reserv_end_time)?"":DateUtil.formatTime(reserv_end_time));
				String reserv_begin_time = map.get("reserv_begin_time").get(i).toString();
				rf.setReserv_begin_time("".equals(reserv_begin_time)?"":DateUtil.formatTime(reserv_begin_time));
				String reserv_record_time = map.get("reserv_record_time").get(i).toString();
				rf.setReserv_record_time("".equals(reserv_record_time)?"":DateUtil.formatTime(reserv_record_time));
				
				rf.setReserv_record_date(map.get("reserv_record_date").get(i).toString());
				rf.setReserv_begin_date(map.get("reserv_begin_date").get(i).toString());
				rf.setReserv_end_date(map.get("reserv_end_date").get(i).toString());
				rf.setBs_name_ch(map.get("bs_name_ch").get(i).toString());
				//list.add(map.get("bs_name_ch").get(i).toString());
				list.add(rf);
				
			}
		}
		
		String path="ReservHis.xls";		
		String file = "预约历史数据";
		super.doExcel(path, list, jsonObj, file);
		return null;
	}

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
}
