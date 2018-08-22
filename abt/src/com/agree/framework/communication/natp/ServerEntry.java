 /*
 * Copyright(C) 2007 Agree Tech, All rights reserved.
 * 
 * Created on 2007-2-7   by Wang Xinhe
 */

package com.agree.framework.communication.natp;

 /**
 * <DL><DT>
 * 服务节点.
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
public class ServerEntry {
	
	private String groupName;
	private String serverName;
	private String ipAddress;
	private int port;
	private int timeout;
	
	private int loadFactor;
	
	
	
	public ServerEntry(String groupName,String serverName,String ipAddress,int port,int timeout,int loadFactor){
		this.serverName=serverName;
		this.ipAddress=ipAddress;
		this.port=port;
		this.timeout=timeout;
		this.loadFactor=loadFactor;
		this.groupName=groupName;
	}
	
	public String getGroupName(){
		return this.groupName;
	}
	public String getServerName(){
		return this.serverName;
	}
	
	public String getIpAddress(){
		return this.ipAddress;
	}
	
	public int getPort(){
		return this.port;
	}
	
	public int getTimeout(){
		return this.timeout;
	}
	
	public int getLoadFactor(){
		return this.loadFactor;
	}
	public String  toString(){
		return "[groupName:"+this.groupName+" serverName:"+this.serverName+" ip:"+this.ipAddress+" port:"+this.port+" timeout:"+this.timeout+" loadFactor:"+this.loadFactor+"]";
	}

}
