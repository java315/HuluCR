package nju.java315.client.game.type;

import nju.java315.client.game.Config;

public enum MonsterType{
    //葫芦娃阵营
    LARGE_HULU("LargeHulu",
        Config.Asset.LARGE_HULU_CARD_URL,
        Config.Asset.LARGE_HULU_ENTITY_LEFT_URL,
        Config.Asset.LARGE_HULU_ENTITY_RIGHT_URL,
        Config.Asset.LARGE_HULU_ENTITY_LEFT_DEAD_URL,
        Config.Asset.LARGE_HULU_ENTITY_RIGHT_DEAD_URL,
        Config.LARGE_HULU_COST,
        Config.LARGE_HULU_HP,
        Config.LARGE_HULU_SENSOR,
        Config.VERY_SLOW
    ),
    EYE_HULU("EyeHulu",
        Config.Asset.EYE_HULU_CARD_URL,
        Config.Asset.EYE_HULU_ENTITY_LEFT_URL,
        Config.Asset.EYE_HULU_ENTITY_RIGHT_URL,
        Config.Asset.EYE_HULU_ENTITY_LEFT_DEAD_URL,
        Config.Asset.EYE_HULU_ENTITY_RIGHT_DEAD_URL,
        Config.EYE_HULU_COST,
        Config.EYE_HULU_HP,
        Config.EYE_HULU_SENSOR,
        Config.NOMAL
    ),
    IRON_HULU("IronHulu",
        Config.Asset.IRON_HULU_CARD_URL,
        Config.Asset.IRON_HULU_ENTITY_LEFT_URL,
        Config.Asset.IRON_HULU_ENTITY_RIGHT_URL,
        Config.Asset.IRON_HULU_ENTITY_LEFT_DEAD_URL,
        Config.Asset.IRON_HULU_ENTITY_RIGHT_DEAD_URL,
        Config.IRON_HULU_COST,
        Config.IRON_HULU_HP,
        Config.IRON_HULU_SENSOR,
        Config.FAST
    ),
    FIRE_HULU("FireHulu",
        Config.Asset.FIRE_HULU_CARD_URL,
        Config.Asset.FIRE_HULU_ENTITY_LEFT_URL,
        Config.Asset.FIRE_HULU_ENTITY_RIGHT_URL,
        Config.Asset.FIRE_HULU_ENTITY_LEFT_DEAD_URL,
        Config.Asset.FIRE_HULU_ENTITY_RIGHT_DEAD_URL,
        Config.FIRE_HULU_COST,
        Config.FIRE_HULU_HP,
        Config.FIRE_HULU_SENSOR,
        Config.NOMAL
    ),
    WATER_HULU("WaterHulu",
        Config.Asset.WATER_HULU_CARD_URL,
        Config.Asset.WATER_HULU_ENTITY_LEFT_URL,
        Config.Asset.WATER_HULU_ENTITY_RIGHT_URL,
        Config.Asset.WATER_HULU_ENTITY_LEFT_DEAD_URL,
        Config.Asset.WATER_HULU_ENTITY_RIGHT_DEAD_URL,
        Config.WATER_HULU_COST,
        Config.WATER_HULU_HP,
        Config.WATER_HULU_SENSOR,
        Config.NOMAL
    ),
    STEALTH_HULU("StealthHulu",
        Config.Asset.STEALTH_HULU_CARD_URL,
        Config.Asset.STEALTH_HULU_ENTITY_LEFT_URL,
        Config.Asset.STEALTH_HULU_ENTITY_RIGHT_URL,
        Config.Asset.STEALTH_HULU_ENTITY_LEFT_DEAD_URL,
        Config.Asset.STEALTH_HULU_ENTITY_RIGHT_DEAD_URL,
        Config.STEALTH_HULU_COST,
        Config.STEALTH_HULU_HP,
        Config.STEALTH_HULU_SENSOR,
        Config.VERY_FAST
    ),
    HULU_HULU("HuluHulu",
        Config.Asset.HULU_HULU_CARD_URL,
        Config.Asset.HULU_HULU_ENTITY_LEFT_URL,
        Config.Asset.HULU_HULU_ENTITY_RIGHT_URL,
        Config.Asset.HULU_HULU_ENTITY_LEFT_DEAD_URL,
        Config.Asset.HULU_HULU_ENTITY_RIGHT_DEAD_URL,
        Config.HULU_HULU_COST,
        Config.HULU_HULU_HP,
        Config.HULU_HULU_SENSOR,
        Config.SLOW
    ),
    //妖精阵营
    NTR("NTR",
        Config.Asset.NTR_CARD_URL,
        Config.Asset.NTR_ENTITY_LEFT_URL,
        Config.Asset.NTR_ENTITY_RIGHT_URL,
        Config.Asset.NTR_ENTITY_LEFT_DEAD_URL,
        Config.Asset.NTR_ENTITY_RIGHT_DEAD_URL,
        Config.NTR_COST,
        Config.NTR_HP,
        Config.NTR_SENSOR,
        Config.FAST
    ),
    SNAKE("Snake",
        Config.Asset.SNAKE_CARD_URL,
        Config.Asset.SNAKE_ENTITY_LEFT_URL,
        Config.Asset.SNAKE_ENTITY_RIGHT_URL,
        Config.Asset.SNAKE_ENTITY_LEFT_DEAD_URL,
        Config.Asset.SNAKE_ENTITY_RIGHT_DEAD_URL,
        Config.SNAKE_COST,
        Config.SNAKE_HP,
        Config.SNAKE_SENSOR,
        Config.VERY_FAST
    ),
    // CENTIPEDE("Centipede",
    //     Config.Asset.CENTIPEDE_CARD_URL,
    //     Config.Asset.CENTIPEDE_ENTITY_LEFT_URL,
    //     Config.Asset.CENTIPEDE_ENTITY_RIGHT_URL,
    //     Config.Asset.CENTIPEDE_ENTITY_LEFT_DEAD_URL,
    //     Config.Asset.CENTIPEDE_ENTITY_RIGHT_DEAD_URL,
    //     Config.CENTIPEDE_COST,
    //     Config.CENTIPEDE_HP,
    //     Config.CENTIPEDE_SENSOR,
    //     Config.NOMAL
    // ),
    // SPIDER("Spider",
    //     Config.Asset.SPIDER_CARD_URL,
    //     Config.Asset.SPIDER_ENTITY_LEFT_URL,
    //     Config.Asset.SPIDER_ENTITY_RIGHT_URL,
    //     Config.Asset.SPIDER_ENTITY_LEFT_DEAD_URL,
    //     Config.Asset.SPIDER_ENTITY_RIGHT_DEAD_URL,
    //     Config.SPIDER_COST,
    //     Config.SPIDER_HP,
    //     Config.SPIDER_SENSOR,
    //     Config.SLOW
    // ),
    // TOAD("Toad",
    //     Config.Asset.TOAD_CARD_URL,
    //     Config.Asset.TOAD_ENTITY_LEFT_URL,
    //     Config.Asset.TOAD_ENTITY_RIGHT_URL,
    //     Config.Asset.TOAD_ENTITY_LEFT_DEAD_URL,
    //     Config.Asset.TOAD_ENTITY_RIGHT_DEAD_URL,
    //     Config.TOAD_COST,
    //     Config.TOAD_HP,
    //     Config.TOAD_SENSOR,
    //     Config.VERY_SLOW
    // ),
    // WASP("Wasp",
    //     Config.Asset.WASP_CARD_URL,
    //     Config.Asset.WASP_ENTITY_LEFT_URL,
    //     Config.Asset.WASP_ENTITY_RIGHT_URL,
    //     Config.Asset.WASP_ENTITY_LEFT_DEAD_URL,
    //     Config.Asset.WASP_ENTITY_RIGHT_DEAD_URL,
    //     Config.WASP_COST,
    //     Config.WASP_HP,
    //     Config.WASP_SENSOR,
    //     Config.NOMAL
    // ),
    // WILD_BOAR("WildBoar",
    //     Config.Asset.WILD_BOAR_CARD_URL,
    //     Config.Asset.WILD_BOAR_ENTITY_LEFT_URL,
    //     Config.Asset.WILD_BOAR_ENTITY_RIGHT_URL,
    //     Config.Asset.WILD_BOAR_ENTITY_LEFT_DEAD_URL,
    //     Config.Asset.WILD_BOAR_ENTITY_RIGHT_DEAD_URL,
    //     Config.WILD_BOAR_COST,
    //     Config.WILD_BOAR_HP,
    //     Config.WILD_BOAR_SENSOR,
    //     Config.NOMAL
    // ),

