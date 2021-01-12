package nju.java315.client.game.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.components.BooleanComponent;
import com.almasb.fxgl.texture.Texture;

import nju.java315.client.game.Config;
import nju.java315.client.game.type.MonsterType;

public class IdentityComponent extends BooleanComponent{
    public static final boolean ENEMY_FLAG = false;
    public static final boolean SELF_FLAG = true;

    public IdentityComponent(boolean identity) {
        super(identity);
    }

    public boolean isEnemy(boolean identity) {
        return super.getValue() != identity;
    }

    @Override
    public void onAdded() {
        if (getValue()==ENEMY_FLAG){
            Texture t = (Texture) entity.getViewComponent().getChildren().get(0);
            t.set(FXGL.texture(((MonsterType) entity.getType()).getLeftUrl()));
        }
    }
}
