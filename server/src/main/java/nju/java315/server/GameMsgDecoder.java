package nju.java315.server;

import com.google.protobuf.GeneratedMessageV3;
import nju.java315.server.msg.GameMsgProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameMsgDecoder extends ChannelInboundHandlerAdapter{
    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgDecoder.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        if(ctx == null || msg == null)
            return;

        if(!(msg instanceof String))
            return;

        try{
            byte[] bytes = ((String)msg).getBytes(CharsetUtil.UTF_8);
            ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);

            //消息的类型
            int msgCode = byteBuf.readShort();

            byte[] msgBody = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(msgBody);

            GeneratedMessageV3 cmd = null;

            switch(msgCode){
                case GameMsgProtocol.MsgCode.WHAT_ROOMS_CMD_VALUE:
                    cmd = GameMsgProtocol.WhatRoomsCmd.parseFrom(msgBody);
                    break;
                case GameMsgProtocol.MsgCode.PLAYER_ENTRY_CMD_VALUE:
                    cmd = GameMsgProtocol.PlayerEntryCmd.parseFrom(msgBody);
                    break;
                case GameMsgProtocol.MsgCode.PLAYER_READY_CMD_VALUE:
                    cmd = GameMsgProtocol.PlayerReadyCmd.parseFrom(msgBody);
                    break;
                case GameMsgProtocol.MsgCode.PLAYER_PUT_CMD_VALUE:
                    cmd = GameMsgProtocol.PlayerPutCmd.parseFrom(msgBody);
                    break;
                case GameMsgProtocol.MsgCode.PLAYER_DIE_CMD_VALUE:
                    cmd = GameMsgProtocol.PlayerDieCmd.parseFrom(msgBody);
                    break;
                case GameMsgProtocol.MsgCode.PLAYER_LEAVE_CMD_VALUE:
                    cmd = GameMsgProtocol.PlayerLeaveCmd.parseFrom(msgBody);
                    break;
                default:
                    break;
            }


            if(cmd != null)
                ctx.fireChannelRead(cmd);

        }catch(Exception ex){
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}