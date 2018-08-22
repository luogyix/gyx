package com.yongboy.socketio.server.transport;

import org.jboss.netty.channel.ChannelHandlerContext;

import com.yongboy.socketio.server.IOHandler;

public abstract interface IOClient
{
  public abstract void send(String paramString);

  public abstract void sendEncoded(String paramString);

  public abstract void heartbeat(IOHandler paramIOHandler);

  public abstract void disconnect();

  public abstract String getSessionID();

  public abstract ChannelHandlerContext getCTX();

  public abstract String getId();

  public abstract boolean isOpen();

  public abstract void setOpen(boolean paramBoolean);
}
