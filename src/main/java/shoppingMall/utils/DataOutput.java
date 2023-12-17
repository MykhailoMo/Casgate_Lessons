package shoppingMall.utils;

import shoppingMall.base.DataBaseImpl;
import shoppingMall.entities.*;
import shoppingMall.enums.PersonType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static shoppingMall.base.DbCollectionNames.*;
import static shoppingMall.enums.PersonType.CUSTOMER;

public class DataOutput {
    public static void printPersons() {
        List<Person> persons = new ArrayList(DataBaseImpl.getInstance().getAllEntities(PERSONS_DB_COLLECTION));
        AtomicInteger i = new AtomicInteger(1);
        persons.stream()
                .forEach(p -> System.out.println(i.getAndIncrement() + ": " + p.toString()));
    }

    public static void printShops(List<Shop> shops, PersonType personType) {
        AtomicInteger i = new AtomicInteger(1);
        if (personType.equals(CUSTOMER)) {
            System.out.println("\tName\t\tType\t\tDelivery");
            shops.stream()
                    .forEach(s -> System.out.println(i.getAndIncrement() + ": " + s.getName() +
                            " \t\t " + s.getType().getGoodsName() +
                            "\t\t" + s.getType().getDelivery().name()));
        } else {
            shops.stream()
                    .forEach(s -> System.out.println(i.getAndIncrement() + ": " + s.toString()));
        }
    }

    public static void printProducts() {
        List<Product> products = new ArrayList(DataBaseImpl.getInstance().getAllEntities(PRODUCTS_DB_COLLECTION));
        AtomicInteger i = new AtomicInteger(1);
        products.stream()
                .forEach(p -> System.out.println(i.getAndIncrement() + ": " + p.toString()));
    }

    public static void printShopProducts(List<ShopProduct> shopProducts) {
        AtomicInteger i = new AtomicInteger(1);
        shopProducts.stream()
                .forEach(s -> System.out.print(i.getAndIncrement() + ": " + s.toString()));
    }

    public static void printCustomerCart(UUID personId) {
        List<Order> orders = GetData.getOrdersByPerson(personId);
        List<OrderDetail> orderDetails;
        Shop shop;
        String productName;
        int productPrice;
        int productQuantity;
        int productAmount;
        for (Order order : orders) {
            orderDetails = GetData.getProductsByOrder(order.getId());
            shop = (Shop) DataBaseImpl.getInstance().getEntity(SHOPS_DB_COLLECTION, order.getShopId());
            System.out.printf("\nOrder: %s\n" +
                    "\tShop: %s\n" +
                    "\tDeliveryDate: %s%n",order.getId(), shop.getName(), order.getDeliveryDate());
            System.out.println("----------------------------------------------------");
            System.out.println("\t\tName\t\tQuantity\t\tPrice\t\tAmount");
            for (OrderDetail orderDetail : orderDetails) {
                productName = GetData.getProductById(orderDetail.getProductId()).getName();
                productPrice = orderDetail.getPrice();
                productQuantity = orderDetail.getQuantity();
                productAmount = productPrice * productQuantity;
                System.out.printf("\t\t%s" +
                        "\t\t\t %d" +
                        "\t\t\t %dUAH" +
                        "\t\t %dUAH%n", productName, productQuantity, productPrice, productAmount);
            }
        }
    }

    public static void printWrongInput () {
        System.out.println("You filled wrong number");
    }

    public static void showProfile(Shop shop) {
            System.out.printf("---------------------------------------\n" +
                    "Shop profile data:\n" +
                    "\tName: %s\n" +
                    "\tShopType: %s\n" +
                    "\tBalanceAmount: %d%n", shop.getName(), shop.getType().getGoodsName(), shop.getWalletAmount());
    }

    public static void showProfile(Person person) {
        System.out.printf("---------------------------------------\n" +
                "User profile data:\n" +
                "\tName: %s\n" +
                "\tProfileType: %s\n" +
                "\tBalanceAmount: %d%n", person.getName(), person.getType().name(), person.getWalletAmount());
    }

}
