/*
 * Copyright(C) 2006 Agree Tech, All rights reserved.
 * 
 * Created on 2006-8-11   by Xu Haibo
 */

package com.agree.framework.communication.natp;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <DL>
 * <DT><B> natp通讯 </B></DT>
 * <p>
 * <DD> 详细介绍 </DD>
 * </DL>
 * <p>
 * 
 * <DL>
 * <DT><B>使用范例</B></DT>
 * <p>
 * <DD> 使用范例说明 </DD>
 * </DL>
 * <p>
 * 
 * @author hxy
 * @author puyun
 * @author 赞同科技
 * @version 1.00, Aug 15, 2006
 */
public class CommunicationNatp implements INatp {
	/**
	 * Logger for this class
	 */
//	private static final Log logger = LogFactory
//			.getLog(CommunicationNatp.class);

	// default encoding
	private static final String ENCODING = "GBK";
	private static final Log logger=LogFactory.getLog(CommunicationNatp.class);//日志

	private static String[] passwordKeywords = null;
	
	//用于3.0解析的Helper， 余雄伟
	private NatpHelper natpHelper;

	private DataTransfer dataTransfer;

	private ByteBuffer buf;

	private Map<String, List<?>> result;

	private StringBuffer sendLog = new StringBuffer();

	private StringBuffer receiveLog = new StringBuffer();

