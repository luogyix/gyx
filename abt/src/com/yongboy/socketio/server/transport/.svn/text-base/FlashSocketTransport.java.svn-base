package com.yongboy.socketio.server.transport;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpRequest;

import com.yongboy.socketio.server.IOHandlerAbs;
import com.yongboy.socketio.server.Transports;

public class FlashSocketTransport extends WebSocketTransport {
	public FlashSocketTransport(IOHandlerAbs handler, HttpRequest req) {
		super(handler, req);
	}

	public String getId() {
		return Transports.FLASHSOCKET.getValue();
	}

	protected GenericIO doPrepareI0Client(ChannelHandlerContext ctx,
			HttpRequest req, String sessionId) {
		FlashSocketIO client = new FlashSocketIO(ctx, req, sessionId);
		client.heartbeat(this.handler);
		client.connect(null);
		return client;
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.transport.FlashSocketTransport JD-Core Version:
 * 0.6.0
 */