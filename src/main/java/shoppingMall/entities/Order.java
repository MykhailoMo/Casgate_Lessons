package shoppingMall.entities;

import lombok.Data;
import shoppingMall.base.ObjectWithData;

import java.util.Date;
import java.util.UUID;

@Data
public class Order extends ObjectWithData {
    private UUID id;
    private UUID shopId;
    private UUID personId;
    private Date created;
    private int amount;

    public Order(UUID id, UUID shopId, UUID personId, Date created) {
        this.id = id;
        this.shopId = shopId;
        this.personId = personId;
        this.created = created;
    }
}

