package com.yongboy.socketio.server;

import com.yongboy.socketio.server.transport.IOClient;

public abstract interface IOHandler
{
  public abstract void OnConnect(IOClient paramIOClient);

  public abstract void OnMessage(IOClient paramIOClient, String paramString);

  public abstract void OnDisconnect(IOClient paramIOClient);

  public abstract void OnShutdown();
}

/* Location:           D:\code\workspace2\chat\lib\socketio-netty.jar
 * Qualified Name:     com.yongboy.socketio.server.IOHandler
 * JD-Core Version:    0.6.0
 */