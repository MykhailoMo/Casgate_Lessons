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

    public static void fillShopsWithProducts() {
        List<Shop> shops = new ArrayList(DataBaseImpl.getInstance().getAllEntities(SHOPS_DB_COLLECTION));
        List<Products> products = Arrays.asList(Products.values());
        ShopProduct shopProduct;
        ShopType shopType;
        for (Shop shop : shops) {
            shopType = shop.getType();
            for (Products product : products) {
                if (product.getShopType().equals(shopType)) {
                    shopProduct = new ShopProduct(UUID.randomUUID(), shop.getId(), product.getProduct().getId(),
                            doubleToInt(product.getProduct().getBasePrice() * 1.2), random());
                    DataBaseImpl.getInstance().saveNewEntity(SHOP_PRODUCTS_DB_COLLECTION, shopProduct.getId(), shopProduct);
                }
            }
        }
    }

//    public static void fillShops(UUID personId, String name, ShopType type) {
//        for (int i = 0; i < 5; i++) {
//            String name = personNames.get(i);
//            Person person = new Person(UUID.randomUUID(), name, name + "1", type);
//            DataBaseImpl.INSTANCE.saveNewEntity(PERSONS_DB_COLLECTION, person.getId(), person);
//        }
//    }

//    public static void fillGroups() {
//        for (int i = 0; i < 3; i++) {
//            Group group = new Group(UUID.randomUUID(), groupName.get(i));
//            DataBaseImpl.INSTANCE.saveNewEntity(GROUPS_DB_COLLECTION, group.getId(), group);
//        }
//    }
//
//    public static void fillTasks() {
//        for (int i = 0; i < 10; i++) {
//            String taskName = "TS-00" + i;
//            Task task = new Task(UUID.randomUUID(), taskName, random() + 1);
//            DataBaseImpl.INSTANCE.saveNewEntity(TASKS_DB_COLLECTION, task.getId(), task);
//        }
//    }
//
//    public static void printStudents() {
//        List <Student> students = new ArrayList(DataBaseImpl.INSTANCE.getAllEntities(STUDENTS_DB_COLLECTION));
//        int i = 1;
//        for (Student student:students) {
//            System.out.println(i + ": " + student.toString());
//            i++;
//        }
//    }
//
//    public static void printTasks() {
//        List <Task> tasks = new ArrayList(DataBaseImpl.INSTANCE.getAllEntities(TASKS_DB_COLLECTION));
//        int i = 1;
//        int maxMark = 0;
//        System.out.println("\nTasks---------------------");
//        for (Task task: tasks) {
//            System.out.println(i + ": " + task.toString());
//            i++;
//            maxMark = maxMark + task.getMark();
//        }
////        System.out.println("MaxResult = " + maxMark + ", 75% = " + (maxMark * 75 / 100) + ", 50% = " + (maxMark * 50 / 100) + ", 25% = " + (maxMark * 25 / 100));
//    }
//    public static void printResults() {
//        List <Result> results = new ArrayList(DataBaseImpl.INSTANCE.getAllEntities(RESULTS_DB_COLLECTION));
//        int i = 1;
//        System.out.println("----*************************");
//        for (Result result:results) {
//            System.out.println(i + ": " + result.toString());
//            i++;
//        }
//    }

    static int random() {
        double data = Math.random() * 9;
        return Math.toIntExact(Math.round(data));
    }

    public static int doubleToInt(double data) {
        return Math.toIntExact(Math.round(data));
    }
}
