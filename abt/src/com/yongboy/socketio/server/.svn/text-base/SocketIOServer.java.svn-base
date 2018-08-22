package com.yongboy.socketio.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketIOServer {
	private static final Logger log = LoggerFactory.getLogger(SocketIOServer.class);
	private ServerBootstrap bootstrap;
	private Channel serverChannel;
	private int port;
	private boolean running;
	private IOHandlerAbs handler;

	public SocketIOServer(IOHandlerAbs handler, int port) {
		this.port = port;
		this.handler = handler;
		this.running = false;
	}

	public boolean isRunning() {
		return this.running;
	}

	public void start() {
		this.bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors
						.newCachedThreadPool()));

		this.bootstrap.setPipelineFactory(new ServerPipelineFactory(this.handler));

		this.bootstrap.setOption("child.reuseAddress", Boolean.valueOf(true));

		this.serverChannel = this.bootstrap.bind(new InetSocketAddress(this.port));
		
		this.running = true;

		log.info("Server Started at port [" + this.port + "]");
	}

	public void stop() {
		if (!this.running) {
			return;
		}
		log.info("Server shutting down.");
		this.handler.OnShutdown();
		this.serverChannel.close();
		this.bootstrap.releaseExternalResources();
		log.info("**SHUTDOWN**");
		this.running = false;
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.SocketIOServer JD-Core Version: 0.6.0
 */