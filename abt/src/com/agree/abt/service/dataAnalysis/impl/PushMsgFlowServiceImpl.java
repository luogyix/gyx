package com.agree.abt.service.dataAnalysis.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.agree.abt.service.dataAnalysis.IPushMsgFlowService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.service.base.BaseService;

/**
 * 消息流水查询实现类
 * 
 * @ClassName: PushMsgFlowServiceImpl 
 * @company 赞同科技   
 * @author  zhaojianyong
 * @date 2015-6-12 下午04:46:33 
 *
 */
@SuppressWarnings("all")
public class PushMsgFlowServiceImpl extends BaseService implements IPushMsgFlowService {
	
	@Transactional
	public List queryPushMsgList(Map map ,Page pageInfo) throws Exception {
		List list=null;
		String hql = "select new map(pm.msgId as msgId, pm.branch as branch, pm.pdNo as pdNo," +
				"pm.msgDate as msgDate, pm.msgTime as msgTime,pm.msgType as msgType," +
				"pm.deviceType as deviceType,pm.msgContent as msgContent, pm.isNew as isNew)" ;
		hql = hql+ " from PushMsgFlow pm left join pm.unit un where 1=1";
		
		HashMap paramaHashMap=new HashMap();
		paramaHashMap.putAll(map);
		
		//开始时间
		if(paramaHashMap.get("msgTime_begin_time")!=null && !paramaHashMap.get("msgTime_begin_time").toString().equals("")){
			String replaceBeginTime = ((String)paramaHashMap.get("msgTime_begin_time")).replace(":", "");
			paramaHashMap.put("msgTime_begin_time", replaceBeginTime);
			hql = hql+" and pm.msgTime >= :msgTime_begin_time";
		}
		//结束时间
		if(paramaHashMap.get("msgTime_end_time")!=null && !paramaHashMap.get("msgTime_end_time").toString().equals("")){
			String replaceEndTime = ((String)paramaHashMap.get("msgTime_end_time")).replace(":", "");
			paramaHashMap.put("msgTime_end_time", replaceEndTime);
			hql = hql+" and pm.msgTime <= :msgTime_end_time";
		}
		//消息流水号
		if(paramaHashMap.get("msgId")!=null && !paramaHashMap.get("msgId").toString().equals("")){
			paramaHashMap.put("msgId", "%" + paramaHashMap.get("msgId") + "%");
			hql = hql+" and pm.msgId like :msgId";
		}
		
		//设备类型
		if(paramaHashMap.get("devicetype")!=null && !paramaHashMap.get("devicetype").toString().equals("")){
			hql = hql+" and pm.deviceType = :devicetype";
		}
		//选择条件的部门的所有下级
		if(paramaHashMap.get("unitid")!=null && !paramaHashMap.get("unitid").toString().equals("")){
			paramaHashMap.put("unitid", "%" + paramaHashMap.get("unitid") + "%");
			hql = hql+" and pm.branch in (" +
					" select unitid " +
					" from pm.unit where un.unitlist like :unitid)" ;
					
		}
		hql = hql+" order by pm.msgTime desc";//hql语句拼写完成
		list = sqlDao_h.queryPage(hql,paramaHashMap,pageInfo,false);//此方法传入的hql为完整的hql，不需传入排序参数
		return list;
	}
	
	
}
