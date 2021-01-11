package nju.java315.client.game.components.ai;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.entity.components.TypeComponent;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.texture.Texture;

import javafx.geometry.Point2D;
import nju.java315.client.game.Config;
import nju.java315.client.game.components.IdentityComponent;
import nju.java315.client.game.components.SensorComponent;
import nju.java315.client.game.components.attack.AttackMethod;

@Required(AStarMoveComponent.class)
@Required(StateComponent.class)
@Required(SensorComponent.class)
public class MovableMonsterAIComponent extends MonsterAICompenonet{
    private AStarMoveComponent astar;
    private IdentityComponent identity;
    Texture t;
    private boolean atLowerPart(){
        return entity.getPosition().getY() > Config.MAIN_TOWER_MID_LINE; 
    }
    
    private EntityState WALK = new EntityState(){
        @Override
        public void onEnteredFrom(EntityState entityState) {
            updateTargetTower();
            astar.moveToCell(targetCell);
        };

        @Override
        public void onUpdate(double tpf) {
            if (sensorComponent.rangeDetect()) {
                stateComponent.changeState(ATTACK);
            }
            else{
                if (astar.isAtDestination()) {
                    updateTargetTower();
                    astar.moveToCell(targetCell);
                }
                else{
                    
                }
            }
            
        }

        private AStarCell targetCell;
        private void updateTargetTower(){
            if (identity.getValue() == IdentityComponent.SELF_FLAG) {
                if (atLowerPart()) {
                    if (FXGL.getGameWorld().getProperties().getBoolean("enemy_down_tower_alive")){
                        targetCell = getCellFromPoint(new Point2D(30, 30).add(Config.ENEMY_DOWN_TOWER_POSITION));
                    }
                    else {
                        targetCell = getCellFromPoint(new Point2D(30, 30).add(Config.ENEMY_MAIN_TOWER_POSITION));
                    }
                        
                }
                else{
                    if (FXGL.getGameWorld().getProperties().getBoolean("enemy_up_tower_alive")){
                        targetCell = getCellFromPoint(new Point2D(30, 30).add(Config.ENEMY_UP_TOWER_POSITION));
                    }
                    else {
                        targetCell = getCellFromPoint(new Point2D(30, 30).add(Config.ENEMY_MAIN_TOWER_POSITION));
                    }
                }
            } 
            else {
                if (atLowerPart()) {
                    if (FXGL.getGameWorld().getProperties().getBoolean("self_down_tower_alive")){
                        targetCell = getCellFromPoint(new Point2D(30, 30).add(Config.SELF_DOWN_TOWER_POSITION));
                    }
                    else {
                        targetCell = getCellFromPoint(new Point2D(30, 30).add(Config.SELF_MAIN_TOWER_POSITION));
                    }
                        
                }
                else{
                    if (FXGL.getGameWorld().getProperties().getBoolean("self_up_tower_alive")){
                        targetCell = getCellFromPoint(new Point2D(30, 30).add(Config.SELF_UP_TOWER_POSITION));
                    }
                    else {
                        targetCell = getCellFromPoint(new Point2D(30, 30).add(Config.SELF_MAIN_TOWER_POSITION));
                    }
                }
            }
        }
    };
    private EntityState ATTACK = new EntityState(){

        public void onEnteredFrom(EntityState entityState) {
            astar.stopMovement();
        };

        @Override
        public void onUpdate(double tpf) {
            if (!sensorComponent.rangeDetect()) {
                stateComponent.changeState(WALK);

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
        super.onAdded();
        stateComponent.changeState(WALK);
    }

	@Override
    public void onUpdate(double tpf) {

    }

    private AStarCell getCell(Entity entity) {

        int x = (int) ((entity.getX() + 40 / 2) / Config.CELL_WIDTH);
        int y = (int) ((entity.getY() + 40 / 2) / Config.CELL_HEIGHT);

        return astar.getGrid().get(x, y);
    }

    private AStarCell getCellFromPoint(Point2D pos) {
        int x = (int) (pos.getX() / Config.CELL_WIDTH);
        int y = (int) (pos.getY() / Config.CELL_HEIGHT);
        return astar.getGrid().get(x, y);
    }

}
