package nju.java315.client.network;

import io.netty.channel.ChannelOutboundHandlerAdapter;
import com.google.protobuf.GeneratedMessageV3;
import nju.java315.client.network.msg.GameMsgProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class GameMsgEncoder extends ChannelOutboundHandlerAdapter{
    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgEncoder.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception{
        if(ctx == null || msg == null)
            return;

        if(!(msg instanceof GeneratedMessageV3)){
            super.write(ctx, msg, promise);
            return;
        }

        try{
            int msgCode = -1;

            if(msg instanceof GameMsgProtocol.WhatRoomsCmd)
                msgCode = GameMsgProtocol.MsgCode.WHAT_ROOMS_CMD_VALUE;
            else if(msg instanceof GameMsgProtocol.PlayerEntryCmd)
                msgCode = GameMsgProtocol.MsgCode.PLAYER_ENTRY_CMD_VALUE;
            else if(msg instanceof GameMsgProtocol.PlayerReadyCmd)
                msgCode = GameMsgProtocol.MsgCode.PLAYER_READY_CMD_VALUE;
            else if(msg instanceof GameMsgProtocol.PlayerPutCmd)
                msgCode = GameMsgProtocol.MsgCode.PLAYER_PUT_CMD_VALUE;
            else if(msg instanceof GameMsgProtocol.PlayerDieCmd)
                msgCode = GameMsgProtocol.MsgCode.PLAYER_DIE_CMD_VALUE;
            else if(msg instanceof GameMsgProtocol.PlayerLeaveCmd)
                msgCode = GameMsgProtocol.MsgCode.PLAYER_LEAVE_CMD_VALUE;
            else{
                LOGGER.error(
                    "无法识别的消息类型，msgClass = {}",
                    msg.getClass().getSimpleName()
                );
                super.write(ctx, msg, promise);
                return;
            }

            byte[] msgBody = ((GeneratedMessageV3)msg).toByteArray();

            ByteBuf byteBuf = ctx.alloc().buffer();
            byteBuf.writeShort((short)msgCode);
            byteBuf.writeBytes(msgBody);

            String outputFrame = byteBuf.toString(CharsetUtil.UTF_8);
            super.write(ctx, outputFrame, promise);
        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
