package nju.java315.server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import nju.java315.server.Room.ROOM_STATE;
import nju.java315.server.msg.GameMsgProtocol;
import io.netty.util.concurrent.ScheduledFuture;



public class GameMsgHandler extends SimpleChannelInboundHandler<Object> {

    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgHandler.class);
    //static private final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // channel.id 到 Player 的映射
    static private HashMap<ChannelId, Player> players = new HashMap<>();

    // 房间号到房间的映射
    static private HashMap<Integer, Room> rooms = new HashMap<>();

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if(ctx == null)
            return;
        Channel channel = ctx.channel();
        //channelGroup.add(channel);
        players.put(channel.id(), new Player(channel));
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
                channel.writeAndFlush(result);
            }

            else if(msg instanceof GameMsgProtocol.PlayerEntryCmd){
                GameMsgProtocol.PlayerEntryCmd cmd = (GameMsgProtocol.PlayerEntryCmd)msg;

                int roomID = cmd.getRoomID();
                int userID = cmd.getPlayerID();
                
                LOGGER.info(userID + "enter room" + roomID);

                Player joiner = players.get(channel.id());
                Player opponent = null;
                Boolean success = false;

                joiner.setPlayerID(userID);

                if(rooms.containsKey(roomID)){
                    Room room = rooms.get(roomID);
                    if(room.getRoomState() == ROOM_STATE.ONE_PLAYER){
                        room.join(joiner);
                        opponent = room.getOpponent(joiner);
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
                    GameMsgProtocol.PlayerEntryResult.Builder resultBuilder = GameMsgProtocol.PlayerEntryResult.newBuilder();
                    resultBuilder.setOpponentID(opponent == null? -1 : opponent.getPlayerID());
                    resultBuilder.setRoomID(roomID);

                    GameMsgProtocol.PlayerEntryResult result = resultBuilder.build();
                    channel.writeAndFlush(result);

                    //通知已经在房间里的玩家
                    if(opponent != null){
                        opponent.getChannel().writeAndFlush(result);
                    }
                }
                else{
                    GameMsgProtocol.PlayerEntryResult.Builder resultBuilder = GameMsgProtocol.PlayerEntryResult.newBuilder();
                    resultBuilder.setOpponentID(-1);
                    resultBuilder.setRoomID(-1);

                    GameMsgProtocol.PlayerEntryResult result = resultBuilder.build();
                    channel.writeAndFlush(result);
                }
            }
            else if(msg instanceof GameMsgProtocol.PlayerReadyCmd){
                Player readyPlayer = players.get(channel.id());
                readyPlayer.READY();

                Room room = rooms.get(readyPlayer.getRoomID());
                Player opponent = room.getOpponent(readyPlayer);

                if(opponent != null && opponent.isReady())
                    room.start((ScheduledFuture<?>)scheduler.scheduleAtFixedRate(room, 0, 500, TimeUnit.MICROSECONDS));

                GameMsgProtocol.PlayerReadyResult.Builder resultBuilder = GameMsgProtocol.PlayerReadyResult.newBuilder();
                resultBuilder.setPlayerID(readyPlayer.getPlayerID());
                resultBuilder.setRoomState(room.getRoomState().getValue());
                GameMsgProtocol.PlayerReadyResult result = resultBuilder.build();

                channel.writeAndFlush(result);
                if(opponent != null){
                    opponent.getChannel().writeAndFlush(result);
                }
            }
            else if(msg instanceof GameMsgProtocol.PlayerPutCmd){
                GameMsgProtocol.PlayerPutCmd cmd = (GameMsgProtocol.PlayerPutCmd)msg;
                GameMsgProtocol.PlayerPutResult.StepInfo.Builder rBuilder = GameMsgProtocol.PlayerPutResult.StepInfo.newBuilder();
                Player player = players.get(channel.id());
                Room room = rooms.get(player.getRoomID());

                rBuilder.setPlayerID(player.getPlayerID());
                rBuilder.setPosX(cmd.getPosX());
                rBuilder.setPosY(cmd.getPosY());
                room.addMsg(rBuilder);
            }
            else if(msg instanceof GameMsgProtocol.PlayerDieCmd){
                Player diePlayer = players.get(channel.id());

                Room room = rooms.get(diePlayer.getRoomID());
                Player opponent = room.getOpponent(diePlayer);

                GameMsgProtocol.PlayerDieResult.Builder resultBuilder = GameMsgProtocol.PlayerDieResult.newBuilder();
                resultBuilder.setDiePlayerID(diePlayer.getPlayerID());
                GameMsgProtocol.PlayerDieResult result = resultBuilder.build();

                channel.writeAndFlush(result);
                opponent.getChannel().writeAndFlush(result);

                diePlayer.UNREADY();
                opponent.UNREADY();
            }
            else if(msg instanceof GameMsgProtocol.PlayerLeaveCmd){
                Player leavePlayer = players.get(channel.id());
                leavePlayer.UNREADY();

                Room room = rooms.get(leavePlayer.getRoomID());
                Player opponent = room.getOpponent(leavePlayer);

                GameMsgProtocol.PlayerLeaveResult.Builder resultBuilder = GameMsgProtocol.PlayerLeaveResult.newBuilder();
                resultBuilder.setLeavePlayerID(leavePlayer.getPlayerID());
                GameMsgProtocol.PlayerLeaveResult result = resultBuilder.build();

                channel.writeAndFlush(result);
                if(opponent != null){
                    opponent.getChannel().writeAndFlush(result);
                    room.leave(leavePlayer);
                }
                else{
                    rooms.remove(leavePlayer.getRoomID());
                }
            }

        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
        }


    }
}