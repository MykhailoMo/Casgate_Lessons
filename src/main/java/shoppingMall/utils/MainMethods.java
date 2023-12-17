package shoppingMall.utils;

import shoppingMall.base.DataBaseImpl;

import shoppingMall.entities.*;
import shoppingMall.enums.*;

import java.util.*;
import java.util.stream.Collectors;

import static shoppingMall.utils.DataOutput.printWrongInput;
import static shoppingMall.base.DbCollectionNames.*;
import static shoppingMall.enums.PersonType.*;


public class MainMethods {
    public static void createNewPerson() {
        PersonType type;
        String name;
        String password;
        Scanner input = new Scanner(System.in);
        System.out.printf("Registration types: \n\t1 - %s\n\t2 - %s\nType number of your registration: ", CUSTOMER, SELLER);
        if (input.next().equals("1")) {
            type = CUSTOMER;
        } else {
            type = SELLER;
        }
        System.out.printf("Type new %s data%n", type);
        System.out.print("Name: ");
        name = input.next();
        System.out.print("Password: ");
        password = input.next();
        Person person = new Person(UUID.randomUUID(), name, password, type);
        DataBaseImpl.getInstance().saveNewEntity(PERSONS_DB_COLLECTION, person.getId(), person);
    }

    public static void createNewShop(UUID personId) {
        List<ShopType> shopTypes = Arrays.asList(ShopType.values());
        String name;
        ShopType type;
        Shop shop;
        Scanner input = new Scanner(System.in);
        int i = 0;
        System.out.print("---New shop registration---\n");
        for (ShopType shopType : shopTypes) {
            System.out.println(++i + " - " + shopType.getGoodsName());
        }
        System.out.println("Fill the number of shop type from the list above: ");
        int value = ValidationInputData.checkInputAsPositiveInt(input) - 1;
        if (value >= 0 && value <= shopTypes.size()) {
            type = shopTypes.get(value);
            System.out.print("Fill shop name: ");
            name = input.next();
            shop = new Shop(UUID.randomUUID(), personId, name, type);
            DataBaseImpl.getInstance().saveNewEntity(SHOPS_DB_COLLECTION, shop.getId(), shop);
        } else {
            printWrongInput();
        }

    }

    public static Order createOrder(UUID shopId, UUID personId) {
        UUID orderId = isOrderExist(shopId, personId);
        Order order;
        if (orderId == null) {
            order = new Order(UUID.randomUUID(), shopId, personId);
            DataBaseImpl.getInstance().saveNewEntity(ORDERS_DB_COLLECTION, order.getId(), order);
        } else {
            order = (Order) DataBaseImpl.getInstance().getEntity(ORDERS_DB_COLLECTION, orderId);
        }
        return order;
    }

    public static Person login() {
        List<Person> persons = new ArrayList(DataBaseImpl.getInstance().getAllEntities(PERSONS_DB_COLLECTION));
        Scanner input = new Scanner(System.in);
        String name;
        String password;
        System.out.print("---Authorization---\nLogin: ");
        name = input.next();
        System.out.print("Password: ");
        password = input.next();
        for (Person person : persons) {
            if (person.getName().equals(name) && person.getPassword().equals(password)) {
                System.out.println("Welcome to Market");
                return person;
            }
        }
        System.out.println("User is not found. Try again");
        return null;
    }

    public static UUID isProductExistInShop(UUID shopId, UUID productId) {
        UUID id = null;
        List<ShopProduct> shopProducts = new ArrayList(DataBaseImpl.getInstance().getAllEntities(SHOP_PRODUCTS_DB_COLLECTION));
        for (ShopProduct shopProduct : shopProducts) {
            if (shopProduct.getShopId().equals(shopId) && shopProduct.getProductId().equals(productId)) {
                id = shopProduct.getId();
            }
        }
        return id;
    }

    public static UUID isProductExistInOrder(UUID orderId, UUID productId) {
        UUID id = null;
        List<OrderDetail> orderDetails = GetData.getProductsByOrder(orderId);
        for (OrderDetail orderDetail : orderDetails) {
            if (orderDetail.getProductId().equals(productId)) {
                id = orderDetail.getId();
            }
        }
        return id;
    }

