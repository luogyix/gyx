package com.yongboy.socketio.server;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.jboss.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yongboy.socketio.server.transport.FlashSocketTransport;
import com.yongboy.socketio.server.transport.HtmlfileTransport;
import com.yongboy.socketio.server.transport.ITransport;
import com.yongboy.socketio.server.transport.JsonpPollingTransport;
import com.yongboy.socketio.server.transport.WebSocketTransport;
import com.yongboy.socketio.server.transport.XhrPollingTransport;

public enum Transports {
	XHRPOLLING("xhr-polling", XhrPollingTransport.class), JSONPP0LLING(
			"jsonp-polling", JsonpPollingTransport.class), HTMLFILE("htmlfile",
			HtmlfileTransport.class), WEBSOCKET("websocket",
			WebSocketTransport.class), FLASHSOCKET("flashsocket",
			FlashSocketTransport.class);
	private static final Logger logger = LoggerFactory.getLogger(Transports.class);
	private String value;
	private Class<? extends ITransport> transportClass;

	private Transports(String value, Class<? extends ITransport> transportClass) {
		this.value = value;
		this.transportClass = transportClass;
	}

	public String getValue() {
		return this.value;
	}

	public Class<? extends ITransport> getTransportClass() {
		return this.transportClass;
	}

	public String getUrlPattern() {
		return "/" + getValue() + "/";
	}

	public boolean checkPattern(String uri) {
		if (uri == null) {
			return false;
		}
		return uri.contains(getUrlPattern());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ITransport getTransportByReq(IOHandlerAbs handler,
			HttpRequest req) {
		if ((req == null) || (handler == null)) {
			return null;
		}
		String uri = req.getUri();

		Transports targetTransport = null;
		for (Transports tran : values()) {
			if (!tran.checkPattern(uri)) {
				continue;
			}
			targetTransport = tran;
			break;
		}

		if (targetTransport == null) {
			return null;
		}
		Class clazz = targetTransport.getTransportClass();
		Constructor constructor = null;
		try {
			constructor = clazz.getDeclaredConstructor(new Class[] {
					IOHandlerAbs.class, HttpRequest.class });
		} catch (SecurityException e) {
			logger.error(e.getMessage(),e);
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage(),e);
		}

		if (constructor == null)
			return null;
		try {
			return (ITransport) constructor.newInstance(new Object[] { handler,
					req });
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(),e);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(),e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(),e);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(),e);
		}

		return null;
	}

	public static Transports getByValue(String value) {
		if (value == null) {
			return null;
		}
		for (Transports tran : values()) {
			if (tran.value.equals(value)) {
				return tran;
			}
		}
		return null;
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.Transports JD-Core Version: 0.6.0
 */