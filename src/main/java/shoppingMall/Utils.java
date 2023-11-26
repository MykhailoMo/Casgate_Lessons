package shoppingMall;

import shoppingMall.base.DataBaseImpl;

import shoppingMall.base.DbCollectionNames;
import shoppingMall.base.ObjectWithData;
import shoppingMall.entities.*;
import shoppingMall.enums.*;

import java.util.*;

import static shoppingMall.Data.doubleToInt;
import static shoppingMall.base.DbCollectionNames.*;
import static shoppingMall.enums.PersonType.*;


public class Utils {
    public static void createNewPerson() {
        PersonType type;
        String name;
        String password;
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Do you want to register new User? Y/N: ");
            if (!input.next().equalsIgnoreCase("Y")) {
                break;
            }
            System.out.print(String.format("Registration types: \n\t1 - %s\n\t2 - %s\nType number of your registration: ", CUSTOMER, SELLER));
            if (input.next().equals("1")) {
                type = CUSTOMER;
            } else {
                type = SELLER;
            }
            System.out.println(String.format("Type new %s data", type));
            System.out.print("Name: ");
            name = input.next();
            System.out.print("Password: ");
            password = input.next();
            Person person = new Person(UUID.randomUUID(), name, password, type);
            DataBaseImpl.getInstance().saveNewEntity(PERSONS_DB_COLLECTION, person.getId(), person);
        }
    }

    public static void createNewShop(UUID personId) {
        List<ShopType> shopTypes = Arrays.asList(ShopType.values());
        String name;
        ShopType type;
        Shop shop;
        Scanner input = new Scanner(System.in);
        int i;
        while (true) {
            System.out.println("Do you want to register new Shop? Y/N: ");
            if (!input.next().equalsIgnoreCase("Y")) {
                break;
            }
            i = 0;
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
        }
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

    public static void printShops(PersonType personType) {
        List<Shop> shops = new ArrayList(DataBaseImpl.getInstance().getAllEntities(SHOPS_DB_COLLECTION));
        int i = 1;
        if (personType.equals(CUSTOMER)) {
            for (Shop shop : shops) {
                System.out.println(i + ": " + shop.getName() + " \t " + shop.getType().getGoodsName() + "\t" + shop.getType().getDelivery().name());
                i++;
            }
        } else {
            for (Shop shop : shops) {
                System.out.println(i + ": " + shop.toString());
                i++;
            }
        }
    }

    public static void printShops(UUID personId) {
        List<Shop> shops = new ArrayList(DataBaseImpl.getInstance().getAllEntities(SHOPS_DB_COLLECTION));
        int i = 1;
        for (Shop shop : shops) {
            if (shop.getPersonId().equals(personId)) {
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

    public static List<Shop> getShopsBySeller(UUID sellerId, DbCollectionNames dbName) {
        List<Shop> objects = new ArrayList(DataBaseImpl.getInstance().getAllEntities(dbName));
        List<Shop> result = new ArrayList();
        for (Shop object : objects) {
            if (object.getPersonId().equals(sellerId)) {
                result.add(object);
            }
        }
        return result;
    }

    public static List<ShopProduct> getProductsByShop(UUID shopId, DbCollectionNames dbName) {
        List<ShopProduct> objects = new ArrayList(DataBaseImpl.getInstance().getAllEntities(dbName));
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

    public static List<Order> getOpdersByCustomer(UUID customerId, DbCollectionNames dbName) {
        List<Order> objects = new ArrayList(DataBaseImpl.getInstance().getAllEntities(dbName));
        List<Order> result = new ArrayList();
        for (Order object : objects) {
            if (object.getPersonId().equals(customerId)) {
                result.add(object);
            }
        }
        return result;
    }

    public static List<OrderDetail> getCurtByOrder(UUID orderId, DbCollectionNames dbName) {
        List<OrderDetail> objects = new ArrayList(DataBaseImpl.getInstance().getAllEntities(dbName));
        List<OrderDetail> result = new ArrayList();
        for (OrderDetail object : objects) {
            if (object.getOrderId().equals(orderId)) {
                result.add(object);
            }
        }
        return result;
    }

    public static void addProductsToShop(UUID shopId) {
        List<Product> products= new ArrayList(DataBaseImpl.getInstance().getAllEntities(PRODUCTS_DB_COLLECTION));
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
                    shopProduct = new ShopProduct(itemId, shopId, productId,
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

}
