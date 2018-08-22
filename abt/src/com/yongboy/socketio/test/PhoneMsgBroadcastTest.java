package com.yongboy.socketio.test;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yongboy.socketio.server.IOHandlerAbs;
import com.yongboy.socketio.server.transport.GenericIO;
import com.yongboy.socketio.server.transport.IOClient;

/*
 * 修改netty源码，并为手持消息通讯服务
 * 支持socket.io 0.9版本
 */
public class PhoneMsgBroadcastTest extends IOHandlerAbs {
	private static final Logger log = LoggerFactory.getLogger(PhoneMsgBroadcastTest.class);
	// 客户端发起的信息，所有的联接，不过滤客户端集合
	public static ConcurrentMap<String, Transclass> msgMap = new ConcurrentHashMap<String, Transclass>();
	
	// 客户端集合，由过滤条件生成，后续由服务端生成数据放置，由客户对像为value
	public static ConcurrentMap<String, Object> filtermsgMap = new ConcurrentHashMap<String, Object>();

	/***
	 * 当客户联接的时候发生调用此方法，把客户ID加入
	 */
	public void OnConnect(IOClient client) {
		log.debug("客户端一个客户联接，联接sessionID :: " + client.getSessionID());
		GenericIO genericIO = (GenericIO) client;
		String sessionid = client.getSessionID();
		if (!msgMap.isEmpty()) {

			Iterator<Entry<String, Transclass>> it = msgMap.entrySet().iterator();
			while (it.hasNext()) {

				Transclass clienttemp = (Transclass) it.next();
				/***
				 * 如果是同一sessionid,认为是同一联接,如果上一联接没有关，还在作用中，跳过
				 */
				if ((genericIO.getSessionID().equalsIgnoreCase(clienttemp.getIoClient().getSessionID()
						)) && clienttemp.getIoClient().isOpen()) {
					log.debug("此联接已经在静态内存中sessionid:::"
							+ genericIO.getSessionID());
					return;
				}
				msgMap.put(sessionid, new Transclass(client,sessionid) );
			}
		}
		msgMap.put(sessionid, new Transclass(client,sessionid));
	}

	/***
	 * 客户端断开联接后，删除在其内存中的联接
	 */
	public void OnDisconnect(IOClient client) {
		log.debug("一个用户终止了联接，联接的sessionID::: " + client.getSessionID()
				+ " :: 准备将其移除！");

		msgMap.remove(client.getSessionID());

	}

	/**
	 * 客户端发送请求信息的时候，这里收到关处理，收到的是json格式数据
	 */
	public void OnMessage(IOClient client, String oriMessage) {
		log.debug("收到客户端信息 :: " + oriMessage + " :: 并通知其它的客户端联接 ");
		String jsonString = oriMessage.substring(oriMessage.indexOf('{'));
		jsonString = jsonString.replaceAll("\\\\", "");
		log.debug("收到的jsonString 串：：：" + jsonString);
		// 如果是统一广播模式，调用此方法
		// emit(client,1,oriMessage);
		msgBroadcast(oriMessage);
	}

	/**
	 * 婢跺嫮鎮婇悽銊﹀煕閸氬秶娈戦柅姘辩叀
	 * 
	 * @author yongboy
	 * @time 2012-3-31
	 * 
	 * @param client
	 * @param oriMessage
	 * @param obj
	 */
	@SuppressWarnings("unused")
	private void handleAckNoticName(IOClient client, String oriMessage,
			Object obj) {
		// 婢跺嫮鎮婄敮锔芥箒 濞戝牊浼卛d+ 閻ㄥ嫭鍎忛崘锟�
		boolean aplus = oriMessage.matches("\\d:\\d{1,}\\+:.*:.*?");
		if (aplus) {
			String aPlusStr = oriMessage.substring(2,
					oriMessage.indexOf('+') + 1);
			// 闁氨鐓oclient閸欐垿锟藉銈嗙Х閹拷
			ackNotify(client, aPlusStr, obj);
		}
	}

	public void OnShutdown() {
		log.debug("shutdown now ~~~");
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
	 * 接收第三方发过的信息，给所有的联接转发出去
	 * 
	 * @param msg
	 *            json的数据格式
	 */
	public static void msgBroadcast(String msg) {

		log.info("将要广播 的消息是：：：" + msg);
		Iterator<Entry<String, Transclass>> it = msgMap.entrySet().iterator();
		while (it.hasNext()) {
			Transclass clienttemp = (Transclass) it.next();
			clienttemp.getIoClient().send(msg);
		}
	}
	
	//内部类，封装在msgmap中的object
	class Transclass
	{
		public IOClient ioClient;
		public IOClient getIoClient() {
			return ioClient;
		}
		public void setIoClient(IOClient ioClient) {
			this.ioClient = ioClient;
		}
		public String getSessionid() {
			return sessionid;
		}
		public void setSessionid(String sessionid) {
			this.sessionid = sessionid;
		}
		String sessionid;
		public Transclass(IOClient ioClient,String msg)
		{
			this.ioClient=ioClient;
			this.sessionid=msg;
		}
	}

}