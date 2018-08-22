package com.agree.framework.communication.natp;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PooledConnectionHandler implements Runnable {
	protected Socket connection;

	private static final Logger logger = LoggerFactory.getLogger(PooledConnectionHandler.class);
	protected static List<Socket> pool = new LinkedList<Socket>();

//	private static final Log logger = LogFactory
//			.getLog(PooledConnectionHandler.class);

	public PooledConnectionHandler() {
	}

//	private static Map<String, String> analyse(String con) throws Exception {
//
//		Map<String, String> retMap = new HashMap<String, String>();
//
//		String r1 = "\\s*\\{\\s*(.*)\\s*\\}\\s*";
//		java.util.regex.Pattern p1 = java.util.regex.Pattern.compile(r1);
//
//		String r2 = "\\s*'(.*)'\\s*:\\s*'(.*)'\\s*";
//		java.util.regex.Pattern p2 = java.util.regex.Pattern.compile(r2);
//
//		String r3 = "\\s*'(.*)'\\s*:\\s*(.*)\\s*";
//		java.util.regex.Pattern p3 = java.util.regex.Pattern.compile(r3);
//
//		Matcher m1 = p1.matcher(con);
//		boolean b1 = m1.matches();
//		if (b1) {
//			String segments[] = m1.group(1).split(",");
//			for (String segment : segments) {
//				Matcher m2 = p2.matcher(segment);
//				boolean b2 = m2.matches();
//				if (b2) {
//					retMap.put(m2.group(1), m2.group(2));
//				} else {
//					Matcher m3 = p3.matcher(segment);
//					boolean b3 = m3.matches();
//					if (b3) {
//						retMap.put(m3.group(1), m3.group(2));
//					} else {
//						throw new Exception("数据格式错误：[" + segment + "]");
//					}
//				}
//			}
//		} else {
//			throw new Exception("数据格式错误：[" + con + "]");
//		}
//		return retMap;
//	}

	@SuppressWarnings("unchecked")
	public void handleConnection() {
		CommunicationNatp natp = new CommunicationNatp();
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		Map<String, String> recvMap = new HashMap<String, String>();
		try {
			map = natp.exchangeEx(connection);
			closeConn(connection);
			//
			recvMap = formateGwreturnMap(map);
			logger.info("----NATP报文接收成功！将AFA推送过来的数据存入缓存，pushlst定时推送到页面... ... ----");
			logger.info("接收NATP消息:"+recvMap);
			/**
			 * 接收NATP消息:{M_CustomerNo=IBP, M_ServiceCode=IQS000001, M_MesgId=00000000000000004897, bs_id=005010102_00154, channeldate=20130718, pagenum=, queue_num=A0006, M_MesgSndTime=211722, currpage=, M_MesgSndDate=20131029, msgtype=msg002, M_MesgPriority=3, custtype_i=root, retrytime=0, channelcode=IBP, pageflag=, queuetype_id=005010102_00110, channelserno=00000000000000004897, M_CallMethod=2, queuenum_type=1, M_MesgRefId=20131029000000004897, qm_num=1, win_num=1, tellerno=123456789012, brno=005010102, M_Reserve=, M_Direction=1, delaytime=0, retrynum=0, deviceaddition=qmnum,1
			 * oid,赞同.susuila_xxxxxxxxxxxxxxxx
		 	 * winnum,2
			 * tradeCode,SCL.softCall.SoftCall, M_ServicerNo=IQS, M_PackageType=NATP, branch=005010102, channeltime=211721}
			 * */
			String key = recvMap.get("msgtype");
//			Properties  prop = new Properties ();
			/*
			 * 读取natp配置文件
			 */
//			ClassPathResource cr = new ClassPathResource("natp.properties");
//			prop.load(cr.getInputStream());
			String value = NatpMsgParam.getParam(key);
			if( value == null ){
				logger.error("----管理端没有对应报文解析方法，请核对报文！----");
			}else{
				Class<IParent> reload = (Class<IParent>) Class.forName(value);
				/*
				 * 执行reload方法
				 */
				IParent ip = reload.newInstance();//实例化
				Method m = reload.getDeclaredMethod("reload", Map.class);
				m.invoke(ip, recvMap);
				
			}
			
//			String srv_info_str = recvMap.get("srv_info");
//			Map<String, String> srv_info_map = analyse(srv_info_str);
//			Set<String> keyset = srv_info_map.keySet();
//			// TODO 添加处理
//			for (Iterator<String> it = keyset.iterator(); it.hasNext();) {
//				String key = (String) it.next();
//			}
			// 转给统一服务平台
			
//			ServicePlatform.getInstance().onMessage("natp", recvMap);
		 } catch (FileNotFoundException e) {
			 logger.error(e.getMessage(),e);
		}catch (EOFException e){
			if(logger.isDebugEnabled()){
				logger.error("推送消息异常,可能的原因,探测包不符合协议规范:"+e.getMessage(),e);
			}
		}
		 catch (IOException e) {
			 logger.error(e.getMessage(),e);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			closeConn(connection);
		}
	}

	/**
	 * 
	 * <p>
	 * 格式化网关返回报文
	 * </p>
	 * 
	 * @param map
	 * @return
	 */
	private Map<String, String> formateGwreturnMap(Map<String, List<?>> map) {
		Iterator<String> iter = map.keySet().iterator();
		Map<String, String> temp = new HashMap<String, String>();
		while (iter.hasNext()) {
			String key = String.valueOf(iter.next());
			String value = String.valueOf(map.get(key));
			if (value == null || value.equals("") || value.equals("null")) {
				value = "";
			} else {
				value = value.substring(1, value.length() - 1);
			}
			temp.put(key, value);
		}
		return temp;
	}

	public static void processRequest(Socket requestToHandle) {
		synchronized (pool) {
			pool.add(pool.size(), requestToHandle);
			pool.notifyAll();
		}
	}

	private void closeConn(Socket socket) {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				logger.error("\nWDPT--Socket close 失败" + e.getMessage(), e);
			}
		}
	}

	public void run() {
		while (true) {
			synchronized (pool) {
				while (pool.isEmpty()) {
					try {
						pool.wait();
					} catch (InterruptedException e) {
						logger.error("pool.wait()失败" + e.getMessage(), e);
					}
				}
				connection = (Socket) pool.remove(0);
			}
			handleConnection();
		}
	}

}
