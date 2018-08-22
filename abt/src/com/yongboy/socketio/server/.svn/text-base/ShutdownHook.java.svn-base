package com.yongboy.socketio.server;

import com.yongboy.socketio.MainServer;

public class ShutdownHook extends Thread {
	private MainServer server;

	public ShutdownHook(MainServer mainServer) {
		this.server = mainServer;
	}

	public void run() {
		this.server.stop();
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.ShutdownHook JD-Core Version: 0.6.0
 */