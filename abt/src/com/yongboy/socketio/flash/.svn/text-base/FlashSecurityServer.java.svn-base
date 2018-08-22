/*    */ package com.yongboy.socketio.flash;
/*    */ 
/*    */ import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.timeout.ReadTimeoutHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;
/*    */ 
/*    */ public class FlashSecurityServer
/*    */ {
/*    */   private Channel serverChannel;
/*    */   private ServerBootstrap bootstrap;
/*    */   private int port;
/*    */ 
/*    */   public FlashSecurityServer(int port)
/*    */   {
/* 28 */     this.port = port;
/* 29 */     if (this.port < 0)
/* 30 */       this.port = 10843;
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 41 */     this.bootstrap = 
/* 43 */       new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), 
/* 43 */       Executors.newCachedThreadPool()));
/*    */ 
/* 45 */     this.bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
/* 46 */       private final Timer timer = new HashedWheelTimer();
/*    */ 
/*    */       public ChannelPipeline getPipeline() throws Exception
/*    */       {
/* 50 */         ChannelPipeline pipeline = Channels.pipeline();
/* 51 */         pipeline.addLast("timeout", new ReadTimeoutHandler(this.timer, 30));
/* 52 */         pipeline.addLast("decoder", new FlashSecurityDecoder());
/* 53 */         pipeline.addLast("handler", new FlashSecurityHandler());
/* 54 */         return pipeline;
/*    */       }
/*    */     });
/* 58 */     this.bootstrap.setOption("child.tcpNoDelay", Boolean.valueOf(true));
/* 59 */     this.bootstrap.setOption("child.keepAlive", Boolean.valueOf(true));
/*    */ 
/* 62 */     this.serverChannel = this.bootstrap.bind(new InetSocketAddress(this.port));
/*    */   }
/*    */ 
/*    */   public void stop()
/*    */   {
/* 72 */     this.serverChannel.close();
/* 73 */     this.bootstrap.releaseExternalResources();
/*    */   }
/*    */ }

/* Location:           D:\code\workspace2\chat\lib\socketio-netty.jar
 * Qualified Name:     com.yongboy.socketio.flash.FlashSecurityServer
 * JD-Core Version:    0.6.0
 */