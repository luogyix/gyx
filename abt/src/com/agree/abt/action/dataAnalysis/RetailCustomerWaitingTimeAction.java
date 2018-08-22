package com.agree.abt.action.dataAnalysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.service.dataAnalysis.impl.RetailCustomerWaitingTimeService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.util.MathUtil;

/** 
 * 零售客户等待时间(达标率)统计
 */ 
@SuppressWarnings({ "rawtypes" })
public class RetailCustomerWaitingTimeAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private RetailCustomerWaitingTimeService retailCustomerWaitingTimeService;
	
	/** 
	 * 进入页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 查询零售客户等待时间(达标率)
	 */
	public String queryCustomerWaitingTime() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		Page pageInfo = this.getPage(jsonObj);
		jsonObj.put("startDate", jsonObj.getString("startDate").replace("-", ""));
		jsonObj.put("endDate", jsonObj.getString("endDate").replace("-", ""));
		List list = retailCustomerWaitingTimeService.queryList(jsonObj,pageInfo);
		for (int i = 0; i < list.size(); i++) {
			Map rMap = (HashMap)list.get(i);
			//白金客户弃票率
			//String PLATINUM_DISCARDED_TICKET_RATE = String.valueOf(Float.parseFloat((rMap.get("PLATINUM_DISCARDED_TICKET_RATE").toString()))*100);
			MathUtil.formatRate(rMap,"PLATINUM_DISCARDED_TICKET_RATE",2);
			MathUtil.formatRate(rMap,"PLATINUM_STANDARD_TICKET_RATE",2);
			MathUtil.formatRate(rMap,"GOLD_DISCARDED_TICKET_RATE",2);
			MathUtil.formatRate(rMap,"GOLD_STANDARD_TICKET_RATE",2);
			MathUtil.formatRate(rMap,"COMMON_DISCARDED_TICKET_RATE",2);
			MathUtil.formatRate(rMap,"COMMON_STANDARD_TICKET_RATE",2);
		}
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/** 
	 * 导出excel
	 */ 
	public String exportExcel()throws Exception{
		String jsonString = ServletActionContext.getRequest().getParameter("querycondition_str");
		
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
//		List list = new ArrayList();
		Page pageInfo = new Page();
		pageInfo.setStart(1);
		pageInfo.setLimit(1000000);
//		User user = super.getLogonUser(false);
//		 
//		cona.set("needpage","0");
//		natp(jsonObj, list, pageInfo, user, false);
		jsonObj.put("startDate", jsonObj.getString("startDate").replace("-", ""));
		jsonObj.put("endDate", jsonObj.getString("endDate").replace("-", ""));
		List list = retailCustomerWaitingTimeService.queryList(jsonObj,pageInfo);
		String path="RetailCustomerWaitingTime.xls";
		String file = "零售客户等候时间统计";
//		//进行时间格式转换
		super.doExcel(path, list, jsonObj, file);
		return null;
	}

	public RetailCustomerWaitingTimeService getRetailCustomerWaitingTimeService() {
		return retailCustomerWaitingTimeService;
	}

	public void setRetailCustomerWaitingTimeService(
			RetailCustomerWaitingTimeService retailCustomerWaitingTimeService) {
		this.retailCustomerWaitingTimeService = retailCustomerWaitingTimeService;
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
//	public void natp(JSONObject jsonObj,List list,Page pageInfo,User user,boolean blan) throws Exception{
//		//正常使用时将一下注释的打开
//		String branch=jsonObj.getString("branch");//机构号
//		String type=jsonObj.getString("reportType");//报表类型
//		String startdate=jsonObj.getString("startDate").replace("-", "");
//		String enddate=jsonObj.getString("endDate").replace("-", "");
//		
//		cona.setHeader("ibp.bms.b301.01", user.getUnitid(), user.getUsercode(),"03");
//		
//		cona.set("branch",branch);
//		cona.set("startdate",startdate);
//		cona.set("enddate",enddate);
//		cona.set("reporttype",type);//报表类型
//		cona.set("selflag","2");
//		
//		//分页专用
//		cona.set("pageflag",pageInfo.getPageflag().toString());//分页标识
//		String currentpage = "";
//		switch (pageInfo.getPageflag()) {
//		case 0:
//			currentpage = "1";
//			pageInfo.setStart(1);
//			break;
//		case 1:
//			currentpage = "1";
//			break;
//		case 2:
//			currentpage = String.valueOf(pageInfo.getStart()+1);
//			break;
//		case 3:
//			currentpage = String.valueOf(pageInfo.getStart()-1);
//			break;
//		case 4:
//			currentpage = String.valueOf(pageInfo.getStart());
//			pageInfo.setStart(pageInfo.getStart());
//			break;
//		}
//		cona.set("currentpage",currentpage);//当前页码
//		cona.set("count",pageInfo.getLimit().toString());//每页记录数
//		
//		HashMap<String, List<String>> map = (HashMap) cona.exchange();
//		String validate="";
//		validate=cona.validMap(map);
//		if(validate!=null&&validate.trim().length()>0){
//			throw new AppException(validate);
//		}
//		if(!"0".equals(map.get("btwaitingtimenum").get(0))){
//			if(blan){
//				pageInfo.setTotal(Integer.parseInt(map.get("rcdsumnum").get(0)));//总条数
//				totalSum = Integer.parseInt(map.get("rcdsumnum").get(0));
//				
////				Integer pageNo = (pageInfo.getTotal() % pageInfo.getLimit() == 0) ? pageInfo.getTotal() / pageInfo.getLimit() : pageInfo.getTotal()
////						/ pageInfo.getLimit() + 1;// 得到总页数
//				Integer pageNo = Integer.parseInt(map.get("totalpage").get(0));//总页数
//				if (pageInfo.getStart() == -1) {// 查询最后一页
//					pageInfo.setRowStart((pageNo - 1) * pageInfo.getLimit());
//					pageInfo.setRowEnd(pageInfo.getRowStart()
//							+ (pageInfo.getTotal() % pageInfo.getLimit() == 0 ? pageInfo.getLimit() : pageInfo.getTotal() % pageInfo.getLimit()));
//					pageInfo.setPage(pageNo);
//					pageInfo.setTotal(pageInfo.getTotal());
//				} else {
//					pageInfo.setRowStart((pageInfo.getStart() - 1) * pageInfo.getLimit() );
//					pageInfo.setRowEnd( (pageInfo.getRowStart() + pageInfo.getLimit()) <= pageInfo.getTotal() ? (pageInfo.getRowStart() + pageInfo.getLimit())
//							: pageInfo.getTotal() );
//					pageInfo.setPage(pageNo);
//					pageInfo.setTotal(pageInfo.getTotal());
//				}
//			}
//			String loopNum = map.get("btwaitingtimenum").get(0);
//			int num = Integer.parseInt(loopNum);
//			for (int i = 0; i < num; i++) {
//				WaitingTime waittime = new WaitingTime();
//				//正常使用时将一下注释的打开
//				waittime.setBranch(map.get("branch").get(i).toString());
//				waittime.setBranch_name(map.get("branch_name").get(i).toString());
//				waittime.setDate(map.get("date").get(i).toString());
//				waittime.setLess3(map.get("less3").get(i).toString());
//				waittime.setMore3less5(map.get("more3less5").get(i).toString());
//				waittime.setMore5less10(map.get("more5less10").get(i).toString());
//				waittime.setMore10less15(map.get("more10less15").get(i).toString());
//				waittime.setMore15less20(map.get("more15less20").get(i).toString());
//				waittime.setMore20less30(map.get("more20less30").get(i).toString());
//				waittime.setMore30(map.get("more30").get(i).toString());
//				
//				String total_waiting_time = String.valueOf(Float.parseFloat(map.get("total_waiting_time").get(i).toString())/60);
//				if(total_waiting_time.length()-total_waiting_time.indexOf(".")>=3){
//					waittime.setTotal_waiting_time(total_waiting_time.substring(0,total_waiting_time.indexOf(".")+3));
//				}else {
//					waittime.setTotal_waiting_time(total_waiting_time);
//				}
//				
//				//waittime.setTotal_waiting_time(map.get("total_waiting_time").get(i).toString());
//				
//				
//				String average_waiting_time = String.valueOf(Float.parseFloat(map.get("average_waiting_time").get(i).toString())/60);
//				if(average_waiting_time.length()-average_waiting_time.indexOf(".")>=3){
//					waittime.setAverage_waiting_time(average_waiting_time.substring(0,average_waiting_time.indexOf(".")+3));
//				}else {
//					waittime.setAverage_waiting_time(average_waiting_time);
//				}
//				
//				//waittime.setAverage_waiting_time(map.get("average_waiting_time").get(i).toString());
//				
//				
//				waittime.setTotal_count(map.get("total_count").get(i).toString());
//				list.add(waittime);
//			}
//		}
//	}
}
