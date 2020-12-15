package nju.java315.client.game;

public class Config {
    private Config() {}

    public static final int WIDTH = 440;
    public static final int HEIGHT= 1000;

    public static final int HORIZONTAL_GRID_NUM = 18;
    public static final int VERTICAL_GRID_NUM = 18;  // 半场的纵向格子数
    public static final int GRID_HORIZONTAL_SIZE = 24;
    public static final int GRID_VERTICAL_SIZE = 16;

    public static final int RIVER_GRID_WIDTH = 1;
    public static final int LEFT_TOWER_GRIX_X = 3; // 从0开始
    public static final int RIGHT_TOWER_GRID_X = 14;
    public static final int TOWER_GRIX_Y = 9;

    public static final int MID_TOWER_GRID_Y = 4;
    public static final int MID_TOWER_GRID_X = 9; 

    public static final int WATER_MAX_COUNT = 10;
    public static final int WATER_UP_TIME = 2; // 2 seconds add a water

    public static final class Asset {
        
    }

}
