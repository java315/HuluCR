package nju.java315.client.game;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.particle.ParticleComponent;
import com.almasb.fxgl.particle.ParticleEmitter;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.texture.Texture;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.geometry.Point2D;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nju.java315.client.game.components.DamageComponent;
import nju.java315.client.game.components.HealthCompoent;
import nju.java315.client.game.components.IdentityComponent;
import nju.java315.client.game.components.MonsterCompenonet;
import nju.java315.client.game.components.OwnerComponent;
import nju.java315.client.game.components.PlayerComponent;
import nju.java315.client.game.components.SensorComponent;
import nju.java315.client.game.components.ai.MovableMonsterAIComponent;
import nju.java315.client.game.components.ai.MoveComponent;
import nju.java315.client.game.components.ai.TargetMoveComponent;
import nju.java315.client.game.components.ai.UnmovableMonsterAIComponent;
import nju.java315.client.game.components.attack.FireBallAttack;
import nju.java315.client.game.type.AttackMethod;
import nju.java315.client.game.type.EntityType;
import nju.java315.client.game.type.MonsterType;
import nju.java315.client.game.type.TowerType;

import static com.almasb.fxgl.dsl.FXGL.*;

import java.util.Map;
import java.util.function.Supplier;

import com.almasb.fxgl.core.util.LazyValue;

public class HuluCRFactory implements EntityFactory {

    @Spawns("Block")
    public Entity newBlock(SpawnData data) {
        int width = data.get("width");
        int height = data.get("height");
        Rectangle rect = new Rectangle(width,height,Color.TRANSPARENT);

        return entityBuilder(data)
                .type(HuluCRType.BLOCK)
                .with(new CollidableComponent(true))
                .viewWithBBox(rect)
                .zIndex(-10)
                .build();
    }

    @Spawns("Background")
    public Entity newBackground(SpawnData data) {
        return entityBuilder().at(-10, -10)
                // bigger than game size to account for camera shake
                .view(texture("background/background.png", Config.WIDTH + 20, Config.HEIGHT + 20)).zIndex(-500).build();
    }

    @Spawns("Player")
    public Entity newPlayer(SpawnData data) {
        Texture texture = texture("life.png");
        texture.setPreserveRatio(true);
        texture.setFitHeight(40);

        return entityBuilder(data).type(HuluCRType.PLAYER).viewWithBBox(texture).with(new PlayerComponent())
                .build();
    }

    @Spawns("Fireball")
    public Entity newFireball(SpawnData data) {

        SimpleBooleanProperty flag = new SimpleBooleanProperty();
        flag.set(true);

        ParticleEmitter emitter = ParticleEmitters.newFireEmitter();

        emitter.startColorProperty().bind(
                Bindings.when(flag)
                        .then(Color.RED)
                        .otherwise(Color.RED)
        );

        emitter.endColorProperty().bind(
                Bindings.when(flag)
                        .then(Color.LIGHTPINK)
                        .otherwise(Color.LIGHTBLUE)
        );

        emitter.setBlendMode(BlendMode.SRC_OVER);
        emitter.setSize(5, 7);
        emitter.setEmissionRate(1);
        //Point2D direction = new Point2D(1,0);
        Point2D direction = data.get("direction");
        return FXGL.entityBuilder(data)
                    .type(AttackMethod.FIREBALL)
                    .bbox(new HitBox(BoundingShape.circle(5)))
                    .with(new CollidableComponent(true))
                    .with(new ParticleComponent(emitter))
                    .with(new ProjectileComponent(direction, 400))
                    .with(new ExpireCleanComponent(Duration.seconds(0.8)))
                    .with(new DamageComponent(100))
                    .build();

    }

    @Spawns("Card")
    public Entity newCard(SpawnData data){
        MonsterType type = data.get("type");
        return FXGL.entityBuilder(data)
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

    @Spawns("ReadyButton")
    public Entity newReadyButton(SpawnData data){
        return FXGL.entityBuilder(data)
                    .viewWithBBox(Config.Asset.READY_BUTTON_URL)
                    .type(EntityType.READY_BUTTON)
                    .build();
    }

    @Spawns("ReadyTitle")
    public Entity newReadyTitle(SpawnData data){
        return FXGL.entityBuilder(data)
                    .viewWithBBox(Config.Asset.READY_TITLE_URL)
                    .type(EntityType.READY_TITLE)
                    .build();
    }

    @Spawns("StartTitle")
    public Entity newStartTitle(SpawnData data){
        return FXGL.entityBuilder(data)
                    .viewWithBBox(Config.Asset.START_TITLE_URL)
                    .type(EntityType.START_TITLE)
                    .build();
    }


    @Spawns("LargeHulu")
    public Entity newLargeHulu(SpawnData data) {
        boolean flag = data.get("flag");
        Entity hulu = FXGL.entityBuilder()
                    .type(MonsterType.LARGE_HULU)
                    .viewWithBBox(MonsterType.LARGE_HULU.getRightUrl())
                    .with(new HealthCompoent(MonsterType.LARGE_HULU.getHp()))
                    .with(new MonsterCompenonet())
                    .with(new CollidableComponent(true))
                    .with(new FireBallAttack())
                    .with(new IdentityComponent(flag))
                    .with(new CellMoveComponent(Config.CELL_WIDTH, Config.CELL_HEIGHT, 30))
                    .with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid"))))
                    .with(new SensorComponent(150.0))
                    .with(new StateComponent())
                    .with(new MovableMonsterAIComponent())
                    .build();

        hulu.setPosition(data.getX() - hulu.getWidth()/2, data.getY() - hulu.getY() - hulu.getHeight()/2);
        return hulu;
    }


    @Spawns("FakeMonster")
    public Entity newFakeMonster(SpawnData data) {

        Entity monster = FXGL.entityBuilder()
                    .type(HuluCRType.MONSTER)
                    .viewWithBBox(((MonsterType)data.get("type")).getRightUrl())
                    .build();

        monster.setPosition(data.getX() - monster.getWidth()/2, data.getY() - monster.getY() - monster.getHeight()/2);
        return monster;
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

    @Spawns("Tower")
    public Entity newTower(SpawnData data) {
        String t;
        boolean flag = data.get("flag");
        if (flag) t = Config.Asset.GRANDFATHER_RIGHT_URL;
        else t = Config.Asset.GRANDFATHER_LEFT_URL;
        Entity tower = entityBuilder(data)
                        .type(MonsterType.GRANDFATHER)
                        .viewWithBBox(t)
                        .with(new CollidableComponent(true))
                        .with(new FireBallAttack())
                        .with(new HealthCompoent(1000))
                        .with(new IdentityComponent(flag))
                        .with(new SensorComponent(200.0))
                        .with(new StateComponent())
                        .with(new UnmovableMonsterAIComponent())
                        .scale(1.5, 1.5)
                        .build();
        
        
  
        return tower;
    }
}
