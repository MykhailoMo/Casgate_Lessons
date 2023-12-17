package shoppingMall.utils;


import shoppingMall.base.DataBaseImpl;
import shoppingMall.entities.*;
import shoppingMall.enums.*;

import java.util.*;

import static shoppingMall.base.DbCollectionNames.*;
import static shoppingMall.enums.Constants.VAT;
import static shoppingMall.enums.PersonType.*;

public class InitialDataSetup {
    static List<String> personNames = List.of("Mykola", "Ivan", "Petro", "Semen", "Mykhailo", "Dmytro", "Olena", "Nata",
            "Marina", "Katerina");

    public static void fillPersonsAndShops() {
        List<Shops> shopsList = Arrays.asList(Shops.values());
        int personsListSize = personNames.size();
        int shopsListSize = shopsList.size();
        String name;
        UUID personId;
        Person person;
        String shopName;
        ShopType shopType;
        Shop shop;
        for (int i = 0; i < personsListSize; i++) {
            name = personNames.get(i);
            personId = UUID.randomUUID();
            if (i < shopsListSize) {
                person = new Person(personId, name, name + "", SELLER);
                DataBaseImpl.getInstance().saveNewEntity(PERSONS_DB_COLLECTION, person.getId(), person);

                shopName = shopsList.get(i).getName();
                shopType = shopsList.get(i).getType();
                shop = new Shop(UUID.randomUUID(), personId, shopName, shopType);
                DataBaseImpl.getInstance().saveNewEntity(SHOPS_DB_COLLECTION, shop.getId(), shop);
            } else {
                person = new Person(personId, name, name + "", CUSTOMER);
                DataBaseImpl.getInstance().saveNewEntity(PERSONS_DB_COLLECTION, person.getId(), person);
            }
        }
    }

    public static void fillProducts() {
        List<Products> productsList = Arrays.asList(Products.values());
        productsList.stream()
                .forEach(p -> DataBaseImpl.getInstance().saveNewEntity(PRODUCTS_DB_COLLECTION, p.getProduct().getId(), p.getProduct()));
    }

    public static void fillProductsInShops() {
        List<Shop> shops = new ArrayList(DataBaseImpl.getInstance().getAllEntities(SHOPS_DB_COLLECTION));
        shops.stream()
                .forEach(s -> storeProductsToShop(s));

    }
    private static void storeProductsToShop (Shop shop) {
        List<Products> products = Arrays.asList(Products.values());
        products.stream()
                .filter(p -> p.getShopType().equals(shop.getType()))
                .forEach(p -> {
                    ShopProduct shopProduct = new ShopProduct(UUID.randomUUID(), shop.getId(), p.getProduct().getId(),
                            p.getProduct().getName(), doubleToInt(p.getProduct().getBasePrice() * VAT.getVatMultiplyer()),
                            random());
                    DataBaseImpl.getInstance().saveNewEntity(SHOP_PRODUCTS_DB_COLLECTION, shopProduct.getId(), shopProduct);
                });
    }

    static int random() {
        return new Random().nextInt(100);
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