	/**
	 * <DL>
	 * <DT><B> 构造器. </B></DT>
	 * <p>
	 * <DD> 构造器说明 </DD>
	 * </DL>
	 * <p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CommunicationNatp() {
		super();
		dataTransfer = new DataTransfer();
		
		//初始化Helper
		natpHelper = new NatpHelper();
		buf = ByteBuffer.allocate(4096);
		result = new LinkedHashMap();
	}
	
	public void setBufFull(){
		buf.clear();
	}

	/**
	 * @param name
	 * @param value
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addResult(String name, String value) {
		List list = (List) result.get(name);
		if (list == null) {
			list = new ArrayList();
			result.put(name, list);
		}
		list.add(value);
	}

	public Map<String, List<?>> exchangeEx(Socket socket) throws Exception {
//		Map returnMap = new HashMap();
		try {
			dataTransfer.setInputStream(socket.getInputStream());
			dataTransfer.setOutputStream(socket.getOutputStream());
			result.clear();
			byte[] retData = dataTransfer.natpRecv();
			
			// 2.0处理网关发来的消息
//			ByteArrayInputStream bin = new ByteArrayInputStream(retData);
//			DataInputStream din = new DataInputStream(bin);
//			
//			while (din.available() > 0) {
//				int nLen = din.readShort();
//				byte[] byName = new byte[nLen];
//				din.read(byName);
//				String sName = new String(byName, ENCODING);
//				nLen = din.readShort();
//				byte[] byValue = new byte[nLen];
//				din.read(byValue);
//				String sValue = new String(byValue, ENCODING);
//				addResult(sName, sValue);
//				receiveLog.append("\t\t");
//				receiveLog.append(sName).append(":").append(sValue)
//						.append("\n");
//			}
			
			String natpVervsion = "";
			if(0x10 == dataTransfer.getNatpVersion()){
				natpVervsion = "1.0";
			}else if (0x30 == dataTransfer.getNatpVersion()){
				natpVervsion = "3.0";
			}else{
				if(logger.isErrorEnabled()){
					logger.error("NATP报文协议版本错误，将采用1.0协议解析报文，natpVersion='"+dataTransfer.getNatpVersion()+"'");
				}
				natpVervsion = "1.0";
			}
			//换用下面这一段，余雄伟
			natpHelper.unpackData(retData,0,0,natpVervsion);
			responseData();
			//			if (logger.isDebugEnabled()) {
//				String logInfo = receiveLog.toString();
//				logger.debug("\n\t" + TimeFactory.getCurrentTime() + "接收到的信息："
//						+ "\n" + "\t\t[发送序列号：" + tSend + "]" + "\n\t\t[通讯用时："
//						+ (tReceive - tSend) + "]" + "\n" + logInfo);
//			} else {
//				if (logger.isInfoEnabled()) {
//					logger.info("\n\t" + TimeFactory.getCurrentTime()
//							+ "接收的信息：" + "\n" + "\t\t[发送序列号：" + tSend + "]");
//				}
//			}
			receiveLog.setLength(0);
//			din.close();
		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
			throw new Exception(e);
		}
		
		// TODO返回网关值
		return result;
	}
	
	public void exchangeExx(Socket socket) throws Exception {
		try {
			dataTransfer.setOutputStream(socket.getOutputStream());
			byte[] sendData = new byte[buf.position()];
			for (int i = 0; i < sendData.length; i++) {
				sendData[i] = buf.get(i);
			}
			

//			if (logger.isDebugEnabled()) {
//
//				String logInfo = sendLog.toString();
//				logger.debug("\n\t" + TimeFactory.getCurrentTime()
//						+ "发送给贵宾的确认的信息：\n\t\t[发送序列号：" + tSend + "]" + "\n" + "\t\t"
//						+ "NatpVersion:" + dataTransfer.getNatpVersion() + "\n"
//						+ "\t\t" + "TradeCode:" + dataTransfer.getTradeCode()
//						+ "\n" + "\t\t" + "TemplateCode:"
//						+ dataTransfer.getTemplateCode() + "\n" + "\t\t"
//						+ "Reserve:" + dataTransfer.getReserve() + "\n"
//						+ logInfo);
//			} else {
//				if (logger.isInfoEnabled()) {
//					logger.info("\n\t" + TimeFactory.getCurrentTime()
//							+ "发送出的信息：\n\t\t[发送序列号：" + tSend + "]"
//							+ "\n\t\t[报文字符数：" + sendLog.length() + "]");
//				}
//			}

			sendLog.setLength(0);
			result.clear();
			dataTransfer.natpSendDatas(sendData);
		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
			throw new Exception(e);
		} finally {
		}
	}

	@SuppressWarnings("rawtypes")
	public Map exchange(String groupName) throws Exception {
		Socket socket = null;
		Object[] objArr = null;
		try {
			// socket= SocketFactory.createSocket(serverName);
			objArr = ServerFinder.createSocket(groupName);
			socket = (Socket) objArr[0];
			NatpPlugin.getDefault().increaseTradeCount(groupName,
					(String) objArr[1]);
			dataTransfer.setOutputStream(socket.getOutputStream());
			dataTransfer.setInputStream(socket.getInputStream());
			byte[] sendData = new byte[buf.position()];
			for (int i = 0; i < sendData.length; i++) {
				sendData[i] = buf.get(i);
			}
			

//			if (logger.isDebugEnabled()) {
//
//				String logInfo = sendLog.toString();
//				if (hasPasswordKeywords(logInfo)) {
//					logInfo = "\t\t[报文字符数：" + logInfo.length() + "]";
//				}
//
//				logger.debug("\n\t" + TimeFactory.getCurrentTime()
//						+ "发送出的信息：\n\t\t[发送序列号：" + tSend + "]" + "\n" + "\t\t"
//						+ "NatpVersion:" + dataTransfer.getNatpVersion() + "\n"
//						+ "\t\t" + "TradeCode:" + dataTransfer.getTradeCode()
//						+ "\n" + "\t\t" + "TemplateCode:"
//						+ dataTransfer.getTemplateCode() + "\n" + "\t\t"
//						+ "Reserve:" + dataTransfer.getReserve() + "\n"
//						+ logInfo);
//			} else {
//				if (logger.isInfoEnabled()) {
//					logger.info("\n\t" + TimeFactory.getCurrentTime()
//							+ "发送出的信息：\n\t\t[发送序列号：" + tSend + "]"
//							+ "\n\t\t[报文字符数：" + sendLog.length() + "]");
//				}
//			}

			sendLog.setLength(0);
			result.clear();
			dataTransfer.natpSendDatas(sendData);
			byte[] retData = dataTransfer.natpRecv();
			ByteArrayInputStream bin = new ByteArrayInputStream(retData);
			DataInputStream din = new DataInputStream(bin);
			while (din.available() > 0) {
				int nLen = din.readShort();
				byte[] byName = new byte[nLen];
				din.read(byName);
				String sName = new String(byName, ENCODING);
				nLen = din.readShort();
				byte[] byValue = new byte[nLen];
				din.read(byValue);
				String sValue = new String(byValue, ENCODING);
				addResult(sName, sValue);
				// if(receiveLog.length()>0)
				receiveLog.append("\t\t");
				receiveLog.append(sName).append(":").append(sValue)
						.append("\n");
			}
			

//			if (logger.isDebugEnabled()) {
//
//				String logInfo = receiveLog.toString();
//				if (hasPasswordKeywords(logInfo)) {
//					logInfo = "\t\t[报文字符数：" + logInfo.length() + "]";
//				}
//
//				logger.debug("\n\t" + TimeFactory.getCurrentTime() + "接收的信息："
//						+ "\n" + "\t\t[发送序列号：" + tSend + "]" + "\n\t\t[通讯用时："
//						+ (tReceive - tSend) + "]" + "\n" + logInfo);
//			} else {
//				if (logger.isInfoEnabled()) {
//					logger.info("\n\t" + TimeFactory.getCurrentTime()
//							+ "接收的信息：" + "\n" + "\t\t[发送序列号：" + tSend + "]");
//				}
//			}

			receiveLog.setLength(0);
			din.close();
		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
			throw new Exception(e);
		} finally {
			if (objArr != null) {
				NatpPlugin.getDefault().decreaseTradeCount(groupName,
						(String) objArr[1]);
			}
			if (socket != null) {

				socket.close();
			}
		}
		// TODO返回网关值
		return result;
	}

	
	private void responseData() throws Exception{
		String[] responseNames = natpHelper.getNames();
		for (int i = 0; i < responseNames.length; i++) {
			String responseName = responseNames[i];
			byte[] responseByte = natpHelper.get(responseName);
			String sValue = new String(responseByte, ENCODING);
			addResult(responseName, sValue);
			receiveLog.append("\t\t");
			receiveLog.append(responseName).append(":").append(sValue)
					.append("\n");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public int getRecordCount(String fieldName) {
		// 1. check
		if (fieldName == null) {
			return -1;
		}

		// 2. count
		List list = (List) result.get(fieldName);
		if (list == null) {
			return 0;
		}
		return list.size();
	}

	public int init(int natpVersion, String transCode, String templateCode,
			String reservedCode) {
		dataTransfer.setNatpVersion(natpVersion);
		dataTransfer.setTradeCode(transCode);
		dataTransfer.setTemplateCode(templateCode);
		dataTransfer.setReserve(reservedCode);
		return 0;
	}

	public void pack(String fieldName, String value) throws Exception {
		// 1. check
		if (fieldName == null || value == null) {
			throw new Exception("打包数据不能为null");
		}
		buf.putShort((short) fieldName.getBytes(ENCODING).length);
		buf.put(fieldName.getBytes(ENCODING));
		buf.putShort((short) value.getBytes(ENCODING).length);
		buf.put(value.getBytes(ENCODING));
		if (!hasPasswordKeywords(fieldName)) {
			sendLog.append("\t\t");
			sendLog.append(fieldName).append(":").append(value).append("\n");
		}

	}

	public void pack(String[] fieldNames, String[] values) throws Exception {
		// 1. check
		if (fieldNames == null || values == null) {
			throw new Exception("打包数据不能为null");
		}
		if (fieldNames.length != values.length) {
			throw new Exception("字段数量不匹配");
		}
		// 2. pack
		for (int i = 0; i < fieldNames.length; i++) {
			buf.putShort((short) fieldNames[i].getBytes(ENCODING).length);
			buf.put(fieldNames[i].getBytes(ENCODING));
			buf.putShort((short) values[i].getBytes(ENCODING).length);
			buf.put(values[i].getBytes(ENCODING));
			if (!hasPasswordKeywords(fieldNames[i])) {
				sendLog.append("\t\t");
				sendLog.append(fieldNames[i]).append(":").append(values[i])
						.append("\n");
			}
		}
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String[] unpack(String fieldName) throws Exception {
		// 1. check
		if (fieldName == null) {
			throw new Exception("字段名称不能为null");
		}

		// 2. return all values
		List findList = (List) result.get(fieldName);
		return (String[]) findList.toArray(new String[0]);
	}

	@SuppressWarnings("rawtypes")
	public String unpack(String fieldName, int iPos) throws Exception {
		// 1. check
		if (fieldName == null) {
			throw new Exception("字段名称不能为null");
		}

		// 2. find iPos value
		List list = (List) result.get(fieldName);
		if (list == null) {
			return null;
		}
		if (iPos < 1 || iPos > list.size()) {
			return null;
		}
		return (String) list.get(iPos - 1);
	}

	public void downloadFile(String serviceName, String remoteFileName,
			String localFileName) throws Exception {
		if (isEmpty(serviceName) || isEmpty(remoteFileName)
				|| isEmpty(localFileName)) {
			throw new Exception("请检查上传文件时参数,参数不能为空,或在配置文件中不能找到合适的值.");
		}
		transferFile(serviceName, remoteFileName, localFileName,
				NatpFileClient.ACTION_DOWNLOAD_FILE);
	}

	/**
	 * 从服务名代表的Natp文件传输服务上,下载文件
	 * 
	 * @param serviceName
	 *            在配置文件中服务名代表了一个提供natp文件传输的ip及port.
	 * @param remotePathId
	 *            指在配置文件中一个标识,代表了一个远程绝对路径
	 * @param remoteFileName
	 *            远程文件的相对路径.
	 * @param localPathId
	 *            在配置文件中一个标识,代表了一个本地绝对路径
	 * @param localFileName
	 *            本地文件的相对路径.
	 */
	public void downloadFile(String serviceName, String remotePathId,
			String remoteFileName, String localPathId, String localFileName)
			throws Exception {
		String remotePath = getFileTransferConfigProperty(remotePathId);
		String localPath = getFileTransferConfigProperty(localPathId);
		if (isEmpty(serviceName) || isEmpty(remotePath) || isEmpty(localPath)
				|| isEmpty(remoteFileName) || isEmpty(localFileName)) {
			throw new Exception("请检查上传文件时参数,参数不能为空,或在配置文件中不能找到合适的值.");
		}
		transferFile(serviceName, joinParts(remotePath, remoteFileName,
				File.separator), joinParts(localPath, localFileName,
				File.separator), NatpFileClient.ACTION_DOWNLOAD_FILE);
	}

