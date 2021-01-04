package nju.java315.client.game.components.ai;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

import nju.java315.client.game.Config;
import nju.java315.client.game.components.SensorComponent;

@Required(StateComponent.class)
@Required(SensorComponent.class)
public class MonsterAICompenonet extends Component {

    protected StateComponent stateComponent;
    protected SensorComponent sensorComponent;


    public MonsterAICompenonet(){
        super();
    }
}
