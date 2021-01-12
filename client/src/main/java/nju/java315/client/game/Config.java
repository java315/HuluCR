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

    public static final int HORIZONTAL_GRID_NUM = 97;
    public static final int VERTICAL_GRID_NUM = 60;
    public static final int CELL_HEIGHT = 10;
    public static final int CELL_WIDTH = 10;

    public static final int RIVER_GRID_WIDTH = 1;
    public static final int UP_TOWER_GRIX_Y = 3; // 从0开始
    public static final int DOWN_TOWER_GRID_Y = 14;
    public static final int TOWER_GRIX_X = 9;

    public static final int CARD_X = 50;
    public static final int[] CARD_Y = { 240, 325, 410, 495, 600 };

    public static final int READY_BUTTON_X = 26;
    public static final int READY_BUTTON_Y = 50;

    public static final int LEFT_BOUND_X = 235;
    public static final int RIGHT_BOUND_X = 910;

    public static final int SELF_READY_TITLE_X = 320;
    public static final int ENEMY_READY_TITLE_X = 660;
    public static final int CENTER_READY_TITLE_X = 490;
    public static final int READY_TITLE_Y = 280;
    public static final int START_TITLE_X = 370;
    public static final int START_TITLE_Y = 210;

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

    public static final int LARGE_HULU_HP = 100;
    public static final int EYE_HULU_HP = 100;
    public static final int IRON_HULU_HP = 100;
    public static final int FIRE_HULU_HP = 100;
    public static final int WATER_HULU_HP = 100;
    public static final int STEALTH_HULU_HP = 100;
    public static final int HULU_HULU_HP = 100;
    public static final int NTR_HP = 100;
    public static final int SNAKE_HP = 100;
    public static final int CENTIPEDE_HP = 100;
    public static final int SPIDER_HP = 100;
    public static final int TOAD_HP = 100;
    public static final int WASP_HP = 100;
    public static final int WILD_BOAR_HP = 100;

    // 网络相关参数
    public static final int SERVER_PORT = 8080;
    public static final String SERVER_IP = "localhost";

    public static final class Asset {
        // TODO write a fxml
        public static final String FXML_MAIN_UI = "main.fxml";

        public static final String LARGE_HULU_CARD_URL = "card/largeHulu.png";
        public static final String LARGE_HULU_ENTITY_LEFT_URL = "monster/red-left.png";
        public static final String LARGE_HULU_ENTITY_RIGHT_URL = "monster/red-right.png";

        public static final String EYE_HULU_CARD_URL = "card/eyeHulu.png";
        public static final String EYE_HULU_ENTITY_LEFT_URL = "monster/orange-left.png";
        public static final String EYE_HULU_ENTITY_RIGHT_URL = "monster/orange-right.png";

        public static final String IRON_HULU_CARD_URL = "card/ironHulu.png";
        public static final String IRON_HULU_ENTITY_LEFT_URL = "monster/yellow-left.png";
        public static final String IRON_HULU_ENTITY_RIGHT_URL = "monster/yellow-right.png";

        public static final String FIRE_HULU_CARD_URL = "card/fireHulu.png";
        public static final String FIRE_HULU_ENTITY_LEFT_URL = "monster/green-left.png";
        public static final String FIRE_HULU_ENTITY_RIGHT_URL = "monster/green-right.png";

        public static final String WATER_HULU_CARD_URL = "card/waterHulu.png";
        public static final String WATER_HULU_ENTITY_LEFT_URL = "monster/blue-left.png";
        public static final String WATER_HULU_ENTITY_RIGHT_URL = "monster/blue-right.png";

        public static final String STEALTH_HULU_CARD_URL = "card/stealthHulu.png";
        public static final String STEALTH_HULU_ENTITY_LEFT_URL = "monster/cyan-left.png";
        public static final String STEALTH_HULU_ENTITY_RIGHT_URL = "monster/cyan-right.png";

        public static final String HULU_HULU_CARD_URL = "card/huluHulu.png";
        public static final String HULU_HULU_ENTITY_LEFT_URL = "monster/purple-left.png";
        public static final String HULU_HULU_ENTITY_RIGHT_URL = "monster/purple-right.png";

        public static final String NTR_CARD_URL = "card/NTR.png";
        public static final String NTR_ENTITY_LEFT_URL = "monster/Scorpion-left.png";
        public static final String NTR_ENTITY_RIGHT_URL = "monster/Scorpion-right.png";

        public static final String SNAKE_CARD_URL = "card/snake.png";
        public static final String SNAKE_ENTITY_LEFT_URL = "monster/snake-left.png";
        public static final String SNAKE_ENTITY_RIGHT_URL = "monster/snake-right.png";

        public static final String CENTIPEDE_CARD_URL = "card/centipede.png";
        public static final String CENTIPEDE_ENTITY_LEFT_URL = "";
        public static final String CENTIPEDE_ENTITY_RIGHT_URL = "";

        public static final String SPIDER_CARD_URL = "card/spider.png";
        public static final String SPIDER_ENTITY_LEFT_URL = "";
        public static final String SPIDER_ENTITY_RIGHT_URL = "";

        public static final String TOAD_CARD_URL = "card/toad.png";
        public static final String TOAD_ENTITY_LEFT_URL = "";
        public static final String TOAD_ENTITY_RIGHT_URL = "";

        public static final String WASP_CARD_URL = "card/wasp.png";
        public static final String WASP_ENTITY_LEFT_URL = "";
        public static final String WASP_ENTITY_RIGHT_URL = "";

        public static final String WILD_BOAR_CARD_URL = "card/wildBoar.png";
        public static final String WILD_BOAR_ENTITY_LEFT_URL = "";
        public static final String WILD_BOAR_ENTITY_RIGHT_URL = "";

        public static final String READY_BUTTON_URL = "readyButton.png";
        public static final String READY_TITLE_URL = "readyTitle.png";
        public static final String START_TITLE_URL = "startTitle.png";
    }
}
