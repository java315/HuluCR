package nju.java315.client.game.components.attack;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import nju.java315.client.game.components.DamageComponent;


public abstract class AttackMethod extends Component{
    private String name;
    protected int damage;
    protected int range;
    protected float attack_speed; // how many times in 1 sec
    private Duration ATTACK_DELAY;
    public AttackMethod(String name, int damage, int range, float attack_speed){
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.attack_speed = attack_speed; 
        this.ATTACK_DELAY = Duration.seconds(1.0f/attack_speed);
    }

    public AttackMethod(int damage,int range, float attack_speed){
        this("AttackMethod", damage, range, attack_speed);
    }

    public String getName() {
        return name;
    }
    
    private LocalTimer attackTimer = FXGL.newLocalTimer();
    public void attack(Entity target){
        if (attackTimer.elapsed(ATTACK_DELAY)) {
            spawnAttack(entity.getCenter(), getDirection(target));
            attackTimer.capture();
        }
    }
    protected abstract Entity spawnAttack(Point2D position, Point2D direction);

    public Point2D getDirection(Entity target){
        return target.getCenter().subtract(entity.getCenter());
    }

}
