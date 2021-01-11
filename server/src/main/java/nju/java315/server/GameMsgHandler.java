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

public class GameMsgHandler extends SimpleChannelInboundHandler<Object> {

    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgHandler.class);
    //static private final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // channel.id 到 Player 的映射
    static private HashMap<ChannelId, Player> players = new HashMap<>();

    // 房间号到房间的映射
    static private HashMap<Integer, Room> rooms = new HashMap<>();

    static private int generator = 0;

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("已连接");
        if(ctx == null)
            return;
        Channel channel = ctx.channel();
        //channelGroup.add(channel);

        players.put(channel.id(), new Player(channel, generator));
        LOGGER.info("分配给刚刚连接的用户的id是" + generator);

        GameMsgProtocol.PlayerActiveResult.Builder resultBuilder = GameMsgProtocol.PlayerActiveResult.newBuilder();
        resultBuilder.setPlayerID(generator++);
        GameMsgProtocol.PlayerActiveResult result = resultBuilder.build();
        channel.writeAndFlush(result);

        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception{
        LOGGER.info("收到:"+msg.toString());
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

                Player entryPlayer = players.get(channel.id());

                int roomID = cmd.getRoomID();
                int playerID = entryPlayer.getPlayerID();

                LOGGER.info(playerID + " enter room " + roomID);

                Player joiner = players.get(channel.id());
                Player enemy = null;
                Boolean success = false;

                // joiner.setPlayerID(playerID);

                if(rooms.containsKey(roomID)){
                    Room room = rooms.get(roomID);
                    if(room.getRoomState() == ROOM_STATE.ONE_PLAYER){
                        room.join(joiner);
                        enemy = room.getEnemy(joiner);
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
                    resultBuilder.setEntryPlayerID(playerID);
                    resultBuilder.setEnemyID(enemy == null? -1 : enemy.getPlayerID());
                    resultBuilder.setEnemyIsReady(enemy == null? false : enemy.isReady());
                    resultBuilder.setRoomID(roomID);

                    GameMsgProtocol.PlayerEntryResult result = resultBuilder.build();
                    channel.writeAndFlush(result);

                    //通知已经在房间里的玩家
                    if(enemy != null){
                        enemy.getChannel().writeAndFlush(result);
                    }
                }
                else{
                    GameMsgProtocol.PlayerEntryResult.Builder resultBuilder = GameMsgProtocol.PlayerEntryResult.newBuilder();
                    resultBuilder.setEnemyID(-1);
                    resultBuilder.setRoomID(-1);

                    GameMsgProtocol.PlayerEntryResult result = resultBuilder.build();
                    channel.writeAndFlush(result);
                }
            }
            else if(msg instanceof GameMsgProtocol.PlayerReadyCmd){
                Player readyPlayer = players.get(channel.id());
                readyPlayer.READY();

                LOGGER.info("Player " + readyPlayer.getPlayerID() + " ready!");

                Room room = rooms.get(readyPlayer.getRoomID());
                Player enemy = room.getEnemy(readyPlayer);

                GameMsgProtocol.PlayerReadyResult.Builder resultBuilder = GameMsgProtocol.PlayerReadyResult.newBuilder();
                resultBuilder.setPlayerID(readyPlayer.getPlayerID());
                resultBuilder.setRoomState(room.getRoomState().getValue());
                GameMsgProtocol.PlayerReadyResult result = resultBuilder.build();

                channel.writeAndFlush(result);
                if(enemy != null){
                    enemy.getChannel().writeAndFlush(result);
                    if(enemy.isReady()){
                        room.start(scheduler.scheduleAtFixedRate(room, 0, 500, TimeUnit.MILLISECONDS));
                        LOGGER.info("Game start");
                    }
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
                Player enemy = room.getEnemy(diePlayer);

                GameMsgProtocol.PlayerDieResult.Builder resultBuilder = GameMsgProtocol.PlayerDieResult.newBuilder();
                resultBuilder.setDiePlayerID(diePlayer.getPlayerID());
                GameMsgProtocol.PlayerDieResult result = resultBuilder.build();

                channel.writeAndFlush(result);
                enemy.getChannel().writeAndFlush(result);

                diePlayer.UNREADY();
                enemy.UNREADY();
            }
            else if(msg instanceof GameMsgProtocol.PlayerLeaveCmd){
                Player leavePlayer = players.get(channel.id());
                leavePlayer.UNREADY();

                Room room = rooms.get(leavePlayer.getRoomID());
                Player enemy = room.getEnemy(leavePlayer);

                GameMsgProtocol.PlayerLeaveResult.Builder resultBuilder = GameMsgProtocol.PlayerLeaveResult.newBuilder();
                resultBuilder.setLeavePlayerID(leavePlayer.getPlayerID());
                GameMsgProtocol.PlayerLeaveResult result = resultBuilder.build();

                channel.writeAndFlush(result);
                if(enemy != null){
                    enemy.getChannel().writeAndFlush(result);
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