package com.yongboy.socketio.server;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yongboy.socketio.server.transport.IOClient;

public abstract class IOHandlerAbs implements IOHandler {
	private static final Logger log = LoggerFactory.getLogger(IOHandlerAbs.class);
	private String serverIp;
	public String getServerIp() {
		return this.serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getServerPort() {
		return this.serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	private String serverPort;
	protected Collection<IOClient> getClients() {
		Store store = SocketIOManager.getDefaultStore();

		return store.getClients();
	}

	protected void broadcast(String message) {
		broadcast(null, message);
	}

	protected void broadcast(IOClient current, String message) {
		for (IOClient client : getClients()) {
			if ((current != null)
					&& (current.getSessionID() == client.getSessionID())) {
				continue;
			}
			client.send(message);
		}
	}

	/***
	 * 單播給聯接方
	 * 
	 * @param client
	 * @param messageIdPlusStr
	 * @param obj
	 */
	protected void singlecast(IOClient current, String message) {

		current.send(message);
	}

	protected void ackNotify(IOClient client, String messageIdPlusStr,
			Object obj) {
		StringBuilder builder = new StringBuilder("6:::");

		String formateJson = JSON.toJSONString(obj);
		if ((formateJson.startsWith("[")) && (formateJson.endsWith("]"))) {
			formateJson = formateJson.substring(1, formateJson.length() - 1);
		}

		builder.append(messageIdPlusStr).append("[").append(formateJson)
				.append("]");

		log.debug("ack message " + builder);
		client.send(builder.toString());
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.IOHandlerAbs JD-Core Version: 0.6.0
 */