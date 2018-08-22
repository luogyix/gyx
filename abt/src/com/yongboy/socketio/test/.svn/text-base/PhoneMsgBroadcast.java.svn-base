package com.yongboy.socketio.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.form.administration.User;
import com.yongboy.socketio.server.IOHandlerAbs;
import com.yongboy.socketio.server.transport.GenericIO;
import com.yongboy.socketio.server.transport.IOClient;
import com.yongboy.socketio.server.transport.TranMsgObject;
import com.yongboy.socketio.server.transport.WebSocketIO;
import com.yongboy.socketio.server.transport.XhrIO;

/*
 * 修改netty源码，并为手持消息通讯服务
 * 支持socket.io 0.9版本
 * 
 */
public class PhoneMsgBroadcast extends IOHandlerAbs {
	private static final Logger log = LoggerFactory.getLogger(PhoneMsgBroadcast.class);
	// 客户端发起的信息，所有的联接，不过滤客户端集合
	public static ConcurrentMap<String, Set<IOClient>> msgMap = new ConcurrentHashMap<String, Set<IOClient>>();

	// 客户端集合，由过滤条件生成，后续由服务端生成数据放置，由客户对像为value
	public static ConcurrentMap<String, Object> filtermsgMap = new ConcurrentHashMap<String, Object>();

	public static final String key = "phonegap";

	public static String socketType;// websocket类型，1：websocket 2:xhr

	/***
	 * 当客户联接的时候发生调用此方法，把客户ID加入
	 */
	public void OnConnect(IOClient client) {
		log.info("客户端一个客户联接，联接sessionID :: " + client.getSessionID());
		if (!msgMap.containsKey(key)) {
			msgMap.put(key, new HashSet<IOClient>());
		} else {
			msgMap.get(key).add(client);
		}
		log.info("现在内存中有socketclient对像有" + msgMap.get(key).size() + "个");
	}

	/***
	 * 客户端断开联接后，删除在其内存中的联接
	 */
	public void OnDisconnect(IOClient client) {
		log.info("一个用户终止了联接，联接的sessionID::: " + client.getSessionID()
				+ " :: 准备将其移除！");

		// msgMap.get(key).remove(client);

		if (client instanceof WebSocketIO) {

			WebSocketIO webSocketIO = (WebSocketIO) client;
			msgMap.get(key).remove(webSocketIO);
		}
		if (client instanceof XhrIO) {

			XhrIO xhjIO = (XhrIO) client;
			msgMap.get(key).remove(xhjIO);
		}
		log.info("现在内存中有socketclient对像有" + msgMap.get(key).size() + "个");

	}

