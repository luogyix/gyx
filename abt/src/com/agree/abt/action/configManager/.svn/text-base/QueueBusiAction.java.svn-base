package com.agree.abt.action.configManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;

/** 
 * 业务可办理时段配置
 */ 
public class QueueBusiAction extends BaseAction{
	
	private static final long serialVersionUID = 7391088210117093555L;
	private Log logger = LogFactory.getLog(QueueBusiAction.class);
	private ABTComunicateNatp cona;
	
	public String loadPage() throws Exception {
		/*ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		HttpServletRequest req = ServletActionContext.getRequest();*/
		//req.setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		//String serv_list = req.getParameter("serv_list");
		//if(serv_list == null){
			return SUCCESS;
		//}else{
		//return "success2";
		//}
	}
	
	/**
	 * 读取配置
	 * @return
	 * @throws Exception
	 */
	public void loadConf() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b120_1.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("servicetimesize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BS_ID, map.get("bs_id").get(i));
			hld.put(IDictABT.BS_NAME_CH, map.get("bs_name_ch").get(i));
			String week = (String) map.get("week").get(i);
			for(int j=0;j<week.length();j++){
				String day = week.substring(j,j+1);
				int today = Integer.parseInt(day);
				switch (today) {
				case 1:
					hld.put("monday", "1");
					break;
				case 2:
					hld.put("tuesday", "1");
					break;
				case 3:
					hld.put("wednesday", "1");
					break;
				case 4:
					hld.put("thursday", "1");
					break;
				case 5:
					hld.put("friday", "1");
					break;
				case 6:
					hld.put("saturday", "1");
					break;
				case 7:
					hld.put("sunday", "1");
					break;
				}
			}
			String apply_holiday = "";
			if("0".equals(map.get("apply_holiday").get(i))){
				apply_holiday = "";
			}else if("1".equals(map.get("apply_holiday").get(i))){
				apply_holiday = "1";
			}else if("".equals(map.get("apply_holiday").get(i))){
				apply_holiday = "";
			}
			hld.put("apply_holiday", apply_holiday);

			String ss_time=(String) map.get("ss_time").get(i);
			if(ss_time.trim().length()>0){
				ss_time=ss_time.substring(0,2)+":"+ss_time.substring(2,4)+":"+ss_time.substring(4,6);
			}
			String se_time=(String) map.get("se_time").get(i);
			if(se_time.trim().length()>0){
				se_time=se_time.substring(0,2)+":"+se_time.substring(2,4)+":"+se_time.substring(4,6);
			}
			hld.put(IDictABT.SS_TIME, ss_time);
			hld.put(IDictABT.SE_TIME, se_time);
			hld.put(IDictABT.AT_TIME, map.get("at_time").get(i));
			hld.put(IDictABT.NOTE, map.get("note").get(i));
			if(map.containsKey("serv_time_list")){
				//格式化数据 添加":"
				String serv_time_list = (String) map.get("serv_time_list").get(i);
				if(serv_time_list.length()>0){
					String[] servTimeArray = serv_time_list.split(";");
					StringBuffer final_serv_time_list = new StringBuffer();
					for ( int j = 0; j < servTimeArray.length; j++) {
						//080000-090000
						//0123456789012
						final_serv_time_list.append(";" + servTimeArray[j].substring(0,2) + ":" + servTimeArray[j].substring(2,4) + 
								":" + servTimeArray[j].substring(4,9) + ":" + servTimeArray[j].substring(9,11) + 
								":" + servTimeArray[j].substring(11));
					}
					//records[i].data.serv_time_list = final_serv_time.substr(1);
					//records.get(i).set('serv_time_list',final_serv_time.substr(1));
					hld.put("serv_time_list", final_serv_time_list.toString().substring(1));
				}
			}
			list.add(hld);
		}
		
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	/**
	 * 保存配置
	 * @return
	 * @throws Exception
	 */
	public String saveConf() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		//{"0":{"bs_id":"Z001","bs_name_ch":"现金业务","monday":"1","tuesday":"1","wednesday":"1","thursday":"1","friday":"1","saturday":"1","sunday":"1","apply_holiday":"","ss_time":"09:00:00","se_time":"18:00:00","at_time":"50","note":""},"1":{"bs_id":"Z004","bs_name_ch":"存取款（对公）","monday":"1","tuesday":"1","wednesday":"1","thursday":"1","friday":"1","saturday":"1","sunday":"1","apply_holiday":"","ss_time":"09:00:00","se_time":"18:00:00","at_time":"50","note":""},"2":{"bs_id":"Z002","bs_name_ch":"外汇业务","monday":"1","tuesday":"1","wednesday":"1","thursday":"1","friday":"1","saturday":"1","sunday":"1","apply_holiday":"","ss_time":"09:00:00","se_time":"18:00:00","at_time":"50","note":""},"3":{"bs_id":"Z003","bs_name_ch":"存取款（个人）","monday":"1","tuesday":"1","wednesday":"1","thursday":"1","friday":"1","saturday":"1","sunday":"1","apply_holiday":"","ss_time":"09:00:00","se_time":"18:00:00","at_time":"50","note":""},"4":{"bs_id":"Z005","bs_name_ch":"个人开户","monday":"1","tuesday":"1","wednesday":"1","thursday":"1","friday":"1","saturday":"1","sunday":"1","apply_holiday":"","ss_time":"09:00:00","se_time":"18:00:00","at_time":"50","note":""},"5":{"bs_id":"Z006","bs_name_ch":"对公开户","monday":"1","tuesday":"1","wednesday":"1","thursday":"1","friday":"1","saturday":"1","sunday":"1","apply_holiday":"","ss_time":"09:00:00","se_time":"18:00:00","at_time":"50","note":""},"6":{"bs_id":"Z007","bs_name_ch":"转账汇款","monday":"1","tuesday":"1","wednesday":"1","thursday":"1","friday":"1","saturday":"1","sunday":"1","apply_holiday":"","ss_time":"09:00:00","se_time":"18:00:00","at_time":"50","note":""},"7":{"bs_id":"Z008","bs_name_ch":"代缴费","monday":"1","tuesday":"1","wednesday":"1","thursday":"1","friday":"1","saturday":"1","sunday":"1","apply_holiday":"","ss_time":"09:00:00","se_time":"18:00:00","at_time":"50","note":""},"8":{"bs_id":"Z009","bs_name_ch":"基金","monday":"1","tuesday":"1","wednesday":"1","thursday":"1","friday":"1","saturday":"1","sunday":"1","apply_holiday":"","ss_time":"09:00:00","se_time":"18:00:00","at_time":"50","note":""},"9":{"bs_id":"Z010","bs_name_ch":"话费充值","monday":"1","tuesday":"1","wednesday":"1","thursday":"1","friday":"1","saturday":"1","sunday":"1","apply_holiday":"","ss_time":"09:00:00","se_time":"18:00:00","at_time":"50","note":""},"10":{"bs_id":"Z011","bs_name_ch":"个人其他业务","monday":"1","tuesday":"1","wednesday":"1","thursday":"1","friday":"1","saturday":"1","sunday":"1","apply_holiday":"","ss_time":"09:00:00","se_time":"18:00:00","at_time":"50","note":""},"11":{"bs_id":"Z012","bs_name_ch":"对公其他业务","monday":"1","tuesday":"1","wednesday":"1","thursday":"1","friday":"1","saturday":"1","sunday":"1","apply_holiday":"","ss_time":"09:00:00","se_time":"18:00:00","at_time":"50","note":""},"12":{"bs_id":"Z013","bs_name_ch":"预约激活","monday":"1","tuesday":"1","wednesday":"1","thursday":"1","friday":"1","saturday":"1","sunday":"1","apply_holiday":"","ss_time":"09:00:00","se_time":"18:00:00","at_time":"50","note":"123"},"13":{"bs_id":"Z014","bs_name_ch":"中文","monday":"","tuesday":"","wednesday":"","thursday":"","friday":true,"saturday":true,"sunday":true,"apply_holiday":"","ss_time":"","se_time":"","at_time":"","note":""}}
		
