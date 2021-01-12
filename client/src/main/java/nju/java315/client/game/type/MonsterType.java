package nju.java315.client.game.type;

import nju.java315.client.game.Config;

public enum MonsterType{
    //葫芦娃阵营
    LARGE_HULU("LargeHulu",
        Config.Asset.LARGE_HULU_CARD_URL,
        Config.Asset.LARGE_HULU_ENTITY_LEFT_URL,
        Config.Asset.LARGE_HULU_ENTITY_RIGHT_URL,
        Config.LARGE_HULU_COST,
        Config.LARGE_HULU_HP
    ),
    EYE_HULU("EyeHulu",
        Config.Asset.EYE_HULU_CARD_URL,
        Config.Asset.EYE_HULU_ENTITY_LEFT_URL,
        Config.Asset.EYE_HULU_ENTITY_RIGHT_URL,
        Config.EYE_HULU_COST,
        Config.EYE_HULU_HP
    ),
    IRON_HULU("IronHulu",
        Config.Asset.IRON_HULU_CARD_URL,
        Config.Asset.IRON_HULU_ENTITY_LEFT_URL,
        Config.Asset.IRON_HULU_ENTITY_RIGHT_URL,
        Config.IRON_HULU_COST,
        Config.IRON_HULU_HP
    ),
    FIRE_HULU("FireHulu",
        Config.Asset.FIRE_HULU_CARD_URL,
        Config.Asset.FIRE_HULU_ENTITY_LEFT_URL,
        Config.Asset.FIRE_HULU_ENTITY_RIGHT_URL,
        Config.FIRE_HULU_COST,
        Config.FIRE_HULU_HP
    ),
    WATER_HULU("WaterHulu",
        Config.Asset.WATER_HULU_CARD_URL,
        Config.Asset.WATER_HULU_ENTITY_LEFT_URL,
        Config.Asset.WATER_HULU_ENTITY_RIGHT_URL,
        Config.WATER_HULU_COST,
        Config.WATER_HULU_HP
    ),
    STEALTH_HULU("StealthHulu",
        Config.Asset.STEALTH_HULU_CARD_URL,
        Config.Asset.STEALTH_HULU_ENTITY_LEFT_URL,
        Config.Asset.STEALTH_HULU_ENTITY_RIGHT_URL,
        Config.STEALTH_HULU_COST,
        Config.STEALTH_HULU_HP
    ),
    HULU_HULU("HuluHulu",
        Config.Asset.HULU_HULU_CARD_URL,
        Config.Asset.HULU_HULU_ENTITY_LEFT_URL,
        Config.Asset.HULU_HULU_ENTITY_RIGHT_URL,
        Config.HULU_HULU_COST,
        Config.HULU_HULU_HP
    ),
    //妖精阵营
    NTR("NTR",
        Config.Asset.NTR_CARD_URL,
        Config.Asset.NTR_ENTITY_LEFT_URL,
        Config.Asset.NTR_ENTITY_RIGHT_URL,
        Config.NTR_COST,
        Config.NTR_HP
    ),
    SNAKE("Snake",
        Config.Asset.SNAKE_CARD_URL,
        Config.Asset.SNAKE_ENTITY_LEFT_URL,
        Config.Asset.SNAKE_ENTITY_RIGHT_URL,
        Config.SNAKE_COST,
        Config.SNAKE_HP
    ),
    CENTIPEDE("Centipede",
        Config.Asset.CENTIPEDE_CARD_URL,
        Config.Asset.CENTIPEDE_ENTITY_LEFT_URL,
        Config.Asset.CENTIPEDE_ENTITY_RIGHT_URL,
        Config.CENTIPEDE_COST,
        Config.CENTIPEDE_HP
    ),
    SPIDER("Spider",
        Config.Asset.SPIDER_CARD_URL,
        Config.Asset.SPIDER_ENTITY_LEFT_URL,
        Config.Asset.SPIDER_ENTITY_RIGHT_URL,
        Config.SPIDER_COST,
        Config.SPIDER_HP
    ),
    TOAD("Toad",
        Config.Asset.TOAD_CARD_URL,
        Config.Asset.TOAD_ENTITY_LEFT_URL,
        Config.Asset.TOAD_ENTITY_RIGHT_URL,
        Config.TOAD_COST,
        Config.TOAD_HP

    ),
    WASP("Wasp",
        Config.Asset.WASP_CARD_URL,
        Config.Asset.WASP_ENTITY_LEFT_URL,
        Config.Asset.WASP_ENTITY_RIGHT_URL,
        Config.WASP_COST,
        Config.WASP_HP
    ),
    WILD_BOAR("WildBoar",
        Config.Asset.WILD_BOAR_CARD_URL,
        Config.Asset.WILD_BOAR_ENTITY_LEFT_URL,
        Config.Asset.WILD_BOAR_ENTITY_RIGHT_URL,
        Config.WILD_BOAR_COST,
        Config.WILD_BOAR_HP
    );

    private String cardUrl;
    private String leftUrl;
    private String rightUrl;
    private String name;
    private int cost;
    private int hp;

    private MonsterType(String name, String cardUrl, String leftUrl, String rightUrl, int cost, int hp){
        this.name = name;
        this.cardUrl = cardUrl;
        this.leftUrl = leftUrl;
        this.rightUrl = rightUrl;
        this.cost = cost;
        this.hp = hp;
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
}