    // unmovable
    GRANDFATHER("Grandfather",
        "",
        Config.Asset.GRANDFATHER_LEFT_URL,
        Config.Asset.GRANDFATHER_RIGHT_URL,
        Config.Asset.GRANDFATHER_LEFT_DEAD_URL,
        Config.Asset.GRANDFATHER_RIGHT_DEAD_URL,
        Config.GRANDFATHER_COST,
        Config.GRANDFATHER_HP,
        Config.GRANDFATHER_SENSOR,
        Config.STILL
    );


    private String cardUrl;
    private String leftUrl;
    private String rightUrl;
    private String leftDeadUrl;
    private String rightDeadUrl;
    private String name;
    private double sensor;
    private int cost;
    private int hp;
    private int speed;
    private MonsterType(String name, String cardUrl, String leftUrl, String rightUrl, String leftDeadUrl, String rightDeadUrl, int cost, int hp, double sensor, int speed){
        this.name = name;
        this.cardUrl = cardUrl;
        this.leftUrl = leftUrl;
        this.rightUrl = rightUrl;
        this.leftDeadUrl = leftDeadUrl;
        this.rightDeadUrl = rightDeadUrl;
        this.cost = cost;
        this.hp = hp;
        this.sensor = sensor;
        this.speed = speed;
    }

    public String getName(){
        return name;
    }

    public String getCardUrl(){
        return cardUrl;
    }

    public String getLeftUrl(){
        return leftUrl;
    }

    public String getRightUrl(){
        return rightUrl;
    }

    public String getLeftDeadUrl(){
        return leftDeadUrl;
    }

    public String getRightDeadUrl(){
        return rightDeadUrl;
    }

    public int getCost(){
        return cost;
    }

    public int getHp(){
        return hp;
    }

    public double getSensor(){
        return sensor;
    }

    public int getSpeed() {
        return speed;
    }
}
