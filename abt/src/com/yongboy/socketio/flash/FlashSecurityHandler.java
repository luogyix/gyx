/*    */ package com.yongboy.socketio.flash;
/*    */ 
/*    */ import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.util.CharsetUtil;
/*    */ 
/*    */ public class FlashSecurityHandler extends SimpleChannelUpstreamHandler
/*    */ {
/* 22 */   //private static final Logger log = Logger.getLogger(FlashSecurityHandler.class);
			private static Log log = LogFactory.getLog("myTest1");
/*    */ 
/* 24 */   private static ChannelBuffer channelBuffer = ChannelBuffers.copiedBuffer(
/* 25 */     "<?xml version=\"1.0\"?><!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\"><cross-domain-policy>    <site-control permitted-cross-domain-policies=\"master-only\"/>   <allow-access-from domain=\"*\" to-ports=\"*\" /></cross-domain-policy>", 
/* 30 */     CharsetUtil.UTF_8);
/*    */ 
/*    */   public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
/*    */     throws Exception
/*    */   {
/* 35 */     ChannelFuture f = e.getChannel().write(channelBuffer);
/* 36 */     f.addListener(ChannelFutureListener.CLOSE);
/*    */   }
/*    */ 
/*    */   public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
/*    */     throws Exception
/*    */   {
/* 42 */     log.warn("Exception now ...");
/* 43 */     e.getChannel().close();
/*    */   }
/*    */ }

/* Location:           D:\code\workspace2\chat\lib\socketio-netty.jar
 * Qualified Name:     com.yongboy.socketio.flash.FlashSecurityHandler
 * JD-Core Version:    0.6.0
 */