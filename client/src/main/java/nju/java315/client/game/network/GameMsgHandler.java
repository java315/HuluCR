package nju.java315.client.game.network;

import java.util.Arrays;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.net.Connection;
import com.almasb.fxgl.net.MessageHandler;
import com.almasb.fxgl.net.Readers;
import com.google.protobuf.GeneratedMessageV3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import javafx.geometry.Point2D;
import nju.java315.client.game.event.PutEvent;
import nju.java315.client.game.network.msg.GameMsgProtocol;
import nju.java315.client.game.network.msg.GameMsgProtocol.PlayerPutResult.StepInfo;

public class GameMsgHandler implements MessageHandler<byte[]> {
    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgHandler.class);

    @Override
    public void onReceive(Connection<byte[]> connection, byte[] msg) {
        LOGGER.info("get server message : {}", Arrays.toString(msg));

        GeneratedMessageV3 cmd = decodeMessage(msg);
        try {
            if (cmd instanceof GameMsgProtocol.WhatRoomsResult) {

            } else if (cmd instanceof GameMsgProtocol.PlayerEntryResult) {
                GameMsgProtocol.PlayerEntryResult result = (GameMsgProtocol.PlayerEntryResult) cmd;
                int roomID = result.getRoomID();
                LOGGER.info("success enter room {}", roomID);
            } else if (cmd instanceof GameMsgProtocol.PlayerReadyResult) {
                LOGGER.info("ready");

            } else if (cmd instanceof GameMsgProtocol.PlayerPutResult) {
                GameMsgProtocol.PlayerPutResult result = (GameMsgProtocol.PlayerPutResult) cmd;
                StepInfo info = result.getStepInfo(1);
                float x = info.getPosX();
                float y = info.getPosY();
                String monsterName = info.getCharacter();
                FXGL.getEventBus().fireEvent(new PutEvent(PutEvent.ENEMY_PUT,monsterName,new Point2D(x,y)));
            }
            else if (cmd instanceof GameMsgProtocol.PlayerDieResult) {

            }
            else if (cmd instanceof GameMsgProtocol.PlayerLeaveResult) {

            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    private GeneratedMessageV3 decodeMessage(byte[] msg) {
        if (msg == null)
            return null;

        try {

            ByteBuf byteBuf = Unpooled.wrappedBuffer(msg);

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
