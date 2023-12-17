package shoppingMall.utils;

import shoppingMall.base.DataBaseImpl;
import shoppingMall.entities.ShopProduct;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static shoppingMall.base.DbCollectionNames.SHOP_PRODUCTS_DB_COLLECTION;
import static shoppingMall.utils.DataOutput.printWrongInput;

public class ChangeShopProducts {
    static Scanner input = new Scanner(System.in);
    public static void changeShopProducts(UUID shopId) {
        ShopProduct shopProduct;
        int productNumberInList;
        int operation;

        List<ShopProduct> shopProducts = GetData.getProductsByShop(shopId);
        DataOutput.printShopProducts(shopProducts);
        System.out.println("---Changing product operation---\n" +
                "Fill the product number: ");

        productNumberInList = ValidationInputData.checkInputAsPositiveInt(input) - 1;
        if (productNumberInList < 0 || productNumberInList > shopProducts.size()) {
            printWrongInput();
        } else {
            shopProduct = shopProducts.get(productNumberInList);
            System.out.print("---Operation types---\n" +
                    "1 - product change price\n" +
                    "2 - product disposal\n" +
                    "Fill the operation number: ");
            operation = ValidationInputData.checkInputAsPositiveInt(input);
            switch (operation) {
                case 1: {
                    changeShopProductPrice(shopProduct);
                    break;
                }
                case 2: {
                    changeShopProductQuantity(shopProduct);
                    break;
                }
                default: {
                    printWrongInput();
                }
            }
        }
    }

    private static void changeShopProductPrice(ShopProduct shopProduct) {
        System.out.print("Type new product price: ");
        int value = ValidationInputData.checkInputAsPositiveInt(input);
        if (value == 0) {
            printWrongInput();
        } else {
            shopProduct.setPrice(value);
            DataBaseImpl.getInstance().updateEntity(SHOP_PRODUCTS_DB_COLLECTION, shopProduct.getId(), shopProduct);
        }
    }

    private static void changeShopProductQuantity(ShopProduct shopProduct) {
        System.out.print("Type disposal product quantity: ");
        int value = ValidationInputData.checkInputAsNaturalInt(input);
        if (value == 0 || ((shopProduct.getQuantity() - value) < 0)) {
            printWrongInput();
        } else {
            changeShopProductQuantity(shopProduct, value, true);
        }
    }

    public static void changeShopProductQuantity(ShopProduct shopProduct, int quantity, boolean isNegative) {
        if (isNegative) {
            shopProduct.setQuantity(shopProduct.getQuantity() - quantity);
        } else {
            shopProduct.setQuantity(shopProduct.getQuantity() + quantity);
        }
        DataBaseImpl.getInstance().updateEntity(SHOP_PRODUCTS_DB_COLLECTION, shopProduct.getId(), shopProduct);
    }
}
