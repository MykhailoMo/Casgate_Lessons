package shoppingMall.utils;

import shoppingMall.base.DataBaseImpl;
import shoppingMall.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static shoppingMall.base.DbCollectionNames.*;
import static shoppingMall.enums.Constants.VAT;
import static shoppingMall.utils.ChangeShopProducts.changeShopProductQuantity;
import static shoppingMall.utils.DataOutput.printWrongInput;
import static shoppingMall.utils.InitialDataSetup.doubleToInt;
import static shoppingMall.utils.MainMethods.*;

public class AddProducts {

    public static void addProductsToShop(UUID shopId) {
        List<Product> products = new ArrayList(DataBaseImpl.getInstance().getAllEntities(PRODUCTS_DB_COLLECTION));
        Shop shop = (Shop) DataBaseImpl.getInstance().getEntity(SHOPS_DB_COLLECTION, shopId);
        int selectedProduct;
        int quantity;
        Scanner input = new Scanner(System.in);

        DataOutput.printProducts();
        System.out.print("\nFill number of product you want to add: ");
        selectedProduct = ValidationInputData.checkInputAsPositiveInt(input) - 1;
        if (selectedProduct >= 0 && selectedProduct <= products.size()) {
            Product product = products.get(selectedProduct);
            System.out.print("Fill quantity of the product: ");
            quantity = ValidationInputData.checkInputAsPositiveInt(input);
            if (quantity >= 0 && shop.getWalletAmount() >= quantity * product.getBasePrice()) {
                UUID itemId = isProductExistInShop(shopId, product.getId());
                storeProductToShop(itemId, shop, product, quantity);
            } else {
                printWrongInput();
            }
        } else {
            printWrongInput();
        }
    }

    public static void addProductToOrder(Order order) {
        List<ShopProduct> shopProducts = GetData.getProductsByShop(order.getShopId());
        Person person = GetData.getPersonById(order.getPersonId());
        Shop shop = GetData.getShopById(order.getShopId());
        ShopProduct shopProduct;
        int selectedProduct;
        int quantity;
        int shopPrice;
        Scanner input = new Scanner(System.in);

        DataOutput.printShopProducts(shopProducts);
        System.out.print("Fill number of product you want to add: ");
        selectedProduct = ValidationInputData.checkInputAsPositiveInt(input) - 1;
        if (selectedProduct < 0 || selectedProduct >= shopProducts.size()) {
            printWrongInput();
        } else {
            System.out.print("Fill quantity of the product: ");
            quantity = ValidationInputData.checkInputAsNaturalInt(input);
            if (quantity == 0 || quantity > shopProducts.get(selectedProduct).getQuantity() ||
                    person.getWalletAmount() < quantity * shopProducts.get(selectedProduct).getPrice()) {
                printWrongInput();
            } else {
                shopProduct = shopProducts.get(selectedProduct);
                shopPrice = shopProduct.getPrice();
                storeProductToOrder(order.getId(), shopProduct.getProductId(), quantity, shopPrice);
                changePersonWalletAmount(person, quantity * shopPrice, true);
                changeShopWalletAmount(shop, quantity * shopPrice, false);
                changeShopProductQuantity(shopProduct, quantity, true);
                System.out.println("Product was successfully added to your order");
                DataOutput.printCustomerCart(person.getId());
            }
        }
    }

    private static void storeProductToOrder(UUID orderId, UUID productId, int quantity, int shopPrice) {
        UUID itemId = isProductExistInOrder(orderId, productId);
        OrderDetail orderDetail;
        if (itemId == null) {
            orderDetail = new OrderDetail(UUID.randomUUID(), orderId, productId, quantity, shopPrice);
        } else {
            orderDetail = (OrderDetail) DataBaseImpl.getInstance().getEntity(ORDER_DETAILS_DB_COLLECTION, itemId);
            orderDetail.setQuantity(orderDetail.getQuantity() + quantity);
        }
        DataBaseImpl.getInstance().updateEntity(ORDER_DETAILS_DB_COLLECTION, orderDetail.getId(), orderDetail);
    }

    private static void storeProductToShop(UUID itemId, Shop shop, Product product, int quantity) {
        ShopProduct shopProduct;
        if (itemId == null) {
            int productPrice = doubleToInt(product.getBasePrice() * VAT.getVatMultiplyer());
            shopProduct = new ShopProduct(UUID.randomUUID(), shop.getId(), product.getId(), product.getName(),
                    productPrice, quantity);
            DataBaseImpl.getInstance().saveNewEntity(SHOP_PRODUCTS_DB_COLLECTION, shopProduct.getId(), shopProduct);
        } else {
            shopProduct = (ShopProduct) DataBaseImpl.getInstance().getEntity(SHOP_PRODUCTS_DB_COLLECTION, itemId);
            shopProduct.setName(product.getName());
            shopProduct.setPrice(doubleToInt(product.getBasePrice() * VAT.getVatMultiplyer()));
            shopProduct.setQuantity(shopProduct.getQuantity() + quantity);
            DataBaseImpl.getInstance().updateEntity(SHOP_PRODUCTS_DB_COLLECTION, itemId, shopProduct);
        }
        changeShopWalletAmount(shop, product.getBasePrice() * quantity, true);
        System.out.println("Product was successfully added to Shop");
    }

}
