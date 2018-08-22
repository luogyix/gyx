/**
 * 文件名: SocketServer.java
 * 创建时间: 2009-12-2 上午10:25:13
 * 命名空间: com.css.resoft.ctax.communication
 */
package com.agree.framework.communication.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.communication.natp.PooledRemoteFileServer;
import com.agree.util.StringUtil;

/**
 * socket服务器
 * @ClassName: SocketServer.java
 * @company 赞同科技
 */
public class SocketServer {
	private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);
	public String port = "7654";
	public int pools;
	public int maxConn;
	private PooledRemoteFileServer sock = null;
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
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

	public SocketServer() {
	}
	
	public SocketServer(String port, int pools,int maxConn) {
		this.port = port;
		this.pools = pools;
		this.maxConn= maxConn;
	}
	
	/** 
	 * @throws Exception 
	 * @Title: startServer 
	 * @Description: 启动服务   
	 */ 
	public void startServer() throws Exception {
		port = System.getProperty("afa.communication.port", port);
		if(port == null)
		{
			throw new Exception("afa.communication.port not config,please connect administrator.");
		}
		logger.info("启动接收AFA通讯SOCKET监听服务. [端口: " + port + "]");
		if(!StringUtil.isNumeric(port.trim())){
			port = "7654";
		}
		sock = new PooledRemoteFileServer(Integer.parseInt(port),pools,maxConn);
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
