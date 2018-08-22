/*    */ package com.yongboy.socketio.flash;
/*    */ 
/*    */ import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;
import org.jboss.netty.handler.codec.replay.VoidEnum;
import org.jboss.netty.util.CharsetUtil;
/*    */ 
/*    */ public class FlashSecurityDecoder extends ReplayingDecoder<VoidEnum>
/*    */ {
/* 19 */   private static final ChannelBuffer requestBuffer = ChannelBuffers.copiedBuffer("<policy-file-request/>", CharsetUtil.UTF_8);
/*    */ 
/*    */   protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, VoidEnum state)
/*    */   {
/* 25 */     ChannelBuffer data = buffer.readBytes(requestBuffer.readableBytes());
/* 26 */     if (data.equals(requestBuffer)) {
/* 27 */       return data;
/*    */     }
/* 29 */     channel.close();
/*    */ 
/* 31 */     return null;
/*    */   }
/*    */ }

/* Location:           D:\code\workspace2\chat\lib\socketio-netty.jar
 * Qualified Name:     com.yongboy.socketio.flash.FlashSecurityDecoder
 * JD-Core Version:    0.6.0
 */