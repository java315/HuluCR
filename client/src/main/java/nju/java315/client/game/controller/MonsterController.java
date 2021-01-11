package nju.java315.client.game.controller;

import javafx.geometry.Point2D;
import nju.java315.client.game.components.MonsterControllerComponent;
import nju.java315.client.game.type.MonsterType;
public abstract class MonsterController {
    private MonsterControllerComponent monsterControllerComponent;
    private String name = "";

    private Point2D initLocation;

    public MonsterController(String name) {
        this.name = name;
        this.initLocation = new Point2D(400, 400);
    }
    
    public String getName() {
        return name;
    }

    public void setMoveDirection(int moveDirection) {
        
    }

    protected void attack() {

    }

    public void setMonsterControllerComponent(MonsterControllerComponent monsterControllerComponent) {
        this.monsterControllerComponent = monsterControllerComponent;
    }

    public void setInitLocation(Point2D initLocation) {
        this.initLocation = initLocation;
    }

    public Point2D getInitLocation() {
        return initLocation;
    }

    public abstract void onDestroy();

    public abstract MonsterType getEntityType();

    
}
