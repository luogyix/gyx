 /*
 * Copyright(C) 2007 Agree Tech, All rights reserved.
 * 
 * Created on 2007-2-7   by Wang Xinhe
 */

package com.agree.framework.communication.natp;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

 /**
 * <DL><DT>
 * Logger for this class.
 * </DT><p><DD>
 * 详细介绍
 * </DD></DL><p>
 * 
 * <DL><DT><B>使用范例</B></DT><p><DD>
 * 使用范例说明
 * </DD></DL><p>
 * 
 * @author Wang Xinhe
 * @author 赞同科技
 * @version $Revision: 1.1 $ $Date: 2011/03/16 01:53:03 $ 
 */
public class ServerFinder {
	
	public static Object[] createSocket(String groupName) throws IOException{
	    	
		Object[] objArr=findServer(groupName);
		 if(objArr==null){
			 throw new UnknownHostException("找不到可用的通讯配置来提供服务.配置名称：" + groupName + "\n请联系技术人员解决此问题！");
		 }else{
			 return objArr;
		 }
		 
	 }
	 
	 
	 @SuppressWarnings({ "rawtypes", "unchecked", "resource" })
	private static Object[] findServer(String serverGroup)throws IOException{
		
		//synchronized(lock){
			 Object[] retV=null;
			 Socket socket=null;
			 List list=null;
			 list=NatpPlugin.getDefault().getAllServerByGroup(serverGroup);
			 if(list==null ||list.isEmpty())
			 {
				 String errorMsg="在server.properties找不到这个组:"+serverGroup;
//				 if(logger.isErrorEnabled())
//				 {
//					 logger.error(errorMsg);
//				 }
				 throw new IOException(errorMsg);
			 }
			 List tmpList=new ArrayList(list);
			 try
			 {
				 try
				 {
					 ServerEntryComparator comparator = new ServerEntryComparator();
					 Collections.sort(tmpList, comparator);
				 } catch (Exception ex) 
				 {
//					 logger.error("sort had exception:" + ex.getMessage());
				 }
				 Iterator iterator = tmpList.iterator();
				 while (iterator.hasNext())
				 {
					 ServerEntry entry = (ServerEntry) iterator.next();
					 try 
					 {
						 socket = new Socket(entry.getIpAddress(), entry.getPort());
						 socket.setSoTimeout(entry.getTimeout() * 1000);
						 if (socket.isConnected()) {
							 // logger.debug("已经选定"+entry.getGroupName()+"_"+entry.getServerName()+"提供服务。");
							 retV = new Object[2];
							 retV[0] = socket;
							 retV[1] = entry.getServerName();
							 return retV;
						 } else 
						 {
							 continue;
						 }
					 } catch (Exception e) 
					 {
//						 logger.error(entry.getGroupName() + "_" + entry.getServerName()
//								 + "不能够正常建立连接。");
						 if (socket != null) {
							 try {
								 socket.close();
							 } catch (Exception ex) {

							 }
						 }
					 }
				 }
			 }finally
			 {
				 if(tmpList!=null)
				 {
					 tmpList.clear();
					 tmpList=null;
				 }
			 }
			
			 return retV;
		//}
	}
	 
	 
		 

}

@SuppressWarnings("rawtypes")
class ServerEntryComparator implements java.util.Comparator{

	
	
		public boolean equals(Object obj){
			return (obj==null?false:(obj instanceof ServerEntryComparator));
		}
		
		
	
		public int compare(Object o1, Object o2) {
			
			
			
			ServerEntry entry1=(ServerEntry)o1;
			ServerEntry entry2=(ServerEntry)o2;
			
			
			
			String serverName1=entry1.getServerName();
			String groupName1=entry1.getGroupName();
			int loadFactor1=entry1.getLoadFactor();
			int  timeout1=entry1.getTimeout();
			long tradeCount1=NatpPlugin.getDefault().getServerCurrentTradeCount(groupName1,serverName1);
			
			String serverName2=entry2.getServerName();
			String groupName2=entry2.getGroupName();
			int loadFactor2=entry2.getLoadFactor();
			int timeout2=entry2.getTimeout();
			long tradeCount2=NatpPlugin.getDefault().getServerCurrentTradeCount(groupName2,serverName2);
			
	
			int server1_ratio=(int)(tradeCount1*1.0d/loadFactor1*100);
			
			int server2_ratio=(int)(tradeCount2*1.0d/loadFactor2*100);
			
			
			
			if(server1_ratio<server2_ratio){
				return -1;
			}else if(server1_ratio==server2_ratio){
				if(timeout1>timeout2){
					return -1;
				}else if(timeout1<timeout2){
					return 1;
				}else{
					return serverName1.compareTo(serverName2);
				}
			}else{
				return 1;
			}
			
		}
		
	}
