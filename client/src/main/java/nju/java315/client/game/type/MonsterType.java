package nju.java315.client.game.type;

import nju.java315.client.game.Config;

public enum MonsterType{
    //葫芦娃阵营
    LARGE_HULU("LargeHulu", Config.Asset.LARGE_HULU_CARD_URL, Config.Asset.LARGE_HULU_ENTITY_URL),
    EYE_HULU("EyeHulu", Config.Asset.EYE_HULU_CARD_URL, Config.Asset.EYE_HULU_ENTITY_URL),
    IRON_HULU("IronHulu", Config.Asset.IRON_HULU_CARD_URL, Config.Asset.IRON_HULU_ENTITY_URL),
    FIRE_HULU("FireHulu", Config.Asset.FIRE_HULU_CARD_URL, Config.Asset.FIRE_HULU_ENTITY_URL),
    WATER_HULU("WaterHulu", Config.Asset.WATER_HULU_CARD_URL, Config.Asset.WATER_HULU_ENTITY_URL),
    STEALTH_HULU("StealthHulu", Config.Asset.STEALTH_HULU_CARD_URL, Config.Asset.STEALTH_HULU_ENTITY_URL),
    HULU_HULU("HuluHulu", Config.Asset.HULU_HULU_CARD_URL, Config.Asset.HULU_HULU_ENTITY_URL),
    //妖精阵营
    NTR("NTR", Config.Asset.NTR_CARD_URL, Config.Asset.NTR_ENTITY_URL),
    SNAKE("Snake", Config.Asset.SNAKE_CARD_URL, Config.Asset.SNAKE_ENTITY_URL),
    BAT_ARROW("BatArrow", Config.Asset.BAT_ARROW_CARD_URL, Config.Asset.BAT_ARROW_ENTITY_URL),
    BAT_SPEAR("BatSpear", Config.Asset.BAT_SPEAR_CARD_URL, Config.Asset.BAT_SPEAR_ENTITY_URL);

    private String cardUrl;
    private String entityUrl;
    private String name;

    private MonsterType(String name, String cardUrl, String entityUrl){
        this.name = name;
        this.cardUrl = cardUrl;
        this.entityUrl = entityUrl;
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
}
