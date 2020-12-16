package nju.java315.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import nju.java315.server.msg.GameMsgProtocol;
import io.netty.util.CharsetUtil;



public class GameMsgHandler extends SimpleChannelInboundHandler<Object> {

    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgHandler.class);

    static private HashMap<Integer, Room> rooms = new HashMap<>();
    static private HashMap<Channel, Integer> channelToRoomId = new HashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if(ctx == null)
            return;

        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception{
        if(ctx == null || msg == null)
            return;

        try{
            Channel channel = ctx.channel();
            if(msg instanceof GameMsgProtocol.WhatRoomsCmd){
                GameMsgProtocol.WhatRoomsResult.Builder resultBuilder = GameMsgProtocol.WhatRoomsResult.newBuilder();

                for(Map.Entry<Integer, Room> entry : rooms.entrySet()){
                    GameMsgProtocol.WhatRoomsResult.RoomInfo.Builder roomInfoBuilder = GameMsgProtocol.WhatRoomsResult.RoomInfo.newBuilder();

                    roomInfoBuilder.setRoomId(entry.getKey());
                    roomInfoBuilder.setRoomState(entry.getValue().getRoomState().getValue());
                    resultBuilder.addRoomInfo(roomInfoBuilder);
                }

                GameMsgProtocol.WhatRoomsResult result = resultBuilder.build();
                ctx.writeAndFlush(result);
            }

            else if(msg instanceof GameMsgProtocol.UserEntryCmd){

            }
            else if(msg instanceof GameMsgProtocol.UserReadyCmd){

            }
            else if(msg instanceof GameMsgProtocol.UserPutCmd){

            }
            else if(msg instanceof GameMsgProtocol.UserDieCmd){

            }
            else if(msg instanceof GameMsgProtocol.UserLeaveCmd){

            }
            


        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
        }


    }
}