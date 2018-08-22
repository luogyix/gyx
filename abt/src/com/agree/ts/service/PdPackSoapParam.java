package com.agree.ts.service;

import java.util.UUID;

import net.sf.json.JSONObject;

import org.slf4j.MDC;

import com.agree.framework.communication.socket.MessServer;
import com.agree.framework.web.form.administration.User;
import com.agree.util.CheckIP;
import com.agree.util.DateUtil;
import com.agree.util.IDictABT;

/**
 * @date 2013-11-28 下午01:32:02
 * @copyright (c) by 赞同科技 2013
 */
public  class   PdPackSoapParam {

	//手持设备报文头填充（最新标准）
	public static JSONObject getMessageHead(User user,JSONObject recvJson)throws Exception{
		JSONObject obj=new JSONObject();
		String date = DateUtil.getCurrentDateByFormat("yyyyMMdd");
		String time = DateUtil.getCurrentDateByFormat("HHmmssSSS");
		//报文头
		obj.element("M_ServerIp",CheckIP.getRealIp());
		obj.element("M_ServerPort",MessServer.getPort());
		obj.element("M_CustomerNo", "HOS");//消费方系统编号
		obj.element("M_PackageType", "JSON");//报文类型
		obj.element("M_ServicerNo", "IBP");//服务方系统编号
		obj.element("M_ServiceCode", recvJson.get("trancode"));//服务代码
		obj.element("M_MesgSndDate",date);//报文发起日期
		obj.element("M_MesgSndTime", time);//报文发起时间
		String msgid = MDC.get(IDictABT.MSGID);
		if(msgid == null)
		{
			msgid = UUID.randomUUID().toString();
			MDC.put(IDictABT.MSGID, msgid);
		}
		obj.element("M_MesgId", msgid);//报文消息ID
		obj.element("M_MesgRefId", "");//报文消息参考号
		obj.element("M_MesgPriority", "1");//报文优先级
		obj.element("M_Direction", "1");//报文方向
		obj.element("M_CallMethod", "1");//调用方式
		obj.element("M_Reserve", "");//（保留域）
		//公共字段
		obj.element("channelcode", IDictABT.CHANNEL_HOS);//渠道代码
		obj.element("channelserno", date+time);//渠道流水号
		obj.element(IDictABT.DEVICETYPE,IDictABT.DEVICE_TYPE_HOS);//柜员号
		if(user!=null){
			obj.element("brno", user.getUnitid()==null?"":user.getUnitid());//机构码
			obj.element("tellerno", user.getUsercode()==null?"":user.getUsercode());//柜员号
			obj.element("clientIP", user.getHostip()==null?"":user.getHostip());//柜员号
//			obj.element("authteller", "");//授權柜员号
//			obj.element("authbranch", "");//授權機構
//			obj.element("device_num", user.getPdno()==null?"":user.getPdno());//柜员号
		}
		return obj;
	}
}
