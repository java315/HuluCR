package nju.java315.client.game.components.attack;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import nju.java315.client.game.components.HealthCompoent;


public abstract class AttackMethod extends Component{
    private String name;
    protected int damage;
    protected int range;
    protected float attack_speed; // how many times in 1 sec
    protected boolean identity;
    private Duration ATTACK_DELAY;
    public AttackMethod(String name, int damage, int range, float attack_speed, boolean identity){
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.attack_speed = attack_speed;
        this.ATTACK_DELAY = Duration.seconds(1.0f/attack_speed);
        this.identity = identity;
    }

    public AttackMethod(int damage,int range, float attack_speed, boolean identity){
        this("AttackMethod", damage, range, attack_speed, identity);
    }

    public String getName() {
        return name;
    }

    public boolean getIdentity(){
        return identity;
    }

    private LocalTimer attackTimer = FXGL.newLocalTimer();
    public void attack(Entity target){
        if (entity.getComponent(HealthCompoent.class).isAlived() && attackTimer.elapsed(ATTACK_DELAY)) {
            spawnAttack(entity.getCenter(), getDirection(target));
            attackTimer.capture();
        }
    }
    protected abstract Entity spawnAttack(Point2D position, Point2D direction);

    public Point2D getDirection(Entity target){
        return target.getCenter().subtract(entity.getCenter());
    }

}
