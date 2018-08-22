/**
 * 文件名: SocketClient.java
 * 创建时间: 2009-11-26 下午04:56:19
 * 命名空间: com.css.resoft.ctax.communication
 */

package com.agree.framework.communication.socket;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.communication.Message;
import com.agree.framework.communication.Packager;
import com.agree.framework.exception.AppException;
import com.agree.framework.web.form.administration.TBsmsMsgbook;
import com.agree.framework.web.service.administration.IMsgService;
import com.agree.util.Constants;
import com.agree.util.StringUtil;

/**
 * 类说明:socket客户端
 * 
 * @author haoruibing
 * 
 */
public class SocketClient {
	private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

	private String host1;
	private int port1;
	private String host2;
	private int port2;
	private int soTimeout;
	private Packager packer;
	private IMsgService msgService;
	
	
	public void setMsgService(IMsgService msgService) {
		this.msgService = msgService;
	}

	public void setPacker(Packager packer) {
		this.packer = packer;
	}

	public SocketClient() {
		
	}
	
	
	
	/** 
	 * @Title: send 
	 * @Description: 发送报文
	 * @param msg    准备发送的报文对象
	 * @return msg
	 * @throws Exception    
	 */ 
	public synchronized Message send(Message msg) throws Exception {
		Socket socket = null;
		String returnXml = null;
		TBsmsMsgbook book=new TBsmsMsgbook(); 
		Message revicemsg =null;
		try {
			String msgid=msgService.getMsgId();
			msg.put(Constants.HEADER, "M_MesgId", msgid);//填写msgid
			msg.put(Constants.BANKCOMMHEADER, "channelserno", msg.getString(Constants.HEADER, "M_MesgSndDate")+msgid);//渠道流水号=日期+msgid
			String data=packer.pack(msg);
			logger.info("发送报文:" + data);
			
			book.setMbflag(Constants.SEND);//往帐
			book.setBrno(msg.getString(Constants.BANKCOMMHEADER, "brno"));
			book.setChannelserno(msg.getString(Constants.BANKCOMMHEADER, "channelserno"));
			book.setCustomerno(msg.getString(Constants.HEADER, "M_CustomerNo"));
			book.setMesgdate(msg.getString(Constants.HEADER, "M_MesgSndDate"));
			book.setMesgtime(msg.getString(Constants.HEADER, "M_MesgSndTime"));
			book.setMesgid(msg.getString(Constants.HEADER, "M_MesgId"));
			book.setServicerno(msg.getString(Constants.HEADER, "M_ServicerNo"));
			book.setTellerno(msg.getString(Constants.BANKCOMMHEADER, "tellerno"));
			book.setServicecode(msg.getString(Constants.HEADER,"M_ServiceCode"));
			
			msgService.addMsgRecord(book);//插入报文登记簿
			
			try {
				socket = new Socket(host1, port1);
			}catch(Exception  e){
				logger.error("处理失败,原因：连接服务器1失败，连接ip："+host1+",端口："+port1,e);
				book.setProcstatus(Constants.PROFAILED);
				book.setProcmsg("处理失败,原因：通讯失败");
				msgService.updateMsgRecord(book);//更新报文登记簿
				if(StringUtil.isNotEmpty(host2)&&port2!=0){
					try {
						socket = new Socket(host2, port2);
					} catch (ConnectException e1) {
						book.setProcstatus(Constants.PROFAILED);
						book.setProcmsg("处理失败,原因：通讯失败");
						msgService.updateMsgRecord(book);//更新报文登记簿
						logger.error("处理失败,原因：连接服务器2失败，连接ip："+host2+",端口："+port2,e);
						throw new AppException("处理失败,原因：连接服务器失败");
					}
				}
			}
			socket.setSoTimeout(soTimeout);
			
			OutputStream os = socket.getOutputStream();
			byte[] packet = data.getBytes("GB2312");
			// 报头前发送报文长度
			
			os.write(packet);
			os.flush();
			
			// 读取返回消息
			InputStream is = socket.getInputStream();
			DataInputStream in=new DataInputStream(is);
			
			byte[] lengthBytes = new byte[8];
			is.read(lengthBytes);
			String length = "";
			for(int i=0;i<lengthBytes.length;i++){
				char temp=(char) lengthBytes[i];
				length=length+temp;
			}
			byte returnContent[] = new byte[Integer.parseInt(length)];
			in.read(returnContent);
			returnXml = new String(returnContent, "GB2312");
			logger.info("接收返回报文:" + length+returnXml);
			revicemsg = packer.unpack(returnXml);
			book.setStatus(revicemsg.getString(Constants.RSPHDR, "status"));
			book.setErrorcode(revicemsg.getString(Constants.RSPHDR, "dealcode"));
			String errmsg = revicemsg.getString(Constants.RSPHDR, "dealmsg");
			if(errmsg.getBytes("GB2312").length>100){
				errmsg=StringUtil.cutMultibyte(errmsg, 100);//如果返回信息长度大于100，则只截取前100字节
			}
			book.setErrormsg(errmsg);
			book.setProcstatus(Constants.PROSUSSCESS);
			book.setProcmsg("成功");
			book.setRspdatetime(revicemsg.getString(Constants.HEADER, "M_MesgSndTime"));
			book.setRspmesgid(msg.getString(Constants.HEADER, "M_MesgId"));
			msgService.updateMsgRecord(book);//更新报文登记簿
			
			if (revicemsg.getString(Constants.RSPHDR, "status").equals("1")) {//只有当报文返回为成功的时候，才可以确认是否推送
				msgService.insertPushlet(book);
			}
			
		}catch(SocketTimeoutException e){
			logger.error(e.getMessage(),e);
			book.setProcstatus(Constants.PROFAILED);
			book.setProcmsg("处理失败,原因：等待超时");
			msgService.updateMsgRecord(book);//更新报文登记簿
			throw new AppException("处理失败,原因：等待超时");
		}catch(NumberFormatException e){//parseInt报错，说明没有返回信息，length不是数字
			book.setProcstatus(Constants.PROFAILED);
			book.setProcmsg("处理失败,原因："+e.getMessage());
			msgService.updateMsgRecord(book);//更新报文登记簿
			throw new AppException("处理失败,原因：发送报文没有返回信息，请检查后台服务！");
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			book.setProcstatus(Constants.PROFAILED);
			book.setProcmsg("处理失败,原因："+e.getMessage());
			msgService.updateMsgRecord(book);//更新报文登记簿
			throw new AppException("处理失败,原因："+e.getMessage());
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
		
		return revicemsg;
	}
	/** 
	 * @Title: send 
	 * @Description: 发送报文，用于向制定的服务器发送报文
	 * @param msg    发送的消息
	 * @param index  指定向哪个服务器发送
	 * @return
	 * @throws Exception     
	 */ 
	public synchronized Message send(Message msg,int index) throws Exception {
		Socket socket = null;
		String returnXml = null;
		TBsmsMsgbook book=new TBsmsMsgbook(); 
		Message revicemsg =null;
		String ip;
		int port;
		if(index==1){
			ip=host1;
			port=port1;
		}else{
			ip=host2;
			port=port2;
		}
		try {
			String msgid=msgService.getMsgId();
			msg.put(Constants.HEADER, "M_MesgId", msgid);//填写msgid
			msg.put(Constants.BANKCOMMHEADER, "channelserno", msg.getString(Constants.HEADER, "M_MesgSndDate")+msgid);//渠道流水号=日期+msgid
			String data=packer.pack(msg);
			logger.info("发送报文:" + data);
			
			socket = new Socket(ip, port);
			socket.setSoTimeout(soTimeout);
			
			OutputStream os = socket.getOutputStream();
			byte[] packet = data.getBytes("GB2312");
			// 报头前发送报文长度
			
			os.write(packet);
			os.flush();
			
			book.setMbflag(Constants.SEND);//往帐
			book.setBrno(msg.getString(Constants.BANKCOMMHEADER, "brno"));
			book.setChannelserno(msg.getString(Constants.BANKCOMMHEADER, "channelserno"));
			book.setCustomerno(msg.getString(Constants.HEADER, "M_CustomerNo"));
			book.setMesgdate(msg.getString(Constants.HEADER, "M_MesgSndDate"));
			book.setMesgtime(msg.getString(Constants.HEADER, "M_MesgSndTime"));
			book.setMesgid(msg.getString(Constants.HEADER, "M_MesgId"));
			book.setServicerno(msg.getString(Constants.HEADER, "M_ServicerNo"));
			book.setTellerno(msg.getString(Constants.BANKCOMMHEADER, "tellerno"));
			
			msgService.addMsgRecord(book);//插入报文登记簿
			
			// 读取返回消息
			InputStream is = socket.getInputStream();
			DataInputStream in=new DataInputStream(is);
			
			byte[] lengthBytes = new byte[8];
			is.read(lengthBytes);
			String length = "";
			for(int i=0;i<lengthBytes.length;i++){
				char temp=(char) lengthBytes[i];
				length=length+temp;
			}
			byte returnContent[] = new byte[Integer.parseInt(length)];
			in.read(returnContent);
			returnXml = new String(returnContent, "GB2312");
			logger.info("接收返回报文:" + length+returnXml);
			revicemsg = packer.unpack(returnXml);
			book.setStatus(revicemsg.getString(Constants.RSPHDR, "status"));
			book.setErrorcode(revicemsg.getString(Constants.RSPHDR, "dealcode"));
			String errmsg = revicemsg.getString(Constants.RSPHDR, "dealmsg");
			if(errmsg.getBytes("GB2312").length>100){
				errmsg=StringUtil.cutMultibyte(errmsg, 100);//如果返回信息长度大于100，则只截取前100字节
			}
			book.setErrormsg(errmsg);
			book.setProcstatus(Constants.PROSUSSCESS);
			book.setProcmsg("成功");
			book.setRspdatetime(revicemsg.getString(Constants.HEADER, "M_MesgSndTime"));
			book.setRspmesgid(msg.getString(Constants.HEADER, "M_MesgId"));
			msgService.updateMsgRecord(book);//更新报文登记簿
		}catch(ConnectException  e){
			book.setProcstatus(Constants.PROFAILED);
			book.setProcmsg("处理失败,原因：通讯失败");
			msgService.updateMsgRecord(book);//更新报文登记簿
			throw new AppException("处理失败,原因：连接服务器失败，连接ip："+ip+",端口："+port);
		}catch(SocketTimeoutException e){
			logger.error(e.getMessage(),e);
			book.setProcstatus(Constants.PROFAILED);
			book.setProcmsg("处理失败,原因：等待超时");
			msgService.updateMsgRecord(book);//更新报文登记簿
			throw new AppException("处理失败,原因：等待超时");
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
			book.setProcstatus(Constants.PROFAILED);
			book.setProcmsg("处理失败,原因："+e.getMessage());
			msgService.updateMsgRecord(book);//更新报文登记簿
			throw new AppException("处理失败,原因："+e.getMessage());
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
		
		return revicemsg;
	}
	

	public String getHost1() {
		return host1;
	}

	public void setHost1(String host1) {
		this.host1 = host1;
	}

	public String getHost2() {
		return host2;
	}

	public void setHost2(String host2) {
		this.host2 = host2;
	}

	/**
	 * @param soTimeout
	 *            the soTimeout to set
	 */
	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

	public int getPort1() {
		return port1;
	}

	public void setPort1(int port1) {
		this.port1 = port1;
	}

	public int getPort2() {
		return port2;
	}

	public void setPort2(int port2) {
		this.port2 = port2;
	}
	
}
