package nju.java315.client.game.collision;


import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import nju.java315.client.game.HuluCRType;
import nju.java315.client.game.components.DamageComponent;
import nju.java315.client.game.components.HealthCompoent;
import nju.java315.client.game.components.MonsterCompenonet;
import nju.java315.client.game.components.OwnerComponent;

public class AttackMethodHandler extends CollisionHandler {

    public AttackMethodHandler() {
        super(HuluCRType.ATTACK, HuluCRType.MONSTER);
    }

    @Override
    protected void onCollision(Entity attack_method, Entity monster) {
        Object owner = attack_method.getComponent(OwnerComponent.class).getValue();

        if (owner == HuluCRType.MONSTER) {

        }

        attack_method.removeFromWorld();

        HealthCompoent hp = monster.getComponent(HealthCompoent.class);
        DamageComponent damage = attack_method.getComponent(DamageComponent.class);
        hp.setValue(hp.getValue() - damage.getValue());

        if (hp.getValue() <= 0){
            if(monster.hasComponent(MonsterCompenonet.class)){
                monster.getComponent(MonsterCompenonet.class).die();
            }
        }
        else{
            // present effect
        }
    }
}
