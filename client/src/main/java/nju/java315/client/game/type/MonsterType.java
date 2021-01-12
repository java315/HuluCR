package nju.java315.client.game.type;

import nju.java315.client.game.Config;

public enum MonsterType{
    //葫芦娃阵营
    LARGE_HULU("LargeHulu",
        Config.Asset.LARGE_HULU_CARD_URL,
        Config.Asset.LARGE_HULU_ENTITY_LEFT_URL,
        Config.Asset.LARGE_HULU_ENTITY_RIGHT_URL,
        Config.LARGE_HULU_COST,
        Config.LARGE_HULU_HP,
        Config.VERY_SLOW
    ),
    EYE_HULU("EyeHulu",
        Config.Asset.EYE_HULU_CARD_URL,
        Config.Asset.EYE_HULU_ENTITY_LEFT_URL,
        Config.Asset.EYE_HULU_ENTITY_RIGHT_URL,
        Config.EYE_HULU_COST,
        Config.EYE_HULU_HP,
        Config.NOMAL
    ),
    IRON_HULU("IronHulu",
        Config.Asset.IRON_HULU_CARD_URL,
        Config.Asset.IRON_HULU_ENTITY_LEFT_URL,
        Config.Asset.IRON_HULU_ENTITY_RIGHT_URL,
        Config.IRON_HULU_COST,
        Config.IRON_HULU_HP,
        Config.FAST
    ),
    FIRE_HULU("FireHulu",
        Config.Asset.FIRE_HULU_CARD_URL,
        Config.Asset.FIRE_HULU_ENTITY_LEFT_URL,
        Config.Asset.FIRE_HULU_ENTITY_RIGHT_URL,
        Config.FIRE_HULU_COST,
        Config.FIRE_HULU_HP,
        Config.NOMAL
    ),
    WATER_HULU("WaterHulu",
        Config.Asset.WATER_HULU_CARD_URL,
        Config.Asset.WATER_HULU_ENTITY_LEFT_URL,
        Config.Asset.WATER_HULU_ENTITY_RIGHT_URL,
        Config.WATER_HULU_COST,
        Config.WATER_HULU_HP,
        Config.NOMAL
    ),
    STEALTH_HULU("StealthHulu",
        Config.Asset.STEALTH_HULU_CARD_URL,
        Config.Asset.STEALTH_HULU_ENTITY_LEFT_URL,
        Config.Asset.STEALTH_HULU_ENTITY_RIGHT_URL,
        Config.STEALTH_HULU_COST,
        Config.STEALTH_HULU_HP,
        Config.VERY_FAST
    ),
    HULU_HULU("HuluHulu",
        Config.Asset.HULU_HULU_CARD_URL,
        Config.Asset.HULU_HULU_ENTITY_LEFT_URL,
        Config.Asset.HULU_HULU_ENTITY_RIGHT_URL,
        Config.HULU_HULU_COST,
        Config.HULU_HULU_HP,
        Config.SLOW
    ),
    
    //妖精阵营
    NTR("NTR",
        Config.Asset.NTR_CARD_URL,
        Config.Asset.NTR_ENTITY_LEFT_URL,
        Config.Asset.NTR_ENTITY_RIGHT_URL,
        Config.NTR_COST,
        Config.NTR_HP,
        Config.FAST
    ),
    SNAKE("Snake",
        Config.Asset.SNAKE_CARD_URL,
        Config.Asset.SNAKE_ENTITY_LEFT_URL,
        Config.Asset.SNAKE_ENTITY_RIGHT_URL,
        Config.SNAKE_COST,
        Config.SNAKE_HP,
        Config.VERY_FAST
    ),
    CENTIPEDE("Centipede",
        Config.Asset.CENTIPEDE_CARD_URL,
        Config.Asset.CENTIPEDE_ENTITY_LEFT_URL,
        Config.Asset.CENTIPEDE_ENTITY_RIGHT_URL,
        Config.CENTIPEDE_COST,
        Config.CENTIPEDE_HP,
        Config.NOMAL
    ),
    SPIDER("Spider",
        Config.Asset.SPIDER_CARD_URL,
        Config.Asset.SPIDER_ENTITY_LEFT_URL,
        Config.Asset.SPIDER_ENTITY_RIGHT_URL,
        Config.SPIDER_COST,
        Config.SPIDER_HP,
        Config.SLOW
    ),
    TOAD("Toad",
        Config.Asset.TOAD_CARD_URL,
        Config.Asset.TOAD_ENTITY_LEFT_URL,
        Config.Asset.TOAD_ENTITY_RIGHT_URL,
        Config.TOAD_COST,
        Config.TOAD_HP,
        Config.VERY_SLOW
    ),
    WASP("Wasp",
        Config.Asset.WASP_CARD_URL,
        Config.Asset.WASP_ENTITY_LEFT_URL,
        Config.Asset.WASP_ENTITY_RIGHT_URL,
        Config.WASP_COST,
        Config.WASP_HP,
        Config.NOMAL
    ),
    WILD_BOAR("WildBoar",
        Config.Asset.WILD_BOAR_CARD_URL,
        Config.Asset.WILD_BOAR_ENTITY_LEFT_URL,
        Config.Asset.WILD_BOAR_ENTITY_RIGHT_URL,
        Config.WILD_BOAR_COST,
        Config.WILD_BOAR_HP,
        Config.NOMAL
    ),

    // unmovable 
    GRANDFATHER("Grandfather",
            "",
            Config.Asset.GRANDFATHER_LEFT_URL,          
            Config.Asset.GRANDFATHER_RIGHT_URL,
            0,
            100,
            Config.STILL
    );

    
    private String cardUrl;
    private String leftUrl;
    private String rightUrl;
    private String name;
    private int cost;
    private int hp;
    private int speed;
    private MonsterType(String name, String cardUrl, String leftUrl, String rightUrl, int cost, int hp, int speed){
        this.name = name;
        this.cardUrl = cardUrl;
        this.leftUrl = leftUrl;
        this.rightUrl = rightUrl;
        this.cost = cost;
        this.hp = hp;
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

    public int getCost(){
        return cost;
    }

    public int getHp(){
        return hp;
    }

    public int getSpeed() {
        return speed;
    }
}
