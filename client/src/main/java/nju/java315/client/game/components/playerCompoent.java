package nju.java315.client.game.components;
import javafx.geometry.Point2D;
import nju.java315.client.game.util.GridPoint;
import nju.java315.client.game.util.PositionHandler;

import com.almasb.fxgl.entity.component.Component;

public class PlayerCompoent extends Component{
    private int currentCard;

    public void chooseCard(int cardID){
        System.out.println("card " + cardID + " has been chosen");
    }

    public void put(Point2D cursorPoint) {
        GridPoint gp = PositionHandler.realPointToGridPoint(cursorPoint);
    }

    public void preput(Point2D cursorPoint) {

    }
}
