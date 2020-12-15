package nju.java315.client.game;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HuluCRFactory implements EntityFactory {
    @Spawns("Fireball")
    public Entity newFireball(SpawnData data) {
        return FXGL.entityBuilder()
                    .type(HuluCRType.FIREBALL)
                    .viewWithBBox(new Rectangle(10, 10, Color.RED))
                    .with(new CollidableComponent(true))
                    //.with(new ProjectileComponent(direction, speed)) // TODO
                    .build();
    }

    @Spawns("Grandfather")
    public Entity newGrandfather(SpawnData data) {
        return FXGL.entityBuilder()
                    .type(HuluCRType.GRANDFATHER)
                    .build();
    }
}
