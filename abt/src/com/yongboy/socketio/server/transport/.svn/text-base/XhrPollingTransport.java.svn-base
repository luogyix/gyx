package com.yongboy.socketio.server.transport;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;

import com.yongboy.socketio.server.IOHandlerAbs;
import com.yongboy.socketio.server.Transports;

public class XhrPollingTransport extends ITransport {
	public XhrPollingTransport(IOHandlerAbs handler, HttpRequest req) {
		super(handler, req);
	}

	public String getId() {
		return Transports.XHRPOLLING.getValue();
	}

	protected GenericIO initGenericClient(ChannelHandlerContext ctx,
			HttpRequest req) {
		GenericIO client = super.initGenericClient(ctx, req);

		if (!(client instanceof XhrIO)) {
			String sessionId = super.getSessionId();
			this.store.remove(sessionId);

			return initGenericClient(ctx, req);
		}

		if (req.getMethod() == HttpMethod.GET) {
			client.reconnect(ctx, req);

			client.heartbeat(this.handler);
		}

		return client;
	}

	protected GenericIO doPrepareI0Client(ChannelHandlerContext ctx,
			HttpRequest req, String sessionId) {
		XhrIO client = new XhrIO(ctx, req, sessionId);
		client.prepare();
		client.connect(null);

		return client;
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.transport.XhrPollingTransport JD-Core Version:
 * 0.6.0
 */