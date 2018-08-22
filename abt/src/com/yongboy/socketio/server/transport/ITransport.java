package com.yongboy.socketio.server.transport;

import java.util.ArrayList;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.jboss.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yongboy.socketio.server.IOHandlerAbs;
import com.yongboy.socketio.server.SocketIOManager;
import com.yongboy.socketio.server.Store;

public abstract class ITransport {
	private static final Logger logger = LoggerFactory.getLogger(ITransport.class);
	protected IOHandlerAbs handler;
	protected Store store;
	protected HttpRequest req;
	private static String SPLIT_CHAR = String.valueOf(65533);

	public ITransport(IOHandlerAbs handler, HttpRequest req) {
		this.handler = handler;
		this.req = req;

		this.store = SocketIOManager.getDefaultStore();
	}

	public boolean check(String uri) {
		return uri.contains("/" + getId() + "/");
	}

	public abstract String getId();

	protected abstract GenericIO doPrepareI0Client(
			ChannelHandlerContext paramChannelHandlerContext,
			HttpRequest paramHttpRequest, String paramString);

	public void doHandle(ChannelHandlerContext ctx, WebSocketFrame frame,
			MessageEvent e) {
	}

	/**
	 * 处理链接上来的http请求，并返回消息处理内容
	 * 首次请求上来的链接可能是http请求，切该次请求也有这个方法处理。
	 * 
	 * 
	 * */
	public void doHandle(ChannelHandlerContext ctx, HttpRequest req,
			MessageEvent e) {

		String sessionId = getSessionId(req);
		if (!this.store.checkExist(sessionId)) {
			handleInvalidRequest(req, e);
			return;
		}

		GenericIO client = null;
		try {
			client = (GenericIO) this.store.get(sessionId);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		String reqURI = req.getUri();

		if ((reqURI.contains("?disconnect"))
				|| (reqURI.contains("&disconnect"))) {
			logger.debug("the request uri contains the 'disconnect' paremeter");
			client.disconnect();

			return;
		}

		boolean isNew = false;
		if (client == null) {
			logger.debug("the client is null with id : " + sessionId);
			client = doPrepareI0Client(ctx, req, sessionId);

			this.store.add(sessionId, client);
			this.handler.OnConnect(client);
			isNew = true;
		}

		if (!reqURI.contains("/" + client.getId() + "/")) {
			this.store.remove(sessionId);

			this.store.add(sessionId, BlankIO.getInstance());
			doHandle(ctx, req, e);
			return;
		}

		if (req.getMethod() == HttpMethod.GET) {
			if (!isNew) {
				client.reconnect(ctx, req);
				client.heartbeat(this.handler);
			}
			logger.debug("生成client会后，建立连接等待请求");
			return;
		}

		if (req.getMethod() != HttpMethod.POST) {
			logger.debug("the request method " + req.getMethod());
			return;
		}

		Channel channel = e.getChannel();
		if ((channel == null) || (!channel.isOpen())) {
			client.disconnect();
			return;
		}

		ChannelBuffer buffer = req.getContent();
		String content = buffer.toString(CharsetUtil.UTF_8);

		String respContent = handleContent(client, content);

		HttpResponse resp = SocketIOManager.getInitResponse(req);
		resp.setContent(ChannelBuffers.copiedBuffer(respContent,
				CharsetUtil.UTF_8));
		resp.addHeader("Connection", "keep-alive");
		HttpHeaders.setContentLength(resp, resp.getContent().readableBytes());

		e.getChannel().write(resp).addListener(ChannelFutureListener.CLOSE);
	}

	private void handleInvalidRequest(HttpRequest req, MessageEvent e) {
		HttpResponse resp = SocketIOManager.getInitResponse(req,
				HttpResponseStatus.FORBIDDEN);

		e.getChannel().write(resp).addListener(ChannelFutureListener.CLOSE);
	}
	/**
	 * 初始化客户端
	 * 
	 * */
	protected GenericIO initGenericClient(ChannelHandlerContext ctx,
			HttpRequest req) {
		String sessionId = getSessionId(req);

		GenericIO client = null;
		try {
			client = (GenericIO) this.store.get(sessionId);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		if (client == null) {
			logger.debug("initGenericClient the client is null with id : "
					+ sessionId);
			client = doPrepareI0Client(ctx, req, sessionId);

			this.store.add(sessionId, client);
			this.handler.OnConnect(client);
		}

		return client;
	}

	@SuppressWarnings("rawtypes")
	protected String handleContent(GenericIO client, String content) {
		if (content.startsWith("d=")) {
			logger.debug("post with parameter d");

			QueryStringDecoder queryStringDecoder = new QueryStringDecoder(
					content, CharsetUtil.UTF_8, false, 2);

			content = (String) ((List) queryStringDecoder.getParameters().get(
					"d")).get(0);
			if (content.startsWith("\"")) {
				content = content.substring(1);
			}

			if (content.endsWith("\"")) {
				content = content.substring(0, content.length() - 1);
			}
		}

		if (content == null)
			content = "";
		else {
			content = content.trim();
		}

		List<String> contentList = null;
		if (content.startsWith(SPLIT_CHAR)) {
			contentList = getSplitResults(content);
		} else {
			contentList = new ArrayList<String>(1);
			contentList.add(content);
		}

		String respContent = null;
		for (String subContent : contentList) {
			if (subContent.startsWith("5:")) {
				this.handler.OnMessage(client, subContent);

				respContent = "1";
			} else if (subContent.equals("2::")) {
				client.heartbeat(this.handler);
				respContent = "1";
			} else if ((subContent.startsWith("0::"))
					|| (subContent.equals("0::"))) {
				this.handler.OnDisconnect(client);

				respContent = subContent;
			}

			if (respContent == null) {
				respContent = "1";
			}

			if (respContent.equals("11")) {
				respContent = "1";
			}
		}

		return respContent;
	}

	private static List<String> getSplitResults(String ori) {
		List<String> list = new ArrayList<String>();
		String[] results = ori.split(SPLIT_CHAR + "\\d{1,}" + SPLIT_CHAR);

		for (String d : results) {
			if (d.equals("")) {
				continue;
			}
			list.add(d);
		}

		return list;
	}

	@Deprecated
	protected String getSessionId(HttpRequest req) {
		String reqURI = req.getUri();
		String[] parts = reqURI.substring(1).split("/");
		String sessionId = parts.length > 3 ? parts[3] : "";
		if (sessionId.indexOf("?") != -1) {
			sessionId = sessionId.replaceAll("\\?.*", "");
		}

		return sessionId;
	}

	public String getSessionId() {
		String reqURI = this.req.getUri();
		String[] parts = reqURI.substring(1).split("/");
		String sessionId = parts.length > 3 ? parts[3] : "";
		if (sessionId.indexOf("?") != -1) {
			sessionId = sessionId.replaceAll("\\?.*", "");
		}

		return sessionId;
	}

	protected void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest req,
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
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.transport.ITransport JD-Core Version: 0.6.0
 */