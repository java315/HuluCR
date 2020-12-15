package nju.java315.client.game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import nju.java315.client.game.components.PlayerCompoent;

import static com.almasb.fxgl.dsl.FXGL.*;
import static nju.java315.client.game.Config.*;

import java.util.Map;

public class HuluCRApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(WIDTH);
        settings.setHeight(HEIGHT);
        settings.setTitle("葫芦娃战争");
    }

    @Override
    protected void initInput() {
        Input input = getInput();
        onKeyDown(KeyCode.Q, "Choose Card 0", () -> playerCompoent.chooseCard(0));
        onKeyDown(KeyCode.W, "Choose Card 1", () -> playerCompoent.chooseCard(1));
        onKeyDown(KeyCode.E, "Choose Card 2", () -> playerCompoent.chooseCard(2));
        onKeyDown(KeyCode.R, "Choose Card 3", () -> playerCompoent.chooseCard(3));
    
        UserAction putCard = new UserAction("put"){
            @Override
            protected void onAction() {
                Point2D cursorPoint = getInput().getMousePositionUI();
                playerCompoent.preput(cursorPoint);
                
            }
            protected void onActionEnd() {
                Point2D cursorPoint = getInput().getMousePositionUI();
                playerCompoent.put(cursorPoint);
            }
        };
        input.addAction(putCard, MouseButton.PRIMARY);
    }

	private PlayerCompoent playerCompoent;


    // 建立映射表
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        // TODO Auto-generated method stub
        super.initGameVars(vars);
    }

    // 初始化游戏元素
    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new HuluCRFactory());
    }

    // 初始化物理环境
    @Override
    protected void initPhysics() {

    }

    // 初始化ui
    @Override
    protected void initUI() {

    }
}
