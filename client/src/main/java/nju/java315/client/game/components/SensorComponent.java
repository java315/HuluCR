package nju.java315.client.game.components;

import java.util.Comparator;

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.ComponentListener;
import com.almasb.fxgl.entity.component.Required;

@Required(IdentityComponent.class)
public class SensorComponent extends Component{
    private double sensorRange = 100;
    public SensorComponent(double range){
        this.sensorRange = range;
    }
    private Entity target = null;
    private IdentityComponent identity;

    @Override
    public void onUpdate(double tpf) {
        if (target == null) {
            updateClosestTarget();
        }
        else {
            if (target.distance(entity) > sensorRange || !target.getComponent(HealthCompoent.class).isAlived()) {
                target = null;
                //System.out.println("lost target");
            }
        }
    }

    private void updateClosestTarget() {
        FXGL.getGameWorld()
            .getEntitiesByComponent(IdentityComponent.class)
            .stream()
            .filter(e -> e.getComponent(IdentityComponent.class).isEnemy(identity.getValue()))
            .min(Comparator.comparingDouble(e -> e.distance(entity)))
            .ifPresent(e -> {
                if (e.distance(entity) >= sensorRange || !e.getComponent(HealthCompoent.class).isAlived()) return;
                target = e;
                target.addComponentListener(new ComponentListener(){

                        @Override
                        public void onAdded(Component component) {}

                        @Override
                        public void onRemoved(Component component) {
                            // to test
                            target = null;
                        }
                });
            });
    }

    public Entity getTarget() {
        return target;
    }

    public boolean rangeDetect(){
        return target != null;
    }
}
