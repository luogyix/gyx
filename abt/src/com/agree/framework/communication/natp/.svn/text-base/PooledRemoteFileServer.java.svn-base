package com.agree.framework.communication.natp;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PooledRemoteFileServer {
	protected int maxConn;

	protected int port;
	
	protected int pools;
	private static final Logger logger = LoggerFactory.getLogger(PooledRemoteFileServer.class);
	private Thread sothread = null;
	private static ServerSocket server = null;
	public PooledRemoteFileServer() {
		super();
	}

	public PooledRemoteFileServer(int port, int pools,int maxConn) {
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
		
		try {
			server = new ServerSocket(port, backlog);
			while (true) {
				incomingConnection = server.accept();
				handleConnection(incomingConnection);
			}
		} 
		catch (BindException e) 
		{
			logger.error(e.getMessage(),e);
		} catch (IOException e)
		{
			logger.error(e.getMessage(),e);
		}
		finally
		{
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
		PooledConnectionHandler.processRequest(connectionToHandle);
	}

	public void setUpHandlers() {
		for (int i = 0; i < pools; i++) {
			PooledConnectionHandler currentHandler = new PooledConnectionHandler();
			new Thread(currentHandler, "Handler " + i).start();
		}
	}

	public boolean start() {
		sothread = new Thread() {
			public void run() {
				PooledRemoteFileServer server = new PooledRemoteFileServer(port, pools,maxConn);
				server.setUpHandlers();
				server.acceptConnections(maxConn);
					
			}
		};
		sothread.start();
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
