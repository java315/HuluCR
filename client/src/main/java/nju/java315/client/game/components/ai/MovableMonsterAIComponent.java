package nju.java315.client.game.components.ai;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.entity.components.TypeComponent;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

import nju.java315.client.game.Config;

@Required(AStarMoveComponent.class)
public class MovableMonsterAIComponent extends MonsterAICompenonet{
    private AStarMoveComponent astar;

    private boolean atLowerPart(){
        return entity.getPosition().getY() < 400; 
    }
    private EntityState WALK = new EntityState(){
        @Override
        public void onEnteredFrom(EntityState entityState) {
            updateTargetTower();
            astar.moveToCell(target_x, target_y);
        };

        @Override
        public void onUpdate(double tpf) {
            if (astar.isAtDestination()) {
                updateTargetTower();
                astar.moveToCell(target_x, target_y);
            }
        }

        private int target_x;
        private int target_y;
        private void updateTargetTower(){
            if (atLowerPart()) {
                if (FXGL.getb("ENEMY_DOWN_TOWER_ALIVE")){
                    target_x = 40;
                    target_y = 40;
                }
                else {
                    target_x = 60;
                    target_y = 30;
                }
                    
            }
            else{
                if (FXGL.getb("ENEMY_UP_TOWER_ALIVE")){
                    target_x = 20;
                    target_y = 40;
                }
                else {
                    target_x = 60;
                    target_y = 30;
                }
            }
        }
    };
    private EntityState ATTACK = new EntityState(){

    };

	@Override
    public void onUpdate(double tpf) {
        Entity target = sensorComponent.getTarget();
        
    }

    private AStarCell getCell(Entity entity) {

        int x = (int) ((entity.getX() + 40 / 2) / Config.CELL_WIDTH);
        int y = (int) ((entity.getY() + 40 / 2) / Config.CELL_HEIGHT);

        return astar.getGrid().get(x, y);
    }


}
