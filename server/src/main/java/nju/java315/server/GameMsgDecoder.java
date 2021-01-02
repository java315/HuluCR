package nju.java315.server;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Optional;

import com.google.protobuf.GeneratedMessageV3;
import nju.java315.server.msg.GameMsgProtocol;
import nju.java315.server.util.ByteArrayUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameMsgDecoder extends ChannelInboundHandlerAdapter {
    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgDecoder.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{

        if(ctx == null || msg == null){
            LOGGER.warn("空消息");
            return;
        }
        
        try{
            int length = ((ByteBuf)msg).readInt();
            System.out.println(length);
            ByteBuf byteBuf = ((ByteBuf)msg).readSlice(length);
            //消息的类型
            //int unknownFlag = byteBuf.readShort();
            int msgCode = byteBuf.readUnsignedShort();

            byte[] msgBody = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(msgBody);
            LOGGER.info("msgCode : {}",msgCode);
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

            LOGGER.info(cmd.toString());
            if(cmd != null){
                LOGGER.info("完成处理");
                ctx.fireChannelRead(cmd);
            }
                

        }catch(Exception ex){
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}