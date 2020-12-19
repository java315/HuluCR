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
import nju.java315.client.game.type.AttackMethod;
import nju.java315.client.game.type.TowerType;

import static com.almasb.fxgl.dsl.FXGL.*;

public class HuluCRFactory implements EntityFactory {
    @Spawns("Background")
    public Entity newBackground(SpawnData data) {
        return entityBuilder()
                .at(-10, -10)
                // bigger than game size to account for camera shake
                .view(texture("background/background.png", Config.WIDTH + 20, Config.HEIGHT + 20))
                .zIndex(-500)
                .build();
    }

    @Spawns("Fireball")
    public Entity newFireball(SpawnData data) {
        return FXGL.entityBuilder()
                    .type(AttackMethod.FIREBALL)
                    .viewWithBBox(new Rectangle(10, 10, Color.RED))
                    .with(new CollidableComponent(true))
                    //.with(new ProjectileComponent(direction, speed)) // TODO
                    .build();
    }

    @Spawns("Grandfather")
    public Entity newGrandfather(SpawnData data) {
        return FXGL.entityBuilder()
                    .type(TowerType.GRANDFATHER)
                    .build();
    }
}
