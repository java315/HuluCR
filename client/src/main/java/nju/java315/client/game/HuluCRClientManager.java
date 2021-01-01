package nju.java315.client.game;

import java.nio.charset.StandardCharsets;

import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.net.Client;
import com.almasb.fxgl.net.ClientConfig;
import com.almasb.fxgl.net.Connection;
import com.google.protobuf.GeneratedMessageV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.util.ArrayUtils;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import nju.java315.client.game.network.GameMsgHandler;
import nju.java315.client.game.network.msg.GameMsgProtocol;

class HuluCRClientManager{
    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgHandler.class);
    private Client<String> client;
    private Connection<String> conn;
    public HuluCRClientManager(String ip, int port){
        try {
            client = FXGL.getNetService().newTCPClient(ip, port, new ClientConfig(String.class));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }

        
        client.setOnConnected(connection -> {
            LOGGER.info("connect to server : " + connection.isConnected());
            conn = connection;
            connection.addMessageHandlerFX(new GameMsgHandler());
        });
        client.connectAsync();
        
    }
    
    public void enterRoom(int id){
        GameMsgProtocol.PlayerEntryCmd.Builder builder = GameMsgProtocol.PlayerEntryCmd.newBuilder();
        builder.setRoomID(id);
        builder.setPlayerID(100);
        GameMsgProtocol.PlayerEntryCmd cmd = builder.build();
        String outputFrame = encode(cmd);

        conn.send(outputFrame);
    }

    private String encode(Object msg){
        try {
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
                return "";
            }

            byte[] msgBody = ((GeneratedMessageV3)msg).toByteArray();

            byte[] bytes = new byte[2 + msgBody.length]; 
            bytes[0] = (byte) (msgCode >> 8 & 0xFF);
            bytes[1] = (byte) (msgCode & 0xFF);
            for(int i=0;i<msgBody.length;i++){
                bytes[i+2] = msgBody[i];
            }
            String outputFrame = new String(bytes,StandardCharsets.UTF_8);
            
            return outputFrame;
        } catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
        }
        return "";
    }
}