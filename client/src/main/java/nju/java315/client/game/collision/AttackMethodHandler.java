package nju.java315.client.game.collision;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import static com.almasb.fxgl.dsl.FXGL.*;

import javafx.geometry.Point2D;
import javafx.util.Duration;
import nju.java315.client.game.HuluCRType;
import nju.java315.client.game.components.AttackIdentityComponent;
import nju.java315.client.game.components.DamageComponent;
import nju.java315.client.game.components.HealthCompoent;
import nju.java315.client.game.components.IdentityComponent;
import nju.java315.client.game.components.attack.AttackMethod;

public class AttackMethodHandler extends CollisionHandler {

    public AttackMethodHandler(Object a, Object b) {
        super(a, b);
    }

    @Override
    protected void onCollisionBegin(Entity attack_method, Entity monster) {
        boolean attackMethodIdentity = attack_method.getComponent(AttackIdentityComponent.class).getValue();
        if(monster.getComponent(IdentityComponent.class).isEnemy(attackMethodIdentity)){
            attack_method.getComponent(CollidableComponent.class).setValue(false);
            attack_method.getComponent(ProjectileComponent.class).setSpeed(0);

            HealthCompoent hp = monster.getComponent(HealthCompoent.class);
            DamageComponent damage = attack_method.getComponent(DamageComponent.class);
            hp.setHP(hp.getValue() - damage.getValue());

            animationBuilder()
                .duration(Duration.seconds(0.5))
                .onFinished(attack_method::removeFromWorld)
                .interpolator(Interpolators.BACK.EASE_IN())
                .scale(attack_method)
                .from(new Point2D(attack_method.getScaleX(), attack_method.getScaleY()))
                .to(new Point2D(0, 0))
                .buildAndPlay();

            System.out.println(hp.getValue());

            if (hp.getValue() <= 0){
                monster.getComponent(HealthCompoent.class).die();
                runOnce(monster::removeFromWorld, Duration.seconds(2));
            }
            else{
                // present effect
            }
        }
    }
}
