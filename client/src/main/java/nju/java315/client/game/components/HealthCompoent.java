package nju.java315.client.game.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.components.IntegerComponent;
import com.almasb.fxgl.texture.Texture;

import nju.java315.client.game.type.MonsterType;

public class HealthCompoent extends IntegerComponent{
    public HealthCompoent(int hp) {
        super(hp);
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
}
