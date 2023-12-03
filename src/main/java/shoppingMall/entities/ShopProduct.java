package shoppingMall.entities;

import lombok.Data;
import shoppingMall.base.ObjectWithData;

import java.util.UUID;

@Data
public class ShopProduct extends ObjectWithData {
    private UUID id;
    private UUID shopId;
    private UUID productId;
    private String name;
    private int price;
    private int quantity;

    public ShopProduct(UUID id, UUID shopId, UUID productId, String name, int price, int quantity) {
        this.id = id;
        this.shopId = shopId;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ShopProduct{" +
                "name=" + name +
                ", price=" + price +
                ", quantity=" + quantity +
                "}\n";
    }
}
