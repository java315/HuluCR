package nju.java315.client.game.util;

import javafx.geometry.Point2D;

public class PositionHandler {
    public static Point2D gridPointToRealPoint(GridPoint gp){

        return new Point2D(0.0,0.0);
    }

    public static GridPoint realPointToGridPoint(Point2D rp){

        return new GridPoint(0,0);
    }
}
