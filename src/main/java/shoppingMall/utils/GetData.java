package shoppingMall.utils;

import shoppingMall.base.DataBaseImpl;
import shoppingMall.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static shoppingMall.base.DbCollectionNames.*;
import static shoppingMall.enums.PersonType.SELLER;

public class GetData {
    public static List<Shop> getShopsList(Person person) {
        List<Shop> shops = new ArrayList(DataBaseImpl.getInstance().getAllEntities(SHOPS_DB_COLLECTION));
        if (person.getType().equals(SELLER)) {
            shops = shops.stream()
                    .filter(s -> s.getPersonId().equals(person.getId()))
                    .collect(Collectors.toList());
        }
        return shops;
    }

    public static List<ShopProduct> getProductsByShop(UUID shopId) {
        List<ShopProduct> shopProducts = new ArrayList(DataBaseImpl.getInstance().getAllEntities(SHOP_PRODUCTS_DB_COLLECTION));
        return shopProducts.stream()
                .filter(s -> s.getShopId().equals(shopId))
                .collect(Collectors.toList());
    }

    public static List<Order> getOpdersByCustomer(UUID customerId) {
        List<Order> orders = new ArrayList(DataBaseImpl.getInstance().getAllEntities(ORDERS_DB_COLLECTION));
        return orders.stream()
                .filter(o -> o.getPersonId().equals(customerId))
                .collect(Collectors.toList());
    }

    public static List<OrderDetail> getCurtByOrder(UUID orderId) {
        List<OrderDetail> orderDetails = new ArrayList(DataBaseImpl.getInstance().getAllEntities(ORDER_DETAILS_DB_COLLECTION));
        return orderDetails.stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .collect(Collectors.toList());
    }

    public static List<Order> getOrdersByPerson(UUID personId) {
        List<Order> orders = new ArrayList(DataBaseImpl.getInstance().getAllEntities(ORDERS_DB_COLLECTION));
        return orders.stream()
                .filter(o -> o.getPersonId().equals(personId))
                .collect(Collectors.toList());
    }

    public static List<OrderDetail> getProductsByOrder(UUID orderId) {
        List<OrderDetail> orderDetails = new ArrayList(DataBaseImpl.getInstance().getAllEntities(ORDER_DETAILS_DB_COLLECTION));
        return orderDetails.stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .collect(Collectors.toList());
    }

    public static Product getProductById(UUID productId) {
        return (Product) DataBaseImpl.getInstance().getEntity(PRODUCTS_DB_COLLECTION, productId);
    }

    public static Person getPersonById(UUID personId) {
        return (Person) DataBaseImpl.getInstance().getEntity(PERSONS_DB_COLLECTION, personId);
    }

    public static Shop getShopById(UUID shopId) {
        return (Shop) DataBaseImpl.getInstance().getEntity(SHOPS_DB_COLLECTION, shopId);
    }
}
