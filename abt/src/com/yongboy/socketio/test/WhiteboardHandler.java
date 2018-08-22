package com.yongboy.socketio.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import com.agree.framework.web.form.administration.User;
import com.agree.ts.service.WorkHandlerService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yongboy.socketio.server.IOHandlerAbs;
import com.yongboy.socketio.server.transport.GenericIO;
import com.yongboy.socketio.server.transport.IOClient;

public class WhiteboardHandler extends IOHandlerAbs{
	private static final Logger log = LoggerFactory.getLogger(WhiteboardHandler.class);
	
	private ConcurrentMap<String, Set<IOClient>> roomClients = 
		new ConcurrentHashMap<String, Set<IOClient>>();
	private static ConcurrentMap<String, HashMap<String, IOClient>> padClients = 
		       new ConcurrentHashMap<String, HashMap<String, IOClient>>();
	private static String PAD="PADKEY";
	//处理客户端建立连接。
	public void OnConnect(IOClient client) {
		log.debug("A user connected :: " + client.getSessionID());

		String content = String.format("5:::{\"name\":\"%s\",\"args\":[%s]}",
				new Object[] {"clientId", String.format("{\"id\":\"%s\"}", 
						   								new Object[] { client.getSessionID() }) }
							 );
		client.send(content);
	}
	//处理客户端断开连接
	public void OnDisconnect(IOClient client) {
		log.info("A user disconnected :: " + client.getSessionID() + " :: hope it was fun");

		GenericIO genericIO = (GenericIO) client;
		Object roomObj = genericIO.attr.get("room");

		if (roomObj == null) {
			log.info("the roomObj is null!");
			return;
		}

		String roomId = roomObj.toString();

		Set<IOClient> clients = (Set<IOClient>) this.roomClients.get(roomId);
		log.info("clients size is " + clients.size());
		clients.remove(client);
		log.info("removed clients's size is " + clients.size());

		int clientNums = clients.size();

		broadcastRoom(roomId, client, "roomCount", 
						String.format("{\"room\":\"%s\",\"num\":%s}", 
								      new Object[] { roomId,Integer.valueOf(clientNums) })
					  );
	}

