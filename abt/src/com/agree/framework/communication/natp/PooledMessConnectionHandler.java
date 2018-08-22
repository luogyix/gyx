package com.agree.framework.communication.natp;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.yongboy.socketio.test.WhiteboardHandler;

public class PooledMessConnectionHandler implements Runnable {
	protected Socket messConnection;

	private static final Logger logger = LoggerFactory.getLogger(PooledMessConnectionHandler.class);
	protected static List<Socket> messPool = new LinkedList<Socket>();

//	private static final Log logger = LogFactory
//			.getLog(PooledConnectionHandler.class);

	public PooledMessConnectionHandler() {
	}


	public void handleConnection()throws Exception{
			String retJsonstr = "";//阶段2，生成消息
			InputStream dis = null;
			// 处理接收的socket
			try {
			messConnection.setSoTimeout(5000);//短连接设置超时时间
			//处理收到数据
				//读取配置文件
				ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
				String pushtype = (String)context.getAttribute(ApplicationConstants.COMMUNICATION_PUSHTYPE);
				dis = messConnection.getInputStream();
				if ("1".equals(pushtype)) {
					byte[] headLen = new byte[8]; //报文长度:8位长度;含义为json报文长度+20位字符编码格式;
					byte[] codeType = new byte[20];//报文编码格式,用于解析json报文
					dis.read(headLen);
					int len = Integer.parseInt(new String(headLen).trim())-20;
					dis.read(codeType);
					String type = new String(codeType).trim();
					byte[] contain = new byte[len];
					dis.read(contain);
					retJsonstr = new String(contain,type);//使用指定编码格式转换报文
				} else {
					byte[] headLen = new byte[8]; //8位报文头
					dis.read(headLen);
					int len = Integer.parseInt(new String(headLen));
					byte[] bytespace = new byte[8];//银行预留8位
					dis.read(bytespace);
					byte[] retData = new byte[len - 8];//主报文
					
					dis.read(retData);
					retJsonstr = new String(retData,"gbk");
				}
			} catch (IOException e) {
				logger.error(this.getClass().toString()+"类-awcRecv方法:接收数据异常");
				throw new Exception("PooledMessConnection接收数据异常，未收到有效数据。", e);
			} 
			finally{
				dis.close();//关闭连接
			}
			JSONObject retJson = JSONObject.fromObject(retJsonstr);
			
			WhiteboardHandler.broadMsg(retJson);
	
	}

	public static void processRequest(Socket requestToHandle) {
		synchronized (messPool) {
			messPool.add(messPool.size(), requestToHandle);
			messPool.notifyAll();
		}
	}


	public void run() {
		while (true) {
			synchronized (messPool) {
				while (messPool.isEmpty()) {
					try {
						messPool.wait();//阶段3
					} catch (InterruptedException e) {
						logger.error("messPool.wait()失败" + e.getMessage(), e);
					}
				}
				messConnection = (Socket) messPool.remove(0);
			}
			try {
				handleConnection();
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
	}
}
