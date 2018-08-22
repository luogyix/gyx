package com.yongboy.socketio.server;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerPipelineFactory implements ChannelPipelineFactory {
	private IOHandlerAbs handler;
	private static final Logger log = LoggerFactory.getLogger(ServerPipelineFactory.class);
	public ServerPipelineFactory(IOHandlerAbs handler) {
		log.info("Constructed ...");
		this.handler = handler;
	}

	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("decoder", new HttpRequestDecoder());
		pipeline.addLast("aggregator", new HttpChunkAggregator(65536));
		pipeline.addLast("encoder", new HttpResponseEncoder());

		pipeline.addLast("handler", new SocketIOTransportAdapter(this.handler));
		return pipeline;
	}
}

/*
 * Location: D:\code\workspace2\chat\lib\socketio-netty.jar Qualified Name:
 * com.yongboy.socketio.server.ServerPipelineFactory JD-Core Version: 0.6.0
 */