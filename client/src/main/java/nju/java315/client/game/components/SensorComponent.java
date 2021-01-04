package nju.java315.client.game.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public class SensorComponent extends Component{
    int sensorRange = 100;
    public SensorComponent(int range){
        this.sensorRange = range;
    }
    Entity target = null;
    @Override
    public void onUpdate(double tpf) {
        if (target == null) {

        }
        FXGL.getGameWorld()
            .getEntitiesByComponent(MonsterCompenonet.class).stream()
            .forEach(m -> {

            });
    }

    public Entity getTarget() {
        return target;
    }

    public boolean rangeDetect(){
        return false;
    }
}
