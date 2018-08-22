package com.agree.abt.action.dataAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.dataAnalysis.AnalyseWaitingTime;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

/**
 * 零售客户等待时间统计
 * @company 赞同科技
 * @author XiWang
 * @date 2014-1-16
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AnalyseWaitingTimeAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private Log logger = LogFactory.getLog(AnalyseWaitingTimeAction.class);	
	private ABTComunicateNatp cona;
	
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}

	/**
	 * 零售客户等待时间统计查询
	 */
	public String queryAnalyseWaitingTime() throws Exception {
		String jsonString = super.getJsonString();
		
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		List list = new ArrayList();
		Page pageInfo = this.getPage(jsonObj,"");
		User user = super.getLogonUser(false);
		 
		cona.set("needpage","1");
		natp(jsonObj, list, pageInfo, user, true);

		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);

		return AJAX_SUCCESS;
	}
	
	public String exportExcel()throws Exception{
		String jsonString = ServletActionContext.getRequest().getParameter("querycondition_str");
		
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		List list = new ArrayList();
		Page pageInfo = new Page();
		pageInfo.setStart(1);
		pageInfo.setPageflag(4);
		pageInfo.setLimit(0);
		User user = super.getLogonUser(false);
		 
		cona.set("needpage","0");
		natp(jsonObj, list, pageInfo, user, false);
		
		String path="AnalyseWaitingTime.xls";		
		String file = "零售客户等待时间统计";
		super.doExcel(path, list, jsonObj, file);
		return null;
	}
	
	/** 
	 * @Title: natp 
	 * @Description: 报文发送并接收
	 * @param @param jsonObj 参数
	 * @param @param list 返回的list数据集合
	 * @param @param pageInfo 分页类
	 * @param @param user 登陆用户
	 * @param @throws Exception    参数 
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public void natp(JSONObject jsonObj,List list,Page pageInfo,User user,boolean blan) throws Exception{
		String branch=jsonObj.getString("branch");//机构号
		String type=jsonObj.getString("reportType");//报表类型
		String custtype_i=jsonObj.getString("custtype");
		String queue_bus_type=jsonObj.getString("business");
		String startdate=jsonObj.getString("startDate").replace("-", "");
		String enddate=jsonObj.getString("endDate").replace("-", "");
		
		//正常使用时将一下注释的打开
		
		cona.setBMSHeader("ibp.bms.b306.01", user);
		cona.set("branch",branch);
		cona.set("startdate",startdate);
		cona.set("enddate",enddate);
		cona.set("custtype_i",custtype_i);
		cona.set("queue_bus_type",queue_bus_type);
		cona.set("reporttype",type);//报表类型
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
		logger.info(map);
		if(!"0".equals(map.get("btcusttypetimenum").get(0))){
			if(blan){
				pageInfo.setTotal(Integer.parseInt((String) map.get("rcdsumnum").get(0)));//总条数
//				Integer pageNo = (pageInfo.getTotal() % pageInfo.getLimit() == 0) ? pageInfo.getTotal() / pageInfo.getLimit() : pageInfo.getTotal()
//						/ pageInfo.getLimit() + 1;// 得到总页数
				Integer pageNo = Integer.parseInt((String) map.get("totalpage").get(0));//总页数
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
			String loopNum = (String) map.get("btcusttypetimenum").get(0);
			int num = Integer.parseInt(loopNum);
			for (int i = 0; i < num; i++) {
				AnalyseWaitingTime awt = new AnalyseWaitingTime();
				awt.setBranch(map.get("branch").get(i).toString());
				awt.setBranch_name(map.get("branch_name").get(i).toString());
				
				String work_date = map.get("work_date").get(i).toString();
				if(!"".equals(work_date)){
					//判断是多长的数据
					switch (work_date.length()) {
					case 5:
						work_date = work_date.substring(0,4) + "-第" + work_date.substring(4) + "季度";
						break;
					case 6:
						work_date = work_date.substring(0,4) + "-" + work_date.substring(4,6) + "月";
						break;
					case 8:
						work_date = work_date.substring(0,4) + "-" + work_date.substring(4,6) + "-" + work_date.substring(6);
						break;
					default:
						break;
					}
				}
				awt.setWork_date(work_date);
				
				awt.setQueue_bus_type(map.get("queue_bus_type").get(i).toString());
				awt.setBus_type_name(map.get("bus_type_name").get(i).toString());
				awt.setCusttype_i(map.get("custtype_i").get(i).toString());
				awt.setCusttype_name(map.get("custtype_name").get(i).toString());
				awt.setTicket_g(map.get("ticket_g").get(i).toString());
				awt.setTicket_v(map.get("ticket_v").get(i).toString());
				awt.setTicket_l(map.get("ticket_l").get(i).toString());
				
				String ticket_l_rate = String.valueOf((Float.parseFloat(map.get("ticket_l_rate").get(i).toString())*100));
				if(ticket_l_rate.length()-ticket_l_rate.indexOf(".")>=3){
					awt.setTicket_l_rate(ticket_l_rate.substring(0,ticket_l_rate.indexOf(".")+3) + "%");
				}else {
					awt.setTicket_l_rate(ticket_l_rate + "%");
				}
				
				awt.setTicket_s(map.get("ticket_s").get(i).toString());
				
				String standard_rate = String.valueOf((Float.parseFloat(map.get("standard_rate").get(i).toString())*100));
				if(standard_rate.length()-standard_rate.indexOf(".")>=3){
					awt.setStandard_rate(standard_rate.substring(0,standard_rate.indexOf(".")+3) + "%");
				}else {
					awt.setStandard_rate(standard_rate + "%");
				}
				
//				String endtime = map.get("endtime").get(i).toString();
//				if(!"".equals(endtime)){
//					endtime = endtime.substring(0,2) + ":" + endtime.substring(2,4) + ":" + endtime.substring(4);
//				}
//				awt.setEndtime(endtime);
				
//				String avgwaittime = String.valueOf(Float.parseFloat(map.get("avgwaittime").get(i).toString())/60);
//				if(avgwaittime.length()-avgwaittime.indexOf(".")>=3){
//					awt.setAvgwaittime(avgwaittime.substring(0,avgwaittime.indexOf(".")+3));
//				}else {
//					awt.setAvgwaittime(avgwaittime);
//				}
				
				list.add(awt);
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