    public static UUID isOrderExist(UUID shopId, UUID personId) {
        UUID id = null;
        List<Order> orders = GetData.getOrdersByPerson(personId);
        for (Order order : orders) {
            if (order.getShopId().equals(shopId)) {
                id = order.getId();
            }
        }
        return id;
    }

    public static boolean isShopSelected(Shop shop) {
        boolean result = (shop != null);
        if (!result) {
            System.out.println("You need to select shop before operation");
        }
        return result;
    }

    public static void addMoneyToPerson(Person person) {
        Scanner input = new Scanner(System.in);
        System.out.print("---Deposit to you account---\n" +
                "Fill amount: ");
        int depositAmount;
        depositAmount = ValidationInputData.checkInputAsNaturalInt(input);
        if (depositAmount > 0) {
            changePersonWalletAmount(person, depositAmount, false);
        } else {
            printWrongInput();
        }
    }

    public static void changeShopBalance(UUID shopId) {
        Shop shop = (Shop) DataBaseImpl.getInstance().getEntity(SHOPS_DB_COLLECTION, shopId);
        int shopAmount = shop.getWalletAmount();
        UUID personId = shop.getPersonId();
        Person person = (Person) DataBaseImpl.getInstance().getEntity(PERSONS_DB_COLLECTION, personId);
        int personAmount = person.getWalletAmount();
        String operation;
        int amount;
        Scanner input = new Scanner(System.in);

        System.out.print("---Operation types---\n" +
                "1 - withdrawal from shop\n" +
                "2 - deposit to shop\n" +
                "Fill the operation number: ");
        operation = input.next();
        System.out.print("Fill the amount: ");
        amount = ValidationInputData.checkInputAsNaturalInt(input);
        if (amount == 0) {
            printWrongInput();
        } else {
            if (operation.equals("1")) {
                if (amount <= shopAmount) {
                    changeShopWalletAmount(shop, amount, true);
                    changePersonWalletAmount(person, amount, false);
                } else {
                    System.out.printf("You have insufficient balance. Your shop wallet has only %d UAH%n", shopAmount);
                }
            } else {
                if (amount <= personAmount) {
                    changeShopWalletAmount(shop, amount, false);
                    changePersonWalletAmount(person, amount, true);
                } else {
                    System.out.printf("You have insufficient balance. Your person wallet has only %d UAH%n", personAmount);
                }
            }
            System.out.printf("Your current balances: \n" +
                    "\tshop - %d UAH\n" +
                    "\tpersonal - %d%n", shopAmount, personAmount);
        }
    }

    public static Shop selectShop(Person person) {
        List<Shop> shops = GetData.getShopsList(person);
        Shop shop = null;
        if (shops.size() != 0) {
            DataOutput.printShops(shops, person.getType());
            System.out.print("Type shop number: ");
            Scanner input = new Scanner(System.in);
            int selectedShop;
            selectedShop = ValidationInputData.checkInputAsPositiveInt(input) - 1;
            if (selectedShop >= 0 && selectedShop <= shops.size()) {
                shop = shops.get(selectedShop);
            } else {
                printWrongInput();
            }
        } else {
            System.out.println("You do not have shops yet");
        }
        return shop;
    }

    public static void changePersonWalletAmount(Person person, int amount, boolean isNegative) {
        if (isNegative) {
            person.setWalletAmount(person.getWalletAmount() - amount);
        } else {
            person.setWalletAmount(person.getWalletAmount() + amount);
        }
        DataBaseImpl.getInstance().updateEntity(PERSONS_DB_COLLECTION, person.getId(), person);
    }

    public static void changeShopWalletAmount(Shop shop, int amount, boolean isNegative) {
        if (isNegative) {
            shop.setWalletAmount(shop.getWalletAmount() - amount);
        } else {
            shop.setWalletAmount(shop.getWalletAmount() + amount);
        }
        DataBaseImpl.getInstance().updateEntity(SHOPS_DB_COLLECTION, shop.getId(), shop);
    }

    public static void findProduct(Shop shop, String productName) {
            List<ShopProduct> shopProducts = GetData.getProductsByShop(shop.getId());
            shopProducts = shopProducts.stream()
                    .filter(s -> s.getName().toUpperCase().contains(productName.toUpperCase()))
                    .collect(Collectors.toList());
            DataOutput.printShopProducts(shopProducts);
    }
}
