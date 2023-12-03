package shoppingMall.entities;

import lombok.Data;
import shoppingMall.base.DataBaseImpl;
import shoppingMall.base.DbCollectionNames;
import shoppingMall.base.ObjectWithData;

import java.util.Date;
import java.util.UUID;

@Data
public class OrderDetail extends ObjectWithData {
    private UUID id;
    private UUID orderId;
    private UUID productId;
    private int quantity;
    private int price;

    public OrderDetail(UUID id, UUID orderId, UUID productId, int quantity, int price) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
