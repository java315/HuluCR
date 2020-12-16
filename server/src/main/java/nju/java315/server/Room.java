package nju.java315.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;



public class Room{
    static private final Logger LOGGER = LoggerFactory.getLogger(Room.class);

    final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

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

    public Room(){
        roomState = ROOM_STATE.ONE_PLAYER;
    }

    public ROOM_STATE getRoomState(){
        return roomState;
    }

    public void join(Channel channel){
        channelGroup.add(channel);
        roomState = ROOM_STATE.TWO_PLAYER;
    }

    public void leave(Channel channel){
        channelGroup.remove(channel);
        roomState = ROOM_STATE.ONE_PLAYER;
    }

    public void start(){
        roomState = ROOM_STATE.PLAYING;
    }

    public void stop(){
        roomState = ROOM_STATE.TWO_PLAYER;
    }
}