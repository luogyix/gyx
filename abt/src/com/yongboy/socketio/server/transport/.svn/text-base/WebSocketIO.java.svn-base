package com.yongboy.socketio.server.transport;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yongboy.socketio.server.IOHandler;
import com.yongboy.socketio.server.SocketIOManager;
import com.yongboy.socketio.server.Transports;

public class WebSocketIO extends GenericIO {
	private static final Logger log = LoggerFactory.getLogger(WebSocketIO.class);
	private TranMsgObject tranMsgObject;

	public TranMsgObject getTranMsgObject() {
		return this.tranMsgObject;
	}

	public void setTranMsgObject(TranMsgObject tranMsgObject) {
		this.tranMsgObject = tranMsgObject;
	}

	public WebSocketIO(ChannelHandlerContext ctx, HttpRequest req, String uID) {
		super(ctx, req, uID);
	}

	public void heartbeat(IOHandler handler) {
		prepareHearbeat();
		scheduleClearTask(handler);
		SocketIOManager.schedule(new Runnable() {
			public void run() {
				Channel chan = ctx.getChannel();
				if (chan.isOpen()) {
					chan.write(new TextWebSocketFrame("2::"));
				}

				log.debug("emitting heartbeat for client " + getSessionID());
			}
		});
	}

	public void sendEncoded(String message) {
		this.queue.offer(message);
		if (!this.open)
			return;
		while (true) {
			String msg = (String) this.queue.poll();
			if (msg == null) {
				break;
			}
			log.debug("websocket writing " + msg + " for client "
					+ getSessionID());
			Channel chan = this.ctx.getChannel();
			if (chan.isOpen())
				chan.write(new TextWebSocketFrame(msg));
		}
	}

	public void sendDirect(String message) {
		if (!this.open) {
			log.debug("this.open is false");
			return;
		}

		log.debug("websocket writing " + message + " for client "
				+ getSessionID());
		Channel chan = this.ctx.getChannel();
		if (chan.isOpen())
			chan.write(new TextWebSocketFrame(message));
		else
			log.debug("chan.isOpen() is false");
	}

	public String getId() {
		return Transports.WEBSOCKET.getValue();
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.transport.WebSocketIO JD-Core Version: 0.6.0
 */