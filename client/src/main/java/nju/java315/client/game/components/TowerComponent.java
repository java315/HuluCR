package nju.java315.client.game.components;

import com.almasb.fxgl.animation.AnimationBuilder;
import com.almasb.fxgl.entity.component.Component;

public class TowerComponent extends Component{
    private int lives;

    public TowerComponent(int lives){
        this.lives = lives;
    }
    public void onHit(int damage){
        lives -= damage;

        // animation

        //

        if(lives == 0) {

        }
        else{
            
        }
    }
}
