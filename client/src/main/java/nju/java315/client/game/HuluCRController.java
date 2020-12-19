package nju.java315.client.game;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import com.almasb.fxgl.ui.ProgressBar;
import com.almasb.fxgl.ui.UIController;

import static com.almasb.fxgl.dsl.FXGL.*;

import com.almasb.fxgl.app.scene.GameScene;

public class HuluCRController implements UIController {

    @FXML
    private ProgressBar waterMeter;

    private GameScene gameScene;

    public HuluCRController(GameScene gameScene){
        this.gameScene = gameScene;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

        waterMeter.setLabelVisible(true);
        waterMeter.setMinValue(0);
        waterMeter.setMaxValue(Config.WATER_MAX_COUNT);
        waterMeter.setCurrentValue(Config.WATER_INIT_COUNT);
        waterMeter.setBackgroundFill(new Color(0.984375, 0.61328125, 0.6015625, 1.0));
        waterMeter.setFill(new Color(0.9921875, 0.26171875, 0.39453125, 1.0));
        waterMeter.setWidth(110);
        waterMeter.setHeight(15);
    }

    public ProgressBar getWaterMeter(){
        return waterMeter;
    }
}
