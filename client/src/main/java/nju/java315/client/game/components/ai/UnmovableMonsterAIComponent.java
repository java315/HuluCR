package nju.java315.client.game.components.ai;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nju.java315.client.game.components.SensorComponent;
import nju.java315.client.game.components.attack.AttackMethod;

@Required(StateComponent.class)
@Required(SensorComponent.class)
public class UnmovableMonsterAIComponent extends MonsterAICompenonet{
    
    private EntityState GUARD = new EntityState() {
        
        @Override
        public void onUpdate(double tpf) {
            if(sensorComponent.rangeDetect()) {
                stateComponent.changeState(ATTACK);
            }
        }
    };

    private EntityState ATTACK = new EntityState() {
        
        @Override
        public void onUpdate(double tpf) {
            if(!sensorComponent.rangeDetect()){
                stateComponent.changeState(GUARD);
            }
            else {
                attack(sensorComponent.getTarget());
            }
        }

        private void attack(Entity target) {
            attackMethod.attack(target);
        }
    };


    @Override
    public void onAdded() {
        stateComponent.changeState(GUARD);
        super.onAdded();
    }

    @Override
    public void onUpdate(double tpf) {
        
    }

    @Override
    public void onRemoved() {
        FXGL.getGameWorld().getProperties().setValue(name + "_alive", false);
    }
}
