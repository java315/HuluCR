package nju.java315.client.game.components.attack;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import javafx.geometry.Point2D;

public class FireBallAttack extends AttackMethod {

    public FireBallAttack(int damage, int range, float attack_speed, boolean identity) {
        super(damage, range, attack_speed, identity);
    }

    public FireBallAttack(boolean identity) {
        super(200, 400, 0.8f, identity);
    }

    @Override
    protected Entity spawnAttack(Point2D position, Point2D direction) {
        return FXGL.spawn("Fireball", new SpawnData(position).put("direction", direction).put("identity", identity));
    }

}
