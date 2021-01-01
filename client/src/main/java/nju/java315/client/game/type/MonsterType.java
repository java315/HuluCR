package nju.java315.client.game.type;

import nju.java315.client.game.Config;

public enum MonsterType{
    //葫芦娃阵营
    LARGE_HULU("LargeHulu",
            Config.Asset.LARGE_HULU_CARD_URL,
            Config.Asset.LARGE_HULU_ENTITY_LEFT_URL,
            Config.Asset.LARGE_HULU_ENTITY_RIGHT_URL,
            Config.LARGE_HULU_COST
    ),
    EYE_HULU("EyeHulu",
            Config.Asset.EYE_HULU_CARD_URL,
            Config.Asset.EYE_HULU_ENTITY_LEFT_URL,
            Config.Asset.EYE_HULU_ENTITY_RIGHT_URL,
            Config.EYE_HULU_COST
    ),
    IRON_HULU("IronHulu",
            Config.Asset.IRON_HULU_CARD_URL,
            Config.Asset.IRON_HULU_ENTITY_LEFT_URL,
            Config.Asset.IRON_HULU_ENTITY_RIGHT_URL,
            Config.IRON_HULU_COST
    ),
    FIRE_HULU("FireHulu",
            Config.Asset.FIRE_HULU_CARD_URL,
            Config.Asset.FIRE_HULU_ENTITY_LEFT_URL,
            Config.Asset.FIRE_HULU_ENTITY_RIGHT_URL,
            Config.FIRE_HULU_COST
    ),
    WATER_HULU("WaterHulu",
            Config.Asset.WATER_HULU_CARD_URL,
            Config.Asset.WATER_HULU_ENTITY_LEFT_URL,
            Config.Asset.WATER_HULU_ENTITY_RIGHT_URL,
            Config.WATER_HULU_COST
    ),
    STEALTH_HULU("StealthHulu",
            Config.Asset.STEALTH_HULU_CARD_URL,
            Config.Asset.STEALTH_HULU_ENTITY_LEFT_URL,
            Config.Asset.STEALTH_HULU_ENTITY_RIGHT_URL,
            Config.STEALTH_HULU_COST
    ),
    HULU_HULU("HuluHulu",
            Config.Asset.HULU_HULU_CARD_URL,
            Config.Asset.HULU_HULU_ENTITY_LEFT_URL,
            Config.Asset.HULU_HULU_ENTITY_RIGHT_URL,
            Config.HULU_HULU_COST
    ),
    //妖精阵营
    NTR("NTR",
            Config.Asset.NTR_CARD_URL,
            Config.Asset.NTR_ENTITY_LEFT_URL,
            Config.Asset.NTR_ENTITY_RIGHT_URL,
            Config.NTR_COST
    ),
    SNAKE("Snake",
            Config.Asset.SNAKE_CARD_URL,
            Config.Asset.SNAKE_ENTITY_LEFT_URL,
            Config.Asset.SNAKE_ENTITY_RIGHT_URL,
            Config.SNAKE_COST
    ),
    CENTIPEDE("Centipede",
            Config.Asset.CENTIPEDE_CARD_URL,
            Config.Asset.CENTIPEDE_ENTITY_LEFT_URL,
            Config.Asset.CENTIPEDE_ENTITY_RIGHT_URL,
            Config.CENTIPEDE_COST
    ),
    SPIDER("Spider",
            Config.Asset.SPIDER_CARD_URL,
            Config.Asset.SPIDER_ENTITY_LEFT_URL,
            Config.Asset.SPIDER_ENTITY_RIGHT_URL,
            Config.SPIDER_COST
    ),
    TOAD("Toad",
            Config.Asset.SNAKE_CARD_URL,
            Config.Asset.SNAKE_ENTITY_LEFT_URL,
            Config.Asset.SNAKE_ENTITY_RIGHT_URL,
            Config.SNAKE_COST
    ),
    WASP("Wasp",
            Config.Asset.TOAD_CARD_URL,
            Config.Asset.TOAD_ENTITY_LEFT_URL,
            Config.Asset.TOAD_ENTITY_RIGHT_URL,
            Config.TOAD_COST
    ),
    WILD_BOAR("WildBoar",
            Config.Asset.WILD_BOAR_CARD_URL,
            Config.Asset.WILD_BOAR_ENTITY_LEFT_URL,
            Config.Asset.WILD_BOAR_ENTITY_RIGHT_URL,
            Config.WILD_BOAR_COST
    );

    private String cardUrl;
    private String leftUrl;
    private String rightUrl;
    private String name;
    private int cost;

    private MonsterType(String name, String cardUrl, String leftUrl, String rightUrl, int cost){
        this.name = name;
        this.cardUrl = cardUrl;
        this.leftUrl = leftUrl;
        this.rightUrl = rightUrl;
        this.cost = cost;
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
}