	/**
	 * 
	 * @param serviceName
	 *            在配置文件中服务名代表了一个提供natp文件传输的ip及port.
	 * @param remotePathId
	 *            指在配置文件中一个标识,代表了一个远程绝对路径
	 * @param remoteFileName
	 *            远程文件的相对路径.
	 * @param localFileAbsolutePath
	 *            本地文件的绝对路径.
	 */
	public void downloadFile(String serviceName, String remotePathId,
			String remoteFileName, String localFileName) throws Exception {
		String remotePath = getFileTransferConfigProperty(remotePathId);
		if (isEmpty(serviceName) || isEmpty(remoteFileName)
				|| isEmpty(localFileName)) {
			throw new Exception("请检查上传文件时参数,参数不能为空,或在配置文件中不能找到合适的值.");
		}
		transferFile(serviceName, joinParts(remotePath, remoteFileName,
				File.separator), localFileName,
				NatpFileClient.ACTION_DOWNLOAD_FILE);
	}

	public void uploadFile(String serviceName, String remoteFileName,
			String localFileName) throws Exception {
		if (isEmpty(serviceName) || isEmpty(remoteFileName)
				|| isEmpty(localFileName)) {
			throw new Exception("请检查上传文件时参数,参数不能为空,或在配置文件中不能找到合适的值.");
		}
		transferFile(serviceName, remoteFileName, localFileName,
				NatpFileClient.ACTION_UPLOAD_FILE);
	}

