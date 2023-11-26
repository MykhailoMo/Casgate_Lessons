package shoppingMall.enums;

public enum PersonType {

    CUSTOMER("customer"),
    SELLER("seller");

    private final String name;

    PersonType(String name) {
        this.name = name;
    }
}
