package com.yongboy.socketio.server.transport;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yongboy.socketio.server.IOHandler;
import com.yongboy.socketio.server.SocketIOManager;

public abstract class GenericIO extends EventClientIO implements IOClient {
	private static final Logger log = LoggerFactory.getLogger(GenericIO.class);
	protected ChannelHandlerContext ctx;
	protected int beat;
	protected String uID;
	private String ip;
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	protected boolean open = false;
	protected HttpRequest req;

	public GenericIO(ChannelHandlerContext ctx, HttpRequest req, String uID) {
		this.ctx = ctx;
		this.req = req;
		this.uID = uID;
		this.open = true;
	}

	public void reconnect(ChannelHandlerContext ctx, HttpRequest req) {
		this.ctx = ctx;
		this.req = req;

		this.open = true;
	}

	public void send(String message) {
		sendEncoded(message);
	}

	public void heartbeat(IOHandler handler) {
		prepareHearbeat();

		scheduleClearTask(handler);

		sendEncoded("2::");
	}

	protected void scheduleClearTask(IOHandler handler) {
		log.debug("scheduleClearTask被调用");
		this.scheduledFuture = SocketIOManager
				.scheduleClearTask(new EventClientIO.ClearTask(getSessionID(),
						handler));
	}

	public void scheduleRemoveTask(IOHandler handler) {
		log.debug("scheduleRemoveTask被调用");
		this.scheduledFuture = SocketIOManager
				.scheduleClearTask(new EventClientIO.ClearTask(getSessionID(),
						handler, true));
	}

	protected void prepareHearbeat() {
		if (this.beat > 0) {
			this.beat += 1;
		}

		log
				.debug("scheduledFuture is null ? "
						+ (this.scheduledFuture == null));
		if (this.scheduledFuture != null)
			if ((!this.scheduledFuture.isCancelled())
					&& (!this.scheduledFuture.isDone())) {
				log.debug("going to cancel the task ~");
				this.scheduledFuture.cancel(true);
			} else {
				log.debug("scheduledFuture had been canceled");
			}
	}

	public ChannelHandlerContext getCTX() {
		return this.ctx;
	}

	public String getSessionID() {
		return this.uID;
	}

	public void disconnect() {
		log.info("调用disconnect开始，关闭链接！");
		Channel chan = this.ctx.getChannel();
		if (chan.isOpen()) {
			log.info("关闭jboss链接！！");
			chan.close();
		}
		this.open = false;
	}

	public abstract void sendEncoded(String paramString);

	public void prepare() {
	}

	public void connect(String info) {
		sendEncoded("1::");
	}

	public void disconnect(String info) {
		sendEncoded("0::");
	}

	public boolean isOpen() {
		return this.open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int hashCode() {
		return getSessionID().hashCode();
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		IOClient objClient = (IOClient) obj;

		return (getId().equals(objClient.getId()))
				&& (getSessionID().equals(objClient.getSessionID()));
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.transport.GenericIO JD-Core Version: 0.6.0
 */