	/**
	 * 从服务名代表的Natp文件传输服务上,上传文件
	 * 
	 * @param serviceName
	 *            在配置文件中服务名代表了一个提供natp文件传输的ip及port.
	 * @param remotePathId
	 *            指在配置文件中一个标识,代表了一个远程绝对路径
	 * @param remoteFileName
	 *            远程文件的相对路径.
	 * @param localPathId
	 *            在配置文件中一个标识,代表了一个本地绝对路径
	 * @param localFileName
	 *            本地文件的相对路径.
	 */
	public void uploadFile(String serviceName, String remotePathId,
			String remoteFileName, String localPathId, String localFileName)
			throws Exception {

		String remotePath = getFileTransferConfigProperty(remotePathId);
		String localPath = getFileTransferConfigProperty(localPathId);
		if (isEmpty(serviceName) || isEmpty(remotePath) || isEmpty(localPath)
				|| isEmpty(remoteFileName) || isEmpty(localFileName)) {
			throw new Exception("请检查上传文件时参数,参数不能为空,或在配置文件中不能找到合适的值.");
		}
		transferFile(serviceName, joinParts(remotePath, remoteFileName,
				File.separator), joinParts(localPath, localFileName,
				File.separator), NatpFileClient.ACTION_UPLOAD_FILE);
	}

