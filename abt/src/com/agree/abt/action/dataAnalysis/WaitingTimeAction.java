package com.agree.abt.action.dataAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.dataAnalysis.WaitingTime;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.administration.IWaitingTimeService;
import com.agree.util.IDictABT;
/** 
 * @ClassName: WaitingTimeAction 
 * @Description: 客户等待时间统计
 * @author lilei
 * @date 2013-10-12 上午11:56:44  
 */ 
@SuppressWarnings({ "unchecked", "rawtypes" })
public class WaitingTimeAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	Integer totalSum = 0;//查询总记录数
	
	private ABTComunicateNatp cona;
	private IWaitingTimeService waitingTimeService;
	
	/** 
	 * 查询页面
	 * @Title: loadPage 
	 * @return String    返回类型 
	 * @throws 
	 */ 
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 重新生成统计分析
	 * @return
	 * @throws Exception
	 */
	public String resetAnalysis() throws Exception {
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		//Map<String, String> map = new HashMap<String, String>();
		//map.put("dateStart", obj.getString("startDate").replace("-", ""));
		//map.put("dateEnd", obj.getString("endDate").replace("-", ""));
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String dateStart = obj.getString("startDate").replace("-", "");
		String dateEnd = obj.getString("endDate").replace("-", "");
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
			throw new AppException("请确认生成时间段不超过3个月!");
		}
		 
		
		cona.setBMSHeader("ibp.bms.b311.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set("startdate", dateStart);
		cona.set("enddate", dateEnd);
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		
		//ServiceReturn sRet = waitingTimeService.resetAnalysis(map);
		
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	/**
	 * 
	 * @Title: queryTime
	 * @Description: 用户等待时间信息（分页）
	 * @return String
	 * @throws Exception
	 */
	public String queryTime() throws Exception {
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
	 * @Description: 导出excel
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
		
		String path="WaitingTimeBook.xls";
		String file = "客户等待时间统计";
		//进行时间格式转换
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
		//正常使用时将一下注释的打开
		String branch=jsonObj.getString("branch");//机构号
		String type=jsonObj.getString("reportType");//报表类型
		String startdate=jsonObj.getString("startDate").replace("-", "");
		String enddate=jsonObj.getString("endDate").replace("-", "");
		
		
		cona.setBMSHeader("ibp.bms.b301.01", user);
		
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
		
		if(!"0".equals(map.get("btwaitingtimenum").get(0))){
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
			String loopNum = map.get("btwaitingtimenum").get(0);
			int num = Integer.parseInt(loopNum);
			for (int i = 0; i < num; i++) {
				WaitingTime waittime = new WaitingTime();
				//正常使用时将一下注释的打开
				waittime.setBranch(map.get("branch").get(i).toString());
				waittime.setBranch_name(map.get("branch_name").get(i).toString());
				waittime.setDate(map.get("date").get(i).toString());
				waittime.setLess3(map.get("less3").get(i).toString());
				waittime.setMore3less5(map.get("more3less5").get(i).toString());
				waittime.setMore5less10(map.get("more5less10").get(i).toString());
				waittime.setMore10less15(map.get("more10less15").get(i).toString());
				waittime.setMore15less20(map.get("more15less20").get(i).toString());
				waittime.setMore20less30(map.get("more20less30").get(i).toString());
				waittime.setMore30(map.get("more30").get(i).toString());
				if(map.get("total_waiting_time").get(i).toString()!=null&&!"".equals(map.get("total_waiting_time").get(i).toString())){
					String total_waiting_time = String.valueOf(Float.parseFloat(map.get("total_waiting_time").get(i).toString())/60);
					if(total_waiting_time.length()-total_waiting_time.indexOf(".")>=3){
						waittime.setTotal_waiting_time(total_waiting_time.substring(0,total_waiting_time.indexOf(".")+3));
					}else {
						waittime.setTotal_waiting_time(total_waiting_time);
					}					
				}else{
					waittime.setTotal_waiting_time("0");
				}
				if(map.get("average_waiting_time").get(i).toString()!=null&&!"".equals(map.get("average_waiting_time").get(i).toString())){					
					String average_waiting_time = String.valueOf(Float.parseFloat(map.get("average_waiting_time").get(i).toString())/60);
					if(average_waiting_time.length()-average_waiting_time.indexOf(".")>=3){
						waittime.setAverage_waiting_time(average_waiting_time.substring(0,average_waiting_time.indexOf(".")+3));
					}else {
						waittime.setAverage_waiting_time(average_waiting_time);
					}
					
				}else{
					waittime.setAverage_waiting_time("0");
				}
				waittime.setTotal_count(map.get("total_count").get(i).toString());
				list.add(waittime);
			}
		}
	}

	public IWaitingTimeService getWaitingTimeService() {
		return waitingTimeService;
	}

	public void setWaitingTimeService(IWaitingTimeService waitingTimeService) {
		this.waitingTimeService = waitingTimeService;
	}

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
}
