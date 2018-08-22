package com.yongboy.socketio.server.transport;

import org.jboss.netty.channel.ChannelHandlerContext;

import com.yongboy.socketio.server.IOHandler;

public class BlankIO implements IOClient {
	private static BlankIO blankIO = null;

	public static BlankIO getInstance() {
		if (blankIO == null) {
			blankIO = new BlankIO();
		}
		return blankIO;
	}

	public void send(String message) {
	}

	public void sendEncoded(String message) {
	}

	public void heartbeat(IOHandler ioHandler) {
	}

	public void disconnect() {
	}

	public String getSessionID() {
		return null;
	}

	public ChannelHandlerContext getCTX() {
		return null;
	}

	public String getId() {
		return null;
	}

	public boolean isOpen() {
		return false;
	}

	public void setOpen(boolean open) {
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.transport.BlankIO JD-Core Version: 0.6.0
 */