package shoppingMall.entities;

import shoppingMall.base.ObjectWithData;
import lombok.Data;

import java.util.UUID;

@Data
public class Product extends ObjectWithData {
    private UUID id;
    private String name;
    private int basePrice;

    public Product(UUID id, String name, int basePrice) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return "\nProduct{" +
                 id +
                ", " + name + '\'' +
                ", " + basePrice + "UAH" +
                '}';
    }
}
