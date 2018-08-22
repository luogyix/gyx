package com.yongboy.socketio.server.transport;

import java.util.concurrent.TimeUnit;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.timeout.ReadTimeoutHandler;
import org.jboss.netty.util.CharsetUtil;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yongboy.socketio.server.IOHandler;
import com.yongboy.socketio.server.SocketIOManager;
import com.yongboy.socketio.server.Transports;

public class XhrIO extends GenericIO {
	private static final Logger log = LoggerFactory.getLogger(XhrIO.class);
	private static final Timer timer = new HashedWheelTimer();

	private TranMsgObject tranMsgObject;

	public TranMsgObject getTranMsgObject() {
		return this.tranMsgObject;
	}

	public void setTranMsgObject(TranMsgObject tranMsgObject) {
		this.tranMsgObject = tranMsgObject;
	}

	public XhrIO(ChannelHandlerContext ctx, HttpRequest req, String uID) {
		super(ctx, req, uID);
		ctx.getPipeline()
				.addFirst("timeout", new ReadTimeoutHandler(timer, 20));
	}

	private void _write(String message) {
		HttpResponse res = SocketIOManager.getInitResponse(this.req);

		res.addHeader("Content-Type", "text/plain; charset=UTF-8");

		res.setContent(ChannelBuffers.copiedBuffer(message, CharsetUtil.UTF_8));
		HttpHeaders.setContentLength(res, res.getContent().readableBytes());

		res.addHeader("Connection", "keep-alive");

		Channel chan = this.ctx.getChannel();
		if (chan.isOpen()) {
			ChannelFuture f = chan.write(res);
			f.addListener(ChannelFutureListener.CLOSE);
		} else {
			this.queue.offer(message);
		}
	}

	public void sendEncoded(String message) {
		log.debug("the message is " + message + " been into queue!");
		this.queue.offer(message);
	}

	private void sendDirectMessage(String message) {
		if (this.open) {
			log.debug("send message is " + message);
			try {
				_write(message);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				this.queue.offer(message);
			}
			this.open = false;
		} else {
			log.debug("this.open is false, push the message into queue");
			this.queue.offer(message);
		}
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
		res.addHeader("Content-Type", "text/plain; charset=UTF-8");
		res.addHeader("Connection", "keep-alive");

		chan.write(res);

		String message = null;
		try {
			// add by zsz 增加队列取数的时间，每十秒取一次
			message = (String) this.queue.poll(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}

		if (message == null)
			message = "8::";
		try {
			chan.write(ChannelBuffers.copiedBuffer(message, CharsetUtil.UTF_8))
					.addListener(ChannelFutureListener.CLOSE);
		} catch (Exception e) {
			log.error("java.io.IOException: 远程主机强迫关闭了一个现有的连接。",e);
		}

		scheduleClearTask(handler);
	}

	/**
	 * connect 交易上来，需要得到message,构建用户对像
	 */
	public void connect(String message) {
		log.info("收到手持链接！信息==" + message);
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
		return Transports.XHRPOLLING.getValue();
	}
}
