package shoppingMall.entities;

import lombok.Data;
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
    private Date deliveryDate;

    public OrderDetail(UUID id, UUID orderId, UUID productId, int quantity, int price, Date deliveryDate) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.deliveryDate = deliveryDate;
    }
}
