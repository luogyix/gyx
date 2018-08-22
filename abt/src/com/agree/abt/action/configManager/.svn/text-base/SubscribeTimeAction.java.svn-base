package com.agree.abt.action.configManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.natp.NatpConfigBean;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;

/**
 * 预约时段配置
 * @company 赞同科技
 * @author XiWang
 * @date 2013-9-21
 */
public class SubscribeTimeAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	private Log logger = LogFactory.getLog(SubscribeTimeAction.class);	
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
	 * 查询预约时段
	 * @return
	 * @throws Exception
	 */
	public String querySubscribeTime() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		//查询交易接口
		
		cona.setBMSHeader("ibp.bds.p205.01", user);
		
		//cona.set("funcCode", "2"); //查询条件.待确认
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("loopNum").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.CUSTTYPE_I, map.get("custtype_i").get(i));
			hld.put(IDictABT.CUSTTYPE_E, map.get("custtype_e").get(i));
			hld.put(IDictABT.CUSTTYPE_NAME, map.get("custtype_name").get(i));
			hld.put(IDictABT.CUSTTYPE_LEVEL, map.get("custtype_level").get(i));
			hld.put(IDictABT.ISVIP, map.get("isvip").get(i));
			hld.put(IDictABT.TICKET_KEY, map.get("ticket_key").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 保存预约时段
	 * @return
	 * @throws Exception
	 */
	public String saveReservTime() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bds.p206.01", user);
		JSONArray array = JSONArray.fromObject(obj.get("listData"));
		int num = array.size();
		//[{"reserv_begin_time":"090000","reserv_end_time":"100000","reserv_max_num":2},{"reserv_begin_time":"110000","reserv_end_time":"120000","reserv_max_num":3},{"reserv_begin_time":"140000","reserv_end_time":"150000","reserv_max_num":4}]
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set("reservtimesize", String.valueOf(num));
		//里面套循环报文...
		StringBuffer final_reserv_begin_time = new StringBuffer("[");
		StringBuffer final_reserv_end_time = new StringBuffer("[");
		StringBuffer final_reserv_max_num = new StringBuffer("[");
		for(int i = 0 ; i<num ; i++){
			JSONObject map = array.getJSONObject(i);
			final_reserv_begin_time.append("'" + map.getString("reserv_begin_time") + "', ");
			final_reserv_end_time.append("'" + map.getString("reserv_end_time") + "', ");
			final_reserv_max_num.append("'" + map.getString("reserv_max_num") + "', ");
		}
		if(num!=0){
			cona.set("reserv_begin_time", final_reserv_begin_time.substring(0, final_reserv_begin_time.length()-2) + "]");
			cona.set("reserv_end_time", final_reserv_end_time.substring(0, final_reserv_end_time.length()-2) + "]");
			cona.set("reserv_max_num", final_reserv_max_num.substring(0, final_reserv_max_num.length()-2) + "]");
		}else{
			cona.set("reserv_begin_time", final_reserv_begin_time + "]");
			cona.set("reserv_end_time", final_reserv_end_time + "]");
			cona.set("reserv_max_num", final_reserv_max_num + "]");
		}
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn tet=new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 读取预约时段
	 * @throws Exception
	 */
	public String loadReservTime() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bds.p205.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		//[{"reserv_begin_time":"090000","reserv_end_time":"100000","reserv_max_num":2},
		//{"reserv_begin_time":"110000","reserv_end_time":"120000","reserv_max_num":3},
		//{"reserv_begin_time":"140000","reserv_end_time":"150000","reserv_max_num":4}]
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("reservtimesize").get(0);
		int num = Integer.parseInt(loopNum);
		//{reserv_max_num=[3, 1, 2], reserv_begin_time=[130000, 090000, 110000], H_ret_desc=[成功], reserv_end_time=[140000, 100000, 120000], H_ret_code=[000000], H_ret_status=[S], reservtimesize=[3]}
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			//判断数据
			hld.put("reserv_begin_time", ((String) map.get("reserv_begin_time").get(i)).substring(0,2));
			hld.put("reserv_max_num", map.get("reserv_max_num").get(i));
			list.add(hld);
		}
		
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 
	 * @Description: 添加预约时段
	 * @return String
	 * @throws Exception
	 */
	public String addSubscribeTime() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		NatpConfigBean configBean= (NatpConfigBean) ServletActionContext.getServletContext().getAttribute(ApplicationConstants.NATPCONFIGBEANV2);
		cona.setNatpConfig(configBean);
		
		//cona.setHeader(""/*交易号*/, user.getUnitid().toString(), user.getUsercode(),CHANNELNUM);
		
		cona.setBMSHeader("", user);