		cona.setBMSHeader("ibp.bms.b120_2.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		int num = obj.size();
		cona.set("servicetimesize", String.valueOf(num));
		cona.set("servicetime", "servicetime");
		//里面套循环报文...
		//拼装week字段
		StringBuffer final_bs_id = new StringBuffer("[");
		StringBuffer final_bs_name_ch = new StringBuffer("[");
		StringBuffer final_week = new StringBuffer("[");
		StringBuffer final_apply_holiday = new StringBuffer("[");
		StringBuffer final_ss_time = new StringBuffer("[");
		StringBuffer final_se_time = new StringBuffer("[");
		StringBuffer final_at_time = new StringBuffer("[");
		StringBuffer final_note = new StringBuffer("[");
		StringBuffer final_serv_time_list = new StringBuffer("[");
		for(int i = 0 ; i<num ; i++){
			JSONObject map = JSONObject.fromObject(obj.get(String.valueOf(i)));
			final_bs_id.append("'" + map.getString("bs_id") + "', ");
			final_bs_name_ch.append("'" + map.getString("bs_name_ch") + "', ");
			//{"bs_id":"Z001","bs_name_ch":"现金业务","monday":"1","tuesday":"1","wednesday":"1","thursday":"1","friday":"1","saturday":"1","sunday":"1","apply_holiday":"","ss_time":"09:00:00","se_time":"18:00:00","at_time":"50","note":""}
			
			//conaV2.set(IDictABT.BS_ID, map.getString("bs_id"));
			//conaV2.set(IDictABT.BS_NAME_CH, map.getString("bs_name_ch"));
			
			StringBuffer sb = new StringBuffer();
			logger.info(map.getString("monday"));
			if("1".equals(map.getString("monday"))||"true".equals(map.getString("monday"))){
				sb.append("1");
			}
			if("1".equals(map.getString("tuesday"))||"true".equals(map.getString("tuesday"))){
				sb.append("2");
			}
			if("1".equals(map.getString("wednesday"))||"true".equals(map.getString("wednesday"))){
				sb.append("3");
			}
			if("1".equals(map.getString("thursday"))||"true".equals(map.getString("thursday"))){
				sb.append("4");
			}
			if("1".equals(map.getString("friday"))||"true".equals(map.getString("friday"))){
				sb.append("5");
			}
			if("1".equals(map.getString("saturday"))||"true".equals(map.getString("saturday"))){
				sb.append("6");
			}
			if("1".equals(map.getString("sunday"))||"true".equals(map.getString("sunday"))){
				sb.append("7");
			}
			String week = sb.toString();
			//conaV2.set(IDictABT.WEEK, week);
			final_week.append("'" + week + "', ");
			String apply_holiday = map.getString("apply_holiday");
			if("".equals(apply_holiday)){
				apply_holiday = "0";
			}else if("false".equals(apply_holiday)){
				apply_holiday = "0";
			}else {
				apply_holiday = "1";
			}
			final_apply_holiday.append("'" + apply_holiday + "', ");
			//conaV2.set("apply_holiday", map.getString("apply_holiday"));
			String ss_time=map.getString("ss_time");
			//校验服务时间始终
			ss_time = ss_time.replace(":", "");
			final_ss_time.append("'" + ss_time + "', ");
			String se_time=map.getString("se_time");
			//conaV2.set(IDictABT.SS_TIME, ss_time);
			se_time = se_time.replace(":", "");
			final_se_time.append("'" + se_time + "', ");
			if((!"".equals(se_time))&&(!"".equals(ss_time))){
				if(Integer.parseInt(ss_time) > Integer.parseInt(se_time)){
					throw new AppException(map.getString("bs_name_ch") + "的服务时间(终)比服务时间(始)小，请核对后提交");
				}else if(Integer.parseInt(ss_time) == Integer.parseInt(se_time)){
					throw new AppException(map.getString("bs_name_ch") + "的服务时间(终)不可与服务时间(始)相同，请核对后提交");
				}
			}
			//conaV2.set(IDictABT.SE_TIME, se_time);
			//conaV2.set(IDictABT.AT_TIME, map.getString("at_time"));
			final_at_time.append("'" + map.getString("at_time") + "', ");
			final_note.append("'" + map.getString("note") + "', ");
			if(map.containsKey("serv_time_list")){
				final_serv_time_list.append("'" + map.getString("serv_time_list").replace(":", "") + "', ");
			}
			//conaV2.set(IDictABT.NOTE, map.getString("note"));
		}
		if(num!=0){
			cona.set(IDictABT.BS_ID, final_bs_id.substring(0, final_bs_id.length()-2) + "]");
			cona.set(IDictABT.BS_NAME_CH, final_bs_name_ch.substring(0, final_bs_name_ch.length()-2) + "]");
			cona.set(IDictABT.WEEK, final_week.substring(0, final_week.length()-2) + "]");
			cona.set(IDictABT.APPLY_HOLIDAY, final_apply_holiday.substring(0, final_apply_holiday.length()-2) + "]");
			cona.set(IDictABT.SS_TIME, final_ss_time.substring(0, final_ss_time.length()-2) + "]");
			cona.set(IDictABT.SE_TIME, final_se_time.substring(0, final_se_time.length()-2) + "]");
			cona.set(IDictABT.AT_TIME, final_at_time.substring(0, final_at_time.length()-2) + "]");
			cona.set("notex", final_note.substring(0, final_note.length()-2) + "]");
			if(final_serv_time_list.length()>2){
				cona.set("serv_time_list", final_serv_time_list.substring(0, final_serv_time_list.length()-2) + "]");
			}else{
				cona.set("serv_time_list", "[]");
			}
		}else{
			cona.set(IDictABT.BS_ID, "[]");
			cona.set(IDictABT.BS_NAME_CH, "[]");
			cona.set(IDictABT.WEEK, "[]");
			cona.set(IDictABT.APPLY_HOLIDAY, "[]");
			cona.set(IDictABT.SS_TIME, "[]");
			cona.set(IDictABT.SE_TIME, "[]");
			cona.set(IDictABT.AT_TIME, "[]");
			cona.set("notex", "[]");
			cona.set("serv_time_list", "[]");
		}

		//判断afa的返回结果,是否成功
		cona.exchange();
		ServiceReturn tet=new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
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
