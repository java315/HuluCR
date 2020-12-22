package nju.java315.client.game.type;

import nju.java315.client.game.Config;

public enum MonsterType{
    //葫芦娃阵营
    LARGE_HULU("LargeHulu",
            Config.Asset.LARGE_HULU_CARD_URL,
            Config.Asset.LARGE_HULU_ENTITY_URL,
            Config.LARGE_HULU_COST
    ),
    EYE_HULU("EyeHulu",
            Config.Asset.EYE_HULU_CARD_URL,
            Config.Asset.EYE_HULU_ENTITY_URL,
            Config.EYE_HULU_COST
    ),
    IRON_HULU("IronHulu",
            Config.Asset.IRON_HULU_CARD_URL,
            Config.Asset.IRON_HULU_ENTITY_URL,
            Config.IRON_HULU_COST
    ),
    FIRE_HULU("FireHulu",
            Config.Asset.FIRE_HULU_CARD_URL,
            Config.Asset.FIRE_HULU_ENTITY_URL,
            Config.FIRE_HULU_COST
    ),
    WATER_HULU("WaterHulu",
            Config.Asset.WATER_HULU_CARD_URL,
            Config.Asset.WATER_HULU_ENTITY_URL,
            Config.WATER_HULU_COST
    ),
    STEALTH_HULU("StealthHulu",
            Config.Asset.STEALTH_HULU_CARD_URL,
            Config.Asset.STEALTH_HULU_ENTITY_URL,
            Config.STEALTH_HULU_COST
    ),
    HULU_HULU("HuluHulu",
            Config.Asset.HULU_HULU_CARD_URL,
            Config.Asset.HULU_HULU_ENTITY_URL,
            Config.HULU_HULU_COST
    ),
    //妖精阵营
    NTR("NTR",
            Config.Asset.NTR_CARD_URL,
            Config.Asset.NTR_ENTITY_URL,
            Config.NTR_COST
    ),
    SNAKE("Snake",
            Config.Asset.SNAKE_CARD_URL,
            Config.Asset.SNAKE_ENTITY_URL,
            Config.SNAKE_COST
    ),
    BAT_ARROW("BatArrow",
            Config.Asset.BAT_ARROW_CARD_URL,
            Config.Asset.BAT_ARROW_ENTITY_URL,
            Config.BAT_ARROW_COST
    ),
    BAT_SPEAR("BatSpear",
            Config.Asset.BAT_SPEAR_CARD_URL,
            Config.Asset.BAT_SPEAR_ENTITY_URL,
            Config.BAT_SPEAR_COST
    );

    private String cardUrl;
    private String entityUrl;
    private String name;
    private int cost;

    private MonsterType(String name, String cardUrl, String entityUrl, int cost){
        this.name = name;
        this.cardUrl = cardUrl;
        this.entityUrl = entityUrl;
        this.cost = cost;
    }

    public String getName(){
        return name;
    }

    public String getCardUrl(){
        return cardUrl;
    }

    public String getEntityUrl(){
        return entityUrl;
    }

    public int getCost(){
        return cost;
    }
}
