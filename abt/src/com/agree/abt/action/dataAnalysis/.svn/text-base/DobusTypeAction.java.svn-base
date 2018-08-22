package com.agree.abt.action.dataAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.dataAnalysis.DobusType;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

/** 
 * @ClassName: DobusTypeAction 
 * @Description: 客户办理业务类别统计 
 * @author lilei
 * @date 2013-10-12 下午03:09:12  
 */ 
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DobusTypeAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private Log logger = LogFactory.getLog(DobusTypeAction.class);	
	Integer totalSum = 0;//查询总记录数
	
	private ABTComunicateNatp cona;
	
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: queryeType
	 * @Description: 柜员办理效率分析
	 * @return String
	 * @throws Exception
	 */
	public String queryType() throws Exception {
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
		pageInfo.setLimit(totalSum);
		User user = super.getLogonUser(false);
		 
		cona.set("needpage","0");
		natp(jsonObj, list, pageInfo, user, false);
		
		String path="DobusTypeBook.xls";		
		String file = "客户办理业务类别统计";
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
		String startdate=jsonObj.getString("startDate").replace("-", "");
		String enddate=jsonObj.getString("endDate").replace("-", "");

		//正常使用时将一下注释的打开
		
		cona.setBMSHeader("ibp.bms.b305.01", user);
		cona.set("branch",branch);
		cona.set("startdate",startdate);
		cona.set("enddate",enddate);
		cona.set("reporttype",type);//报表类型
		cona.set("selflag","2");
		
		//分页专用
		logger.info(pageInfo.getStart().toString());
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
		
		if(!"0".equals(map.get("btdobustypenum").get(0))){
			if(blan){
				pageInfo.setTotal(Integer.parseInt(map.get("rcdsumnum").get(0)));//总条数
				totalSum = Integer.parseInt(map.get("rcdsumnum").get(0));
				
//				Integer pageNo = (pageInfo.getTotal() % pageInfo.getLimit() == 0) ? pageInfo.getTotal() / pageInfo.getLimit() : pageInfo.getTotal()
//						/ pageInfo.getLimit() + 1;// 得到总页数
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
			String loopNum = map.get("btdobustypenum").get(0);
			int num = Integer.parseInt(loopNum);
			for (int i = 0; i < num; i++) {
				DobusType dobustype = new DobusType();
				//正常使用时将一下注释的打开
				dobustype.setBranch(map.get("branch").get(i).toString());
				dobustype.setBranch_name(map.get("branch_name").get(i).toString());
				dobustype.setDate(map.get("date").get(i).toString());
				dobustype.setQueue_bus_type(map.get("queue_bus_type").get(i).toString());
				dobustype.setBus_type_name(map.get("bus_type_name").get(i).toString());
				dobustype.setDobus_count(map.get("dobus_count").get(i).toString());
				
				String avgwaittime = String.valueOf(Float.parseFloat(map.get("avgwaittime").get(i).toString())/60);
				if(avgwaittime.length()-avgwaittime.indexOf(".")>=3){
					dobustype.setAvgwaittime(avgwaittime.substring(0,avgwaittime.indexOf(".")+3));
				}else {
					dobustype.setAvgwaittime(avgwaittime);
				}
				
				String total_wait_time = String.valueOf(Float.parseFloat(map.get("total_wait_time").get(i).toString())/60);
				if(total_wait_time.length()-total_wait_time.indexOf(".")>=3){
					dobustype.setTotal_wait_time(total_wait_time.substring(0,total_wait_time.indexOf(".")+3));
				}else {
					dobustype.setTotal_wait_time(total_wait_time);
				}
				
				String avgservtime = String.valueOf(Float.parseFloat(map.get("avgservtime").get(i).toString())/60);
				if(avgservtime.length()-avgservtime.indexOf(".")>=3){
					dobustype.setAvgservtime(avgservtime.substring(0,avgservtime.indexOf(".")+3));
				}else {
					dobustype.setAvgservtime(avgservtime);
				}
				
				String total_service_time = String.valueOf(Float.parseFloat(map.get("total_service_time").get(i).toString())/60);
				if(total_service_time.length()-total_service_time.indexOf(".")>=3){
					dobustype.setTotal_service_time(total_service_time.substring(0,total_service_time.indexOf(".")+3));
				}else {
					dobustype.setTotal_service_time(total_service_time);
				}
				
				list.add(dobustype);
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
