/*
 * Copyright(C) 2007 Agree Tech, All rights reserved.
 * 
 * Created on 2007-2-7   by Wang Xinhe
 */

package com.agree.framework.communication.natp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.LoggerFactory;

public class NatpPlugin {

	// The shared instance.
	private static NatpPlugin plugin;
	private static final org.slf4j.Logger logger =LoggerFactory.getLogger(NatpPlugin.class);
	public static final Long LONG_ONE = new Long(1);
	public static final Long LONG_ZERO = new Long(0);
	public static final String DEFAULT_TIMEOUT = "60";
	public static final String DEFAULT_LOAD_FACTOR = "1";
	/**
	 * Logger for this class
	 */
	// private static final Log logger = LogFactory.getLog(NatpPlugin.class);

	@SuppressWarnings("rawtypes")
	private static final List serverGroupList = new ArrayList();

	@SuppressWarnings("rawtypes")
	private static final Hashtable serverHashtable = new Hashtable();

	@SuppressWarnings("rawtypes")
	private static final Hashtable eachServerCurrentTradeCountTable = new Hashtable();
	
	@SuppressWarnings("rawtypes")
	private Hashtable sectionTable = new Hashtable();


	/*
	 * private static boolean isDebug(){ if (debugMode == null) { String
	 * debugModeStr = Platform.getPreferencesService().getString(DEBUGQUALIFIER,
	 * DEBUGMODE, "false", null); debugMode = new
	 * Boolean("true".equals(debugModeStr)); } return debugMode.booleanValue();
	 * }
	 */

	/**
	 * The constructor.
	 */
	@SuppressWarnings("unchecked")
	public NatpPlugin() {
		Collections.synchronizedList(serverGroupList);
		plugin = this;
		// logger.debug("new NatpPlugin instance...");
	}

	/**
	 * This method is called upon plug-in activation
	 */
	// public void start(BundleContext context) throws Exception {
	// super.start(context);
	// }

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop() throws Exception {
		// super.stop(context);
		serverGroupList.clear();
		serverHashtable.clear();
		eachServerCurrentTradeCountTable.clear();
		sectionTable.clear();
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static NatpPlugin getDefault() {
		return plugin;
	}

	public long getServerCurrentTradeCount(String serverGroup, String serverName) {
		return ((Long) (eachServerCurrentTradeCountTable.get(serverGroup
				+ INatpConstants.GROUP_SERVER_CONNECTOR + serverName)))
				.longValue();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void loadCfg() throws IOException {
		if (natpcfg == null) {
			throw new IOException("AFA服务器配置未加载成功！");
		}
		String[] groups = natpcfg.split(";");
		if (groups.length == 0) {
			return;
		}
		// ServerName@IP:port:timeout:loadfactor,IP:port:timeout:loadfactor;ServerName@IP:port:timeout:loadfactor,IP:port:timeout:loadfactor
		sectionTable = new Hashtable();
		for (int i = 0; i < groups.length; i++) {
			String group = groups[i];
			if (group != null && group.length() != 0) {
				String[] segs = group.trim().split("@");
				if (segs == null || segs.length == 0) {
					continue;
				}
				if (segs.length == 1 || segs.length == 2) {
					Hashtable currentHashtable = (Hashtable) sectionTable
							.get(segs.length == 1 ? "default" : segs[0].trim());
					if (currentHashtable == null) {
						currentHashtable = new Hashtable();
						sectionTable.put(
								segs.length == 1 ? "default" : segs[0],
								currentHashtable);
					}
					String[] ips = segs[0].split(",");
					for (int j = 0; j < ips.length; j++) {
						String ip = ips[j].trim();
						currentHashtable.put("server" + (j + 1), ip);
					}

				}else{
					logger.error("AFA服务器地址格式配置错误："+group);
				}

			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void copyAllGroupPropery()throws IOException{
		serverGroupList.clear();
		serverGroupList.addAll(sectionTable.keySet());//把所有组加入列表中
		
		//遍历每个组中的服务，把相关信息保存.
		for(int i=0;i<serverGroupList.size();i++){
			String serverGroupName=(String)serverGroupList.get(i);
			Hashtable groupServerPro=(Hashtable) sectionTable.get(serverGroupName);
			if(groupServerPro!=null){
				List group=new LinkedList();
				Set set=groupServerPro.entrySet();
				Iterator iterator=set.iterator();
				while(iterator.hasNext()){
					Map.Entry entry=(Map.Entry)iterator.next();
					String serverKey=(String)entry.getKey();
					String serverVal=(String)entry.getValue();
					String[] segs=(serverVal).split(":");
					
					String group_server=(serverGroupName+INatpConstants.GROUP_SERVER_CONNECTOR+serverKey);
			    	if(segs.length<INatpConstants.SERVERCONFIGFILE_PARAM_LENGHT){
			    		new IOException("服务器“"+group_server+"”的配置错误，格式为“IP地址:端口号:超时:负载系数”！");
			    	}
			    	eachServerCurrentTradeCountTable.put(group_server,LONG_ZERO);
					group.add(new ServerEntry(serverGroupName,serverKey,segs[0],Integer.parseInt(segs[1]),Integer.parseInt(segs[2]),Integer.parseInt(segs[3])));
				}
				serverHashtable.put(serverGroupName,group);
			}
			
		}
	}


	@SuppressWarnings("rawtypes")
	public List getAllServerByGroup(String serverGroupName) throws IOException {
		if(serverHashtable.isEmpty()){
			loadCfg();
			copyAllGroupPropery();
		}
		return (List) (serverHashtable.get(serverGroupName));
	}

	@SuppressWarnings("unchecked")
	public void increaseTradeCount(String groupName, String serverName) {
		String key = (groupName + INatpConstants.GROUP_SERVER_CONNECTOR + serverName);
		long serverTradeCount = ((Long) (eachServerCurrentTradeCountTable
				.get(key))).longValue();
		eachServerCurrentTradeCountTable.put(key,
				new Long(serverTradeCount + 1));
	}

	@SuppressWarnings("unchecked")
	public void decreaseTradeCount(String groupName, String serverName) {
		String key = (groupName + INatpConstants.GROUP_SERVER_CONNECTOR + serverName);
		long serverTradeCount = ((Long) (eachServerCurrentTradeCountTable
				.get(key))).longValue();
		if (serverTradeCount > 0) {
			eachServerCurrentTradeCountTable.put(key, new Long(
					serverTradeCount - 1));
		}

	}

	private String natpcfg;

	public String getNatpcfg() {
		return natpcfg;
	}

	public void setNatpcfg(String natpcfg) {
		this.natpcfg = natpcfg;
	}

}
