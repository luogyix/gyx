package com.yongboy.socketio.server.transport;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpRequest;

import com.yongboy.socketio.server.IOHandlerAbs;
import com.yongboy.socketio.server.Transports;

public class JsonpPollingTransport extends ITransport {
	public JsonpPollingTransport(IOHandlerAbs handler, HttpRequest req) {
		super(handler, req);
	}

	public String getId() {
		return Transports.JSONPP0LLING.getValue();
	}

	protected GenericIO doPrepareI0Client(ChannelHandlerContext ctx,
			HttpRequest req, String sessionId) {
		JsonpIO client = new JsonpIO(ctx, req, sessionId);
		client.prepare();
		client.connect(null);

		return client;
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.transport.JsonpPollingTransport JD-Core Version:
 * 0.6.0
 */