package com.yongboy.socketio.server.transport;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpRequest;

import com.yongboy.socketio.server.IOHandlerAbs;
import com.yongboy.socketio.server.Transports;

public class HtmlfileTransport extends ITransport {
	public HtmlfileTransport(IOHandlerAbs handler, HttpRequest req) {
		super(handler, req);
	}

	public String getId() {
		return Transports.HTMLFILE.getValue();
	}

	protected GenericIO doPrepareI0Client(ChannelHandlerContext ctx,
			HttpRequest req, String sessionId) {
		HtmlfileIO client = new HtmlfileIO(ctx, req, sessionId);

		client.prepare();
		client.connect(null);

		client.heartbeat(this.handler);

		return client;
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.transport.HtmlfileTransport JD-Core Version:
 * 0.6.0
 */