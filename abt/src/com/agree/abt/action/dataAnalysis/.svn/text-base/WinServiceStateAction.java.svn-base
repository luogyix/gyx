package com.agree.abt.action.dataAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.EventPullSource;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.ContextLoader;

import com.agree.abt.model.dataAnalysis.BtQmWin;
import com.agree.framework.communication.natp.IParent;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

/** 
 * @ClassName: WinServiceStateAction 
 * @Description: 窗口服务状态监控
 * @company agree   
 * @author lilei
 * @date 2013-9-20 下午05:22:13 
 *  
 */ 
@SuppressWarnings({ "unchecked", "rawtypes" })
public class WinServiceStateAction extends BaseAction implements IParent{
	
	private static final long serialVersionUID = 1108872513210308463L;
	private ABTComunicateNatp cona;
	
	/** 
	 * @Title: loadPage 
	 * @Description: 
	 * @param @return
	 * @param @throws Exception    参数 
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
	
	public String queryBusiness() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		User user = (User) request.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		//String branch = jsonObj.getString("branch");
		//String branch = super.getLogonUser(false).getUnitid();
		 
		
		cona.setBMSHeader("ibp.bms.b204.01", super.getLogonUser(false));
		if(jsonObj.getString("branch").equals("")||jsonObj.getString("branch") == null){
			cona.set("branch", user.getUnitid());
		}else{
			cona.set("branch", jsonObj.getString("branch"));
		}
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<BtQmWin> list = new ArrayList<BtQmWin>();
		for(int i=0;i<Integer.parseInt(map.get("winsize").get(0));i++){
			BtQmWin win = new BtQmWin();
			win.setBranch(map.get("branch").get(i));
			win.setWin_num(map.get("win_num").get(i));
			win.setQm_num(map.get("qm_num").get(i));
			win.setIp(map.get("ip").get(i));
			win.setWin_oid(map.get("win_oid").get(i));
			win.setCall_rule(map.get("call_rule").get(i));
			win.setStatus(map.get("status").get(i));
			win.setScreen_id(map.get("screen_id").get(i));
			win.setSoftcall_id(map.get("softcall_id").get(i));
			win.setAssess_id(map.get("assess_id").get(i));
			win.setWin_service_status(map.get("win_service_status").get(i));
			win.setTeller_num(map.get("teller_num").get(i));
			win.setTeller_name(map.get("teller_name").get(i));
			list.add(win);
		}
		//更新缓存中的数据
		ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		List<Map> listMap = (List<Map>)context.getAttribute(ApplicationConstants.NATP_CACHE);
		if(listMap==null){
			listMap = new ArrayList<Map>();
		}
		String key = "msg007";//natp报文的msgtype
		for(int i = 0;i<listMap.size();i++){
			if(listMap.get(i).get("key").equals(key)){
				listMap.get(i).put("value", list);
			}
		}
		context.setAttribute(ApplicationConstants.NATP_CACHE, listMap);
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, list);
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
//	/** 
//	 * @Title: runPushlet 
//	 * @Description: 启动推送
//	 * @param @throws Exception    参数 
//	 * @return void    返回类型 
//	 * @throws 
//	 */ 
//	public String runPushlet() throws Exception{
//
//		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
//		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
//		super.setActionresult(returnJson.toString());
//		return AJAX_SUCCESS;
//	}
//	/** 
//	 * @Title: stopPushlet 
//	 * @Description: 停止推送
//	 * @param @throws Exception    参数 
//	 * @return void    返回类型 
//	 * @throws 
//	 */ 
//	public String stopPushlet() throws Exception{
//
//		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
//		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
//		super.setActionresult(returnJson.toString());
//		return AJAX_SUCCESS;
//	}
	/** 
	 * @ClassName: WinServiceStatePullSources 
	 * @Description: 
	 * @company agree   
	 * @author lilei
	 * @date 2013-9-20 下午05:24:15 
	 *  
	 */ 
	public static class WinServiceStatePullSources extends EventPullSource{

		/* (non-Javadoc)
		 * <p>Title: getSleepTime</p> 
		 * <p>Description: 设定休眠时间(单位：毫秒)</p> 
		 * @return 
		 * @see nl.justobjects.pushlet.core.EventPullSource#getSleepTime() 
		 */ 
		@Override
		protected long getSleepTime() {
			return 6000;
		}

		/* (non-Javadoc)
		 * <p>Title: pullEvent</p> 
		 * <p>Description: 创建事件，业务逻辑部分在这里被定时调用</p> 
		 * @return 
		 * @see nl.justobjects.pushlet.core.EventPullSource#pullEvent() 
		 */ 
		@Override
		protected Event pullEvent() {
			//logger.info("------进入窗口服务状态推送功能... ... !------");
			/*
			 * 按要求添加配置，判断是否用户登陆来决定是否推送
			 */
			Event event =Event.createDataEvent("/winServiceState/Monitor");
			ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			/*
			 * 获取缓存中NATP报文相关的信息
			 */
			//ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			List<Map> listMap = (List<Map>)context.getAttribute(ApplicationConstants.NATP_CACHE);
			if(listMap==null){
				listMap = new ArrayList<Map>();
			}
			String key = "msg007";//natp报文的msgtype
			List<BtQmWin> list = new ArrayList<BtQmWin>();//存储缓存中的队列列表
			for(int i = 0;i<listMap.size();i++){
				if(listMap.get(i).get("key").equals(key)){
					list = (List<BtQmWin>) listMap.get(i).get("value");
				}
			}
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("list", list);
			event.setField("result",jsonobj.toString());
			return event;
		}
		
	}
	public void reload(Map recvMap) {
		ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		/*
		 * 获取缓存中NATP报文相关的信息
		 */
		//ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		List<Map> listMap = (List<Map>)context.getAttribute(ApplicationConstants.NATP_CACHE);
		if(listMap==null){
			listMap = new ArrayList<Map>();
		}
		String key = "msg007";//natp报文的msgtype
		List<BtQmWin> list = new ArrayList<BtQmWin>();//存储缓存中的队列列表
		for(int i = 0;i<listMap.size();i++){
			if(listMap.get(i).get("key").equals(key)){
				list = (List<BtQmWin>) listMap.get(i).get("value");
			}
		}
		for(int i=0;i<list.size();i++){
			if(list.get(i).getBranch().equals(recvMap.get("branch")) &&
					list.get(i).getWin_num().equals(recvMap.get("win_num")) &&
					list.get(i).getQm_num().equals(recvMap.get("qm_num"))){
				
				list.get(i).setStatus(recvMap.get("status").toString());
				list.get(i).setWin_service_status(recvMap.get("win_service_status").toString());
			}
		}
		
		//更新缓存中的数据
//		boolean submitListMap = true;
		for(int i=0;i<listMap.size();i++){
			if(listMap.get(i).get("key").equals(key)){
				listMap.get(i).put("value", list);
//				submitListMap = false;
			}
		}
//		if(submitListMap){
//			Map m = new HashMap();
//			m.put("key", key);
//			m.put("value", list);
//			listMap.add(m);
//		}
		context.setAttribute(ApplicationConstants.NATP_CACHE, listMap);
		
		
	}

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}

}
