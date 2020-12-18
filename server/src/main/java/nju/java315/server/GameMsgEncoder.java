package nju.java315.server;

import io.netty.channel.ChannelOutboundHandlerAdapter;
import com.google.protobuf.GeneratedMessageV3;
import nju.java315.server.msg.GameMsgProtocol;
import nju.java315.server.msg.GameMsgProtocol.WhatRoomsResult;
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

            if(msg instanceof WhatRoomsResult)
                msgCode = GameMsgProtocol.MsgCode.WHAT_ROOMS_RESULT_VALUE;
            else if(msg instanceof GameMsgProtocol.PlayerEntryResult)
                msgCode = GameMsgProtocol.MsgCode.PLAYER_ENTRY_RESULT_VALUE;
            else if(msg instanceof GameMsgProtocol.PlayerReadyResult)
                msgCode = GameMsgProtocol.MsgCode.PLAYER_READY_RESULT_VALUE;
            else if(msg instanceof GameMsgProtocol.PlayerPutResult)
                msgCode = GameMsgProtocol.MsgCode.PLAYER_PUT_RESULT_VALUE;
            else if(msg instanceof GameMsgProtocol.PlayerDieResult)
                msgCode = GameMsgProtocol.MsgCode.PLAYER_DIE_RESULT_VALUE;
            else if(msg instanceof GameMsgProtocol.PlayerLeaveResult)
                msgCode = GameMsgProtocol.MsgCode.PLAYER_LEAVE_RESULT_VALUE;
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
