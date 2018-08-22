package com.agree.abt.action.configManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
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
 * 预约业务配置
 * @ClassName: SubscribeBusinessAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2013-9-21
 */
public class SubscribeBusinessAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	private Log logger = LogFactory.getLog(SubscribeBusinessAction.class);	
	
	/**
	 *加载主页面
	 * @return
	 * @throws Exception
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		
		logger.info("已注入:" + cona.getIp() + ":" + cona.getPort());//已注入:192.9.202.224:9012
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 读取预约业务
	 * @throws Exception
	 */
	public void loadReservBusiness() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bds.p203.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = "0";
		if(map.containsKey("reservbusinesssize")){
			loopNum = (String) map.get("reservbusinesssize").get(0);
		}else{
			throw new AppException("查询数据出错,传输失败");
		}
		
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			//判断数据
			hld.put("bs_id", map.get("bs_id").get(i));
			hld.put("bs_name_ch", map.get("bs_name_ch").get(i));
			hld.put("status", "1".equals(map.get("status").get(i))?"true":"");
			
			//对时间字段进行拼接
			hld.put("t9_10", "");
			hld.put("t10_11", "");
			hld.put("t11_12", "");
			hld.put("t12_13", "");
			hld.put("t13_14", "");
			hld.put("t14_15", "");
			hld.put("t15_16", "");
			hld.put("t16_17", "");
			String[] bs_reserv_times = ((String) map.get("bs_reserv_time").get(i)).split(",");
			for (int j = 0; j < bs_reserv_times.length; j++) {
				if (bs_reserv_times[j].length()>=2) {
					switch (Integer.parseInt(bs_reserv_times[j].substring(0,2))) {
					case 9:
						hld.remove("t9_10");
						hld.put("t9_10", "true");
						break;
					case 10:
						hld.remove("t10_11");
						hld.put("t10_11", "true");
						break;
					case 11:
						hld.remove("t11_12");
						hld.put("t11_12", "true");
						break;
					case 12:
						hld.remove("t12_13");
						hld.put("t12_13", "true");
						break;
					case 13:
						hld.remove("t13_14");
						hld.put("t13_14", "true");
						break;
					case 14:
						hld.remove("t14_15");
						hld.put("t14_15", "true");
						break;
					case 15:
						hld.remove("t15_16");
						hld.put("t15_16", "true");
						break;
					case 16:
						hld.remove("t16_17");
						hld.put("t16_17", "true");
						break;
					}
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
	 * 保存预约业务
	 * @return
	 * @throws Exception
	 */
	public String saveReservBusiness() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bds.p204.01", user);
		JSONArray array = JSONArray.fromObject(obj.get("listData"));
		//[{"bs_id":"geren_0","bs_name_ch":"个人0","status":true,"t9_10":"","t10_11":"","t11_12":true,"t12_13":"","t13_14":true,"t14_15":"","t15_16":"","t16_17":""},{"bs_id":"geren_1","bs_name_ch":"个人1","status":true,"t9_10":true,"t10_11":"","t11_12":true,"t12_13":true,"t13_14":"","t14_15":"","t15_16":"","t16_17":""},{"bs_id":"geren_2","bs_name_ch":"个人2","status":true,"t9_10":"","t10_11":true,"t11_12":true,"t12_13":"","t13_14":"","t14_15":"","t15_16":"","t16_17":""},{"bs_id":"geren_3","bs_name_ch":"个人3","status":true,"t9_10":"","t10_11":"","t11_12":true,"t12_13":"","t13_14":"","t14_15":"","t15_16":"","t16_17":""},{"bs_id":"geren_4","bs_name_ch":"个人4","status":true,"t9_10":"","t10_11":true,"t11_12":true,"t12_13":"","t13_14":true,"t14_15":"","t15_16":"","t16_17":""}]
		int num = array.size();
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set("reservbusinesssize", String.valueOf(num));
		//里面套循环报文...
		StringBuffer final_bs_id = new StringBuffer("[");
		StringBuffer final_status = new StringBuffer("[");
		StringBuffer final_bs_reserv_time = new StringBuffer("[");
		for(int i = 0 ; i<num ; i++){
			JSONObject map = array.getJSONObject(i);
			final_bs_id.append("'" + map.getString("bs_id") + "', ");
			String status = ("true".equals(map.getString("status"))?"1":"0");
			final_status.append("'" + status + "', ");
			//判断关键地方:那个一长串的业务时段-
			if("1".equals(status)){//启用此配置了,将数据拼接上
				StringBuffer bs_reserv_time = new StringBuffer();
				if("true".equals(map.getString("t9_10"))){
					bs_reserv_time.append("09:00-10:00,");
				}
				if("true".equals(map.getString("t10_11"))){
					bs_reserv_time.append("10:00-11:00,");
				}
				if("true".equals(map.getString("t11_12"))){
					bs_reserv_time.append("11:00-12:00,");
				}
				if("true".equals(map.getString("t12_13"))){
					bs_reserv_time.append("12:00-13:00,");
				}
				if("true".equals(map.getString("t13_14"))){
					bs_reserv_time.append("13:00-14:00,");
				}
				if("true".equals(map.getString("t14_15"))){
					bs_reserv_time.append("14:00-15:00,");
				}
				if("true".equals(map.getString("t15_16"))){
					bs_reserv_time.append("15:00-16:00,");
				}
				//收尾判断
				if("true".equals(map.getString("t16_17"))){
					bs_reserv_time.append("16:00-17:00");
					final_bs_reserv_time.append("'" + bs_reserv_time + "', ");
				}else{
					//未点选t16-17的收尾
					if(bs_reserv_time.length()>0){
						final_bs_reserv_time.append("'" + bs_reserv_time.substring(0, bs_reserv_time.length()-1) + "', ");
					}else{
						final_bs_reserv_time.append("'', ");
					}
				}
			}else{//这个业务未启用预约,时间段不管勾选与否都直接传空
				final_bs_reserv_time.append("'', ");
			}
		}
		if(num!=0){
			cona.set("bs_id", final_bs_id.substring(0, final_bs_id.length()-2) + "]");
			cona.set("status", final_status.substring(0, final_status.length()-2) + "]");
			cona.set("bs_reserv_time", final_bs_reserv_time.substring(0, final_bs_reserv_time.length()-2) + "]");
		}else{
			cona.set("bs_id", "[]");
			cona.set("status", "[]");
			cona.set("bs_reserv_time", "[]");
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