//		int binStart=Integer.parseInt(obj.getString("binStart"));
		
//		if(binEnd >dataEnd){
//			throw new AppException("卡Bin验证的结束位置不能大于数据的结束位置，请核对" );
//		}
		//cona.set(IDictABT.CUSTTYPE_I, obj.getString("custtype_i"));
		cona.set(IDictABT.CUSTTYPE_E, obj.getString("custtype_e"));
		cona.set(IDictABT.CUSTTYPE_NAME, obj.getString("custtype_name"));
		cona.set(IDictABT.CUSTTYPE_LEVEL, obj.getString("custtype_level"));
		cona.set(IDictABT.ISVIP, obj.getString("isvip"));
		cona.set(IDictABT.TICKET_KEY, obj.getString("ticket_key"));
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn tet=new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 修改预约时段
	 * @return String
	 * @throws Exception
	 */
	public String editSubscribeTime() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		NatpConfigBean configBean= (NatpConfigBean) ServletActionContext.getServletContext().getAttribute(ApplicationConstants.NATPCONFIGBEANV2);
		cona.setNatpConfig(configBean);
		//cona.setHeader("", user.getUnitid().toString(), user.getUsercode(),CHANNELNUM);
		
		cona.setBMSHeader("", user);
		cona.set(IDictABT.CUSTTYPE_I, obj.getString("custtype_i"));
		cona.set(IDictABT.CUSTTYPE_E, obj.getString("custtype_e"));
		cona.set(IDictABT.CUSTTYPE_NAME, obj.getString("custtype_name"));
		cona.set(IDictABT.CUSTTYPE_LEVEL, obj.getString("custtype_level"));
		cona.set(IDictABT.ISVIP, obj.getString("isvip"));
		cona.set(IDictABT.TICKET_KEY, obj.getString("ticket_key"));
		
//		if(Integer.parseInt(dataEnd)-Integer.parseInt(dataStart)<0){
//			throw new AppException("数据的结束位置不能小于数据的开始位置，请核对" );
//		}
		
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		if(map.get("H_ret_code") == null){
			throw new AppException("与后台服务通讯失败,没有返回状态码(H_ret_code)和状态信息");
		}
		String H_ret_code = (String) map.get("H_ret_code").get(0);
		if(!H_ret_code.equals(IDictABT.AFARESULTSTATUS_SUCC)){
			throw new AppException("错误码:"+H_ret_code+","+"错误信息:"+map.get("H_ret_desc").get(0));
		}
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 删除预约时段
	 * @return String
	 * @throws Exception
	 */
	public String delSubscribeTime() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		JSONObject jsonObj = JSONObject.fromObject(jsonArray.getString(0));
		Object obj[] = jsonObj.values().toArray();
		NatpConfigBean configBean= (NatpConfigBean) ServletActionContext.getServletContext().getAttribute(ApplicationConstants.NATPCONFIGBEAN);
		cona.setNatpConfig(configBean);
		
		//cona.setHeader("", user.getUnitid().toString(), user.getUsercode(),CHANNELNUM);
		
		cona.setBMSHeader("", user);
		cona.set(IDictABT.CUSTTYPE_I, obj[0].toString());
//		cona.set("funcCode", "4");
//		cona.set("cardName", obj[0].toString());
//		cona.set("cardType", obj[1].toString());
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		String H_ret_code = (String) map.get("H_ret_code").get(0);
		if(!H_ret_code.equals(IDictABT.AFARESULTSTATUS_SUCC)){
			throw new AppException("错误码:"+H_ret_code+","+"错误信息:"+map.get("H_ret_desc").get(0));
		}
	
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	public ABTComunicateNatp getCona() {
		return cona;
	}
	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
}
