package nju.java315.client.game.event;

import com.almasb.fxgl.entity.Entity;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Point2D;

public class PutEvent extends Event {

    public static final EventType<PutEvent> ANY = new EventType<PutEvent>(Event.ANY, "PUT_EVENT");

    public PutEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    private String monsterName;
    private Point2D point;

    public PutEvent(EventType<? extends Event> eventType, String monsterName, Point2D point) {
        super(eventType);

        this.monsterName = monsterName;
        this.point = point;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public Point2D getPoint() {
        return point;
    }
    
}
