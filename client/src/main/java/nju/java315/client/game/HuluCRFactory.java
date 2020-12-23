package nju.java315.client.game;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.texture.Texture;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nju.java315.client.game.components.HealthCompoent;
import nju.java315.client.game.components.MonsterCompenonet;
import nju.java315.client.game.components.OwnerComponent;
import nju.java315.client.game.components.PlayerComponent;
import nju.java315.client.game.type.AttackMethod;
import nju.java315.client.game.type.MonsterType;
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

    @Spawns("Player")
    public Entity newPlayer(SpawnData data) {
        Texture texture = texture("life.png");
        texture.setPreserveRatio(true);
        texture.setFitHeight(40);

        return entityBuilder().from(data)
                .type(HuluCRType.PLAYER)
                .viewWithBBox(texture)
                .with(new PlayerComponent())
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

    @Spawns("Card")
    public Entity newCard(SpawnData data){
        MonsterType type = data.get("type");
        return FXGL.entityBuilder()
                .from(data)
                .type(type)
                .viewWithBBox(type.getCardUrl())
                .build();
    }

    @Spawns("Grandfather")
    public Entity newGrandfather(SpawnData data) {
        return FXGL.entityBuilder()
                    .type(TowerType.GRANDFATHER)
                    .build();
    }

    @Spawns("LargeHulu")
    public Entity newLargeHulu(SpawnData data) {
        return FXGL.entityBuilder().from(data)
                    .type(MonsterType.LARGE_HULU)
                    .viewWithBBox(MonsterType.LARGE_HULU.getEntityUrl())
                    .with(new HealthCompoent(data.get("hp")))
                    .with(new MonsterCompenonet())
                    .build();
    }

    @Spawns("Arrow")
    public Entity newArrow(SpawnData data) {
        Entity owner = data.get("owner");

        return entityBuilder()
                .type(AttackMethod.ARROW)
                .at(owner.getCenter().add(-3,18))
                .viewWithBBox("arrow.png")
                .collidable()
                .with(new OwnerComponent(owner.getType()))
                .with(new ProjectileComponent(new Point2D(0,1), 600))
                .with(new OffscreenCleanComponent())
                .with("dead", false)
                .build();
    }
}