	/**
	 * 
	 * @param serviceName
	 *            在配置文件中服务名代表了一个提供natp文件传输的ip及port.
	 * @param remotePathId
	 *            指在配置文件中一个标识,代表了一个远程绝对路径
	 * @param remoteFileName
	 *            远程文件的相对路径.
	 * @param localFileAbsolutePath
	 *            本地文件的绝对路径.
	 */
	public void uploadFile(String serviceName, String remotePathId,
			String remoteFileName, String localFileAbsolutePath)
			throws Exception {
		String remotePath = getFileTransferConfigProperty(remotePathId);
		if (isEmpty(serviceName) || isEmpty(remotePath)
				|| isEmpty(remoteFileName) || isEmpty(localFileAbsolutePath)) {
			throw new Exception("请检查上传文件时参数,参数不能为空,或在配置文件中不能找到合适的值.");
		}
		transferFile(serviceName, joinParts(remotePath, remoteFileName,
				File.separator), localFileAbsolutePath,
				NatpFileClient.ACTION_UPLOAD_FILE);
	}

	public void uploadFileByBytes(String serviceName, String remoteFileName,
			String localFileName, byte[] content) throws Exception {
		if (isEmpty(serviceName) || isEmpty(remoteFileName)) {
			throw new Exception("请检查上传文件时参数,参数不能为空,或在配置文件中不能找到合适的值.");
		}
		if (isNull(content) || content.length <= 0) {
			throw new Exception("上传的内容不能为空");
		}
		localFileName = (isNull(localFileName) ? "" : localFileName);
		NatpFileClient client = null;
		try {
			String[] serviceConfig = getNatpFileServiceConfig(serviceName);
			client = new NatpFileClient(serviceConfig[0], Integer
					.parseInt(serviceConfig[1]));
			client.initUpload(InetAddress.getLocalHost().getHostName(),
					remoteFileName, localFileName, content);
			client.run();
		} finally {
			if (client != null) {
				client.destroy();
			}
		}
	}

	public void uploadFileByBytes(String serviceName, String remotePathId,
			String remoteFileName, String localFileName, byte[] content)
			throws Exception {

		if (isNull(serviceName)) {
			throw new Exception("serviceName 不能为空.");
		}
		if (isNull(content) || content.length <= 0) {
			throw new Exception("上传的内容不能为空");
		}
		if (isEmpty(remotePathId)) {
			throw new Exception("上传时,远程文件父路径标识不能为空.");
		}
		if (isEmpty(remoteFileName)) {
			throw new Exception("请检查上传文件时参数,远程文件名不能为空.");
		}
		String remotePath = getFileTransferConfigProperty(remotePathId);
		if (isEmpty(remotePath)) {
			throw new Exception("请检查远程文件父路径标识是否在配置文件中配置了相应的值");
		}

		uploadFileByBytes(serviceName, joinParts(remotePath, remoteFileName,
				File.separator), localFileName, content);
	}

	private void transferFile(String serviceName, String remoteFileName,
			String localFileName, String action) throws Exception {

		if (isEmpty(serviceName)) {
			throw new Exception("请检查参数：服务名,它不能为空。");
		}

		String[] serviceConfig = getNatpFileServiceConfig(serviceName);

		NatpFileClient client = null;
		try {
			client = new NatpFileClient(serviceConfig[0], Integer
					.parseInt(serviceConfig[1]));
			client.init(InetAddress.getLocalHost().getHostName(),
					localFileName, remoteFileName, action);
			client.run();
		} finally {
			if (client != null)
				client.destroy();
		}
	}

