package nju.java315.client.network;

import com.google.protobuf.GeneratedMessageV3;
import nju.java315.client.network.msg.GameMsgProtocol;
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
                case GameMsgProtocol.MsgCode.WHAT_ROOMS_RESULT_VALUE:
                    cmd = GameMsgProtocol.WhatRoomsResult.parseFrom(msgBody);
                    break;
                case GameMsgProtocol.MsgCode.PLAYER_ENTRY_RESULT_VALUE:
                    cmd = GameMsgProtocol.PlayerEntryResult.parseFrom(msgBody);
                    break;
                case GameMsgProtocol.MsgCode.PLAYER_READY_RESULT_VALUE:
                    cmd = GameMsgProtocol.PlayerReadyResult.parseFrom(msgBody);
                    break;
                case GameMsgProtocol.MsgCode.PLAYER_PUT_RESULT_VALUE:
                    cmd = GameMsgProtocol.PlayerPutResult.parseFrom(msgBody);
                    break;
                case GameMsgProtocol.MsgCode.PLAYER_DIE_RESULT_VALUE:
                    cmd = GameMsgProtocol.PlayerDieResult.parseFrom(msgBody);
                    break;
                case GameMsgProtocol.MsgCode.PLAYER_LEAVE_RESULT_VALUE:
                    cmd = GameMsgProtocol.PlayerLeaveResult.parseFrom(msgBody);
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
