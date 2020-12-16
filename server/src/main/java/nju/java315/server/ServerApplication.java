/*
 * @Author: zb-nju
 * @Date: 2020-12-13 23:41:23
 * @LastEditors: zb-nju
 * @LastEditTime: 2020-12-16 08:35:31
 */
package nju.java315.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

@SpringBootApplication
public class ServerApplication {

	static private final Logger LOGGER = LoggerFactory.getLogger(ServerApplication.class);

	public static void main(String[] args) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();

		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workGroup);
		b.channel(NioServerSocketChannel.class);
		b.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();

				//pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
				//pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
				//pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
				pipeline.addLast(new GameMsgDecoder());
				pipeline.addLast(new GameMsgEncoder());
				pipeline.addLast(new GameMsgHandler());
			}
		});

		b.option(ChannelOption.SO_BACKLOG, 128);
		b.childOption(ChannelOption.SO_KEEPALIVE, true);

		try {
			// 绑定端口
			ChannelFuture f = b.bind(8080).sync();

			if (f.isSuccess()){
				LOGGER.info("Server start successfully!");
			}

			// 等待服务器信道关闭
			f.channel().closeFuture().sync();

		} catch (Exception e){
			LOGGER.error(e.getMessage(), e);
		} finally {
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}
