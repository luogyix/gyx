package com.yongboy.socketio.server.transport;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.jboss.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yongboy.socketio.server.IOHandler;
import com.yongboy.socketio.server.SocketIOManager;
import com.yongboy.socketio.server.Transports;

public class JsonpIO extends GenericIO {
	private static final Logger log = LoggerFactory.getLogger(JsonpIO.class);
	public JsonpIO(ChannelHandlerContext ctx, HttpRequest req, String uID) {
		super(ctx, req, uID);
	}

	private void _write(String message) {
		HttpResponse res = SocketIOManager.getInitResponse(this.req);

		res.addHeader("Content-Type", "text/javascript; charset=UTF-8");

		res.setContent(ChannelBuffers.copiedBuffer(message, CharsetUtil.UTF_8));
		HttpHeaders.setContentLength(res, res.getContent().readableBytes());

		res.addHeader("Connection", "keep-alive");
		res.addHeader("X-XSS-Protection", "0");

		Channel chan = this.ctx.getChannel();
		if (chan.isOpen()) {
			ChannelFuture f = chan.write(res);
			f.addListener(ChannelFutureListener.CLOSE);
		} else {
			this.queue.offer(message);
		}
	}

	public void sendEncoded(String message) {
		this.queue.offer(message);
	}

	private void sendDirectMessage(String message) {
		if (!this.open) {
			this.queue.offer(message);
			return;
		}
		try {
			String iString = getTargetFormatMessage(message);
			_write(iString);
		} catch (Exception e) {
			log.info("Exception " + e.toString(),e);
			this.queue.offer(message);
		}

		this.open = false;
	}

	public void heartbeat(IOHandler handler) {
		if (!this.open) {
			scheduleClearTask(handler);
			return;
		}

		prepareHearbeat();

		Channel chan = this.ctx.getChannel();
		if (!chan.isOpen()) {
			this.open = false;
			scheduleClearTask(handler);
			return;
		}

		HttpResponse res = SocketIOManager.getInitResponse(this.req);
		res.addHeader("Content-Type", "text/javascript; charset=UTF-8");
		res.addHeader("Connection", "keep-alive");
		res.addHeader("X-XSS-Protection", "0");
		chan.write(res);

		String message = null;
		try {
			message = (String) this.queue.poll(19996L, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}

		if (message == null) {
			message = "8::";
		}

		String templateMessage = getTargetFormatMessage(message);

		chan
				.write(
						ChannelBuffers.copiedBuffer(templateMessage,
								CharsetUtil.UTF_8)).addListener(
						ChannelFutureListener.CLOSE);

		scheduleClearTask(handler);
	}

	private String getTargetFormatMessage(String message) {
		QueryStringDecoder queryStringDecoder = new QueryStringDecoder(this.req
				.getUri());

		List<String> paras = (List<String>) queryStringDecoder.getParameters().get("i");
		String iString = null;
		if ((paras == null) || (paras.isEmpty()))
			iString = "0";
		else {
			iString = (String) paras.get(0);
		}

		log.debug("format json message : "
				+ String.format("io.j[%s]('%s');", new Object[] { iString,
						message }));

		return String.format("io.j[%s]('%s');",
				new Object[] { iString, message });
	}

	public void connect(String message) {
		sendDirectMessage("1::");
	}

	public void disconnect() {
		super.disconnect();

		this.open = false;
	}

	public void disconnect(String info) {
		super.disconnect(info);

		this.open = false;
	}

	public String getId() {
		return Transports.JSONPP0LLING.getValue();
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.transport.JsonpIO JD-Core Version: 0.6.0
 */