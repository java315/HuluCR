package nju.java315.server;

import io.netty.channel.ChannelId;

public class Player{
    private final ChannelId channelID;
    private int playerID;
    private int roomID;
    private Boolean ready;

    public Player(ChannelId channelID){
        this.channelID = channelID;
        playerID = -1;
        roomID = -1;
        ready = false;
    }

    public ChannelId getChannelID(){
        return channelID;
    }

    public void setPlayerID(int ID){
        playerID = ID;
    }

    public int getPlayerID(){
        return playerID;
    }

    public void setRoomID(int ID){
        roomID = ID;
    }

    public int getRoomID(){
        return roomID;
    }

    public void READY(){
        ready = true;
    }

    public void UNREADY(){
        ready = false;
    }

    public Boolean isReady(){
        return ready;
    }
}
