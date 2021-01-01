package nju.java315.client.game.network;

import com.almasb.fxgl.net.Connection;
import com.almasb.fxgl.net.MessageHandler;
import com.google.protobuf.GeneratedMessageV3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import nju.java315.client.game.network.msg.GameMsgProtocol;

public class GameMsgHandler implements MessageHandler<String> {
    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgHandler.class);
    @Override
    public void onReceive(Connection<String> connection, String msg) {
        System.out.println("get server message");
        GeneratedMessageV3 cmd = decodeMessage(msg);
        try{
            if (cmd instanceof GameMsgProtocol.WhatRoomsResult) {

            }
            else if (cmd instanceof GameMsgProtocol.PlayerEntryResult) {
                System.out.println("player enter");
            }
            else if (cmd instanceof GameMsgProtocol.PlayerReadyResult) {
                System.out.println("player ready");
            }
            else if (cmd instanceof GameMsgProtocol.PlayerPutResult) {

            }
            else if (cmd instanceof GameMsgProtocol.PlayerDieResult) {

            }
            else if (cmd instanceof GameMsgProtocol.PlayerLeaveResult) {

            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    private GeneratedMessageV3 decodeMessage(String msg) {
        if (msg == null || !(msg instanceof String))
            return null;

        try {
            byte[] bytes = msg.getBytes(CharsetUtil.UTF_8);
            ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);

            // 消息的类型
            int msgCode = byteBuf.readShort();

            byte[] msgBody = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(msgBody);

            GeneratedMessageV3 cmd = null;

            switch (msgCode) {
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

            if (cmd != null)
                return cmd;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

        return null;
    }


    
}
