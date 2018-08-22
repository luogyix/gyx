package com.yongboy.socketio.server.transport;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yongboy.socketio.server.IOHandler;
import com.yongboy.socketio.server.SocketIOManager;
import com.yongboy.socketio.server.Store;

abstract class EventClientIO implements IOClient {
	private static final Logger log = LoggerFactory.getLogger(SocketIOManager.class);
	public Map<String, Object> attr = null;
	protected final BlockingQueue<String> queue;
	protected ScheduledFuture<?> scheduledFuture;

	public EventClientIO() {
		this.attr = new HashMap<String,Object>();
		this.queue = new LinkedBlockingQueue<String>();
	}

	protected static class ClearTask implements Runnable {
		private String sessionId;
		private boolean clearSession = false;
		private IOHandler handler = null;

		public ClearTask(String sessionId, IOHandler handler) {
			this.sessionId = sessionId;
			this.handler = handler;
		}

		public ClearTask(String sessionId, IOHandler handler,
				boolean clearSession) {
			this(sessionId, handler);

			this.clearSession = clearSession;
		}

		public void run() {
			EventClientIO.log
					.debug("entry ClearTask run method clearSession is "
							+ this.clearSession + " and sessionId is "
							+ this.sessionId);
			Store store = SocketIOManager.getDefaultStore();
			IOClient client = store.get(this.sessionId);
			if (client == null) {
				EventClientIO.log.debug("the client is null");
				return;
			}

			if ((!this.clearSession) && (client.isOpen())) {
				client.setOpen(false);
			}

			if (!this.clearSession) {
				EventClientIO.log.debug("add new task ~");
				SocketIOManager.scheduleClearTask(new ClearTask(this.sessionId,
						this.handler, true));
				return;
			}

			if (client.isOpen()) {
				EventClientIO.log.debug("the client's open is "
						+ client.isOpen());
				return;
			}

			EventClientIO.log
					.info("now remove the clients from store with sessionid "
							+ this.sessionId);

			if (this.handler != null) {
				log.info("handler 不为空，调用OnDisconnect !");
				this.handler.OnDisconnect(client);
			} else {
				EventClientIO.log.info("ioHandler is null");
			}
			log.info("调用 disconnect!");
			client.disconnect();
			store.remove(this.sessionId);
		}
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.transport.EventClientIO JD-Core Version: 0.6.0
 */