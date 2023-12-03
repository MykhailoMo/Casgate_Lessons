package shoppingMall;

import shoppingMall.entities.Order;
import shoppingMall.entities.Person;
import shoppingMall.entities.Shop;
import shoppingMall.entities.ShopProduct;
import shoppingMall.enums.Steps;

import java.util.List;
import java.util.Scanner;

import static shoppingMall.Data.*;
import static shoppingMall.Utils.*;
import static shoppingMall.enums.Steps.*;


public class Main {
    static Steps step = AUTHORIZATION;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] arg) {
        dataBaseInit();
        printPersons();

        Person person = null;
        Shop shop = null;
        while (true) {
            askToAuth();
            if (step == LOGIN) {
                person = login();
                step = PROFILE;
            }
            if (person != null) {
                switch (person.getType()) {
                    case CUSTOMER: {
                        switch (step) {
                            case PROFILE: {
                                showPersonProfile(person);
                                customerMenu();
                                break;
                            }
                            case DEPOSIT: {
                                addMoneyToPerson(person);
                                step = PROFILE;
                                break;
                            }
                            case VIEW_SHOP: {
                                shop = selectShop(person);
                                step = VIEW_SHOP_PRODUCTS;
                                break;
                            }
                            case VIEW_SHOP_PRODUCTS: {
                                List<ShopProduct> shopProducts = getProductsByShop(shop.getId());
                                printShopProducts(shopProducts);
                                customerMenu();
                                break;
                            }
                            case SHOPPING: {
                                Order order = createOrder(shop.getId(), person.getId());
                                addProductsToOrder(order);
                                customerMenu();
                                break;
                            }
                            case VIEW_CART: {
                                printCustomerCart(person.getId());
                                customerMenu();
                                break;
                            }
                            case LOGOUT: {
                                person = null;
                                shop = null;
                                step = AUTHORIZATION;
                                break;
                            }
                        }
                        break;
                    }

                    case SELLER: {
                        switch (step) {
                            case PROFILE: {
                                showPersonProfile(person);
                                sellerMenu();
                                break;
                            }
                            case DEPOSIT: {
                                addMoneyToPerson(person);
                                step = PROFILE;
                                break;
                            }
                            case CREATE_SHOP: {
                                createNewShop(person.getId());
                                sellerMenu();
                                break;
                            }
                            case VIEW_SHOP: {
                                shop = selectShop(person);
                                showShopProfile(shop);
                                sellerMenu();
                                break;
                            }
                            case VIEW_SHOP_PRODUCTS: {
                                List<ShopProduct> shopProducts = getProductsByShop(shop.getId());
                                printShopProducts(shopProducts);
                                sellerMenu();
                                break;
                            }
                            case ADD_SHOP_PRODUCTS: {
                                addProductsToShop(shop.getId());
                                step = VIEW_SHOP_PRODUCTS;
                                break;
                            }
                            case CHANGE_SHOP_BALANCE: {
                                changeShopBalance(shop.getId());
                                step = VIEW_SHOP;
                                break;
                            }
                            case CHANGE_SHOP_PRODUCTS: {
                                changeShopProducts(shop.getId());
                                step = VIEW_SHOP_PRODUCTS;
                                break;
                            }
                            case LOGOUT: {
                                person = null;
                                shop = null;
                                step = AUTHORIZATION;
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }

    }

    private static void askToAuth() {
        if (step == AUTHORIZATION) {
            System.out.print("Welcome to the market:\n" +
                    "\t1 - Login\n" +
                    "\t2 - Register new user\n" +
                    "Fill operation number: ");
            if (input.next().equalsIgnoreCase("1")) {
                step = LOGIN;
            } else {
                createNewPerson();
                step = AUTHORIZATION;
            }
        }
    }

    private static void customerMenu() {
        System.out.print("Possible operations:\n" +
                "\t1 - Show Profile\n" +
                "\t2 - Deposit To Account\n" +
                "\t3 - View Shops And Select\n" +
                "\t4 - View Shop Products\n" +
                "\t5 - Add Products To Cart\n" +
                "\t6 - View_Cart\n" +
                "\t7 - Logout\n" +
                "Fill operation number: ");
        switch (input.next()) {
            case "1": {
                step = PROFILE;
                break;
            }
            case "2": {
                step = DEPOSIT;
                break;
            }
            case "3": {
                step = VIEW_SHOP;
                break;
            }
            case "4": {
                step = VIEW_SHOP_PRODUCTS;
                break;
            }
            case "5": {
                step = SHOPPING;
                break;
            }
            case "6": {
                step = VIEW_CART;
                break;
            }
            case "7": {
                step = LOGOUT;
                break;
            }
        }

    }

    private static void sellerMenu() {
        System.out.print("Possible operations:\n" +
                "\t1 - Show Profile\n" +
                "\t2 - Deposit To Account\n" +
                "\t3 - Create Shop\n" +
                "\t4 - View Shop\n" +
                "\t5 - View Shop Products\n" +
                "\t6 - Add Products To Shop\n" +
                "\t7 - Change Shop Balance\n" +
                "\t8 - Change Shop Products\n" +
                "\t9 - Logout\n" +
                "Fill operation number: ");
        switch (input.next()) {
            case "1": {
                step = PROFILE;
                break;
            }
            case "2": {
                step = DEPOSIT;
                break;
            }
            case "3": {
                step = CREATE_SHOP;
                break;
            }
            case "4": {
                step = VIEW_SHOP;
                break;
            }
            case "5": {
                step = VIEW_SHOP_PRODUCTS;
                break;
            }
            case "6": {
                step = ADD_SHOP_PRODUCTS;
                break;
            }
            case "7": {
                step = CHANGE_SHOP_BALANCE;
                break;
            }
            case "8": {
                step = CHANGE_SHOP_PRODUCTS;
                break;
            }
            case "9": {
                step = LOGOUT;
                break;
            }
        }

    }

}
