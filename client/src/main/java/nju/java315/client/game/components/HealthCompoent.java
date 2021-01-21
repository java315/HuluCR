package nju.java315.client.game.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.IntegerComponent;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.ProgressBar;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nju.java315.client.game.type.MonsterType;

public class HealthCompoent extends IntegerComponent{
    private static int HP_BAR_BIAS = 30;
    private int full_hp;
    private Entity hpBar;
    public HealthCompoent(int hp) {     
        super(hp);
        full_hp = hp;
    }

    @Override
    public void onAdded() {
        System.out.println("create hp bar");
        hpBar = FXGL.spawn("hpBar",entity.getX(),entity.getY()-HP_BAR_BIAS);
    }

    @Override
    public void onRemoved() {
        hpBar.removeFromWorld();
    }

    @Override
    public void onUpdate(double tpf) {
        hpBar.setPosition(entity.getPosition().subtract(new Point2D(0,HP_BAR_BIAS)));
    }

    public boolean isAlived(){
        return getValue() > 0;
    }

    public void die() {
        Texture t = (Texture) entity.getViewComponent().getChildren().get(0);
        if(entity.getComponent(IdentityComponent.class).getValue() == IdentityComponent.SELF_FLAG)
            t.set(FXGL.texture(((MonsterType) entity.getType()).getRightDeadUrl()));
        else
            t.set(FXGL.texture(((MonsterType) entity.getType()).getLeftDeadUrl()));
    }
    
    public void setHP(int value) {
        if (value < 0) value = 0;
        super.setValue(value);
        Rectangle t = (Rectangle) hpBar.getViewComponent().getChildren().get(1);
        double width = (double)super.getValue()/(double)full_hp * 60.0;
        t.setWidth(width);;
    }
}
