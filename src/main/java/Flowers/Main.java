package Flowers;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Flowers[] flowersInShop = new Flowers[]{
//                new Flowers(),
                new Flowers("Rose lux", 100, 7),
                new Flowers("Rose standard", 70, 5),
                new Flowers("Rose economy", 50, 11),
                new Flowers("Tulpan", 40, 20, 15),
                new Flowers("Romashka", 30, 15, 19),
                new Flowers("Chrisantema", 50, 25, 50)
        };
        Cart[] shopCart = new Cart[20];
        Scanner input = new Scanner(System.in);
        String answerString;
        int answerInteger;
        int flowerType;
        int flowerQuantity;
        int flowerShopQuantyty;
        int cartPosition = 0;
        int cartMoneyLeft;
        int orderPrice;
        boolean shopping = true;
        while (shopping) {
            System.out.println("Do you want to by flowers? Type: Y/N");
            answerString = input.next();
            if (answerString.equalsIgnoreCase("Y")) {
                Flowers.flowersInShop(flowersInShop);
                System.out.print("Please fill the number of the flower: ");
                flowerType = Integer.parseInt(input.next()) - 1;
                if (flowerType >= 0 && flowerType < flowersInShop.length) {
                    flowerShopQuantyty = flowersInShop[flowerType].getQuantity();
                    System.out.print("Now we have: " + flowerShopQuantyty + "pcs. " + "Please fill the quantity of the flower: ");
                    flowerQuantity = Integer.parseInt(input.next());
                    orderPrice = flowerQuantity * flowersInShop[flowerType].getPrice();
                    cartMoneyLeft = Cart.money - orderPrice;
                    if (flowerQuantity <= flowerShopQuantyty) {
                        if (cartMoneyLeft >= 0) {
                            cartPosition++;
                            shopCart[cartPosition] = new Cart(flowerType, flowerQuantity);
                            flowersInShop[flowerType].setQuantity(flowerShopQuantyty - flowerQuantity);
                            Cart.money = cartMoneyLeft;
                            System.out.println("Your bought: " + flowersInShop[shopCart[cartPosition].getFlowerType()].getName()
                                    + " " + shopCart[cartPosition].getQuantity() + "pcs. for " + orderPrice + "UAH");
                        } else {
                            System.out.println("You need " + orderPrice + "UAH, but you have only " + Cart.money + "UAH");
                        }
                    } else {
                        System.out.println("You typed wrong quantity, please try again from begin");
                    }
                } else {
                    System.out.println("You typed wrong number, please try again from begin");
                }
            } else {
                shopping = false;
            }
        }
    }
}
