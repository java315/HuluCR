package nju.java315.client.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.ScheduledFuture;

public class GameMsgHandler extends SimpleChannelInboundHandler<Object>{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }
    
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {

    }
}
