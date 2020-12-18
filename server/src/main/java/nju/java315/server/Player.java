package nju.java315.server;

import io.netty.channel.Channel;

public class Player{
    private final Channel channel;
    private int playerID;
    private int roomID;
    private Boolean ready;

    public Player(Channel channel){
        this.channel = channel;
        playerID = -1;
        roomID = -1;
        ready = false;
    }

    public Channel getChannel(){
        return channel;
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
