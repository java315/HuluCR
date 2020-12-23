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
    public static final int WATER_UP_TIME = 3; // 2 seconds add a water
    public static final double WATER_INIT_COUNT = 7.0;
    public static final double WATER_UP_STEP = 30.0;

    public static final int CHILD_TOWER_LIVES = 1500;
    public static final int MAIN_TOWER_LIVES = 2500;

    public static final int LARGE_HULU_COST = 2;
    public static final int EYE_HULU_COST = 2;
    public static final int IRON_HULU_COST = 2;
    public static final int FIRE_HULU_COST = 2;
    public static final int WATER_HULU_COST = 2;
    public static final int STEALTH_HULU_COST = 2;
    public static final int HULU_HULU_COST = 2;
    public static final int NTR_COST = 2;
    public static final int SNAKE_COST = 2;
    public static final int CENTIPEDE_COST = 2;
    public static final int SPIDER_COST = 2;
    public static final int TOAD_COST = 2;
    public static final int WASP_COST = 2;
    public static final int WILD_BOAR_COST = 2;

    public static final class Asset {
        // TODO write a fxml
        public static final String FXML_MAIN_UI = "main.fxml";

        public static final String LARGE_HULU_CARD_URL = "card/largeHulu.png";
        public static final String LARGE_HULU_ENTITY_URL = "";

        public static final String EYE_HULU_CARD_URL = "card/eyeHulu.png";
        public static final String EYE_HULU_ENTITY_URL = "";

        public static final String IRON_HULU_CARD_URL = "card/ironHulu.png";
        public static final String IRON_HULU_ENTITY_URL = "";

        public static final String FIRE_HULU_CARD_URL = "card/fireHulu.png";
        public static final String FIRE_HULU_ENTITY_URL = "";

        public static final String WATER_HULU_CARD_URL = "card/waterHulu.png";
        public static final String WATER_HULU_ENTITY_URL = "";

        public static final String STEALTH_HULU_CARD_URL = "card/stealthHulu.png";
        public static final String STEALTH_HULU_ENTITY_URL = "";

        public static final String HULU_HULU_CARD_URL = "card/huluHulu.png";
        public static final String HULU_HULU_ENTITY_URL = "";

        public static final String NTR_CARD_URL = "card/NTR.png";
        public static final String NTR_ENTITY_URL = "";

        public static final String SNAKE_CARD_URL = "card/snake.png";
        public static final String SNAKE_ENTITY_URL = "";

        public static final String CENTIPEDE_CARD_URL = "card/centipede.png";
        public static final String CENTIPEDE_ENTITY_URL = "";

        public static final String SPIDER_CARD_URL = "card/spider.png";
        public static final String SPIDER_ENTITY_URL = "";

        public static final String TOAD_CARD_URL = "card/toad.png";
        public static final String TOAD_ENTITY_URL = "";

        public static final String WASP_CARD_URL = "card/wasp.png";
        public static final String WASP_ENTITY_URL = "";

        public static final String WILD_BOAR_CARD_URL = "card/wildBoar.png";
        public static final String WILD_BOAR_ENTITY_URL = "";
    }
}
