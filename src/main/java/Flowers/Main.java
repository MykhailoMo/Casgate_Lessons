package Flowers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static final int EXIT = 0;
    static final int START = 1;
    static final int SELECT_FLOWER = 2;
    static final int SELECT_FLOWERS_QUANTITY = 3;
    static final int CUSTOMER_ORDER_ANALISE = 4;
    static final Flowers[] FLOWERS_IN_SHOP = new Flowers[]{
            new Flowers("Rose lux", 100, 7),
            new Flowers("Rose standard", 70, 5),
            new Flowers("Rose economy", 50, 11),
            new Flowers("Tulpan", 40, 20, 15),
            new Flowers("Romashka", 30, 15, 19),
            new Flowers("Chrisantema", 50, 25, 50)
    };
    static int step = START;
    static int flowerType;
    static int flowerQuantity;
    static int cartMoneyLeft;
    static int flowerShopQuantyty;
    static int orderPrice;
    static List<Cart> shopCart = new ArrayList<>();
    static boolean isNextStep = true;

    public static void main(String[] args) {
        while (step != EXIT) {
            askToShopping();
            selectFlower();
            selectFlowerQuantity();
            customerOrderAnalise();
        }
    }

    private static void askToShopping() {
        if (step == START) {
            Scanner input = new Scanner(System.in);
            System.out.println("\nDo you want to by flowers? Type: Y/N");
            if (input.next().equalsIgnoreCase("Y")) {
                step = SELECT_FLOWER;
            } else {
                step = EXIT;
                isNextStep = false;
            }
        }
    }

    private static void selectFlower() {
        if (step == SELECT_FLOWER) {
            Scanner input = new Scanner(System.in);
            Flowers.flowersInShop(FLOWERS_IN_SHOP);
            System.out.print("\nPlease fill the number of the flower: ");
            flowerType = Integer.parseInt(input.next()) - 1;
            if (flowerType >= 0 && flowerType < FLOWERS_IN_SHOP.length) {
                step = SELECT_FLOWERS_QUANTITY;
            } else {
                System.out.println("\nYou typed wrong number, please try again from begin");
                step = START;
            }
        }
    }

    private static void selectFlowerQuantity() {
        if (step == SELECT_FLOWERS_QUANTITY) {
            Scanner input = new Scanner(System.in);
            flowerShopQuantyty = FLOWERS_IN_SHOP[flowerType].getQuantity();
            System.out.print("\nNow we have: " + flowerShopQuantyty + "pcs. " + "Please fill the quantity of the flower: ");
            flowerQuantity = Integer.parseInt(input.next());
            orderPrice = flowerQuantity * FLOWERS_IN_SHOP[flowerType].getPrice();
            cartMoneyLeft = Cart.money - orderPrice;
            if (flowerQuantity <= flowerShopQuantyty && flowerQuantity > 0) {
                step = CUSTOMER_ORDER_ANALISE;
            } else {
                System.out.println("\nYou typed wrong quantity, please try again from begin");
                step = START;
            }
        }
    }

    private static void customerOrderAnalise() {
        if (step == CUSTOMER_ORDER_ANALISE) {
            if (cartMoneyLeft >= 0) {
                customerCartUpdate(flowerType, flowerQuantity);
                FLOWERS_IN_SHOP[flowerType].setQuantity(flowerShopQuantyty - flowerQuantity);
                Cart.money = cartMoneyLeft;
            } else {
                System.out.println("\nYou need " + orderPrice + "UAH, but you have only " + Cart.money + "UAH");
            }
            customerCartShow();
            step = START;
        }
    }

    private static void customerCartUpdate(int flowerType, int flowerQuantity) {
        boolean isUpdated = false;
        for (Cart cart: shopCart) {
            if (cart.getFlowerType().equals(flowerType)) {
                isUpdated = true;
                cart.setQuantity(cart.getQuantity() + flowerQuantity);
                break;
            }
        }
        if (!isUpdated) {
            shopCart.add(new Cart(flowerType, flowerQuantity));
        }
    }

    private static void customerCartShow() {
        int quantity;
        int price;
        int orderSum = 0;
        System.out.println("\n=====================================");
        System.out.println("---------Your bought flowers---------");
        for (Cart cart: shopCart) {
            quantity = cart.getQuantity();
            price = FLOWERS_IN_SHOP[cart.getFlowerType()].getPrice() * quantity;
            orderSum = orderSum + price;
            System.out.println(FLOWERS_IN_SHOP[cart.getFlowerType()].getName()
                        + " " + quantity + "pcs. for "
                        + price + "UAH");
        }
        System.out.println("-------------------------------------");
        System.out.println("Order summ: " + orderSum + "UAH");
        System.out.println("=====================================");
    }

}