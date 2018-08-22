package com.yongboy.socketio.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelFutureProgressListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.DefaultFileRegion;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.FileRegion;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.jboss.netty.handler.ssl.SslHandler;
import org.jboss.netty.handler.stream.ChunkedFile;
import org.jboss.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yongboy.socketio.server.transport.BlankIO;
import com.yongboy.socketio.server.transport.GenericIO;
import com.yongboy.socketio.server.transport.IOClient;
import com.yongboy.socketio.server.transport.ITransport;

public class SocketIOTransportAdapter extends SimpleChannelUpstreamHandler {
	private static final Logger logger = LoggerFactory.getLogger(SocketIOTransportAdapter.class);
	private IOHandlerAbs handler;
	private ITransport currentTransport = null;

	public SocketIOTransportAdapter(IOHandlerAbs handler) {
		this.handler = handler;
	}

	private String getUniqueID() {
		return UUID.randomUUID().toString();
	}

	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		if (this.currentTransport == null) {
			return;
		}

		logger.debug("this.currentTransport.id " + this.currentTransport.getId());
		if ("websocket,flashsocket,htmlfile".contains(this.currentTransport
				.getId())) {
			logger.debug("going to clear client~");
			Store store = SocketIOManager.getDefaultStore();
			String sessionId = this.currentTransport.getSessionId();
			IOClient client = store.get(sessionId);
			if (client == null) {
				logger.debug("client had been removed by session id " + sessionId);
				return;
			}

			if ((client instanceof GenericIO)) {
				GenericIO genericIO = (GenericIO) client;
				genericIO.scheduleRemoveTask(this.handler);
			}
		}

