package shoppingMall.enums;

import static shoppingMall.enums.ShopType.*;

public enum Shops {
    COMP_MARKET("Computes", COMPUTERS),
    MARKET("Market", FOOD),
    SILPO("Silpo", FOOD),
    DIY("DIY", BUILDING_MATERIALS),
    ALL_FOR_YOU("All for you", HOUSEHOLD),
    WOOD("Wood", BUILDING_MATERIALS);

    private final String name;
    private final ShopType type;

    Shops(String name, ShopType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ShopType getType() {
        return type;
    }

}
