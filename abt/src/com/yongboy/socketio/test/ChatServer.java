 package com.yongboy.socketio.test;
 
 import com.yongboy.socketio.MainServer;
 
 public class ChatServer
 {
   public static void main(String[] args)
   {
//     MainServer chatServer = new MainServer(new DemoChatHandler(),       9000);
  
     MainServer chatServer = new MainServer(new PhoneMsgBroadcast(),       9000);
     chatServer.start();
				
   }
 }
