package nju.java315.client.game.event;

import javafx.event.Event;
import javafx.event.EventType;

public class EntryResultEvent extends Event{
    public static final EventType<EntryResultEvent> ANY = new EventType<EntryResultEvent>(Event.ANY, "ENTRY_RESULT");
    public static final EventType<EntryResultEvent> SELF_ENTRY_RESULT = new EventType<>(ANY, "SELF_ENTRY_RESULT");
    public static final EventType<EntryResultEvent> ENEMY_ENTRY_RESULT = new EventType<>(ANY, "ENEMY_ENTRY_RESULT");
    private int roomID;
    private int enemyID;
    private boolean enemyIsReady;
    public EntryResultEvent(EventType<? extends Event> eventType, int roomID, int enemyID, boolean enemyIsReady){
        super(eventType);
        this.roomID = roomID;
        this.enemyID = enemyID;
        this.enemyIsReady = enemyIsReady;
    }

    public EntryResultEvent(EventType<? extends Event> eventType, int enemyID){
        super(eventType);
        this.enemyID = enemyID;
        this.roomID = -1;
        this.enemyIsReady = false;
    }

    public int getRoomID(){
        return roomID;
    }

    public int getEnemyID(){
        return enemyID;
    }

    public boolean getEnemyIsReady(){
        return enemyIsReady;
    }
}
