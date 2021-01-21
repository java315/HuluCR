package nju.java315.client.game.components.attack;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import javafx.geometry.Point2D;

public class VenomBallAttack extends AttackMethod {

    public VenomBallAttack(int damage, int range, float attack_speed) {
        super(damage, range, attack_speed);
    }

    public VenomBallAttack() {
        super(200, 400, 0.8f);
    }

    @Override
    protected Entity spawnAttack(Point2D position, Entity target) {
        return FXGL.spawn("Venomball", new SpawnData(position).put("direction", getDirection(target)).put("target", target));
    }

}
