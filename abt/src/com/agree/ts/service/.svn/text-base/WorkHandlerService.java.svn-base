package com.agree.ts.service;


import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.communication.http.HttpSentRev;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.base.BaseService;

public class WorkHandlerService extends BaseService {
	
	private HttpSentRev httpSentRev;
	public static 	String trancode = "trancode";	//交易码字段
	private static final Logger logger = LoggerFactory.getLogger(WorkHandlerService.class);
	
	public WorkHandlerService() {
		super();
	}
	
	/**
	 * 手持请求服务-用于直接送Json到afa
	 * 
	 */
	public JSONObject fechData(String msgStr,User user) throws Exception{
		if(logger.isInfoEnabled()){
			logger.info("-----------------------------开始处理手持请求---------------------------");
		}
		JSONObject retJson = transJsonToAfa(msgStr,user);
		if(logger.isInfoEnabled()){
			logger.info("-----------------------------手持请求处理结束---------------------------");
		}
		return retJson;
	}
	
	/***
	 * json发送 接收 json 从afa
	 * @param jsonArray
	 * @param recvJson
	 * @return
	 * @throws Exception 
	 */
	public JSONObject transJsonToAfa(String msgstr,User user) throws Exception
	{
		JSONObject retjson = new JSONObject();
		JSONObject recvJson = JSONObject.fromObject(msgstr);
		Object tcode = recvJson.get(trancode);
		if(tcode == null || tcode.equals(""))
		{
			return retjson.element("error", "交易代码不存在");
		}
		
		JSONObject recvMsg=exchangeFromAfa(msgstr,user);
		return recvMsg;
	}
	
	/***
	 * 直接与afa通讯得到数据返回，不做任何处理
	 * enter param
	 * 
	 * @return
	 * @throws Exception 
	 */
	private  JSONObject exchangeFromAfa(String msgstr,User user) throws Exception
	{
		if(logger.isInfoEnabled()){
			logger.info("收到json报文：" + msgstr );
		}
		JSONObject recvJson = JSONObject.fromObject(msgstr);
		JSONObject msgHead=PdPackSoapParam.getMessageHead(user, recvJson);
		//将固定报文头和报文体结合。
		recvJson.accumulateAll(msgHead);
		
		//获得发送通讯配置
		/*
		int randomNum = 0;//用于记录随机连接次数
		ServletContext context=ContextLoader.getCurrentWebApplicationContext().getServletContext();
		List<Map<String, Map>> sendCommlist = (List<Map<String, Map>>)context.getAttribute("sendComm_p"+project_SOAP.getRcid());
		if(sendCommlist==null||sendCommlist.isEmpty()){
			sendCommlist = communicationService.getManaySendCom(project_SOAP.getRcid());
			context.setAttribute("sendComm_p"+project_SOAP.getRcid(), sendCommlist);
		}*/
		//读取http配置文件
//		ServletContext context=ContextLoader.getCurrentWebApplicationContext().getServletContext();
//		Map<String, String> confHttpAfa = (Map<String, String>)context.getAttribute(ApplicationConstants.COMMUNICATION_AFAHTTPCONF);
//		JSONObject recvMsg = JSONObject.fromObject(httpSentRev.readContentFromPost(recvJson,confHttpAfa));
		JSONObject recvMsg = JSONObject.fromObject(httpSentRev.readContentFromPost(recvJson));

		return recvMsg;
	}

	public HttpSentRev getHttpSentRev() {
		return httpSentRev;
	}

	public void setHttpSentRev(HttpSentRev httpSentRev) {
		this.httpSentRev = httpSentRev;
	}

}
