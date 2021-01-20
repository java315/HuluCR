package nju.java315.client.game.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public class AttackTargetComponent extends Component{
    private Entity target;
    public AttackTargetComponent(Entity target) {
        this.target = target;
    }

    public Entity getTarget(){
        return target;
    }
}
