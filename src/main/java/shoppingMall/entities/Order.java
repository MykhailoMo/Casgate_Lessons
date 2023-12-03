package shoppingMall.entities;

import lombok.Data;
import shoppingMall.base.DataBaseImpl;
import shoppingMall.base.ObjectWithData;

import java.time.LocalDate;
import java.util.UUID;

import static shoppingMall.base.DbCollectionNames.SHOPS_DB_COLLECTION;

@Data
public class Order extends ObjectWithData {
    private UUID id;
    private UUID shopId;
    private UUID personId;
    private LocalDate created;
    private LocalDate deliveryDate;
    private int amount;

    public Order(UUID id, UUID shopId, UUID personId) {
        this.id = id;
        this.shopId = shopId;
        this.personId = personId;
        this.created = LocalDate.now();
        Shop shop = (Shop) DataBaseImpl.getInstance().getEntity(SHOPS_DB_COLLECTION, shopId);
        this.deliveryDate = this.created.plusDays(shop.getType().getDelivery().getDaysToDelivery());
    }
}