		logger.debug("client is not null");
	}

	public void disconnect(IOClient client) {
		client.disconnect();
		SocketIOManager.getDefaultStore().remove(client.getSessionID());

		this.handler.OnDisconnect(client);
	}

	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		Object msg = e.getMessage();
		InetSocketAddress sa = (InetSocketAddress) e.getRemoteAddress();
		sa.toString();
		if ((msg instanceof HttpRequest)) {
			logger.info("得到是的http请求！！！");
			logger.info(((HttpRequest) msg).getMethod().getName()
					+ " request uri " + ((HttpRequest) msg).getUri());
			handleHttpRequest(ctx, (HttpRequest) msg, e);
			return;
		}

		if ((msg instanceof WebSocketFrame)) {
			if (this.currentTransport != null)
				this.currentTransport.doHandle(ctx, (WebSocketFrame) msg, e);
			else
				logger.warn("currentTransport is null, do nothing ...");
		}
	}

	private void handleHttpRequest(ChannelHandlerContext ctx, HttpRequest req,
			MessageEvent e) throws Exception {
		String reqURI = req.getUri();

		logger.info("接收到了请求" + req.getMethod().getName());
		logger.info(req.getMethod().getName() + " request uri " + reqURI);

		if ((reqURI.equals("/")) || (reqURI.endsWith(".js"))
				|| (reqURI.endsWith(".swf"))
				|| (reqURI.toLowerCase().endsWith(".css"))
				|| (reqURI.toLowerCase().endsWith(".htm"))
				|| (reqURI.toLowerCase().endsWith(".html"))) {
			handleStaticRequest(req, e, reqURI);
			return;
		}

		if (reqURI.matches("/.*/\\d{1}/([^/]*)?")) {
			handleHandshake(req, e, reqURI);
			return;
		}
		// 反射得到请处理类
		if (this.currentTransport == null) {
			this.currentTransport = Transports.getTransportByReq(this.handler,
					req);
		}
		// 首次握手成功处理的到处理类，并处理http请求。如果是websocket请求会调用websocketTransports的doHandle处理请求。
		// 其他的请求都会调用Itransprot的doHandle
		if (this.currentTransport != null) {
			this.currentTransport.doHandle(ctx, req, e);
			return;
		}
		// 如果没有的到相应的处理类，则返回禁用状态。
		sendHttpResponse(ctx, req, SocketIOManager.getInitResponse(req,
				HttpResponseStatus.FORBIDDEN));
	}

	/**
	 * 
	 * 握手处理服务端返回给客户端信息（包括sessionid，心跳时间，客户端关闭时间），如果是jsonp格式的需要特殊处理
	 * 此时记录一个空的client在内存中
	 * */
	private void handleHandshake(HttpRequest req, MessageEvent e, String reqURI) {
		logger.info("进入handleHandshake方法");
		HttpResponse resp = SocketIOManager.getInitResponse(req);
		resp.addHeader("Content-Type", "text/plain; charset=UTF-8");

		final String uID = getUniqueID();
		// 返回给客户端sessionid，格式如273cef46-b539-426b-9f8f-2d5e23a4d503:20:60:websocket,flashsocket,htmlfile,xhr-polling,jsonp-polling
		String contentString = String.format(
				SocketIOManager.getHandshakeResult(), new Object[] { uID });

		QueryStringDecoder queryStringDecoder = new QueryStringDecoder(reqURI);

		String jsonpValue = getParameter(queryStringDecoder, "jsonp");

		if (jsonpValue != null) {
			logger.debug("request uri with parameter jsonp = " + jsonpValue);
			contentString = "io.j[" + jsonpValue + "]('" + contentString
					+ "');";
			resp.addHeader("Content-Type", "application/javascript");
		}

		ChannelBuffer content = ChannelBuffers.copiedBuffer(contentString,
				CharsetUtil.UTF_8);

		resp.addHeader("Connection", "keep-alive");
		resp.setContent(content);

		e.getChannel().write(resp).addListener(ChannelFutureListener.CLOSE);

		Store store = SocketIOManager.getDefaultStore();
		// 首次握手创立一个空client
		store.add(uID, BlankIO.getInstance());

		SocketIOManager.schedule(new Runnable() {
			public void run() {
				Store store = SocketIOManager.getDefaultStore();
				IOClient client = store.get(uID);
				if (client == null)
					store.remove(uID);
			}
		});
	}

	private static String getParameter(QueryStringDecoder queryStringDecoder,
			String parameterName) {
		if ((queryStringDecoder == null) || (parameterName == null)) {
			return null;
		}
		List<String> values = (List<String>) queryStringDecoder.getParameters().get(
				parameterName);

		if ((values == null) || (values.isEmpty())) {
			return null;
		}
		return values.get(0);
	}

	private void handleStaticRequest(HttpRequest req, MessageEvent e,
			String reqURI) throws IOException {
		logger.info("进入handleStaticRequest方法");
		String fileName = null;

		if (req.getUri().indexOf("/socket.io/") != -1)
			fileName = SocketIOManager.getFileName(req.getUri());
		else {
			fileName = req.getUri();
		}

		if ((fileName == null) || (fileName.trim().equals("/"))
				|| (fileName.trim().equals(""))) {
			fileName = "index.html";
		}

		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getResource("/").toString()).append(
				SocketIOManager.option.Static);

		if (!fileName.startsWith("/")) {
			sb.append("/");
		}
		sb.append(fileName);
		if (sb.indexOf("file:/") != -1) {
			sb.delete(0, 6);
		}

		if (sb.indexOf("/") != 0) {
			sb.insert(0, "/");
		}

		logger.debug("contentPath : " + sb);

		File file = new File(sb.toString());
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException fnfe) {
			logger.error(fnfe.getMessage(), fnfe);
			HttpResponse response = new DefaultHttpResponse(
					HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
			e.getChannel().write(response)
					.addListener(ChannelFutureListener.CLOSE);
			return;
		}
		long fileLength = raf.length();

		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1,
				HttpResponseStatus.OK);
		HttpHeaders.setContentLength(response, fileLength);

		Channel ch = e.getChannel();
		ch.write(response);
		ChannelFuture writeFuture;

		if (ch.getPipeline().get(SslHandler.class) != null) {
			writeFuture = ch.write(new ChunkedFile(raf, 0L, fileLength, 8192));
		} else {
			final FileRegion region = new DefaultFileRegion(raf.getChannel(),
					0, fileLength);
			writeFuture = ch.write(region);
			writeFuture.addListener(new ChannelFutureProgressListener() {
				public void operationComplete(ChannelFuture future) {
					region.releaseExternalResources();
				}

				public void operationProgressed(ChannelFuture future,
						long amount, long current, long total) {
				}
			});
		}
		if (!HttpHeaders.isKeepAlive(req))
			writeFuture.addListener(ChannelFutureListener.CLOSE);
	}

	private void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest req,
			HttpResponse res) {
		if (res.getStatus().getCode() != 200) {
			res.setContent(ChannelBuffers.copiedBuffer(res.getStatus()
					.toString(), CharsetUtil.UTF_8));
			HttpHeaders.setContentLength(res, res.getContent().readableBytes());
		}

		ChannelFuture f = ctx.getChannel().write(res);
		if ((!HttpHeaders.isKeepAlive(req))
				|| (res.getStatus().getCode() != 200))
			f.addListener(ChannelFutureListener.CLOSE);
	}

	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		logger.debug("exceptionCaught now ...");
		if (this.currentTransport == null) {
			return;
		}

		Store store = SocketIOManager.getDefaultStore();
		String sessionId = this.currentTransport.getSessionId();
		IOClient client = store.get(sessionId);
		if (client == null) {
			logger.info("client had been removed by session id " + sessionId);
			return;
		}

		if ((client instanceof GenericIO)) {
			GenericIO genericIO = (GenericIO) client;
			genericIO.scheduleRemoveTask(this.handler);
		}

		logger.error("ERROR !",e.getCause());
		e.getChannel().close();
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.SocketIOTransportAdapter JD-Core Version: 0.6.0
 */