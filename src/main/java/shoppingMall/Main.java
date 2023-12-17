package shoppingMall;

import shoppingMall.entities.Order;
import shoppingMall.entities.Person;
import shoppingMall.entities.Shop;
import shoppingMall.entities.ShopProduct;
import shoppingMall.enums.Steps;
import shoppingMall.utils.ChangeShopProducts;
import shoppingMall.utils.DataOutput;
import shoppingMall.utils.GetData;

import java.util.List;
import java.util.Scanner;

import static shoppingMall.utils.AddProducts.*;
import static shoppingMall.utils.InitialDataSetup.*;
import static shoppingMall.utils.MainMethods.*;
import static shoppingMall.enums.Steps.*;


public class Main {
    static Steps step = AUTHORIZATION;
    static Scanner input = new Scanner(System.in);
    static Shop shop = null;

    public static void main(String[] arg) {
        dataBaseInit();
        DataOutput.printPersons();
        Person person = null;

        while (true) {
            askToAuth();
            if (step == LOGIN) {
                person = login();
                if (person != null) {
                    step = PROFILE;
                } else {
                    step = AUTHORIZATION;
                }
            }
            if (person != null) {
                switch (person.getType()) {
                    case CUSTOMER: {
                        switch (step) {
                            case PROFILE: {
                                DataOutput.showProfile(person);
                                step = MENU;
                                break;
                            }
                            case DEPOSIT: {
                                addMoneyToPerson(person);
                                step = PROFILE;
                                break;
                            }
                            case VIEW_SHOP: {
                                shop = selectShop(person);
                                if (shop != null) {
                                    step = VIEW_SHOP_PRODUCTS;
                                } else {
                                    step = MENU;
                                }
                                break;
                            }
                            case VIEW_SHOP_PRODUCTS: {
                                List<ShopProduct> shopProducts = GetData.getProductsByShop(shop.getId());
                                DataOutput.printShopProducts(shopProducts);
                                step = MENU;
                                break;
                            }
                            case SHOPPING: {
                                Order order = createOrder(shop.getId(), person.getId());
                                addProductToOrder(order);
                                step = MENU;
                                break;
                            }
                            case VIEW_CART: {
                                DataOutput.printCustomerCart(person.getId());
                                step = MENU;
                                break;
                            }
                            case LOGOUT: {
                                person = null;
                                shop = null;
                                step = AUTHORIZATION;
                                break;
                            }
                            case MENU: {
                                customerMenu();
                                break;
                            }
                        }
                        break;
                    }

                    case SELLER: {
                        switch (step) {
                            case PROFILE: {
                                DataOutput.showProfile(person);
                                step = MENU;
                                break;
                            }
                            case DEPOSIT: {
                                addMoneyToPerson(person);
                                step = PROFILE;
                                break;
                            }
                            case CREATE_SHOP: {
                                createNewShop(person.getId());
                                step = MENU;
                                break;
                            }
                            case VIEW_SHOP: {
                                shop = selectShop(person);
                                if (shop != null) {
                                    DataOutput.showProfile(shop);
                                }
                                step = MENU;
                                break;
                            }
                            case VIEW_SHOP_PRODUCTS: {
                                List<ShopProduct> shopProducts = GetData.getProductsByShop(shop.getId());
                                DataOutput.printShopProducts(shopProducts);
                                step = MENU;
                                break;
                            }
                            case ADD_SHOP_PRODUCTS: {
                                addProductsToShop(shop.getId());
                                step = VIEW_SHOP_PRODUCTS;
                                break;
                            }
                            case CHANGE_SHOP_BALANCE: {
                                changeShopBalance(shop.getId());
                                DataOutput.showProfile(shop);
                                step = MENU;
                                break;
                            }
                            case CHANGE_SHOP_PRODUCTS: {
                                ChangeShopProducts.changeShopProducts(shop.getId());
                                step = VIEW_SHOP_PRODUCTS;
                                break;
                            }
                            case LOGOUT: {
                                person = null;
                                shop = null;
                                step = AUTHORIZATION;
                                break;
                            }
                            case MENU: {
                                sellerMenu();
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
            System.out.print("---------------------------------------\n" +
                    "Welcome to the market:\n" +
                    "\t1 - Login\n" +
                    "\t2 - Register new user\n" +
                    "Fill operation number: ");
            switch (input.next()) {
                case "1": {
                    step = LOGIN;
                    break;
                }
                case "2": {
                    createNewPerson();
                    step = AUTHORIZATION;
                    break;
                }
                default: {
                    System.out.println("You typed wrong operation, try again");
                    break;
                }
            }
        }
    }

    private static void customerMenu() {
        System.out.print("---------------------------------------\n" +
                "Possible operations:\n" +
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
                if (isShopSelected(shop)) {
                    step = VIEW_SHOP_PRODUCTS;
                }
                break;
            }
            case "5": {
                if (isShopSelected(shop)) {
                    step = SHOPPING;
                }
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
            default: {
                System.out.println("You typed wrong operation, try again");
                break;
            }
        }

    }


    private static void sellerMenu() {
        System.out.print("---------------------------------------\n" +
                "Possible operations:\n" +
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
                if (isShopSelected(shop)) {
                    step = VIEW_SHOP_PRODUCTS;
                }
                break;
            }
            case "6": {
                if (isShopSelected(shop)) {
                    step = ADD_SHOP_PRODUCTS;
                }
                break;
            }
            case "7": {
                if (isShopSelected(shop)) {
                    step = CHANGE_SHOP_BALANCE;
                }
                break;
            }
            case "8": {
                if (isShopSelected(shop)) {
                    step = CHANGE_SHOP_PRODUCTS;
                }
                break;
            }
            case "9": {
                step = LOGOUT;
                break;
            }
            default: {
                System.out.println("You typed wrong operation, try again");
                break;
            }
        }

    }

}
