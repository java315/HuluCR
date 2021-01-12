package nju.java315.client.game.components.attack;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import javafx.geometry.Point2D;

public class FireBallAttack extends AttackMethod {

    public FireBallAttack(int damage, int range, float attack_speed) {
        super(damage, range, attack_speed);
    }

    public FireBallAttack() {
        super(200, 400, 0.8f);
    }

    @Override
    protected Entity spawnAttack(Point2D position, Point2D direction) {
        return FXGL.spawn("Fireball", new SpawnData(position).put("direction", direction));
    }
    
}
