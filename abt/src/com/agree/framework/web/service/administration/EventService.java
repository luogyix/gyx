/**   
 * @Title: EventService.java 
 * @Package com.agree.framework.web.service.administration 
 * @Description: TODO 
 * @company agree   
 * @author authorname   
 * @date 2013-3-20 下午05:56:04 
 * @version V1.0   
 */ 

package com.agree.framework.web.service.administration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import nl.justobjects.pushlet.core.Event;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import com.agree.framework.dao.core.IHibernateGenericDao;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.util.VelocityUtil;
import com.agree.framework.web.form.administration.TBsmsEventtask;
import com.agree.framework.web.form.administration.User;

/** 
 * @ClassName: EventService 
 * @Description: TODO
 * @company agree   
 * @author authorname   
 * @date 2013-3-20 下午05:56:04 
 *  
 */
@Service
@SuppressWarnings({"unchecked","rawtypes"})
public class EventService implements IEventService {
	@Resource(name="habernateDao")
	protected IHibernateGenericDao sqlDao_h;
	/* (non-Javadoc)
	 * <p>Title: run</p> 
	 * <p>Description: </p> 
	 * @param event
	 * @return
	 * @throws Exception 
	 * @see com.agree.framework.web.service.administration.IEventService#run(nl.justobjects.pushlet.core.Event) 
	 */
	public Event run() throws Exception {
		Event event =Event.createDataEvent("");
		List sessionList = (List)ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(ApplicationConstants.SESSIONID);
		if(sessionList == null){//如果没有登录的用户，则返回空主题的Event
			return event;
		}else{
			List usercodes = new ArrayList();//当前登录用户的list集合
			for(int i=0; i< sessionList.size(); i++){
				HttpSession  sess = (HttpSession)sessionList.get(i);
				try {
					User us = (User) sess.getAttribute(ApplicationConstants.LOGONUSER);
					usercodes.add(us.getUsercode());
				} catch (Exception e) {//如果session失效，则将session删除，另外重新开始循环处理
					sessionList.remove(sess);
					i=-1;
					usercodes=new ArrayList();
				}
			}
			if(usercodes.size()==0){
				return event;
			}
			String struser = "(";
			for(int i=0; i < usercodes.size(); i++ ){
				if (i == (usercodes.size()-1)){
					struser = struser+"'"+usercodes.get(i)+"'";
				}else{
					struser = struser+"'"+usercodes.get(i)+"',";
				}
			}
			struser = struser + ")";
			String taskhql = "select * from T_Bsms_Eventtask t where t.usercode in "+struser+" and t.enabled='0' order by t.channelserno asc";
			Connection conn=sqlDao_h.getConn();
			PreparedStatement ps = conn.prepareStatement(taskhql);  
			ResultSet rs = ps.executeQuery();  
			List<TBsmsEventtask> tasks=new ArrayList();
			try{
			 if(rs.next()){  
				 TBsmsEventtask task=new TBsmsEventtask();
				 task.setCenterstatus(rs.getString("centerstatus"));
				 task.setChannelcode(rs.getString("channelcode"));
				 task.setChanneldate(rs.getString("channeldate"));
				 task.setChannelserno(rs.getString("channelserno"));
				 task.setEnabled(rs.getString("enabled"));
				 task.setEndtime(rs.getDate("endtime"));
				 task.setListenevent(rs.getString("listenevent"));
				 task.setResultobject(rs.getString("resultobject"));
				 task.setStarttime(rs.getDate("starttime"));
				 task.setTasksql(rs.getString("tasksql"));
				 task.setTempath(rs.getString("tempath"));
				 task.setUsercode(rs.getString("usercode"));
				 tasks.add(task);
			 }
			}catch (Exception e) {
				throw e;
			}finally{
				 rs.close();
				 ps.close();
				// conn.close();
			}
			if(tasks.size()>0){//按照时间排列，选取最早一条
				TBsmsEventtask task=tasks.get(0);

				Map param=new HashMap();
				param.put("channelcode", task.getChannelcode().toString());
				param.put("channeldate", task.getChanneldate().toString());
				param.put("channelserno", task.getChannelserno().toString());
				task.getUsercode().toString();
				String objhql = "select new map( x.workdate as workdate, x.agentserialno as agentserialno, x.worktime as worktime," +
				"x.centerstatus as centerstatus, x.centerdealmsg as centerdealmsg, y.servicename as servicename, y.tempath as tempath ,z.pubcodename as pubcodename" +
				") from TBuetBusinessevent x,TBsmsTaskcfg y,TPcmPubcodevalue z" +
		 		" where   x.servicecode=y.servicecode and z.pubcodeitem ='PMI5268' and z.pubcodevalue=x.centerstatus and x.channelcode=:channelcode and x.channeldate=:channeldate and x.channelserno=:channelserno";
				//查询交易事件表，查看交易的中心状态是否需要推送
				Map objEvent= (Map)sqlDao_h.getRecord(objhql, param, false);

				if(objEvent!=null && 
						!objEvent.get("centerstatus").toString().equals(task.getCenterstatus())){//如果需要推送
					Map pmap=new HashMap();
					pmap.put("workdate", objEvent.get("workdate").toString());
					pmap.put("agentserialno", objEvent.get("agentserialno").toString());
					Object result=sqlDao_h.getRecord(task.getTasksql()+" where workdate=:workdate and agentserialno=:agentserialno", pmap, false);
					
					Date endDate=new Date();
					Map parameters=new HashMap();
					parameters.put("channelcode", task.getChannelcode().toString());
					parameters.put("channeldate", task.getChanneldate().toString());
					parameters.put("channelserno", task.getChannelserno().toString());
					parameters.put("endDate", endDate);
					if(result!=null){
						event = Event.createDataEvent(task.getListenevent());//为事件赋予主题
						
						//弹窗显示信息的源码-字符串
						Map prompt = new HashMap();
						objEvent.put("workdate", objEvent.get("workdate").toString().substring(0, 4)+"-"+objEvent.get("workdate").toString().substring(4, 6)+"-"+objEvent.get("workdate").toString().substring(6, 8));
						objEvent.put("worktime",objEvent.get("worktime").toString().substring(0, 2)+":"+objEvent.get("worktime").toString().substring(2, 4)+":"+objEvent.get("worktime").toString().substring(4, 6));
						objEvent.put("pubcodename",  objEvent.get("pubcodename").toString());
						prompt.put("objEvent", objEvent);
						prompt.put("result", result);
						String promptstr = VelocityUtil.merge(objEvent.get("tempath").toString(),prompt,"utf-8");
						JSONObject fromObject = JSONObject.fromObject(result);
						fromObject.put("usercode", task.getUsercode().toString());
						fromObject.put("promptstr", promptstr);
						fromObject.put("protitle", objEvent.get("servicename").toString());
						event.setField("result", fromObject.toString());//将要推送的交易数据转换成json推到前端
						parameters.put("status", objEvent.get("centerstatus").toString());
						//修改任务表状态
						sqlDao_h.excuteHql("update TBsmsEventtask set endtime=:endDate ,centerstatus=:status,enabled='1' where channelcode=:channelcode and channeldate=:channeldate and channelserno=:channelserno", parameters);
					}else{
						//在业务表中找不到该笔业务的话，直接修改任务状态为无效
						sqlDao_h.excuteHql("update TBsmsEventtask set endtime=:endDate ,centerstatus=null,enabled='1' where channelcode=:channelcode and channeldate=:channeldate and channelserno=:channelserno", parameters);
					}
				}
			}
			return event;
		}
	}

	
//	public static void main(String[] args){
//		List sessionList=new ArrayList();
//		sessionList.add("1");
//		sessionList.add("2");
//		sessionList.add("3");
//		sessionList.add("4");
//		List<String> usercodes = new ArrayList();//当前登录用户的list集合
//		for(int i=0; i< sessionList.size(); i++){
//			String  sess = (String)sessionList.get(i);
//			try {
//				System.out.println("i="+i);
//				System.out.println("sess="+sess);
//				usercodes.add(sess);
//				if("2".equals(sess)){
//					throw new Exception();
//				}
//				
//			} catch (Exception e) {
//				sessionList.remove(sess);
//				i=-1;
//				usercodes=new ArrayList();
//			}
//		}
//		for(String s:usercodes){
//			System.out.println(s);
//		}
//	}
}
