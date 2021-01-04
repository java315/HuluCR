package nju.java315.client.game.components.ai;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;


@Required(AStarMoveComponent.class)
public class TargetMoveComponent extends Component{
    private AStarMoveComponent astar;
    private int target_cell_x;
    private int target_cell_y;
    public TargetMoveComponent(int target_cell_x,int target_cell_y){
        this.target_cell_x = target_cell_x;
        this.target_cell_y = target_cell_y;
    }
    @Override
    public void onUpdate(double tpf) {
        
        if (astar.isAtDestination()) {
            astar.moveToCell(target_cell_x, target_cell_y);
        }
        else{

        }
    } 


}
