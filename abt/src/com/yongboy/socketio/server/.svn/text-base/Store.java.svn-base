package com.yongboy.socketio.server;

import java.util.Collection;

import org.jboss.netty.channel.ChannelHandlerContext;

import com.yongboy.socketio.server.transport.IOClient;

public abstract interface Store
{
  public abstract void remove(String paramString);

  public abstract void add(String paramString, IOClient paramIOClient);

  public abstract Collection<IOClient> getClients();

  public abstract IOClient get(String paramString);

  public abstract boolean checkExist(String paramString);

  public abstract IOClient getByCtx(ChannelHandlerContext paramChannelHandlerContext);
}

/* Location:           D:\code\workspace2\chat\lib\socketio-netty.jar
 * Qualified Name:     com.yongboy.socketio.server.Store
 * JD-Core Version:    0.6.0
 */