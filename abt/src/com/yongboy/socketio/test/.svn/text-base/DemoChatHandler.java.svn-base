package com.yongboy.socketio.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yongboy.socketio.server.IOHandlerAbs;
import com.yongboy.socketio.server.transport.GenericIO;
import com.yongboy.socketio.server.transport.IOClient;

public class DemoChatHandler extends IOHandlerAbs {
	private static final Logger log = LoggerFactory.getLogger(DemoChatHandler.class);
	private ConcurrentMap<String, String> nicknames = new ConcurrentHashMap<String,String>();

	public void OnConnect(IOClient client) {
		log.debug("一个用户联接了，sessionID:::" + client.getSessionID());
	}

	public void OnDisconnect(IOClient client) {
		log.debug("一个用户断开联接，sessionID::: " + client.getSessionID());

		GenericIO genericIO = (GenericIO) client;
		Object nickNameObj = genericIO.attr.get("nickName");

		if (nickNameObj == null) {
			return;
		}
		String nickName = nickNameObj.toString();
		this.nicknames.remove(nickName);
		emit("announcement", nickName + "  disconnected");

		emit("nicknames", this.nicknames);
	}

	@SuppressWarnings("unused")
	public void OnMessage(IOClient client, String oriMessage) {
		log.debug("收到一个信息 " + oriMessage + " :: 信息sessionID :: "
				+ client.getSessionID());
		String jsonString = oriMessage.substring(oriMessage.indexOf('{'));

		jsonString = jsonString.replaceAll("\\\\", "");

		log.debug("jsonString ===" + jsonString);

		JSONObject jsonObject = JSON.parseObject(jsonString);
		String eventName = jsonObject.get("name").toString();
		log.info("eventname==" + eventName);
		// if (eventName.equals("nickname")) {
		if (true) {
			JSONArray argsArray = jsonObject.getJSONArray("args");
			String nickName = argsArray.getString(0);
			if (this.nicknames.containsKey(nickName)) {
				handleAckNoticName(client, oriMessage, Boolean.valueOf(true));

				return;
			}

			handleAckNoticName(client, oriMessage, Boolean.valueOf(false));
			this.nicknames.put(nickName, nickName);

			GenericIO genericIO = (GenericIO) client;
			genericIO.attr.put("nickName", nickName);

			emit("announcement", nickName + " connected");
			emit("nicknames", this.nicknames);
			return;
		}

		if (eventName.equals("user message")) {
			GenericIO genericIO = (GenericIO) client;
			String nickName = genericIO.attr.get("nickName").toString();
			log.info("nickName==" + nickName);
			JSONArray argsArray = jsonObject.getJSONArray("args");
			String message = argsArray.getString(0);

			emit(client, eventName, nickName, message);
		}
	}

	private void handleAckNoticName(IOClient client, String oriMessage,
			Object obj) {
		boolean aplus = oriMessage.matches("\\d:\\d{1,}\\+::.*?");
		if (aplus) {
			String aPlusStr = oriMessage.substring(2,
					oriMessage.indexOf('+') + 1);

			ackNotify(client, aPlusStr, obj);
		}
	}

	public void OnShutdown() {
		log.debug("shutdown now ~~~");
	}

	private void emit(String eventName, Map<String, String> nicknames) {
		String content = String.format("5:::{\"name\":\"%s\",\"args\":[%s]}",
				new Object[] { eventName, JSON.toJSONString(nicknames) });
		super.broadcast(content);
	}

	private void emit(String eventName, String message) {
		String content = String.format(
				"5:::{\"name\":\"%s\",\"args\":[\"%s\"]}", new Object[] {
						eventName, message });
		super.broadcast(content);
	}

	private void emit(IOClient client, String eventName, String message,
			String message2) {
		String content = String.format(
				"5:::{\"name\":\"%s\",\"args\":[\"%s\",\"%s\"]}", new Object[] {
						eventName, message, message2 });
		log.info("要广播的信息是===" + content);
		super.broadcast(client, content);
	}
}

/*
 * Location:
 * E:\赞同项目\手持技术开发\phonegap\socket.io\chat_server\chat_server\chat_server.jar
 * Qualified Name: com.yongboy.socketio.test.DemoChatHandler JD-Core Version:
 * 0.6.0
 */