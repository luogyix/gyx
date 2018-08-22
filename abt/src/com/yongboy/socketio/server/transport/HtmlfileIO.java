package com.yongboy.socketio.server.transport;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.DefaultHttpChunk;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;

import com.yongboy.socketio.server.IOHandler;
import com.yongboy.socketio.server.SocketIOManager;
import com.yongboy.socketio.server.Transports;

public class HtmlfileIO extends GenericIO {
	private static final String TEMPLATE = "<script>_('%s');</script>";

	public HtmlfileIO(ChannelHandlerContext ctx, HttpRequest req, String uID) {
		super(ctx, req, uID);
	}

	public void heartbeat(IOHandler handler) {
		prepareHearbeat();

		scheduleClearTask(handler);

		SocketIOManager.schedule(new Runnable() {
			public void run() {
				__write(String.format(TEMPLATE, "2::"));
			}
		});
	}

	private void __write(String message) {
		if (!this.open) {
			return;
		}
		Channel chan = this.ctx.getChannel();
		if (chan.isOpen())
			writeStringChunk(chan, message);
	}

	private void _write(String message) {
		if (!this.open) {
			return;
		}
		HttpResponse response = SocketIOManager.getInitResponse(this.req);
		response.setChunked(true);
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		response.addHeader("Connection", "keep-alive");
		response.setHeader("Transfer-Encoding", "chunked");

		Channel chan = this.ctx.getChannel();
		chan.write(response);

		writeStringChunk(chan, message);
	}

	private void writeStringChunk(Channel channel, String data) {
		ChannelBuffer chunkContent = ChannelBuffers.dynamicBuffer(channel
				.getConfig().getBufferFactory());
		chunkContent.writeBytes(data.getBytes());
		HttpChunk chunk = new DefaultHttpChunk(chunkContent);

		channel.write(chunk);
	}

	public void sendEncoded(String message) {
		this.queue.offer(message);
		if (!this.open) {
			return;
		}
		while (true) {
			String msg = (String) this.queue.poll();
			if (msg == null) {
				break;
			}
			__write(String.format("<script>_('%s');</script>",
					new Object[] { msg.replaceAll("\"", "\\\"") }));
		}
	}

	public void prepare() {
		StringBuilder builder = new StringBuilder();
		builder
				.append("<html><body><script>var _ = function (msg) { parent.s._(msg, document); };</script>");
		int leftChars = 256 - builder.length();
		for (int i = 0; i < leftChars; i++) {
			builder.append(" ");
		}

		_write(builder.toString());
	}

	public void disconnect(String info) {
		sendEncoded("0::");

		this.open = false;
	}

	public String getId() {
		return Transports.HTMLFILE.getValue();
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.transport.HtmlfileIO JD-Core Version: 0.6.0
 */