	public void OnMessage(final IOClient client, String oriMessage) {
		log.debug("Got a message :: " + oriMessage + " :: echoing it back to :: " + client.getSessionID());
		
		String jsonString = oriMessage.substring(oriMessage.indexOf('{'));
		jsonString = jsonString.replaceAll("\\\\", "");

		log.debug("jsonString " + jsonString);

		JSONObject jsonObject = JSON.parseObject(jsonString);
		String eventName = jsonObject.get("name").toString();

		JSONArray argsArray = jsonObject.getJSONArray("args");
		final JSONObject obj = argsArray.getJSONObject(0);

		String roomId = obj.getString("room");

		if (eventName.equals("roomNotice")) {
			if (!this.roomClients.containsKey(roomId)) {
				this.roomClients.put(roomId, new HashSet<IOClient>());
			}

			GenericIO genericIO = (GenericIO) client;
			genericIO.attr.put("room", roomId);
			((Set<IOClient>) this.roomClients.get(roomId)).add(client);

			int clientNums = ((Set<IOClient>) this.roomClients.get(roomId)).size();
			broadcastRoom(roomId, client, "roomCount", String.format("{\"room\":\"%s\",\"num\":%s}", 
																	  new Object[] { roomId, Integer.valueOf(clientNums)}));
			String content = String.format("5:::{\"name\":\"%s\",\"args\":[%s]}", 
											new Object[] {"roomCount",String.format("{\"room\":\"%s\",\"num\":%s}",
									new Object[] { roomId,
											Integer.valueOf(clientNums) }) });
			client.send(content);

			return;
		}
		/***/
		if(eventName.equals("registPad")){
			new Thread(new Runnable(){

				public void run() {
					String clientID = obj.getString("H_Pdno");
					String datacondition = "{\"branch\":\""+ obj.get("branch")+"\",\"pdno\":\""+obj.get("pdno")+"\",\"oid\":\""+obj.get("oid")+"\"}";
					obj.put("datacondition", datacondition);
					WorkHandlerService workHandlerService=(WorkHandlerService)ContextLoader.getCurrentWebApplicationContext().getBean("workHandlerService");
					net.sf.json.JSONObject rec=null;
					try {
//						Properties prop = new Properties ();
					// ClassPathResource cr = new
					// ClassPathResource("conf.properties");
//						prop.load(cr.getInputStream());
//						net.sf.json.JSONObject json=new net.sf.json.JSONObject();
//						json.accumulate("trancode", "H033");
//						json.accumulate("pdno", clientID);
//						json.accumulate("userid", userid);
//						json.accumulate("oid", oid);
//						json.accumulate("serverIP", getServerIp());
//						json.accumulate("serverPort",prop.getProperty("cust.deviceport"));
						User user = new User();
						user.setChannelID("04");
						user.setUnitid(obj.get("branch").toString());
						user.setUsercode(obj.get("userid").toString());
						user.setPdno(obj.get("pdno").toString());
					 rec=new net.sf.json.JSONObject();
					 rec = workHandlerService.fechData(obj.toString(),user);
					} catch (Exception e) {
						log.error(e.getMessage(),e);
					}
					String recode=rec.getString("H_ret_code");
					if(recode.equals("000000")){
						log.info("有手持设备接入注册,clientID为:" + clientID);
						if (!padClients.containsKey(PAD)) {
							synchronized (padClients) {
								padClients.put(PAD, new HashMap<String,IOClient>());
								padClients.get(PAD).put(clientID, client);
							}
						}else{
							padClients.get(PAD).put(clientID, client);
						}
					}else{
						String content = String.format(
								"5:::{\"name\":\"%s\",\"args\":[%s]}", new Object[] {
										"message", rec.toString() });
						client.send(content);
					}
				}
				
				
			}, "WhiteboardHandler:" + client.getSessionID()).start();
			return;
		}
		broadcastRoom(PAD, client, eventName, obj.toJSONString());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void broadcastRoom(String roomId, IOClient client, String eventName, String jsonString) {
		
		Set clients = (Set) padClients.get(roomId);
		if ((clients == null) || (clients.isEmpty())) {
			return;
		}
		String content = String.format("5:::{\"name\":\"%s\",\"args\":[%s]}",
				new Object[] { eventName, jsonString });
		Iterator<IOClient> it = clients.iterator();

		while (it.hasNext()) {
			IOClient rc = (IOClient) it.next();
			if ((rc == null)
					|| (rc.getSessionID().equals(client.getSessionID()))) {
				continue;
			}
			rc.send(content);
		}
	}

	public static void broadMsg(net.sf.json.JSONObject msg){
		String client = msg.getString("pdno");
		HashMap<String, IOClient> pdClients = (HashMap<String, IOClient>) padClients.get(PAD) == null ?
				new HashMap<String, IOClient>() : (HashMap<String, IOClient>) padClients.get(PAD);
		IOClient rc=pdClients.get(client);
		
		log.info("检查是否有符合推送条件的设备,pdno为:" + client + ",pdClients为:" + pdClients + ",rc为:" + rc);
		if(rc!=null){
			log.info("需要广播的用户对像==" + rc.getSessionID()+"ip:"+((GenericIO)rc).getIp());
			String content = String.format(
					"5:::{\"name\":\"%s\",\"args\":[%s]}", new Object[] {
							"message", msg.toString() });
			rc.send(content);
		}else{
			
		}
	}
	public static void sendMessage(JSONObject message){
		
	}
	public void OnShutdown() {
		log.debug("shutdown now ~~~");
	}
	

	class RegistClient implements Runnable{
		
		private String clientID;
		/* 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			net.sf.json.JSONObject json=new net.sf.json.JSONObject();
			json.accumulate("trancode", "H033");
			json.accumulate("clientid", clientID);
		}
	}
}
