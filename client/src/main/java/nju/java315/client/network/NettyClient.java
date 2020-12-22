/*
 * @Author: zb-nju
 * @Date: 2020-12-13 23:48:37
 * @LastEditors: zb-nju
 * @LastEditTime: 2020-12-13 23:56:31
 */
package nju.java315.client.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    public void start() throws Exception {
        String host = "127.0.0.1";
        int port = 8080;

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
				    ChannelPipeline pipeline = ((Channel) ch).pipeline();
                    pipeline.addLast(new GameMsgDecoder());
                    pipeline.addLast(new GameMsgEncoder());
                    pipeline.addLast(new GameMsgHandler());
				}
            });

            // 启动客户端.
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();

        } finally {
            group.shutdownGracefully();
        }
    }
}
