package shoppingMall.enums;

public enum Steps {
    AUTHORIZATION,
    LOGIN,
    PROFILE,
    DEPOSIT,
    CREATE_SHOP,
    VIEW_SHOP,
    VIEW_SHOP_PRODUCTS,
    ADD_SHOP_PRODUCTS,
    CHANGE_SHOP_BALANCE,
    CHANGE_SHOP_PRODUCTS,
    LOGOUT,
    SHOPPING,
    VIEW_CART;

    private String step;

    Steps() {
    }
}
