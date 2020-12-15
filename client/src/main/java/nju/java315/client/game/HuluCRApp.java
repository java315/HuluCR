package nju.java315.client.game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.input.Input;

import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;
import static nju.java315.client.game.Config.*;

import java.security.ProtectionDomain;
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
        onKey(KeyCode.Q, "Choose Card 1", () -> playerCompoent.chooseCard1());
    }

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
