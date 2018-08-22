package com.yongboy.socketio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yongboy.socketio.server.IOHandlerAbs;
import com.yongboy.socketio.server.SocketIOServer;

public class MainServer {
	private static final Logger logger = LoggerFactory.getLogger(MainServer.class);
	private IOHandlerAbs handler;
	private int socketIOPort;
//	private int flashSecurityPort = SocketIOManager.option.flash_policy_port;
	private SocketIOServer socketIOServer;
//	private FlashSecurityServer flashSecurityServer;

//	public MainServer(IOHandlerAbs handler, int socketIOPort, int flashSecurityPort) {
//		this.handler = handler;
//		this.socketIOPort = socketIOPort;
//		this.flashSecurityPort = flashSecurityPort;
//	}

	public MainServer(IOHandlerAbs handler, int socketIOPort) {
		this.handler = handler;
		this.socketIOPort = socketIOPort;
	}

	public void start() {
		logger.info("start the SocketIOServer with port : " + this.socketIOPort);
		
		this.socketIOServer = new SocketIOServer(this.handler,this.socketIOPort);
		this.socketIOServer.start();

//		if (SocketIOManager.option.flash_policy_server) {
//			logger.info("start the FlashSecurityServer with port : " + this.flashSecurityPort);
//			this.flashSecurityServer = new FlashSecurityServer(this.flashSecurityPort);
//			this.flashSecurityServer.start();
//		}

	}

	public void stop() {
		if (this.socketIOServer != null)
			this.socketIOServer.stop();
//		if (this.flashSecurityServer != null)
//			this.flashSecurityServer.stop();
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.MainServer JD-Core Version: 0.6.0
 */