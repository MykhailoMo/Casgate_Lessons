package shoppingMall;

import shoppingMall.base.DataBaseImpl;
import shoppingMall.entities.Person;
import shoppingMall.entities.Shop;
import shoppingMall.entities.ShopProduct;
import shoppingMall.enums.PersonType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static shoppingMall.Data.*;
import static shoppingMall.Utils.*;
import static shoppingMall.base.DbCollectionNames.*;
import static shoppingMall.enums.PersonType.*;


public class Main {

    public static void main(String[] arg) {
        DataBaseImpl.getInstance();
//        createNewPerson();
        fillPersonsAndShops();
        printPersons();
        fillProducts();
        fillShopsWithProducts();
//        printShops(SELLER);
//        fillProducts();
//       printProducts();

        Person user = login();
        UUID userId = user.getId();
        createNewShop(userId);
        List<Shop> shops = new ArrayList(getShopsBySeller(userId,SHOPS_DB_COLLECTION));
        System.out.println(shops);
        List<ShopProduct> shopProducts = getProductsByShop(shops.get(0).getId(), SHOP_PRODUCTS_DB_COLLECTION);
        System.out.println(shopProducts);
        addProductsToShop(shops.get(0).getId());
        shopProducts = getProductsByShop(shops.get(0).getId(), SHOP_PRODUCTS_DB_COLLECTION);
        System.out.println(shopProducts);
        System.out.println(shops.get(0).getWalletAmount());
        Scanner input = new Scanner(System.in);
//        while (true) {
//            switch (user.getType()) {
//                case SELLER: {
//                    switch (input.nextInt()) {
//                        case 1: { //User's profile
//                            printPersons(userId);
//                            printShops(userId);
//                            break;
//                        }
//                        case 2: {
//                            createNewShop(userId);
//                            printShops(userId);
//                            break;
//                        }
//                        case 3: { //work with shop
//                            printShops(userId);
//                            UUID shopId = selectShop(userId);
//                            while (true) {
//                                switch (input.nextInt()) {
//                                    case 1: {
//                                        printShopProducts(shopId);
//                                        break;
//                                    }
//                                    case 2: {
//                                        changeShopBalance(userId);
//                                        break;
//                                    }
//                                    case 3: {
//                                        changeShopProducts(userId); //add + change (утилізація)
//                                        printShopProducts(shopId);
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    createNewShop(user.getId());
//                    printShops(SELLER);
//                    break;
//                }
//                case CUSTOMER: {
//                    switch (input.nextInt()) {
//                        case 1: { //User's profile
//                            printPersons(userId);
//                            break;
//                        }
//                        case 2: { //User's shopping
//                            printShops(CUSTOMER);
//                            UUID shopId = selectShop(userId);
//                            while (true) {
//                                switch (input.nextInt()) {
//                                    case 1: { //add to cart
//                                        addOrderProducts(shopId, orderId);
//                                        break;
//                                    }
//                                    case 2: { //User's cart
//                                        printOrder(userId);
//                                        break;
//                                    }
//                                    case 3: {
//                                        deleteOrderProducts(orderId);
//                                        break;
//                                    }
//                                }
//                            }
//                            break;
//                        }
//                        case 2: { //User's cart
//                            printOrder(userId);
//                            break;
//                        }
//                    }
//
//
//                    break;
//                }
//            }
//        }


    }
}
