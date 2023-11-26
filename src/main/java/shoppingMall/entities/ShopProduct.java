package shoppingMall.entities;

import lombok.Data;
import shoppingMall.base.ObjectWithData;

import java.util.UUID;

@Data
public class ShopProduct extends ObjectWithData {
    private UUID id;
    private UUID shopId;
    private UUID productId;
    private int price;
    private int quantity;

    public ShopProduct(UUID id, UUID shopId, UUID productId, int price, int quantity) {
        this.id = id;
        this.shopId = shopId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ShopProduct{" +
                "id=" + id +
                ", shopId=" + shopId +
                ", productId=" + productId +
                ", price=" + price +
                ", quantity=" + quantity +
                "}\n";
    }
}
