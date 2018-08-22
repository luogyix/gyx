package com.agree.abt.action.subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.dataAnalysis.ReservFlow;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.DateUtil;
import com.agree.util.IDictABT;

/**
 * 预约信息类
 * @ClassName: SubscribeAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2013-12-26
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SubscribeAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	int totalSum = 0;
	private ABTComunicateNatp cona;
	private Log logger = LogFactory.getLog(SubscribeAction.class);	
	/**
	 * 跳转预约信息页面
	 * @return
	 * @throws Exception
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 查询预约流水-用于预约信息配置
	 * @throws Exception
	 */
	public String queryReservFlowPage() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		List list = new ArrayList();
		Page pageInfo = this.getPage(jsonObj,"");
		User user = super.getLogonUser(false);

		 
		
		cona.setBMSHeader("ibp.bds.p002.01", user);
		
		cona.set("reserv_branch",jsonObj.getString("branch"));
		if(!"".equals(jsonObj.getString("bs_id"))){
			cona.set("reserv_bs_id",jsonObj.getString("bs_id"));
		}
		if(!"".equals(jsonObj.getString("reserv_id"))){
			cona.set("reserv_id",jsonObj.getString("reserv_id").trim());
		}
		if(!"".equals(jsonObj.getString("startdate"))){
			cona.set("reserv_begin_date",jsonObj.getString("startdate").replace("-", ""));
		}
		if(!"".equals(jsonObj.getString("enddate"))){
			cona.set("reserv_end_date",jsonObj.getString("enddate").replace("-", ""));
		}
		if(!"".equals(jsonObj.getString("reserv_begin_time"))){
			cona.set("reserv_begin_time",jsonObj.getString("reserv_begin_time").replace(":", ""));
		}
		if(!"".equals(jsonObj.getString("reserv_end_time"))){
			cona.set("reserv_end_time",jsonObj.getString("reserv_end_time").replace(":", ""));
		}
		if(!"".equals(jsonObj.getString("custinfo_type"))){
			cona.set("custinfo_type",jsonObj.getString("custinfo_type"));
		}
		if(!"".equals(jsonObj.getString("custinfo_num"))){
			cona.set("custinfo_num",jsonObj.getString("custinfo_num"));
		}
		if(!"".equals(jsonObj.getString("phone_no"))){
			cona.set("phone_no",jsonObj.getString("phone_no"));
		}
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
		if(!"0".equals(map.get("reservinfosize").get(0))){
			pageInfo.setTotal(Integer.parseInt((String) map.get("rcdsumnum").get(0)));//总条数	
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
			String loopNum = (String) map.get("reservinfosize").get(0);
			int num = Integer.parseInt(loopNum);
			for (int i = 0; i < num; i++) {
				ReservFlow rf = new ReservFlow();
				//正常使用时将一下注释的打开
				String work_date = map.get("work_date").get(i).toString();
				if(work_date.trim().length()>0){
					work_date=work_date.substring(0,4)+"-"+work_date.substring(4,6)+"-"+work_date.substring(6,8);
				}
				rf.setWork_date(work_date);
				rf.setRequest_seq(map.get("request_seq").get(i).toString());
				rf.setRequest_seq(map.get("request_channel").get(i).toString());
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
				//rf.setCheck_reserv_value(map.get("check_reserv_value").get(i).toString());
				
				String reserv_modify_time = map.get("reserv_modify_time").get(i).toString();
				rf.setReserv_modify_time("".equals(reserv_modify_time)?"":DateUtil.formatTime(reserv_modify_time));
				String reserv_end_time = map.get("reserv_end_time").get(i).toString();
				rf.setReserv_end_time("".equals(reserv_end_time)?"":DateUtil.formatTime(reserv_end_time));
				String reserv_begin_time = map.get("reserv_begin_time").get(i).toString();
				rf.setReserv_begin_time("".equals(reserv_begin_time)?"":DateUtil.formatTime(reserv_begin_time));
				String reserv_record_time = map.get("reserv_record_time").get(i).toString();
				rf.setReserv_record_time("".equals(reserv_record_time)?"":DateUtil.formatTime(reserv_record_time));
				
				String reserv_record_date = map.get("reserv_record_date").get(i).toString();
				if(reserv_record_date.trim().length()>0){
					reserv_record_date=reserv_record_date.substring(0,4)+"-"+reserv_record_date.substring(4,6)+"-"+reserv_record_date.substring(6,8);
				}
				rf.setReserv_record_date(reserv_record_date);
				
				String reserv_begin_date = map.get("reserv_begin_date").get(i).toString();
				if(reserv_begin_date.trim().length()>0){
					reserv_begin_date=reserv_begin_date.substring(0,4)+"-"+reserv_begin_date.substring(4,6)+"-"+reserv_begin_date.substring(6,8);
				}
				rf.setReserv_begin_date(reserv_begin_date);
				
				String reserv_end_date = map.get("reserv_end_date").get(i).toString();
				if(reserv_end_date.trim().length()>0){
					reserv_end_date=reserv_end_date.substring(0,4)+"-"+reserv_end_date.substring(4,6)+"-"+reserv_end_date.substring(6,8);
				}
				rf.setReserv_end_date(reserv_end_date);
				
				list.add(rf);
			}
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);

		return AJAX_SUCCESS;
	}
	
	/**
	 * 预约信息修改
	 * @return
	 * @throws Exception
	 */
	public String editReservFlow() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bds.p003.01", user);
		cona.set("reserv_id",obj.getString("reserv_id"));
		cona.set("reserv_bs_id",obj.getString("reserv_bs_id"));
		cona.set("reserv_begin_date",obj.getString("reserv_begin_date").replace("-", ""));
		cona.set("reserv_end_date",obj.getString("reserv_end_date").replace("-", ""));
		cona.set("reserv_begin_time",obj.getString("reserv_begin_time").replace(":", ""));
		cona.set("reserv_end_time",obj.getString("reserv_end_time").replace(":", ""));
		cona.set("reserv_zone",obj.getString("reserv_zone"));
		cona.set("reserv_branch",obj.getString("reserv_branch"));
		cona.set("sms_customer","on".equals(obj.getString("sms_customer"))?"1":"0");
		cona.set("phone_no",obj.getString("phone_no"));
		
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	
	public String delReservFlow() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = JSONObject.fromObject(jsonArray.getString(i));
			Object obj[] = jsonObj.values().toArray();
			 
			//初始化NATP通讯
			cona.reInit();
			cona.setBMSHeader("ibp.bds.p004.01", user);
			cona.set("reserv_id",obj[0].toString());
			//判断afa的返回结果,是否成功
			Map<String,List<String>> map = cona.exchange();
			if(map.get("H_ret_code") == null){
				throw new AppException("与后台服务通讯失败,没有返回状态码(H_ret_code)和状态信息");
			}
			String H_ret_code = (String) map.get("H_ret_code").get(0);
			if(!H_ret_code.equals(IDictABT.AFARESULTSTATUS_SUCC)){
				throw new AppException("错误码:"+H_ret_code+","+"错误信息:"+map.get("H_ret_desc").get(0));
			}
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 跳转预约登记页面
	 * @return
	 * @throws Exception
	 */
	public String loadSubPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return "subscribe";
	}
	
	/**
	 * 预约登记提交
	 * @return
	 * @throws Exception
	 */
	public String submit() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		cona.setBMSHeader("ibp.bds.p001.01", user);
		String branch = obj.getString("branch");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		logger.info(branch+sdf.format(new Date()));
		cona.set("request_seq", branch+sdf.format(new Date()));
		cona.set("request_channel", "03");
		cona.set("reserv_bs_id", obj.getString("reserv_bs_id"));
		
		cona.set("reserv_begin_date", obj.getString("reserv_begin_date").substring(0,obj.getString("reserv_begin_date").indexOf("T")).replace("-", ""));
		cona.set("reserv_end_date", obj.getString("reserv_end_date").substring(0,obj.getString("reserv_end_date").indexOf("T")).replace("-", ""));
		cona.set("reserv_begin_time", obj.getString("reserv_begin_time").replace(":", ""));
		cona.set("reserv_end_time", obj.getString("reserv_end_time").replace(":", ""));
		
		cona.set("reserv_zone", obj.getString("reserv_zone"));//预约区域
		cona.set("reserv_branch", obj.getString("branch"));
		cona.set("custinfo_type", obj.getString("custinfo_type"));
		cona.set("custinfo_num", obj.getString("custinfo_num"));
		cona.set("custinfo_name", obj.getString("custinfo_name"));
		cona.set("account", obj.getString("account"));//卡号/账号
		cona.set("sms_customer", obj.getString("sms_customer"));
		cona.set("phone_no", obj.getString("phone_no"));
		
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		if(map.get("H_ret_code") == null){
			throw new AppException("与后台服务通讯失败,没有返回状态码(H_ret_code)和状态信息");
		}
		String H_ret_code = (String) map.get("H_ret_code").get(0);
		if(!H_ret_code.equals(IDictABT.AFARESULTSTATUS_SUCC)){
			throw new AppException("错误码:"+H_ret_code+","+"错误信息:"+map.get("H_ret_desc").get(0));
		}
		ServiceReturn ret=new ServiceReturn(ServiceReturn.SUCCESS,"");
		String reserv_id = (String) map.get("reserv_id").get(0);
		ret.put("reserv_id", reserv_id);
		
		JSONObject returnJson=super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
}
