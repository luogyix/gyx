package com.agree.framework.communication.natp;

import java.util.HashMap;
import java.util.Map;

public class NatpMsgParam {

	private static Map<String, String> params;
	
	static{
		//队列实时监控
		params = new HashMap<String, String>();
		params.put("msg001", "com.agree.abt.action.dataAnalysis.QueueRealTimeMonitorAction");
		params.put("msg002", "com.agree.abt.action.dataAnalysis.QueueRealTimeMonitorAction");
		//窗口服务状态监控
		params.put("msg007", "com.agree.abt.action.dataAnalysis.WinServiceStateAction");
	}
	
	public static String getParam(String key){
		return params.get(key);
	}
	
}
