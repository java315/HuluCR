/*
 * @Author: java315
 * @Date: 2020-12-15 11:48:37
 * @LastEditors: zb-nju
 * @LastEditTime: 2020-12-15 11:49:13
 */
package nju.java315.client.game;

public class Config {
    private Config() {}

    public static final int WIDTH = 970;
    public static final int HEIGHT= 600;

    public static final int HORIZONTAL_GRID_NUM = 18;
    public static final int VERTICAL_GRID_NUM = 18;  // 半场的纵向格子数
    public static final int GRID_HORIZONTAL_SIZE = 24;
    public static final int GRID_VERTICAL_SIZE = 16;

    public static final int RIVER_GRID_WIDTH = 1;
    public static final int UP_TOWER_GRIX_Y = 3; // 从0开始
    public static final int DOWN_TOWER_GRID_Y = 14;
    public static final int TOWER_GRIX_X = 9;

    public static final int MAIN_TOWER_GRID_Y = 4;
    public static final int MAIN_TOWER_GRID_X = 9;

    public static final double WATER_MAX_COUNT = 10.0;
    public static final int WATER_UP_TIME = 2; // 2 seconds add a water
    public static final double WATER_INIT_COUNT = 7.0;
    public static final double WATER_UP_STEP = 30.0;

    public static final int CHILD_TOWER_LIVES = 1500;
    public static final int MAIN_TOWER_LIVES = 2500;


    public static final class Asset {
        // TODO write a fxml
        public static final String FXML_MAIN_UI = "main.fxml";
    }

}
