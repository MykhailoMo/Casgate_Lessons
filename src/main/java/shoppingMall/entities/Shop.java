package shoppingMall.entities;

import shoppingMall.base.ObjectWithData;
import lombok.Data;
import shoppingMall.enums.ShopType;

import java.util.UUID;

@Data
public class Shop extends ObjectWithData {
    private UUID id;
    private UUID personId;
    private String name;
    private ShopType type;
    private int walletAmount;

    public Shop(UUID id, UUID personId, String name, ShopType type) {
        this.id = id;
        this.personId = personId;
        this.name = name;
        this.type = type;
        this.walletAmount = 10000;
    }

    @Override
    public String toString() {
        return "\nShop{" +
                 id +
                ", ownerId = " + personId +
                ", " + name +
                ", " + type +
                ", " + walletAmount + "UAH" +
                "}";
    }
}
