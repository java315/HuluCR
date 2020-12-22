package nju.java315.client.game.components;
import javafx.geometry.Point2D;
import nju.java315.client.game.event.PutEvent;
import nju.java315.client.game.util.GridPoint;
import nju.java315.client.game.util.PositionHandler;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

public class PlayerComponent extends Component{
    private int currentCard;

    private int name;


    public void chooseCard(int cardID){
        System.out.println("card " + cardID + " has been chosen");
    }

    public void put(Point2D cursorPoint) {
        System.out.println("put:" + cursorPoint.toString());
        FXGL.getEventBus().fireEvent(new PutEvent(PutEvent.ANY, "LargeHulu", cursorPoint));
    }

    public void preput(Point2D cursorPoint) {
        System.out.println("preput:" + cursorPoint.toString());
    }
}