	/**
	 * 客户端发送请求信息的时候，这里收到关处理，收到的是json格式数据 上送数据：用户号，排队号，设备号,
	 * 只要客户不退出，就可以收到信息，OnMessage是注册方法，要注册登陆的用户sessionID号，并去校验是否存在这个用户的sessionid
	 */
	@SuppressWarnings("unchecked")
	public void OnMessage(IOClient client, String oriMessage) {

		log.info("收到客户端信息 :: " + oriMessage
				+ " :: 并通知其它的客户端联接 ,并且 sessionid===" + client.getSessionID());
		String jsonString = oriMessage.substring(oriMessage.indexOf('{'));
		jsonString = jsonString.replaceAll("\\\\", "");
		log.info("收到的jsonString 串：：：" + jsonString);
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		// String userid = jsonObject.getString("userid");// 用户ID
		// String mgno = jsonObject.getString("mgno");
		// String name = jsonObject.getString("user");
		JSONArray jsonArray = jsonObject.getJSONArray("args");
		String tsessionid = (String) jsonArray.get(0);
		TranMsgObject tranobj = new TranMsgObject();
		log.info("收到信息用户传入的字段===" + tsessionid);

		boolean seflag = false;// 标识用户是否能找到sessionid在web工程的整个会话中
		JSONObject jsonobj = new JSONObject();
		//List sessionList = (ArrayList) ApplicationConstants.sessionMap.get(ApplicationConstants.SESSIONID);
		List<HttpSession> sessionList = (List<HttpSession>)ServletActionContext.getRequest().getSession().getServletContext().getAttribute(ApplicationConstants.SESSIONID);
		if (sessionList != null) {
			for (int i = 0; i < sessionList.size(); i++) {
				HttpSession sess = (HttpSession) sessionList.get(i);
				User us = null;
				try {
					us = (User) sess.getAttribute(ApplicationConstants.LOGONUSER);
				} catch (Exception e) {
					sessionList.remove(i);
					log.info("session: " + sess.getId() + "实效，从sessionList中删除！");
				}
				log.info("web 中的sessionid===" + sess.getId() + "用户userid==" + us.getUserid());
				if (sess.getId().equalsIgnoreCase(tsessionid)) {
					log.info("websocket 中找到相应该的sessionid!!");
					tranobj.setSessionid(tsessionid);
					tranobj.setUserid(us.getUsercode());
					seflag = true;
					break;
				}
			}
			// 如果没有找到，返回失败信息给，报客户已经失效，不会对失效客户发信息
			if (seflag == false) {
				jsonobj.accumulate("rspcode", false);
				jsonobj.accumulate("rspmsg", "用戶已經失效，請重新聯接！");
				String content = String.format(
						"5:::{\"name\":\"%s\",\"args\":[%s]}", new Object[] {
								"message", jsonobj.toString() });
				singlecast(client, content);
				return;
			}
		}

		if (seflag == true) {

			if (client instanceof WebSocketIO) {
				socketType = "1";
				WebSocketIO webSocketIO = (WebSocketIO) client;
				webSocketIO.setTranMsgObject(tranobj);
				if (!msgMap.containsKey(key)) {
					msgMap.put(key, new HashSet<IOClient>());
					msgMap.get(key).add(webSocketIO);
				} else {
					msgMap.get(key).add(webSocketIO);
				}
			}
			if (client instanceof XhrIO) {
				socketType = "2";
				XhrIO xhjIO = (XhrIO) client;
				xhjIO.setTranMsgObject(tranobj);
				if (!msgMap.containsKey(key)) {
					msgMap.put(key, new HashSet<IOClient>());
					msgMap.get(key).add(xhjIO);
				} else {
					msgMap.get(key).add(xhjIO);
				}
			}

			// 如果是统一广播模式，调用此方法
			// emit(client,1,oriMessage);
			Map<String,Object> result = new HashMap<String,Object>();
			List<Map<String,Object>> relist = new ArrayList<Map<String,Object>>();

			result.put("queuetype", new Integer(1));
			result.put("queuename", "对公业务");
			result.put("waitcount", new Integer(5));
			result.put("agvtime", new Integer(6));
			result.put("mdno", "00005");
			relist.add(result);

			jsonobj.accumulate("rspcode", true);
			jsonobj.accumulate("msgcode", "sysinfo");
			jsonobj.accumulate("rspmsg", "交易成功!");
			jsonobj.accumulate("result", relist);

			log.info("jsonobj====" + jsonobj.toString());

			String content = String.format(
					"5:::{\"name\":\"%s\",\"args\":[%s]}", new Object[] {
							"message", jsonobj.toString() });

			// String content =
			// String.format("{\"name\":\"%s\",\"args\":\"%s\"]}","message",
			// jsonobj.toString());
			log.info("json====" + content.toString());
			msgBroadcast(content);
		}

		log.info("现在内存中有socketclient对像有" + msgMap.get(key).size() + "个");
	}

	@SuppressWarnings("unused")
	private void handleAckNoticName(IOClient client, String oriMessage,
			Object obj) {
		boolean aplus = oriMessage.matches("\\d:\\d{1,}\\+:.*:.*?");
		if (aplus) {
			String aPlusStr = oriMessage.substring(2,
					oriMessage.indexOf('+') + 1);
			ackNotify(client, aPlusStr, obj);
		}
	}

	public void OnShutdown() {
		log.info("shutdown now ~~~");
	}

	/**
	 * 消息通知模式，
	 * 
	 * @param client
	 * @param msgflag
	 *            1：所以联接者都可以通知 2：只有本人群组用户可以通知 3：单个消息广播p2p的消息
	 * @param message
	 *            消息源式
	 */
	@SuppressWarnings("unused")
	private void emit(IOClient client, int msgflag, String message) {

		super.broadcast(client, message);
	}

	/***
	 * 接收第三方发过的信息，给所有的联接转发出去，所以有联接都在msgMap中,从报文中得到排队机号，给相应的排队机手持发送
	 * 
	 * @param msg
	 *            json的数据格式
	 */
	public static void msgBroadcast(String msg) {

		log.info("将要广播 的消息是：：：" + msg);
		Set<IOClient> clientset = msgMap.get(key);
		if ((clientset == null) || (clientset.isEmpty())) {
			log.info("用户数据池Secoet为空，返回！");
			return;
		}
		Iterator<IOClient> iterator = clientset.iterator();
		IOClient ioClient = null;
		while (iterator.hasNext()) {
			ioClient = (GenericIO) iterator.next();

			log.info("需要广播的用户对像==" + ioClient.getSessionID()+"ip:"+((GenericIO)ioClient).getIp());
			String content = String.format(
					"5:::{\"name\":\"%s\",\"args\":[%s]}", new Object[] {
							"message", msg.toString() });
			ioClient.send(content);
		}
	}

}