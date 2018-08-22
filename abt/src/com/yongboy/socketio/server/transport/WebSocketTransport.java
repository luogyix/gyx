package com.yongboy.socketio.server.transport;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yongboy.socketio.server.IOHandlerAbs;
import com.yongboy.socketio.server.SocketIOManager;
import com.yongboy.socketio.server.Transports;

public class WebSocketTransport extends ITransport {
	private static final Logger log = LoggerFactory.getLogger(WebSocketTransport.class);
	private WebSocketServerHandshaker handshaker;

	public WebSocketTransport(IOHandlerAbs handler, HttpRequest req) {
		super(handler, req);
	}

	public String getId() {
		return Transports.WEBSOCKET.getValue();
	}

	protected GenericIO doPrepareI0Client(ChannelHandlerContext ctx,
			HttpRequest req, String sessionId) {
		WebSocketIO client = new WebSocketIO(ctx, req, sessionId);
		client.connect(null);
		client.heartbeat(this.handler);
		return client;
	}

	/**
	 * 
	 * websocket 处理http请求，并建立出事链接。
	 * 
	 * 
	 * */
	public void doHandle(ChannelHandlerContext ctx, HttpRequest req,
			MessageEvent e) {
		log.debug("websocket handls the request ...");
		String sessionId = super.getSessionId();
		log.debug("session id " + sessionId);

		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
				getTargetLocation(req, sessionId), null, false);
		this.handshaker = wsFactory.newHandshaker(req);
		if (this.handshaker == null) {
			wsFactory.sendUnsupportedWebSocketVersionResponse(ctx.getChannel());
			return;
		}

		this.handshaker.handshake(ctx.getChannel(), req);

		doPrepareClient(ctx, req, sessionId,e);
	}

	private void doPrepareClient(ChannelHandlerContext ctx, HttpRequest req,
			String sessionId,MessageEvent e) {
		GenericIO client = null;
		try {
			client = (GenericIO) SocketIOManager.getDefaultStore().get(
					sessionId);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}

		if (client != null) {
			return;
		}
		InetSocketAddress sa=(InetSocketAddress)e.getRemoteAddress();
		InetAddress ia=sa.getAddress();
		String ip=ia.getHostAddress();
		log.debug("the client is null now ...");
		client = doPrepareI0Client(ctx, req, sessionId);
		client.setIp(ip);
		SocketIOManager.getDefaultStore().add(sessionId, client);
		//服务端与客户端建立链接对应关系
		this.handler.OnConnect(client);
	}

	public void doHandle(ChannelHandlerContext ctx, WebSocketFrame frame,
			MessageEvent e) {
		if ((frame instanceof CloseWebSocketFrame)) {
			this.handshaker
					.close(ctx.getChannel(), (CloseWebSocketFrame) frame);
			return;
		}
		if ((frame instanceof PingWebSocketFrame)) {
			ctx.getChannel().write(
					new PongWebSocketFrame(frame.getBinaryData()));
			return;
		}
		if (!(frame instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(String.format(
					"%s frame types not supported", new Object[] { frame
							.getClass().getName() }));
		}

		TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
		String content = textFrame.getText();

		GenericIO client = initGenericClient(ctx, null);
		if (client == null) {
			return;
		}

		String respContent = handleContent(client, content);

		client.sendEncoded(respContent);
	}

	protected String getSessionId(HttpRequest req) {
		String webSocketUrl = this.handshaker.getWebSocketUrl();

		return webSocketUrl.substring(webSocketUrl.lastIndexOf('/') + 1);
	}

	private String getTargetLocation(HttpRequest req, String sessionId) {
		return "ws://" + req.getHeader("Host") + "/socket.io/1/" + getId()
				+ "/" + sessionId;
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.transport.WebSocketTransport JD-Core Version:
 * 0.6.0
 */