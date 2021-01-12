package nju.java315.client.game.network;

import java.util.Arrays;

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
import nju.java315.client.game.event.EntryResultEvent;
import nju.java315.client.game.event.PutEvent;
import nju.java315.client.game.event.ReadyEvent;
import nju.java315.client.game.network.msg.GameMsgProtocol;
import nju.java315.client.game.network.msg.GameMsgProtocol.PlayerPutResult.StepInfo;
import nju.java315.client.game.util.Room.ROOM_STATE;

import static com.almasb.fxgl.dsl.FXGL.*;

public class GameMsgHandler implements MessageHandler<byte[]> {
    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgHandler.class);

    @Override
    public void onReceive(Connection<byte[]> connection, byte[] msg) {
        //LOGGER.info("get server message : {}", Arrays.toString(msg));

        GeneratedMessageV3 cmd = decodeMessage(msg);
        try {
            if (cmd instanceof GameMsgProtocol.WhatRoomsResult) {

            } else if (cmd instanceof GameMsgProtocol.PlayerEntryResult) {
                GameMsgProtocol.PlayerEntryResult result = (GameMsgProtocol.PlayerEntryResult) cmd;

                int entryPlayerID = result.getEntryPlayerID();
                if(entryPlayerID == geti("playerID")){
                    int roomID = result.getRoomID();
                    int enemyID = result.getEnemyID();
                    boolean enemyIsReady = result.getEnemyIsReady();
                    System.out.println(enemyIsReady);

                    getEventBus().fireEvent(new EntryResultEvent(EntryResultEvent.SELF_ENTRY_RESULT, roomID, enemyID, enemyIsReady));
                }
                else{
                    int enemyID = result.getEntryPlayerID();

                    getEventBus().fireEvent(new EntryResultEvent(EntryResultEvent.ENEMY_ENTRY_RESULT, enemyID));
                }
            } else if (cmd instanceof GameMsgProtocol.PlayerReadyResult) {
                GameMsgProtocol.PlayerReadyResult result = (GameMsgProtocol.PlayerReadyResult) cmd;
                int id = result.getPlayerID();

                if(id == geti("playerID"))
                    getEventBus().fireEvent(new ReadyEvent(ReadyEvent.SELF_READY));
                else if(id == geti("enemyID"))
                    getEventBus().fireEvent(new ReadyEvent(ReadyEvent.ENEMY_READY));
                else
                    LOGGER.error("未知的玩家，id = {}", id);

                ROOM_STATE roomState = decodeRoomState(result.getRoomState());
                LOGGER.info("ready");

            } else if (cmd instanceof GameMsgProtocol.PlayerPutResult) {
                GameMsgProtocol.PlayerPutResult result = (GameMsgProtocol.PlayerPutResult) cmd;
                for(int i=0;i < result.getStepInfoCount();i++){
                    StepInfo info = result.getStepInfo(i);
                    int id = info.getPlayerID();
                    float x = info.getPosX();
                    float y = info.getPosY();
                    String monsterName = info.getCharacter();
                    if(id == geti("playerID"))
                        getEventBus().fireEvent(new PutEvent(PutEvent.SELF_PUT, monsterName, x, y));
                    else if(id == geti("enemyID"))
                        getEventBus().fireEvent(new PutEvent(PutEvent.ENEMY_PUT, monsterName, x, y));
                    else
                        LOGGER.error("未知的玩家，id = {}", id);
                }

            }
            else if (cmd instanceof GameMsgProtocol.PlayerDieResult) {

            }
            else if (cmd instanceof GameMsgProtocol.PlayerLeaveResult) {

            }
            else if (cmd instanceof GameMsgProtocol.PlayerActiveResult) {
                GameMsgProtocol.PlayerActiveResult result = (GameMsgProtocol.PlayerActiveResult) cmd;
                int playerID = result.getPlayerID();
                System.out.println("get ID" + playerID);
                set("playerID", playerID);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    private ROOM_STATE decodeRoomState(int state){
        ROOM_STATE roomState = null;
        switch(state){
            case ROOM_STATE.ONT_PLAYER_VALUE:
                roomState = ROOM_STATE.ONE_PLAYER;
                break;
            case ROOM_STATE.TWO_PLAYER_VALUE:
                roomState = ROOM_STATE.TWO_PLAYER;
                break;
            case ROOM_STATE.PLAYING_VALUE:
                roomState = ROOM_STATE.PLAYING;
                break;
            default:
                break;
        }
        return roomState;
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
                case GameMsgProtocol.MsgCode.PLAYER_ACTIVE_RESULT_VALUE:
                    cmd = GameMsgProtocol.PlayerActiveResult.parseFrom(msgBody);
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
