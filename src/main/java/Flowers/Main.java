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
    static int flowerType;
    static int flowerQuantity;
    static int cartMoneyLeft;
    static int cartPosition = 0;
    static int flowerShopQuantyty;
    static int orderPrice;
    static Cart[] shopCart = new Cart[20];

    public static void main(String[] args) {
        int step = START;
        while (step != EXIT) {
            switch (step) {
                case START: {
                    step = askToShopping();
                    break;
                }
                case SELECT_FLOWER: {
                    step = selectFlower();
                    break;
                }
                case SELECT_FLOWERS_QUANTITY: {
                    step = selectFlowerQuantity();
                    break;
                }
                case CUSTOMER_MONEY_ANALISE: {
                    step = customerMoneyAnalise();
                    break;
                }
            }
        }
    }

    private static int askToShopping() {
        int nextStep = EXIT;
        Scanner input = new Scanner(System.in);
        System.out.println("Do you want to by flowers? Type: Y/N");
        if (input.next().equalsIgnoreCase("Y")) {
            nextStep = SELECT_FLOWER;
        }
        return nextStep;
    }

    private static int selectFlower() {
        int nextStep = START;
        Scanner input = new Scanner(System.in);
        Flowers.flowersInShop(FLOWERS_IN_SHOP);
        System.out.print("Please fill the number of the flower: ");
        flowerType = Integer.parseInt(input.next()) - 1;
        if (flowerType >= 0 && flowerType < FLOWERS_IN_SHOP.length) {
            nextStep = SELECT_FLOWERS_QUANTITY;
        } else {
            System.out.println("You typed wrong number, please try again from begin");
        }
        return nextStep;
    }

    private static int selectFlowerQuantity() {
        int nextStep = START;
        Scanner input = new Scanner(System.in);
        flowerShopQuantyty = FLOWERS_IN_SHOP[flowerType].getQuantity();
        System.out.print("Now we have: " + flowerShopQuantyty + "pcs. " + "Please fill the quantity of the flower: ");
        flowerQuantity = Integer.parseInt(input.next());
        orderPrice = flowerQuantity * FLOWERS_IN_SHOP[flowerType].getPrice();
        cartMoneyLeft = Cart.money - orderPrice;
        if (flowerQuantity <= flowerShopQuantyty) {
            nextStep = CUSTOMER_MONEY_ANALISE;
        } else {
            System.out.println("You typed wrong quantity, please try again from begin");
        }
        return nextStep;
    }

    private static int customerMoneyAnalise() {
        if (cartMoneyLeft >= 0) {
            cartPosition++;
            shopCart[cartPosition] = new Cart(flowerType, flowerQuantity);
            FLOWERS_IN_SHOP[flowerType].setQuantity(flowerShopQuantyty - flowerQuantity);
            Cart.money = cartMoneyLeft;
            System.out.println("Your bought: " + FLOWERS_IN_SHOP[shopCart[cartPosition].getFlowerType()].getName()
                    + " " + shopCart[cartPosition].getQuantity() + "pcs. for " + orderPrice + "UAH");
        } else {
            System.out.println("You need " + orderPrice + "UAH, but you have only " + Cart.money + "UAH");
        }
        return START;
    }

}