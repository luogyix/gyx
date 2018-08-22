package com.yongboy.socketio.server;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.netty.channel.ChannelHandlerContext;

import com.yongboy.socketio.server.transport.BlankIO;
import com.yongboy.socketio.server.transport.IOClient;

public class MemoryStore implements Store {
	private static final ConcurrentHashMap<String, IOClient> clients = new ConcurrentHashMap<String,IOClient>();

	public IOClient get(String sessionId) {
		IOClient client = (IOClient) clients.get(sessionId);

		if (client == null) {
			return client;
		}
		if ((client instanceof BlankIO)) {
			return null;
		}
		return client;
	}

	public void remove(String sessionId) {
		clients.remove(sessionId);
	}

	public void add(String sessionId, IOClient client) {
		if ((sessionId == null) || (client == null)) {
			return;
		}
		clients.put(sessionId, client);
	}

	public Collection<IOClient> getClients() {
		return clients.values();
	}

	public boolean checkExist(String sessionId) {
		return clients.containsKey(sessionId);
	}

	public IOClient getByCtx(ChannelHandlerContext ctx) {
		if (ctx == null) {
			return null;
		}
		for (IOClient client : getClients()) {
			if (ctx == client.getCTX()) {
				return client;
			}
		}

		return null;
	}

	public static void main(String[] args) {
		clients.put("001", BlankIO.getInstance());
		System.out.println(clients.containsKey("001"));
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.MemoryStore JD-Core Version: 0.6.0
 */