	private String[] getNatpFileServiceConfig(String serviceName)
			throws Exception {

		String serviceConfig = ServerMapping
				.getNatpFileServiceServer(serviceName);
		if (serviceConfig == null || serviceConfig.trim().length() <= 0) {
			throw new Exception("在configuration/natpfileservice.properties找不到 "
					+ serviceName + "的配置");
		}
		int idx = serviceConfig.indexOf(":");
		if (idx != -1) {
			String[] segs = serviceConfig.split(":");
			if (segs.length < 2) {
				new Exception("Natp 文件传输服务 " + serviceName
						+ "的配置错误，格式为“IP地址:端口号”！");
			}
			return segs;
		} else {
			return new String[] { serviceConfig, "" };
		}

	}

//	public String getABPlatformAbsolutePath() throws Exception {
//		return new File(Platform.getInstallLocation().getURL().getFile())
//				.getAbsolutePath()
//				+ File.separator;
//	}

//	/**
//	 * 按照现在的部署方式，一般情况下，ab平台的父路径总是存在的。否则getAbsolutePath会抛出异常.
//	 */
//	public String getABPlatformParentPath() throws Exception {
//		return (new File(Platform.getInstallLocation().getURL().getFile())
//				.getParentFile().getAbsolutePath() + File.separator);
//	}

	public String getFileTransferConfigProperty(String key) throws Exception {
		return ServerMapping.getFileTransferConfigProperty(key);
	}

	private boolean isNull(Object obj) {
		return (obj == null);
	}

	private boolean isEmpty(String obj) {
		return (obj == null || obj.trim().length() == 0);
	}

	private String joinParts(String part1, String part2, String con) {
		if (part1.endsWith("\\") || part1.endsWith("/")) {
			return part1 + part2;
		} else {
			return part1 + "/" + part2;
		}
	}

	private static boolean hasPasswordKeywords(String value) {
		if (value == null || value.length() == 0) {
			return false;
		}

		String lowcaseValue = value.toLowerCase();

		String[] passwdKeywds = getPasswordKeywords();
		for (int i = 0; i < passwdKeywds.length; i++) {
			if (lowcaseValue.indexOf(passwdKeywds[i]) > 0) {
				return true;
			}
		}

		return false;
	}

	private static String[] getPasswordKeywords() {
		if (passwordKeywords == null) {
			loadPasswordKeyword();
		}

		return passwordKeywords;
	}

