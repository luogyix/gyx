package com.agree.abt.action.dataAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.dataAnalysis.ClientAssess;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

/** 
 * @ClassName: ClientAssessAction 
 * @Description: TODO 
 * @author lilei
 * @date 2013-10-12 下午03:08:29  
 */ 
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ClientAssessVAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
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
	 * @Title: queryAssess
	 * @Description: 客户评价分析
	 * @return String
	 * @throws Exception
	 */
	public String queryAssess() throws Exception {
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
		
		String path="ClientAssessBookV.xls";		
		String file = "客户评价统计(有评分)";
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
		String teller = jsonObj.getString("teller");

		//正常使用时将一下注释的打开
		
		cona.setBMSHeader("ibp.bms.b303.01", user);
		
		cona.set("startdate",startdate);
		cona.set("enddate",enddate);
		cona.set("reporttype",type);
		if("".equals(teller)){
			cona.set("selflag","1");
		}else{
			cona.set("teller",teller);
			if("".equals(branch)){
				branch = user.getUnitid();
				cona.set("selflag","3");
			}else {
				cona.set("selflag","4");
			}
		}
		cona.set("branch",branch);
		
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
		
		if(!"0".equals(map.get("bttellerassessnum").get(0))){
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
			String loopNum = map.get("bttellerassessnum").get(0);
			int num = Integer.parseInt(loopNum);
			for (int i = 0; i < num; i++) {
				ClientAssess clas = new ClientAssess();
				clas.setBranch(map.get("branch").get(i).toString());
				clas.setBranch_name(map.get("branch_name").get(i).toString());
				clas.setDate(map.get("date").get(i).toString());
				clas.setTeller(map.get("teller").get(i).toString());
				clas.setTeller_name(map.get("teller_name").get(i).toString());
				clas.setTotal_call_num(map.get("total_call_num").get(i).toString());
				clas.setTotal_service_num(map.get("total_service_num").get(i).toString());
				clas.setTotal_assess_num(map.get("total_assess_num").get(i).toString());
				clas.setVery_satisfy(map.get("very_satisfy").get(i).toString());
				clas.setSatisfy(map.get("satisfy").get(i).toString());
				clas.setGenl_satisfy(map.get("genl_satisfy").get(i).toString());
				clas.setNot_satisfy(map.get("not_satisfy").get(i).toString());
				clas.setNot_evaluate(map.get("not_evaluate").get(i).toString());
				clas.setNot_sed_evaluate(map.get("not_sed_evaluate").get(i).toString());
				
				if (!"".equals(map.get("assess_rate").get(i).toString())) {
					String assess_rate = String.valueOf((Float.parseFloat(map.get("assess_rate").get(i).toString())*100));
					if(assess_rate.length()-assess_rate.indexOf(".")>=3){
						clas.setAssess_rate(assess_rate.substring(0,assess_rate.indexOf(".")+3) + "%");
					}else {
						clas.setAssess_rate(assess_rate + "%");
					}					
				} else {
					clas.setAssess_rate("0%");
				}
				if (!"".equals(map.get("very_satisfy_rate").get(i).toString())) {
					String very_satisfy_rate = String.valueOf((Float.parseFloat(map.get("very_satisfy_rate").get(i).toString())*100));
					if(very_satisfy_rate.length()-very_satisfy_rate.indexOf(".")>=3){
						clas.setVery_satisfy_rate(very_satisfy_rate.substring(0,very_satisfy_rate.indexOf(".")+3) + "%");
					}else {
						clas.setVery_satisfy_rate(very_satisfy_rate + "%");
					}					
				} else {
					clas.setVery_satisfy_rate("0%");
				}
				if (!"".equals(map.get("satisfy_rate").get(i).toString())) {
					String satisfy_rate = String.valueOf((Float.parseFloat(map.get("satisfy_rate").get(i).toString())*100));
					if(satisfy_rate.length()-satisfy_rate.indexOf(".")>=3){
						clas.setSatisfy_rate(satisfy_rate.substring(0,satisfy_rate.indexOf(".")+3) + "%");
					}else {
						clas.setSatisfy_rate(satisfy_rate + "%");
					}					
				} else {
					clas.setSatisfy_rate("0%");
				}
				if (!"".equals(map.get("genl_satisfy_rate").get(i).toString())) {
					String genl_satisfy_rate = String.valueOf((Float.parseFloat(map.get("genl_satisfy_rate").get(i).toString())*100));
					if(genl_satisfy_rate.length()-genl_satisfy_rate.indexOf(".")>=3){
						clas.setGenl_satisfy_rate(genl_satisfy_rate.substring(0,genl_satisfy_rate.indexOf(".")+3) + "%");
					}else {
						clas.setGenl_satisfy_rate(genl_satisfy_rate + "%");
					}					
				} else {
					clas.setGenl_satisfy_rate("0%");
				}
				if (!"".equals(map.get("genl_satisfy_rate").get(i).toString())) {
					String not_satisfy_rate = String.valueOf((Float.parseFloat(map.get("not_satisfy_rate").get(i).toString())*100));
					if(not_satisfy_rate.length()-not_satisfy_rate.indexOf(".")>=3){
						clas.setNot_satisfy_rate(not_satisfy_rate.substring(0,not_satisfy_rate.indexOf(".")+3) + "%");
					}else {
						clas.setNot_satisfy_rate(not_satisfy_rate + "%");
					}					
				} else {
					clas.setNot_satisfy_rate("0%");
				}
				if (!"".equals(map.get("not_evaluate_rate").get(i).toString())){
					String not_evaluate_rate = String.valueOf((Float.parseFloat(map.get("not_evaluate_rate").get(i).toString())*100));
					if(not_evaluate_rate.length()-not_evaluate_rate.indexOf(".")>=3){
						clas.setNot_evaluate_rate(not_evaluate_rate.substring(0,not_evaluate_rate.indexOf(".")+3) + "%");
					}else {
						clas.setNot_evaluate_rate(not_evaluate_rate + "%");
					}					
				} else {
					clas.setNot_evaluate_rate("0%");
				}
				if (!"".equals(map.get("not_sed_evaluate_rate").get(i).toString())) {
					String not_sed_evaluate_rate = String.valueOf((Float.parseFloat(map.get("not_sed_evaluate_rate").get(i).toString())*100));
					if(not_sed_evaluate_rate.length()-not_sed_evaluate_rate.indexOf(".")>=3){
						clas.setNot_sed_evaluate_rate(not_sed_evaluate_rate.substring(0,not_sed_evaluate_rate.indexOf(".")+3) + "%");
					}else {
						clas.setNot_sed_evaluate_rate(not_sed_evaluate_rate + "%");
					}
				} else {
					clas.setNot_sed_evaluate_rate("0%");
				}				
				clas.setServiceratings(map.get("serviceratings").get(i).toString());
				clas.setServiceratings_avg(map.get("serviceratings_avg").get(i).toString());
				list.add(clas);
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
