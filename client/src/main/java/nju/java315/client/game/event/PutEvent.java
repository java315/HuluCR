package nju.java315.client.game.event;

import com.almasb.fxgl.entity.Entity;

import javafx.event.Event;
import javafx.event.EventType;

public class PutEvent extends Event {

    public static final EventType<PutEvent> ANY = new EventType<PutEvent>(Event.ANY, "PUT_EVENT");

    public PutEvent(EventType<? extends Event> eventType) {
        super(eventType);
        
    }

    private Entity entity;


    public PutEvent(EventType<? extends Event> eventType, Entity entity) {
        super(eventType);
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
    
}