	/**
	 * <DL>
	 * <DT><B> 加载口令关键字段 </B></DT>
	 * <p>
	 * <DD>
	 * DEFAULT_PASSWORD_KEYWORDS中标记的字段是一定有的，只从配置中补充DEFAULT_PASSWORD_KEYWORDS中间没有的字段
	 * <p>
	 * 
	 * 配置字符串的关键字之间使用逗号分隔，要求使用小写字符串 </DD>
	 * </DL>
	 * <p>
	 */
	private static synchronized void loadPasswordKeyword() {
		if (passwordKeywords != null) {
			return;
		}

//		String passwdKeywdStr = Platform.getPreferencesService().getString(
//				BUNDLE_ID, PASSWD_KEYWD, null, null);
		// most of time the passwdKeywdStr is null
//		if (passwdKeywdStr == null || passwdKeywdStr.length() == 0) {
//			passwordKeywords = DEFAULT_PASSWORD_KEYWORDS;
//			return;
//		}
//
//		String[] appendKeyWords = passwdKeywdStr.split("[,]");
//
//		passwordKeywords = new String[appendKeyWords.length
//				+ DEFAULT_PASSWORD_KEYWORDS.length];
//
//		System.arraycopy(DEFAULT_PASSWORD_KEYWORDS, 0, passwordKeywords, 0,
//				DEFAULT_PASSWORD_KEYWORDS.length);
//
//		System.arraycopy(appendKeyWords, 0, passwordKeywords,
//				DEFAULT_PASSWORD_KEYWORDS.length, passwordKeywords.length);

	}
	
//	public static void main(String[] args) throws Exception{
//		byte [] b = {0, 17, 72, 95, 115, 116, 97, 114, 116, 95, 116, 105, 109, 101, 115, 116, 97, 109, 112, 0, 23, 50, 48, 49, 49, 45, 48, 52, 45, 48, 56, 32, 48, 48, 58, 49, 57, 58, 48, 50, 46, 52, 49, 48, 0, 11, 72, 95, 83, 101, 114, 118, 105, 99, 101, 73, 100, 0, 4, 116, 48, 48, 49, 0, 12, 72, 95, 67, 104, 97, 110, 110, 101, 108, 83, 101, 113, 0, 8, 50, 48, 49, 50, 48, 51, 49, 50, 0, 13, 72, 95, 67, 104, 97, 110, 110, 101, 108, 68, 97, 116, 101, 0, 8, 50, 48, 49, 50, 48, 51, 49, 50, 0, 9, 116, 114, 97, 110, 115, 99, 111, 100, 101, 0, 4, 116, 48, 48, 49, 0, 7, 115, 121, 115, 116, 105, 109, 101, 0, 10, 50, 48, 49, 49, 45, 48, 52, 45, 48, 56, 0, 6, 72, 95, 78, 111, 100, 101, 0, 9, 49, 50, 51, 52, 53, 54, 55, 56, 57, 0, 8, 83, 69, 81, 85, 69, 78, 67, 69, 0, 8, 48, 48, 48, 48, 48, 54, 52, 57, 0, 11, 72, 95, 67, 104, 97, 110, 110, 101, 108, 73, 100, 0, 2, 48, 50, 0, 13, 72, 95, 67, 104, 97, 110, 110, 101, 108, 84, 105, 109, 101, 0, 6, 49, 48, 48, 50, 50, 53, 0, 8, 115, 114, 118, 95, 105, 110, 102, 111, 0, -20, 123, 39, 84, 82, 65, 78, 83, 95, 70, 76, 65, 71, 39, 58, 32, 39, 48, 48, 39, 44, 32, 39, 83, 69, 82, 86, 73, 67, 69, 95, 68, 69, 83, 67, 39, 58, 32, 39, 116, 116, 116, 39, 44, 32, 39, 73, 78, 84, 69, 82, 70, 65, 67, 69, 95, 75, 69, 89, 39, 58, 32, 39, 116, 48, 48, 49, 39, 44, 32, 39, 77, 79, 68, 95, 67, 79, 68, 69, 39, 58, 32, 39, 97, 98, 116, 109, 39, 44, 32, 39, 83, 69, 82, 86, 73, 67, 69, 95, 77, 79, 68, 69, 39, 58, 32, 39, 48, 48, 39, 44, 32, 39, 83, 69, 82, 86, 73, 67, 69, 95, 83, 84, 65, 84, 85, 83, 39, 58, 32, 39, 48, 48, 39, 44, 32, 39, 84, 82, 65, 68, 69, 95, 67, 79, 68, 69, 39, 58, 32, 39, 116, 48, 48, 49, 39, 44, 32, 39, 84, 82, 65, 78, 83, 95, 68, 69, 83, 84, 95, 67, 72, 65, 78, 78, 69, 76, 39, 58, 32, 78, 111, 110, 101, 44, 32, 39, 83, 69, 82, 86, 73, 67, 69, 95, 73, 68, 39, 58, 32, 39, 116, 48, 48, 49, 39, 44, 32, 39, 73, 78, 84, 69, 82, 70, 65, 67, 69, 95, 67, 72, 69, 67, 75, 95, 70, 76, 65, 71, 39, 58, 32, 39, 48, 49, 39, 125, 0, 8, 72, 95, 84, 101, 108, 108, 101, 114, 0, 12, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 0, 12, 116, 101, 109, 112, 108, 97, 116, 101, 99, 111, 100, 101, 0, 4, 97, 98, 116, 109};
//		NatpHelper natpHelper = new NatpHelper();
//		natpHelper.unpackData(b,0,0,"1.0");
//	}
	
}




