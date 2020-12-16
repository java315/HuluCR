package nju.java315.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import jdk.javadoc.internal.doclets.formats.html.resources.standard;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import nju.java315.server.Room.ROOM_STATE;
import nju.java315.server.msg.GameMsgProtocol;
import io.netty.util.CharsetUtil;



public class GameMsgHandler extends SimpleChannelInboundHandler<Object> {

    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgHandler.class);
    static private final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // channel.id 到 Player 的映射
    static private HashMap<ChannelId, Player> players = new HashMap<>();

    // 房间号到房间的映射
    static private HashMap<Integer, Room> rooms = new HashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if(ctx == null)
            return;
        Channel channel = ctx.channel();
        channelGroup.add(channel);
        players.put(channel.id(), new Player(channel.id()));
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

                    roomInfoBuilder.setRoomID(entry.getKey());
                    roomInfoBuilder.setRoomState(entry.getValue().getRoomState().getValue());
                    resultBuilder.addRoomInfo(roomInfoBuilder);
                }

                GameMsgProtocol.WhatRoomsResult result = resultBuilder.build();
                ctx.writeAndFlush(result);
            }

            else if(msg instanceof GameMsgProtocol.UserEntryCmd){
                GameMsgProtocol.UserEntryCmd cmd = (GameMsgProtocol.UserEntryCmd)msg;

                int roomID = cmd.getRoomID();
                int userID = cmd.getUserID();
                Player joiner = players.get(channel.id());
                Player opponent = null;
                Boolean success = false;

                joiner.setPlayerID(userID);

                if(rooms.containsKey(roomID)){
                    Room room = rooms.get(roomID);
                    if(room.getRoomState() == ROOM_STATE.ONE_PLAYER){
                        opponent = room.getPlayer();
                        room.join(joiner);
                        success = true;
                    }
                }
                else{
                    Room room = new Room(joiner);
                    rooms.put(roomID, room);
                    success = true;
                }

                if(success){
                    joiner.setRoomID(roomID);
                    GameMsgProtocol.UserEntryResult.Builder resultBuilder = GameMsgProtocol.UserEntryResult.newBuilder();
                    resultBuilder.setOpponentID(opponent == null? -1 : opponent.getPlayerID());
                    resultBuilder.setRoomID(roomID);

                    GameMsgProtocol.UserEntryResult result = resultBuilder.build();
                    channel.writeAndFlush(result);

                    //通知已经在房间里的玩家
                    if(opponent != null){
                        channelGroup.find(opponent.getChannelID()).writeAndFlush(result);
                    }
                }
                else{
                    GameMsgProtocol.UserEntryResult.Builder resultBuilder = GameMsgProtocol.UserEntryResult.newBuilder();
                    resultBuilder.setOpponentID(-1);
                    resultBuilder.setRoomID(-1);

                    GameMsgProtocol.UserEntryResult result = resultBuilder.build();
                    channel.writeAndFlush(result);
                }
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