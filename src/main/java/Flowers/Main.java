package Flowers;

import java.util.Scanner;

public class Main {
    static final int EXIT = 0;
    static final int START = 1;
    static final int SELECT_FLOWER = 2;
    static final int SELECT_FLOWERS_QUANTITY = 3;
    static final int CUSTOMER_MONEY_ANALISE = 4;
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
    static int cartPosition = 0;
    static int flowerShopQuantyty;
    static int orderPrice;
    static Cart[] shopCart = new Cart[20];
    static boolean isNextStep = true;

    public static void main(String[] args) {
        while (step != EXIT) {
            askToShopping();
            selectFlower();
            selectFlowerQuantity();
            customerMoneyAnalise();
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
                step = CUSTOMER_MONEY_ANALISE;
            } else {
                System.out.println("\nYou typed wrong quantity, please try again from begin");
                step = START;
            }
        }
    }

    private static void customerMoneyAnalise() {
        if (step == CUSTOMER_MONEY_ANALISE) {
            if (cartMoneyLeft >= 0) {
                cartPosition++;
                shopCart[cartPosition] = new Cart(flowerType, flowerQuantity);
                FLOWERS_IN_SHOP[flowerType].setQuantity(flowerShopQuantyty - flowerQuantity);
                Cart.money = cartMoneyLeft;
                System.out.println("\nYour bought: " + FLOWERS_IN_SHOP[shopCart[cartPosition].getFlowerType()].getName()
                        + " " + shopCart[cartPosition].getQuantity() + "pcs. for " + orderPrice + "UAH");
            } else {
                System.out.println("\nYou need " + orderPrice + "UAH, but you have only " + Cart.money + "UAH");
            }
            step = START;
        }
    }

}