package nju.java315.client.game.event;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Point2D;

public class PutEvent extends Event {

    public static final EventType<PutEvent> ANY = new EventType<PutEvent>(Event.ANY, "PUT_EVENT");
    public static final EventType<PutEvent> SELF_PUT = new EventType<>(ANY, "SELF_PUT");
    public static final EventType<PutEvent> ENEMY_PUT = new EventType<>(ANY, "ENEMY_PUT");
    public PutEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    private String monsterName;
    private float x;
    private float y;

    public PutEvent(EventType<? extends Event> eventType, String monsterName, float x, float y) {
        super(eventType);

        this.monsterName = monsterName;
        this.x = x;
        this.y = y;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }
}
