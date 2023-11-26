package shoppingMall.enums;

public enum DeliveryTypes {
    IMMEDIATE(0),
    NEXT_DAY(1),
    THIRD_DAY(3),
    WEEK(7);

    private final int daysToDelivery;

    DeliveryTypes(int daysToDelivery) {
        this.daysToDelivery = daysToDelivery;
    }
}
