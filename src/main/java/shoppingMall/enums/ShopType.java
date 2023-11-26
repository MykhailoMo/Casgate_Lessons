package shoppingMall.enums;

import static shoppingMall.enums.DeliveryTypes.*;

public enum ShopType {
    FOOD("Food", IMMEDIATE),
    COMPUTERS("Computers", NEXT_DAY),
    HOUSEHOLD("Household", THIRD_DAY),
    BUILDING_MATERIALS("Building", WEEK);

    private final String goodsName;
    private final DeliveryTypes delivery;

    ShopType(String goodsName, DeliveryTypes delivery) {
        this.goodsName = goodsName;
        this.delivery = delivery;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public DeliveryTypes getDelivery() {
        return delivery;
    }

    @Override
    public String toString() {
        return "ShopType{" +
                "goodsName='" + goodsName + '\'' +
                '}';
    }
}
