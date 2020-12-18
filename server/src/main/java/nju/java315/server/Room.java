package nju.java315.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;



public class Room{
    static private final Logger LOGGER = LoggerFactory.getLogger(Room.class);
    Player[] players;

    public enum ROOM_STATE{
        ONE_PLAYER(0),TWO_PLAYER(1),PLAYING(2);
        public static final int ONT_PLAYER_VALUE = 0;
        public static final int TWO_PLAYER_VALUE = 1;
        public static final int PLAYING_VALUE = 2;

        private final int value;
        private ROOM_STATE(int value){
            this.value = value;
        }

        public int getValue(){
            return this.value;
        }
    };
    private ROOM_STATE roomState;

    public Room(Player player){
        players = new Player[2];
        players[0] = player;
        roomState = ROOM_STATE.ONE_PLAYER;
    }

    public ROOM_STATE getRoomState(){
        return roomState;
    }

    public Player getPlayer(){
        if(players[0] != null)
            return players[0];
        else
            return players[1];
    }

    public void join(Player player){
        if(players[0] == null)
            players[0] = player;
        else
            players[1] = player;
        roomState = ROOM_STATE.TWO_PLAYER;
    }

    public void leave(Player player){
        if(player == players[0])
            players[0] = null;
        else
            players[1] = null;
        roomState = ROOM_STATE.ONE_PLAYER;
    }

    public void start(){
        roomState = ROOM_STATE.PLAYING;
    }

    public void stop(){
        roomState = ROOM_STATE.TWO_PLAYER;
    }
}