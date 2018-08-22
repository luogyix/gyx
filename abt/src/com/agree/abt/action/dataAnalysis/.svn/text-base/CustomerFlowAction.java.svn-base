package com.agree.abt.action.dataAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.dataAnalysis.CustomerFlow;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

/** 
 * @ClassName: CustomerFlowAction 
 * @Description: 时段客户流量统计 
 * @author lilei
 * @date 2013-10-12 下午03:08:46  
 */ 
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CustomerFlowAction extends BaseAction {
	
	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = 1254080318580562419L;
	
	Integer totalSum = 0;//查询总记录数
	
	private ABTComunicateNatp cona;
	
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(	ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/** 
	 * @Title: queryFlow 
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws 
	 */ 
	public String queryFlow() throws Exception {
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

	/** 
	 * @Title: exportExcel 
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws 
	 */ 
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
		
		String path="CustomerFlowBook.xls";		
		String file = "时段客户流量统计";
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
		
		cona.setBMSHeader("ibp.bms.b304.01", user);
		cona.set("branch",branch);
		cona.set("startdate",startdate);
		cona.set("enddate",enddate);
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
		
		if(!"0".equals(map.get("btbranchflownum").get(0))){
			if(blan){
				pageInfo.setTotal(Integer.parseInt((String) map.get("rcdsumnum").get(0)));//总条数
				totalSum = Integer.parseInt((String) map.get("rcdsumnum").get(0));
				
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
			String loopNum = (String) map.get("btbranchflownum").get(0);
			int num = Integer.parseInt(loopNum);
			for (int i = 0; i < num; i++) {
				CustomerFlow flow = new CustomerFlow();
				//正常使用时将一下注释的打开
				flow.setBranch(map.get("branch").get(i).toString());
				flow.setBranch_name(map.get("branch_name").get(i).toString());
				flow.setDate(map.get("date").get(i).toString());
				flow.setLess_8(map.get("less_8").get(i).toString());
				flow.setT8_9(map.get("t8_9").get(i).toString());
				flow.setT9_10(map.get("t9_10").get(i).toString());
				flow.setT10_11(map.get("t10_11").get(i).toString());
				flow.setT11_12(map.get("t11_12").get(i).toString());
				flow.setT12_13(map.get("t12_13").get(i).toString());
				flow.setT13_14(map.get("t13_14").get(i).toString());
				flow.setT14_15(map.get("t14_15").get(i).toString());
				flow.setT15_16(map.get("t15_16").get(i).toString());
				flow.setT16_17(map.get("t16_17").get(i).toString());
				flow.setT17_18(map.get("t17_18").get(i).toString());
				flow.setMore_18(map.get("more_18").get(i).toString());
				flow.setTotal(map.get("total").get(i).toString());

				list.add(flow);
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
