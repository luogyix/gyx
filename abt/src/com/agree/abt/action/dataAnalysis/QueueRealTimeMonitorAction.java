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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import com.agree.abt.model.dataAnalysis.BtQmQueue;
import com.agree.abt.model.dataAnalysis.BtQmQueuetype;
import com.agree.abt.service.dataAnalysis.IQmQueueService;
import com.agree.framework.communication.natp.IParent;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.Unit;
import com.agree.framework.web.form.administration.User;
import com.agree.util.DateUtil;

/**
 * @ClassName: QueueRealTimeMonitorAction
 * @Description: 队列实时监控
 * @company agree
 * @author lilei
 * @date 2013-9-22 上午11:41:13
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class QueueRealTimeMonitorAction extends BaseAction implements IParent {
	
	private static final long serialVersionUID = 7768795029786695816L;

	private static final Logger logger = LoggerFactory.getLogger(QueueRealTimeMonitorAction.class);

	private ABTComunicateNatp cona;
	private IQmQueueService service;

	/**
	 * @Title: loadPage
	 * @Description: 进入页面的时候，加载机构树信息
	 * @param @return
	 * @param @throws Exception 参数
	 * @return String 返回类型
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
	 * @Title: query
	 * @Description: 页面点击查询按钮是查询的方法
	 * @param @return
	 * @param @throws Exception 参数
	 * @return String 返回类型
	 * @throws
	 */
	public String query() throws Exception {
		String branch = ServletActionContext.getRequest().getParameter("branch");
		/*
		 * 发起查询“排队系统流水信息主表-实时”表信息，条件是网点号 branch
		 */
		List<BtQmQueuetype> resultList = new ArrayList<BtQmQueuetype>();
		Unit unit = super.getUnitByID(branch);
		unit.getBank_level();
		List<Unit> list = unit.getChildNodes();
		String haschild = "0";
		if (list != null && list.size() > 0) {
			resultList = service.getChildDetails(branch);
			haschild = "1";
		} else {
			resultList = queryQueue(branch);
		}

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, resultList);
		sRet.put(ServiceReturn.FIELD2, haschild);
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	/**
	 * 手工弃号
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delNum() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		// {"queue_num":"A001","branch":"756029","work_date":"20131210"}
		JSONObject jsonObj = JSONObject.fromObject(inputJsonStr);
		String queue_num = jsonObj.getString("queue_num");
		String branch = jsonObj.getString("branch");
		String work_date = jsonObj.getString("work_date");
		 
		
		cona.setBMSHeader("ibp.bms.b203_3.01", user);
		cona.set("queue_num", queue_num);
		cona.set("branch", branch);
		cona.set("work_date", work_date);
		//判断afa的返回结果,是否成功
		cona.exchange();
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}

	/**
	 * @Title: queryQueue
	 * @Description: 对应的页面查询调用的方法
	 * @param @param branch
	 * @param @return
	 * @param @throws Exception 参数
	 * @return List<BtQmQueuetype> 返回类型
	 * @throws
	 */
	public List<BtQmQueuetype> queryQueue(String branch) throws Exception {
		// 临时注释，AFA链条测试的时候在开开
		 
		
		cona.setBMSHeader("ibp.bms.b203.01", super.getLogonUser(false));
		cona.set("branch", branch);
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();

		List<BtQmQueue> btqmqueuelist = new ArrayList<BtQmQueue>();// 查询返回的数据
		for (int i = 0; i < Integer.parseInt(map.get("queuesize").get(0)); i++) {
			BtQmQueue btq = new BtQmQueue();
			// 临时注释，AFA链条测试的时候在开开
			btq.setWork_date(map.get("work_date").get(i));
			btq.setQueue_num(map.get("queue_num").get(i));
			btq.setBranch(branch);
			btq.setBranch_name(map.get("branch_name").get(i));
			btq.setTransfer_num(map.get("transfer_num").get(i));
			btq.setQm_num(map.get("qm_num").get(i));
			btq.setQm_ip(map.get("qm_ip").get(i));
			btq.setQueue_seq(map.get("queue_seq").get(i));
			btq.setSoftcall_teller(map.get("softcall_teller").get(i));
			btq.setSoftcall_teller_name(map.get("softcall_teller_name").get(i));
			btq.setSoftcall_seq(map.get("softcall_seq").get(i));
			btq.setBs_id(map.get("bs_id").get(i));
			btq.setBs_name_ch(map.get("bs_name_ch").get(i));
			btq.setQueuetype_id(map.get("queuetype_id").get(i));
			btq.setQueuetype_name(map.get("queuetype_name").get(i));
			btq.setCusttype_i(map.get("custtype_i").get(i));
			btq.setCusttype_name(map.get("custtype_name").get(i));
			btq.setNode_id(map.get("node_id").get(i));
			btq.setWin_num(map.get("win_num").get(i));
			btq.setWindow_ip(map.get("window_ip").get(i));
			btq.setQueuenum_type(map.get("queuenum_type").get(i));
			btq.setCustinfo_type(map.get("custinfo_type").get(i));
			btq.setCustinfo_num(map.get("custinfo_num").get(i));
			btq.setQueue_status(map.get("queue_status").get(i));
			btq.setEn_queue_time(map.get("en_queue_time").get(i));
			btq.setDe_queue_time(map.get("de_queue_time").get(i));
			btq.setStart_serv_time(map.get("start_serv_time").get(i));
			btq.setFinish_serv_time(map.get("finish_serv_time").get(i));
			btq.setAssess_status(map.get("assess_status").get(i));
			btq.setReserv_flag(map.get("reserv_flag").get(i));
			btq.setReserv_id(map.get("reserv_id").get(i));
			btq.setRemaind_flag(map.get("remaind_flag").get(i));
			btq.setRemaind_phone(map.get("remaind_phone").get(i));
			btq.setNoti_waitnum(map.get("noti_waitnum").get(i));
			btq.setNoti_setnum(map.get("noti_setnum").get(i));
			btq.setIsnotify(map.get("isnotify").get(i));
			btq.setIsbefore(map.get("isbefore").get(i));
			btq.setBeforestatus(map.get("beforestatus").get(i));

			btqmqueuelist.add(btq);
		}

		List<BtQmQueuetype> newlist = new ArrayList<BtQmQueuetype>();// 查询返回的队列列表
		for (int i = 0; i < Integer.parseInt(map.get("queuetypesize").get(0)); i++) {
			BtQmQueuetype bttype = new BtQmQueuetype();
			bttype.setQueuetype_id(map.get("queuetype_id_x").get(i));
			bttype.setBranch(branch);
			bttype.setQueuetype_name(map.get("queuetype_name_x").get(i));
			bttype.setQueuetype_prefix(map.get("queuetype_prefix").get(i));
			bttype.setQueuetype_prefix_num(map.get("queuetype_prefix_num").get(i));
			bttype.setQueuetype_level(map.get("queuetype_level").get(i));
			bttype.setStatus(map.get("status").get(i));
			newlist.add(bttype);
		}
/*
		ServletContext context = ContextLoader
				.getCurrentWebApplicationContext().getServletContext();
		List<Map> listMap = (List<Map>) context
				.getAttribute(ApplicationConstants.NATP_CACHE);
		if (listMap == null) {
			listMap = new ArrayList<Map>();
		}
		String key = "msg001";// natp报文的msgtype
*/		List<BtQmQueuetype> list = new ArrayList<BtQmQueuetype>();// 存储缓存中的队列列表
/*		for (int i = 0; i < listMap.size(); i++) {
			if (listMap.get(i).get("key").equals(key)) {
				list = (List<BtQmQueuetype>) listMap.get(i).get("value");
			}
		}
		// 删除缓存队列中指定网点号的队列信息
		for (int i = 0; i < list.size(); i++) {
			list.remove(i);
			i = -1;
		}*/
		// 将指定网点号的队列信息添加到缓存队列中
		list.addAll(newlist);
		// 将查询返回的队列流水信息按要求插入到缓存队列表中
		for (int i = 0; i < list.size(); i++) {
			List<BtQmQueue> ml = new ArrayList<BtQmQueue>();
			for(int j = 0; j<btqmqueuelist.size(); j++){
				if( list.get(i).getQueuetype_id().equals(btqmqueuelist.get(j).getQueuetype_id()) &&
						list.get(i).getBranch().equals(btqmqueuelist.get(j).getBranch())){
					ml.add(btqmqueuelist.get(j));
				}
			}
			list.get(i).setBtqmqueue(ml);
		}
		// 计算人数和等待时间
		for (int i = 0; i < list.size(); i++) {
			// 临时注释，等机构信功能完善后放开注释
			if (list.get(i).getBranch().equals(branch)) {
				List<BtQmQueue> t = list.get(i).getBtqmqueue();
				long times = 0;//定义等待时间，单位：秒。初始化为0秒
				for(int j = 0; j < t.size(); j++){
					long  times1=DateUtil.diffTime(t.get(j).getEn_queue_time());
					if(times1>0){
							times = times + times1;
					}
				}
				if (t.size() == 0) {
					String averagewaittime = String.valueOf(times);// 等待时间
					list.get(i).setAveragewaittime(averagewaittime);
					list.get(i).setQueuedetails("0");
				} else {
					String averagewaittime = String.valueOf(times / t.size());// 等待时间
					list.get(i).setAveragewaittime(averagewaittime);
					list.get(i).setQueuedetails("1");// 详情设置
				}
				list.get(i).setBtqmsum(String.valueOf(t.size()));// 等待人数
				// logger.info("队列实时监控 "+list.get(i).getQueuetype_name()+":平均等待时间："+list.get(i).getAveragewaittime());
				// logger.info("队列实时监控 "+list.get(i).getQueuetype_name()+":平均等待人数："+list.get(i).getBtqmsum());
			}
		}
/*		boolean submitListMap = true;
		for (int i = 0; i < listMap.size(); i++) {
			if (listMap.get(i).get("key").equals(key)) {
				listMap.get(i).put("value", list);
				submitListMap = false;
			}
		}
		if (submitListMap) {
			Map m = new HashMap();
			m.put("key", key);
			m.put("value", list);
			listMap.add(m);
		}
		context.setAttribute(ApplicationConstants.NATP_CACHE, listMap);*/
		List<BtQmQueuetype> resultList = new ArrayList<BtQmQueuetype>();
		for (int i = 0; i < list.size(); i++) {
			// 临时注释，等机构信功能完善后放开注释
			if (list.get(i).getBranch().equals(branch)) {
				resultList.add(list.get(i));
			}
		}
		return resultList;
	}

	/**
	 * @Title: details
	 * @Description: 查看某一队列详细信息
	 * @param @return
	 * @param @throws Exception 参数
	 * @return String 返回类型
	 * @throws
	 */
	public String details() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		String branch = jsonObj.getString("branch");// 网点ID
		String queuetype_id = jsonObj.getString("queuetype_id");// 队列ID
		/*
		 * 根据网点id和队列id查询队列详情 返回队列详情
		 */
		 
		
		cona.setBMSHeader("ibp.bms.b203.01", super.getLogonUser(false));
		//conaV2.set("branch", branch);
		cona.set("branch", branch);
		cona.set("queuetype_id", queuetype_id);
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();

		// HashMap<String, List<String>> map = new HashMap<String,
		// List<String>>();
		// List x = new ArrayList();
		// x.add("15");
		// map.put("BtQmQueueSum", x);

		List<BtQmQueue> list = new ArrayList<BtQmQueue>();// 队列详情信息
		for (int i = 0; i < Integer.parseInt(map.get("queuesize").get(0)); i++) {
			BtQmQueue btq = new BtQmQueue();
			btq.setWork_date(map.get("work_date").get(i));
			btq.setQueue_num(map.get("queue_num").get(i));
			btq.setBranch(branch);
			btq.setBranch_name(map.get("branch_name").get(i));
			btq.setTransfer_num(map.get("transfer_num").get(i));
			btq.setQm_num(map.get("qm_num").get(i));
			btq.setQm_ip(map.get("qm_ip").get(i));
			btq.setQueue_seq(map.get("queue_seq").get(i));
			btq.setSoftcall_teller(map.get("softcall_teller").get(i));
			btq.setSoftcall_teller_name(map.get("softcall_teller_name").get(i));
			btq.setSoftcall_seq(map.get("softcall_seq").get(i));
			btq.setBs_id(map.get("bs_id").get(i));
			btq.setBs_name_ch(map.get("bs_name_ch").get(i));
			btq.setQueuetype_id(map.get("queuetype_id").get(i));
			btq.setQueuetype_name(map.get("queuetype_name").get(i));
			btq.setCusttype_i(map.get("custtype_i").get(i));
			btq.setCusttype_name(map.get("custtype_name").get(i));
			btq.setNode_id(map.get("node_id").get(i));
			btq.setWin_num(map.get("win_num").get(i));
			btq.setWindow_ip(map.get("window_ip").get(i));
			btq.setQueuenum_type(map.get("queuenum_type").get(i));
			btq.setCustinfo_type(map.get("custinfo_type").get(i));
			btq.setCustinfo_num(map.get("custinfo_num").get(i));
			btq.setQueue_status(map.get("queue_status").get(i));
			String en_queue_time = map.get("en_queue_time").get(i);
			btq.setEn_queue_time(en_queue_time);
			// 获取时间差,传至前台
			long time = 0;
			if ("".equals(map.get("de_queue_time").get(i))) {
				time = DateUtil.diffTime(en_queue_time);
			}else{
				time = DateUtil.diffTime2(map.get("en_queue_time").get(i), map.get("de_queue_time").get(i));
			}
			btq.setTime(time / 60 == 0 ? 1 : time / 60);

			btq.setDe_queue_time(map.get("de_queue_time").get(i));
			btq.setStart_serv_time(map.get("start_serv_time").get(i));
			btq.setFinish_serv_time(map.get("finish_serv_time").get(i));
			btq.setAssess_status(map.get("assess_status").get(i));
			btq.setReserv_flag(map.get("reserv_flag").get(i));
			btq.setReserv_id(map.get("reserv_id").get(i));
			btq.setRemaind_flag(map.get("remaind_flag").get(i));
			btq.setRemaind_phone(map.get("remaind_phone").get(i));
			btq.setNoti_waitnum(map.get("noti_waitnum").get(i));
			btq.setNoti_setnum(map.get("noti_setnum").get(i));
			btq.setIsnotify(map.get("isnotify").get(i));
			btq.setIsbefore(map.get("isbefore").get(i));
			btq.setBeforestatus(map.get("beforestatus").get(i));

			// if(queuetype_id.equals("123")){
			// btq.setBtQmQueue123();
			// }else{
			// btq.setBtQmQueue456();
			// }

			list.add(btq);
		}
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, list);
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * @Title: adjust
	 * @Description: 队列调整
	 * @param @return
	 * @param @throws Exception 参数
	 * @return String 返回类型
	 * @throws
	 */
	public String adjust() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		String branch = jsonObj.getString("branch");
		 
		
		cona.setBMSHeader("ibp.bms.b203_1.01", super.getLogonUser(false));
		cona.set("branch", branch);
		cona.set("queue_num", jsonObj.getString("queue_num"));
		cona.set("queuetype_id", jsonObj.getString("new_queuetype_id"));
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		List<BtQmQueuetype> resultList = queryQueue(branch);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, resultList);
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * @ClassName: QueueRealTimePullSources
	 * @company agree
	 * @author lilei
	 * @date 2013-9-22 上午11:43:17
	 * 
	 */
	public static class QueueRealTimePullSources extends EventPullSource {

		/*
		 * (non-Javadoc) <p>Title: getSleepTime</p> <p>Description: </p>
		 * 
		 * @return
		 * 
		 * @see nl.justobjects.pushlet.core.EventPullSource#getSleepTime()
		 */
		@Override
		protected long getSleepTime() {
			return 10000;
		}

		/*
		 * (non-Javadoc) <p>Title: pullEvent</p> <p>Description: </p>
		 * 
		 * @return
		 * 
		 * @see nl.justobjects.pushlet.core.EventPullSource#pullEvent()
		 */
		@Override
		protected Event pullEvent() {
			// logger.info("------进入队列实时监控推送功能... ... !------");
			Event event = Event.createDataEvent("/queue/RealTime/info");
			/*
			 * 获取缓存中NATP报文相关的信息
			 */
			ServletContext context = ContextLoader
					.getCurrentWebApplicationContext().getServletContext();
			String key = "msg001";// natp报文的msgtype
			List<Map> listMap = (List<Map>) context
					.getAttribute(ApplicationConstants.NATP_CACHE);
			List<BtQmQueuetype> list = new ArrayList<BtQmQueuetype>();
			if (listMap == null) {
				listMap = new ArrayList<Map>();
			}
			if (listMap != null) {
				for (int i = 0; i < listMap.size(); i++) {
					if (key.equals(listMap.get(i).get("key"))) {
						list = (List<BtQmQueuetype>) listMap.get(i)
								.get("value");
					}
				}
			}
			// List<BtQmQueuetype> list = (List<BtQmQueuetype>)
			// context.getAttribute(ApplicationConstants.NATP_CACHE);
			// 计算人数和等待时间
 			for (int i = 0; i < list.size(); i++) {
				// 临时注释，等机构信功能完善后放开注释
				// if(list.get(i).getBranch().equals(branch)){
				List<BtQmQueue> t = list.get(i).getBtqmqueue();
				long times = 0;// 定义等待时间，单位：秒。初始化为0秒
				if (t != null) {
					for (int j = 0; j < t.size(); j++) {
						times = times+ DateUtil.diffTime(t.get(j).getEn_queue_time());
					}
					if (t.size() == 0) {
						String averagewaittime = String.valueOf(times);// 等待时间
						list.get(i).setAveragewaittime(averagewaittime);
						list.get(i).setQueuedetails("0");
					}else{
						String averagewaittime = String.valueOf(times/t.size());//等待时间
						list.get(i).setAveragewaittime(averagewaittime);
						list.get(i).setQueuedetails("1");// 详情设置
					}
					list.get(i).setBtqmsum(String.valueOf(t.size()));// 等待人数

				}else{
					String averagewaittime = list.get(i).getAveragewaittime();
					int btqmsum =Integer.valueOf(list.get(i).getBtqmsum());
					if(!"0".equals(averagewaittime)){
						Double averagewaittime1= null;
						if (btqmsum<=0) {
							btqmsum = 0;
							list.get(i).setAveragewaittime("0");
						} else {
							if(averagewaittime.indexOf(".")!=-1){
								 averagewaittime1 = Double.parseDouble(averagewaittime.substring(0, averagewaittime.indexOf(".")))+10;
								
							}else{
								 averagewaittime1 = Double.parseDouble(averagewaittime)+10;
							}
							list.get(i).setAveragewaittime(String.valueOf(averagewaittime1));
						}
					}
				}
			}

			JSONObject jsonobj = new JSONObject();
			jsonobj.put("list", list);
			event.setField("result", jsonobj.toString());
			return event;
		}

	}
	public void reload(Map recvMap) {
		logger.info("----接收到AFA推送过来的“队列实时监控”报文，解析并存入缓存中！----");
		ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		/*
		 * 获取缓存中NATP报文相关的信息
		 */
		List<Map> listMap = (List<Map>) context
				.getAttribute(ApplicationConstants.NATP_CACHE);
		if (listMap == null) {
			listMap = new ArrayList<Map>();
		}
		String key = "msg001";// natp报文的msgtype
		List<BtQmQueuetype> list = new ArrayList<BtQmQueuetype>();// 存储缓存中的队列列表
		for (int i = 0; i < listMap.size(); i++) {
			if (listMap.get(i).get("key").equals(key)) {
				list = (List<BtQmQueuetype>) listMap.get(i).get("value");
			}
		}
		List<Unit> units = (List<Unit>) context
				.getAttribute(ApplicationConstants.SYSTEMUNITS);
		Unit unit = null;

		/*
		 * 将natp发送过来的信息按逻辑要求编辑到map中
		 */
		if (recvMap.get("msgtype").equals(key)) {// 取号
			BtQmQueue model = new BtQmQueue();
			// {M_CustomerNo=IBP, M_MesgId=00000000000000006294, waitting=,
			// M_ServiceCode=IQS000001, bs_id=005010102_00149,
			// channeldate=20130718, pagenum=, queue_num=A0017,
			// M_MesgSndTime=231852, currpage=, M_MesgSndDate=20131030,
			// msgtype=msg001, M_MesgPriority=3, retrytime=0, channelcode=IBP,
			// pageflag=, waitnum=8, channelserno=00000000000000006294,
			// M_CallMethod=2, M_MesgRefId=20131030000000006294, qm_num=1,
			// tellerno=123456789012, brno=005010102, M_Reserve=, M_Direction=1,
			// delaytime=0, retrynum=0, M_ServicerNo=IQS, deviceaddition=qmnum,1
			// oid,赞同.susuila_xxxxxxxxxxxxxxxx
			// winnum,2
			// tradeCode,SCL.softCall.SoftCall, M_PackageType=NATP,
			// branch=005010102, channeltime=231852}
			model.setEn_queue_time(recvMap.get("en_queue_time").toString());// 取号时间
			model.setQueue_num(recvMap.get("queue_num").toString());// 队列号
			model.setBranch(recvMap.get("branch").toString());// 网点ID
			model.setQueuetype_id(recvMap.get("queuetype_id").toString());// 队列ID
			for (Unit curUnit : units) {
				if (model.getBranch().equals(curUnit.getUnitid())) {
					unit = curUnit;
					break;
				}
			}
			if(unit ==null)
			{
				throw new AppException("消息内容错误，机构号错误");
			}
			String[] unitlist = unit.getUnitlist().split("\\|");
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < unitlist.length; j++) {
					if (model.getBranch().equals(list.get(i).getBranch())
							&& model.getQueuetype_id().equals(
									list.get(i).getQueuetype_id())) {
						model.setQueuetype_name(list.get(i).getQueuetype_name());
						List<BtQmQueue> addlist = list.get(i).getBtqmqueue();
						addlist.add(model);
						list.get(i).setBtqmqueue(addlist);
						break;
					} else if (unitlist[j].equals(list.get(i).getBranch())) {
						String averagewaittime = list.get(i).getAveragewaittime();
						int btqmsum =Integer.valueOf(list.get(i).getBtqmsum())+1;
						Double averagewaittime1 = null;
						if(averagewaittime.indexOf(".")!=-1){
							averagewaittime1 = (Double.parseDouble(averagewaittime.substring(0, averagewaittime.indexOf("."))))* (btqmsum-1)/btqmsum;							
						}else{
							averagewaittime1 = (Double.parseDouble(averagewaittime))* btqmsum-1/btqmsum;							
						}
						list.get(i).setBtqmsum(String.valueOf(btqmsum));
						list.get(i).setAveragewaittime(String.valueOf(averagewaittime1));
						break;
					}
				}
			}
		} else if (recvMap.get("msgtype").equals("msg002")) {// 叫号
			/**
			 * {M_CustomerNo=IBP, M_ServiceCode=IQS000001,
			 * M_MesgId=00000000000000006309, bs_id=005010102_00148,
			 * channeldate=20130718, pagenum=, queue_num=A0009,
			 * M_MesgSndTime=232228, currpage=, M_MesgSndDate=20131030,
			 * msgtype=msg002, M_MesgPriority=3, custtype_i=root, retrytime=0,
			 * channelcode=IBP, pageflag=, queuetype_id=005010102_00110,
			 * channelserno=00000000000000006309, M_CallMethod=2,
			 * queuenum_type=1, M_MesgRefId=20131030000000006309, qm_num=1,
			 * win_num=1, tellerno=123456789012, brno=005010102, M_Reserve=,
			 * M_Direction=1, delaytime=0, retrynum=0, deviceaddition=qmnum,1
			 * oid,赞同.susuila_xxxxxxxxxxxxxxxx winnum,2
			 * tradeCode,SCL.softCall.SoftCall, M_ServicerNo=IQS,
			 * M_PackageType=NATP, branch=005010102, channeltime=232228}
			 * */
			for (Unit curUnit : units) {
				if (recvMap.get("branch").equals(curUnit.getUnitid())) {
					unit = curUnit;
					break;
				}
			}
			if(unit ==null)
			{
				throw new AppException("消息内容错误，机构号错误");
			}
			String[] unitlist = unit.getUnitlist().split("\\|");
			for (int i = 0; i < list.size(); i++) {
				List<BtQmQueue> oldlist = list.get(i).getBtqmqueue();
				if (oldlist==null) {
					for (int k = 0; k < unitlist.length; k++) {
						if (unitlist[k].equals(list.get(i).getBranch())) {
							String averagewaittime = list.get(i).getAveragewaittime();
							int btqmsum =Integer.valueOf(list.get(i).getBtqmsum())-1;
							Double averagewaittime1 = null;
							if(btqmsum<=0){
								btqmsum = 0;
								averagewaittime1 = 0d;
							}else{
								if(averagewaittime.indexOf(".")!=-1){
									averagewaittime1 = Double.parseDouble(averagewaittime.substring(0, averagewaittime.indexOf(".")))-10;							
								}else{
									averagewaittime1 = Double.parseDouble(averagewaittime)-10;							
								}								
							}
							list.get(i).setBtqmsum(String.valueOf(btqmsum));
							list.get(i).setAveragewaittime(String.valueOf(averagewaittime1));
							break;
						}
					}
					
				} else {
					for (int j = 0; j < oldlist.size(); j++) {					
						if (oldlist.get(j).getQueue_num()
								.equals(recvMap.get("queue_num").toString())) {
							if (oldlist.get(j).getBranch()
									.equals(recvMap.get("branch").toString())) {
								oldlist.remove(j);
								j--;
							}
						}
					}

				}
			}
		}
		for (int i = 0; i < listMap.size(); i++) {
			if (listMap.get(i).get("key").equals(key)) {
				listMap.get(i).put("value", list);
			}
		}
		context.setAttribute(ApplicationConstants.NATP_CACHE, listMap);
	}

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}

	public IQmQueueService getService() {
		return service;
	}

	public void setService(IQmQueueService service) {
		this.service = service;
	}

}
