package shoppingMall;


import shoppingMall.base.DataBaseImpl;
import shoppingMall.entities.*;
import shoppingMall.enums.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static shoppingMall.base.DbCollectionNames.*;
import static shoppingMall.enums.PersonType.*;

public class Data {
    static List<String> personNames = List.of("Mykola", "Ivan", "Petro", "Semen", "Mykhailo", "Dmytro", "Olena", "Nata",
            "Marina", "Katerina");


    public static void fillPersonsAndShops() {
        List<Shops> shopsList = Arrays.asList(Shops.values());
        String name;
        UUID personId;
        Person person;
        String shopName;
        ShopType shopType;
        Shop shop;
        for (int i = 0; i < 10; i++) {
            name = personNames.get(i);
            personId = UUID.randomUUID();
            if (i < 5) {
                person = new Person(personId, name, name + "", CUSTOMER);
                DataBaseImpl.getInstance().saveNewEntity(PERSONS_DB_COLLECTION, person.getId(), person);
            } else {
                person = new Person(personId, name, name + "", SELLER);
                DataBaseImpl.getInstance().saveNewEntity(PERSONS_DB_COLLECTION, person.getId(), person);

                shopName = shopsList.get(i-5).getName();
                shopType = shopsList.get(i-5).getType();
                shop = new Shop(UUID.randomUUID(), personId, shopName, shopType);
                DataBaseImpl.getInstance().saveNewEntity(SHOPS_DB_COLLECTION, shop.getId(), shop);
            }
        }
    }

    public static void fillProducts() {
        List<Products> productsList = Arrays.asList(Products.values());
        for (Products product: productsList) {
            DataBaseImpl.getInstance().saveNewEntity(PRODUCTS_DB_COLLECTION, product.getProduct().getId(), product.getProduct());
        }
    }

    public static void fillProductsInShops() {
        List<Shop> shops = new ArrayList(DataBaseImpl.getInstance().getAllEntities(SHOPS_DB_COLLECTION));
        List<Products> products = Arrays.asList(Products.values());
        ShopProduct shopProduct;
        ShopType shopType;
        for (Shop shop : shops) {
            shopType = shop.getType();
            for (Products product : products) {
                if (product.getShopType().equals(shopType)) {
                    shopProduct = new ShopProduct(UUID.randomUUID(), shop.getId(), product.getProduct().getId(),
                            product.getProduct().getName(), doubleToInt(product.getProduct().getBasePrice() * 1.2),
                            random());
                    DataBaseImpl.getInstance().saveNewEntity(SHOP_PRODUCTS_DB_COLLECTION, shopProduct.getId(), shopProduct);
                }
            }
        }
    }

    static int random() {
        double data = Math.random() * 9;
        return Math.toIntExact(Math.round(data));
    }

    public static int doubleToInt(double data) {
        return Math.toIntExact(Math.round(data));
    }

    public static void dataBaseInit() {
        DataBaseImpl.getInstance();
        fillPersonsAndShops();
        fillProducts();
        fillProductsInShops();
    }

}
