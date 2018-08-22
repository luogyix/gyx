package com.yongboy.socketio.server;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketIOManager {
	public static Option option = new Option();
	private static final Logger log = LoggerFactory.getLogger(SocketIOManager.class);

	private static final ScheduledExecutorService scheduledExecutorService = Executors
			.newScheduledThreadPool(1);

	public static final Set<String> fobbiddenEvents = new HashSet<String>(
			Arrays.asList("message,connect,disconnect,open,close,error,retry,reconnect"
					.split(",")));

	private static Store store = new MemoryStore();

	public static Store getDefaultStore() {
		return store;
	}

	public static void schedule(Runnable runnable) {
		scheduledExecutorService.schedule(runnable, option.heartbeat_interval,
				TimeUnit.SECONDS);
	}

	public static String getHandshakeResult() {
		return "%s:"
				+ (option.heartbeat ? Integer
						.toString(option.heartbeat_timeout) : "") + ":"
				+ option.close_timeout + ":" + option.transports;
	}

	public static HttpResponse getInitResponse(HttpRequest req) {
		return getInitResponse(req, HttpResponseStatus.OK);
	}

	public static HttpResponse getInitResponse(HttpRequest req,
			HttpResponseStatus status) {
		HttpResponse resp = new DefaultHttpResponse(HttpVersion.HTTP_1_1,
				status);
		log.info("设置回复报文头信息开始");
		if ((req != null) && (req.getHeader("Origin") != null)) {
			resp.addHeader("Access-Control-Allow-Origin",
					req.getHeader("Origin"));
			resp.addHeader("Access-Control-Allow-Credentials", "true");
		}

		return resp;
	}

	public static ScheduledFuture<?> scheduleClearTask(Runnable runnable) {
		return scheduledExecutorService.schedule(runnable,
				option.heartbeat_timeout, TimeUnit.SECONDS);
	}

	public static String getFileName(String filename) {
		if (filename == null) {
			return null;
		}
		int index = indexOfLastSeparator(filename);
		return filename.substring(index + 1);
	}

	private static int indexOfLastSeparator(String filename) {
		if (filename == null) {
			return -1;
		}
		int lastUnixPos = filename.lastIndexOf('/');
		int lastWindowsPos = filename.lastIndexOf('\\');
		return Math.max(lastUnixPos, lastWindowsPos);
	}

	public static final class Option {
		public boolean heartbeat;
		public int heartbeat_timeout;
		public int close_timeout;
		public int heartbeat_interval;
		public boolean flash_policy_server;
		public int flash_policy_port;
		public String transports;
		public String Static;

		public Option() {
			this.heartbeat = true;
			this.heartbeat_timeout = 60;
			this.close_timeout = 60;
			this.heartbeat_interval = 25;
			this.flash_policy_server = true;
			this.flash_policy_port = 10843;
			this.transports = "websocket,jsonp-polling,xhr-polling";
			this.Static = "static";

			ResourceBundle bundle = ResourceBundle.getBundle("socketio");

			this.heartbeat = bundle.getString("heartbeat").equals("true");
			this.heartbeat_timeout = Integer.parseInt(bundle
					.getString("heartbeat_timeout"));
			this.close_timeout = Integer.parseInt(bundle
					.getString("close_timeout"));
			this.heartbeat_interval = Integer.parseInt(bundle
					.getString("heartbeat_interval"));
			this.flash_policy_server = bundle.getString("flash_policy_server")
					.equals("true");
			this.flash_policy_port = Integer.parseInt(bundle
					.getString("flash_policy_port"));
			this.transports = bundle.getString("transports");
			this.Static = bundle.getString("static");
		}
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.SocketIOManager JD-Core Version: 0.6.0
 */