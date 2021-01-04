package nju.java315.client.game.components.ai;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;


@Required(AStarMoveComponent.class)
public class MoveComponent extends Component {
    private AStarMoveComponent astar;

    @Override
    public void onUpdate(double tpf) {
        
        if (astar.isAtDestination()) {
            astar.getGrid()
                .getRandomCell(c -> c.getState().isWalkable())
                .ifPresent(astar::moveToCell);
        }
        else{

        }
    }
}
