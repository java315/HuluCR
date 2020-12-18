package nju.java315.client.game.event;

import javafx.event.Event;
import javafx.event.EventType;

public class GameEvent extends Event {

    public GameEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
    
}
