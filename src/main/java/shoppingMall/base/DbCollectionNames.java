package shoppingMall.base;

public enum DbCollectionNames {

    CUSTOMERS_DB_COLECTON("customers"),
    SELLERS_DB_COLLECTION("sellers"),
    PERSONS_DB_COLLECTION("persons"),
    SHOPS_DB_COLLECTION("shops"),
    PRODUCTS_DB_COLLECTION("products"),
    ORDERS_DB_COLLECTION("orders"),
    ORDER_DETAILS_DB_COLLECTION("orderDetails"),
    SHOP_PRODUCTS_DB_COLLECTION("shopProducts");

    private final String name;

    DbCollectionNames(String name) {
        this.name = name;
    }
}
