package nju.java315.client.game.components.attack;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import javafx.geometry.Point2D;

public class WaterBallAttack extends AttackMethod {

    public WaterBallAttack(int damage, int range, float attack_speed) {
        super(damage, range, attack_speed);
    }

    public WaterBallAttack() {
        super(200, 400, 0.8f);
    }

    @Override
    protected Entity spawnAttack(Point2D position, Entity target) {
        return FXGL.spawn("Waterball", new SpawnData(position).put("direction", getDirection(target)).put("target", target));
    }

}
