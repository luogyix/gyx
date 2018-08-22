package com.agree.framework.communication.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.communication.natp.PooledMessRemoteFileServer;

public class MessServer{
	private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);
	public static int port = 9981;
	public int pools;
	public int maxConn;
	private PooledMessRemoteFileServer sock = null;
	public static int getPort() {
		return port;
	}

	public void setPort(int port) {
		MessServer.port = port;
	}

	public int getPools() {
		return pools;
	}

	public void setPools(int pools) {
		this.pools = pools;
	}

	public int getMaxConn() {
		return maxConn;
	}

	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}

	public MessServer() {
	}
	
	public MessServer(int port, int pools,int maxConn) {
		MessServer.port = port;
		this.pools = pools;
		this.maxConn= maxConn;
	}
	
	/** 
	 * @throws Exception 
	 * @Title: startServer 
	 * @Description: 启动服务   
	 */ 
	public void startServer() throws Exception {
		String sport = System.getProperty("mobile.communication.port", String.valueOf(port));
		if(sport == null)
		{
			throw new Exception("afa.communication.port not config,please connect administrator.");
		}
		logger.info("启动接收手持通讯SOCKET监听服务. [端口: " + sport + "]");
		port = Integer.parseInt(sport);
		sock = new PooledMessRemoteFileServer(port,pools,maxConn);
		sock.start();
	}
	
	public void stopServer()
	{
		if(sock != null)
		{
			sock.stop();
		}
	}
}