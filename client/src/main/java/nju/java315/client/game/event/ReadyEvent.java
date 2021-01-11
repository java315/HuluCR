package nju.java315.client.game.event;

import javafx.event.Event;
import javafx.event.EventType;

public class ReadyEvent extends Event{

    public static final EventType<ReadyEvent> ANY = new EventType<ReadyEvent>(Event.ANY, "READY_EVENT");
    public static final EventType<ReadyEvent> SELF_READY = new EventType<>(ANY, "SELF_READY");
    public static final EventType<ReadyEvent> ENEMY_READY = new EventType<>(ANY, "ENEMY_READY");
    public ReadyEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
