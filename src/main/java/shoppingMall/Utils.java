package shoppingMall;

import shoppingMall.base.DataBaseImpl;

import shoppingMall.base.DbCollectionNames;
import shoppingMall.entities.*;
import shoppingMall.enums.*;

import java.util.*;

import static shoppingMall.Data.doubleToInt;
import static shoppingMall.base.DbCollectionNames.*;
import static shoppingMall.enums.PersonType.*;
import static shoppingMall.enums.Steps.PROFILE;


public class Utils {
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
//        while (true) {
//            System.out.println("Do you want to register new Shop? Y/N: ");
//            if (!input.next().equalsIgnoreCase("Y")) {
//                break;
//            }
//            i = 0;
            System.out.print("---New shop registration---\n");
            for (ShopType shopType : shopTypes) {
                System.out.println(++i + " - " + shopType.getGoodsName());
            }
            System.out.println("Fill the number of shop type from the list above: ");
            type = shopTypes.get(input.nextInt() - 1);
            System.out.print("Fill shop name: ");
            name = input.next();
            shop = new Shop(UUID.randomUUID(), personId, name, type);
            DataBaseImpl.getInstance().saveNewEntity(SHOPS_DB_COLLECTION, shop.getId(), shop);
//        }
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
        while (true) {
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
        }
    }

    public static void printPersons() {
        List<Person> persons = new ArrayList(DataBaseImpl.getInstance().getAllEntities(PERSONS_DB_COLLECTION));
        int i = 1;
        for (Person person : persons) {
            System.out.println(i + ": " + person.toString());
            i++;
        }
    }

    public static void printShops(List<Shop> shops, PersonType personType) {
        int i = 1;
        if (personType.equals(CUSTOMER)) {
            System.out.println("\tName\t\tType\t\tDelivery");
            for (Shop shop : shops) {
                System.out.println(i + ": " + shop.getName() + " \t\t " + shop.getType().getGoodsName() +
                        "\t\t" + shop.getType().getDelivery().name());
                i++;
            }
        } else {
            for (Shop shop : shops) {
                System.out.println(i + ": " + shop.toString());
                i++;
            }
        }
    }

    public static void printProducts() {
        List<Product> products = new ArrayList(DataBaseImpl.getInstance().getAllEntities(PRODUCTS_DB_COLLECTION));
        int i = 1;
        for (Product product : products) {
            System.out.println(i + ": " + product.toString());
            i++;
        }
    }

    public static void printShopProducts(List<ShopProduct> shopProducts) {
        int i = 1;
        for (ShopProduct shopProduct : shopProducts) {
            System.out.println(i + ": " + shopProduct.toString());
            i++;
        }
    }

    public static List<Shop> getShopsList(Person person) {
        List<Shop> shops = new ArrayList(DataBaseImpl.getInstance().getAllEntities(SHOPS_DB_COLLECTION));
        List<Shop> result;
        if (person.getType().equals(SELLER)) {
            result = new ArrayList();
            for (Shop shop : shops) {
                if (shop.getPersonId().equals(person.getId())) {
                    result.add(shop);
                }
            }
        } else {
            result = shops;
        }
        return result;
    }

    public static List<ShopProduct> getProductsByShop(UUID shopId) {
        List<ShopProduct> objects = new ArrayList(DataBaseImpl.getInstance().getAllEntities(SHOP_PRODUCTS_DB_COLLECTION));
        List<ShopProduct> result = new ArrayList();
        for (ShopProduct object : objects) {
            if (object.getShopId().equals(shopId)) {
                result.add(object);
            }
        }
        return result;
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
        List<OrderDetail> orderDetails = getProductsByOrder(orderId);
        for (OrderDetail orderDetail : orderDetails) {
            if (orderDetail.getProductId().equals(productId)) {
                id = orderDetail.getId();
            }
        }
        return id;
    }

    public static UUID isOrderExist(UUID shopId, UUID personId) {
        UUID id = null;
        List<Order> orders = getOrdersByPerson(personId);
        for (Order order : orders) {
            if (order.getShopId().equals(shopId)) {
                id = order.getId();
            }
        }
        return id;
    }

    public static List<Order> getOpdersByCustomer(UUID customerId) {
        List<Order> orders = new ArrayList(DataBaseImpl.getInstance().getAllEntities(ORDERS_DB_COLLECTION));
        List<Order> result = new ArrayList();
        for (Order order : orders) {
            if (order.getPersonId().equals(customerId)) {
                result.add(order);
            }
        }
        return result;
    }

    public static List<OrderDetail> getCurtByOrder(UUID orderId) {
        List<OrderDetail> orderDetails = new ArrayList(DataBaseImpl.getInstance().getAllEntities(ORDER_DETAILS_DB_COLLECTION));
        List<OrderDetail> result = new ArrayList();
        for (OrderDetail orderDetail : orderDetails) {
            if (orderDetail.getOrderId().equals(orderId)) {
                result.add(orderDetail);
            }
        }
        return result;
    }

    public static void addProductsToShop(UUID shopId) {
        List<Product> products = new ArrayList(DataBaseImpl.getInstance().getAllEntities(PRODUCTS_DB_COLLECTION));
        Shop shop = (Shop) DataBaseImpl.getInstance().getEntity(SHOPS_DB_COLLECTION, shopId);
        ShopProduct shopProduct;
        int selectedProduct;
        int quantity;
        UUID productId;
        UUID itemId;
        Scanner input = new Scanner(System.in);

        while (true) {
            printProducts();
            System.out.print("Fill number of product you want to add: ");
            selectedProduct = input.nextInt() - 1;
            System.out.print("Fill quantity of the product: ");
            quantity = input.nextInt();
            if (quantity * products.get(selectedProduct).getBasePrice() < shop.getWalletAmount()) {
                productId = products.get(selectedProduct).getId();
                itemId = isProductExistInShop(shopId, productId);
                if (itemId == null) {
                    itemId = UUID.randomUUID();
                    shopProduct = new ShopProduct(itemId, shopId, productId, products.get(selectedProduct).getName(),
                            doubleToInt(products.get(selectedProduct).getBasePrice() * 1.2), quantity);
                    DataBaseImpl.getInstance().saveNewEntity(SHOP_PRODUCTS_DB_COLLECTION, shopProduct.getId(), shopProduct);
                } else {
                    shopProduct = (ShopProduct) DataBaseImpl.getInstance().getEntity(SHOP_PRODUCTS_DB_COLLECTION, itemId);
                    shopProduct.setQuantity(shopProduct.getQuantity() + quantity);
                    shopProduct.setPrice(doubleToInt(products.get(selectedProduct).getBasePrice() * 1.2));
                    DataBaseImpl.getInstance().updateEntity(SHOP_PRODUCTS_DB_COLLECTION, shopProduct.getId(), shopProduct);
                }
                shop.setWalletAmount(shop.getWalletAmount() - products.get(selectedProduct).getBasePrice() * quantity);
                DataBaseImpl.getInstance().updateEntity(SHOPS_DB_COLLECTION, shopId, shop);
                System.out.println("Product was successfully added to Shop");
            } else {
                System.out.println("You wallet does not have enough money");
            }
            System.out.println("Do you want to add new product? Y/N: ");
            if (!input.next().equalsIgnoreCase("Y")) {
                break;
            }
        }
    }

    public static void addProductsToOrder(Order order) {
        List<ShopProduct> shopProducts = getProductsByShop(order.getShopId());
        Person person = getPersonById(order.getPersonId());
        Shop shop = getShopById(order.getShopId());
        ShopProduct shopProduct;
        int selectedProduct;
        int quantity;
        UUID productId;
        UUID itemId;
        OrderDetail orderDetail;
        int shopPrice;
        int shopQuantity;
        Scanner input = new Scanner(System.in);

        while (true) {
            printShopProducts(shopProducts);
            System.out.print("Fill number of product you want to add: ");
            selectedProduct = input.nextInt() - 1;
            System.out.print("Fill quantity of the product: ");
            quantity = input.nextInt();
            shopProduct = shopProducts.get(selectedProduct);
            shopPrice = shopProduct.getPrice();
            shopQuantity = shopProduct.getQuantity();
            productId = shopProduct.getProductId();
            if (quantity > 0 && quantity <= shopQuantity &&
                    quantity * shopPrice < person.getWalletAmount()) {
                itemId = isProductExistInOrder(order.getId(), productId);
                if (itemId == null) {
                    itemId = UUID.randomUUID();
                    orderDetail = new OrderDetail(itemId, order.getId(), productId, quantity, shopPrice);
                    DataBaseImpl.getInstance().saveNewEntity(ORDER_DETAILS_DB_COLLECTION, orderDetail.getId(), orderDetail);
                } else {
                    orderDetail = (OrderDetail) DataBaseImpl.getInstance().getEntity(ORDER_DETAILS_DB_COLLECTION, itemId);
                    orderDetail.setQuantity(orderDetail.getQuantity() + quantity);
                    DataBaseImpl.getInstance().updateEntity(ORDER_DETAILS_DB_COLLECTION, orderDetail.getId(), orderDetail);
                }
                person.setWalletAmount(person.getWalletAmount() - quantity * shopPrice);
                DataBaseImpl.getInstance().updateEntity(PERSONS_DB_COLLECTION, person.getId(), person);
                shop.setWalletAmount(shop.getWalletAmount() + quantity * shopPrice);
                DataBaseImpl.getInstance().updateEntity(SHOPS_DB_COLLECTION, shop.getId(), shop);
                shopProduct.setQuantity(shopQuantity - quantity);
                DataBaseImpl.getInstance().updateEntity(SHOP_PRODUCTS_DB_COLLECTION, shopProduct.getId(), shopProduct);
                System.out.println("Product was successfully added to your order");
            } else {
                System.out.println("You typed invalid quantity");
            }
            printCustomerCart(person.getId());
            System.out.println("Do you want to add new product? Y/N: ");
            if (!input.next().equalsIgnoreCase("Y")) {
                break;
            }
        }
    }

    public static void addMoneyToPerson(Person person) {
        Scanner input = new Scanner(System.in);
        System.out.print("---Deposit to you account---\n" +
                "Fill amount: ");
        int depositAmount = input.nextInt();
        if (depositAmount > 0) {
            person.setWalletAmount(person.getWalletAmount() + depositAmount);
            DataBaseImpl.getInstance().updateEntity(PERSONS_DB_COLLECTION, person.getId(), person);
        }
    }

    public static void changeShopBalance(UUID shopId) {
        Shop shop = (Shop) DataBaseImpl.getInstance().getEntity(SHOPS_DB_COLLECTION, shopId);
        int shopAmount = shop.getWalletAmount();
        UUID personId = shop.getPersonId();
        Person person = (Person) DataBaseImpl.getInstance().getEntity(PERSONS_DB_COLLECTION, personId);
        int personAmount = person.getWalletAmount();
        String operation;
        int withdrawalAmount;
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("---Operation types---\n" +
                    "1 - withdrawal from shop\n" +
                    "2 - deposit to shop\n" +
                    "Fill the operation number: ");
            operation = input.next();
            System.out.print("Fill the amount: ");
            withdrawalAmount = input.nextInt();
            if(operation.equals("1")) {
                if (withdrawalAmount < shopAmount) {
                    shopAmount = shopAmount - withdrawalAmount;
                    shop.setWalletAmount(shopAmount );
                    DataBaseImpl.getInstance().updateEntity(SHOPS_DB_COLLECTION, shopId, shop);
                    personAmount = personAmount + withdrawalAmount;
                    person.setWalletAmount(personAmount);
                    DataBaseImpl.getInstance().updateEntity(PERSONS_DB_COLLECTION, personId, person);
                } else {
                    System.out.printf("You have insufficient balance. You wallet has only %d UAH%n", shopAmount);
                }
            } else {
                if (withdrawalAmount < personAmount) {
                    personAmount = personAmount - withdrawalAmount;
                    person.setWalletAmount(personAmount);
                    DataBaseImpl.getInstance().updateEntity(PERSONS_DB_COLLECTION, personId, person);
                    shopAmount = shopAmount + withdrawalAmount;
                    shop.setWalletAmount(shopAmount);
                    DataBaseImpl.getInstance().updateEntity(SHOPS_DB_COLLECTION, shopId, shop);
                } else {
                    System.out.printf("You have insufficient balance. You wallet has only %d UAH%n", personAmount);
                }
            }
            System.out.printf("Your current balance: \n" +
                    "\tshop - %d UAH\n" +
                    "\tpersonal - %d%n", shopAmount, personAmount);
            System.out.println("Do you want to change balance? Y/N: ");
            if (!input.next().equalsIgnoreCase("Y")) {
                break;
            }
        }
    }

    public static void changeShopProducts(UUID shopId) {
        ShopProduct shopProduct;
        Scanner input = new Scanner(System.in);
        int productNumberInList;
        int operation;
        int value;

        while (true) {
            List<ShopProduct> shopProducts = getProductsByShop(shopId);
            printShopProducts(shopProducts);
            System.out.println("---Changing product operation---\n" +
                    "Fill the product number: ");
            productNumberInList = input.nextInt() - 1;
            if (productNumberInList < shopProducts.size()) {
                shopProduct = shopProducts.get(productNumberInList);
                System.out.print("---Operation types---\n" +
                        "1 - product change price\n" +
                        "2 - product disposal\n" +
                        "Fill the operation number: ");
                operation = input.nextInt();
                if (operation == 1) {
                    System.out.print("Type new price: ");
                    value = input.nextInt();
                    if (value > 0) {
                        shopProduct.setPrice(value);
                        DataBaseImpl.getInstance().updateEntity(SHOP_PRODUCTS_DB_COLLECTION, shopProduct.getId(), shopProduct);
                    } else {
                        System.out.println("You filled invalid price");
                    }
                } else {
                    System.out.print("Type disposal product quantity: ");
                    value = input.nextInt();
                    if (value > 0 && (shopProduct.getQuantity() - value) >= 0) {
                        shopProduct.setQuantity(shopProduct.getQuantity() - value);
                        DataBaseImpl.getInstance().updateEntity(SHOP_PRODUCTS_DB_COLLECTION, shopProduct.getId(), shopProduct);
                    } else {
                        System.out.println("You filled invalid quantity");
                    }
                }
            } else {
                System.out.println("You filled invalid number");
            }

            System.out.println("Do you want to change other product? Y/N: ");
            if (!input.next().equalsIgnoreCase("Y")) {
                break;
            }
        }
    }

    public static List<Order> getOrdersByPerson(UUID personId) {
        List<Order> orders = new ArrayList(DataBaseImpl.getInstance().getAllEntities(ORDERS_DB_COLLECTION));
        List<Order> ordersByPerson = new ArrayList();
        for (Order order : orders) {
            if (order.getPersonId().equals(personId)) {
                ordersByPerson.add(order);
            }
        }
        return ordersByPerson;
    }

    public static List<OrderDetail> getProductsByOrder(UUID orderId) {
        List<OrderDetail> orderDetails = new ArrayList(DataBaseImpl.getInstance().getAllEntities(ORDER_DETAILS_DB_COLLECTION));
        List<OrderDetail> currentOrderDetails = new ArrayList();
        for (OrderDetail orderDetail : orderDetails) {
            if (orderDetail.getOrderId().equals(orderId)) {
                currentOrderDetails.add(orderDetail);
            }
        }
        return currentOrderDetails;
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

    public static void printCustomerCart(UUID personId) {
        List<Order> orders = getOrdersByPerson(personId);
        List<OrderDetail> orderDetails;
        Shop shop;
        String productName;
        int productPrice;
        int productQuantity;
        int productAmount;
        for (Order order : orders) {
            orderDetails = getProductsByOrder(order.getId());
            shop = (Shop) DataBaseImpl.getInstance().getEntity(SHOPS_DB_COLLECTION, order.getShopId());
            System.out.printf("\nOrder: %s\n" +
                    "\tShop: %s\n" +
                    "\tDeliveryDate: %s%n",order.getId(), shop.getName(), order.getDeliveryDate());
            System.out.println("----------------------------------------------------");
            System.out.println("\t\tName\t\tQuantity\t\tPrice\t\tAmount");
            for (OrderDetail orderDetail : orderDetails) {
                productName = getProductById(orderDetail.getProductId()).getName();
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

    public static Shop selectShop(Person person) {
        List<Shop> shops = getShopsList(person);
        Shop shop = new Shop();
        printShops(shops, person.getType());
        System.out.print("Type shop number: ");
        Scanner input = new Scanner(System.in);
        int selectedShop = input.nextInt();
        if (selectedShop > 0 && selectedShop <= shops.size() + 1) {
            shop = shops.get(selectedShop - 1);
        } else {
            System.out.println("--!!!--You typed invalid number--!!!--");
        }
        return shop;
    }

    public static void showShopProfile(Shop shop) {
            System.out.printf("Shop profile data:\n" +
                    "\tName: %s\n" +
                    "\tShopType: %s\n" +
                    "\tBalanceAmount: %d%n", shop.getName(), shop.getType(), shop.getWalletAmount());
    }

    public static void showPersonProfile(Person person) {
        System.out.printf("Shop profile data:\n" +
                "\tNane: %s\n" +
                "\tShopType: %s\n" +
                "\tBalanceAmount: %d%n", person.getName(), person.getType().name(), person.getWalletAmount());
    }

}
