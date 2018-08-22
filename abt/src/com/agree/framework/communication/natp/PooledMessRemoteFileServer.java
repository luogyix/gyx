package com.agree.framework.communication.natp;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PooledMessRemoteFileServer {
	protected int maxConn;

	protected int port;
	
	protected int pools;
	private static ServerSocket server = null;
	private static final Logger logger = LoggerFactory.getLogger(PooledMessRemoteFileServer.class);
	public PooledMessRemoteFileServer() {
		super();
	}

	public PooledMessRemoteFileServer(int port, int pools,int maxConn) {
		super();
		this.maxConn = maxConn;
		this.port = port;
		this.pools = pools;
	}

	public int getMaxConn() {
		return maxConn;
	}

	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getPools() {
		return pools;
	}

	public void setPools(int pools) {
		this.pools = pools;
	}

	public void acceptConnections(int backlog) {
		
		Socket incomingConnection = null;
		server = null;
		try
		{
			server = new ServerSocket(port, backlog);
			while (true) {
				incomingConnection = server.accept();
				handleConnection(incomingConnection);
			}
		} 
		catch (BindException e) 
		{
			logger.error(e.getMessage(),e);
		}
		catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		finally{
			if(incomingConnection != null)
			{
				try {
					incomingConnection.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
			if(server != null)
			{
				try {
					server.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
		}
	}

	protected void handleConnection(Socket connectionToHandle) {
		PooledMessConnectionHandler.processRequest(connectionToHandle);
	}

	public void setUpHandlers() {
		for (int i = 0; i < pools; i++) {
			PooledMessConnectionHandler currentHandler = new PooledMessConnectionHandler();
			new Thread(currentHandler, "Handler " + i).start();
		}
	}

	public boolean start() {
		new Thread() {
			public void run() {
				PooledMessRemoteFileServer server = new PooledMessRemoteFileServer(port, pools,maxConn);
				server.setUpHandlers();
				server.acceptConnections(maxConn);
					
			}
		}.start();
		return true;
	}
	
	public void stop()
	{
		if(server != null)
		{
			try {
				server.close();
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
	}
}