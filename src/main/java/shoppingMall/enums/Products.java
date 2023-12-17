package shoppingMall.enums;

import shoppingMall.entities.Product;

import java.util.UUID;

import static shoppingMall.enums.ShopType.*;

public enum Products {
    MOLOKO(new Product(UUID.randomUUID(), "Moloko", 30), FOOD),
    SMETANA(new Product(UUID.randomUUID(), "Smetana", 25), FOOD),
    HLIB(new Product(UUID.randomUUID(), "Hlib", 22), FOOD),
    KETCHUP(new Product(UUID.randomUUID(), "Ketchup", 15), FOOD),
    NOTEBOOK_HP(new Product(UUID.randomUUID(), "Notebook HP", 35000), COMPUTERS),
    NOTEBOOK_ACER(new Product(UUID.randomUUID(), "Notebook Acer", 28000), COMPUTERS),
    PLANSHET_1(new Product(UUID.randomUUID(), "Planshet Samsung", 18000), COMPUTERS),
    PLANSHET_2(new Product(UUID.randomUUID(), "Planshet X", 10000), COMPUTERS),
    CEMENT(new Product(UUID.randomUUID(), "Cement 500", 100), BUILDING_MATERIALS),
    GIPS(new Product(UUID.randomUUID(), "Gips", 33), BUILDING_MATERIALS),
    FARBA(new Product(UUID.randomUUID(), "Farba", 234), BUILDING_MATERIALS),
    DOSHKA(new Product(UUID.randomUUID(), "Doshka", 380), BUILDING_MATERIALS),
    BALKA(new Product(UUID.randomUUID(), "Balka", 456), BUILDING_MATERIALS),
    DROVA(new Product(UUID.randomUUID(), "Drova", 1000), BUILDING_MATERIALS),
    KOSHYK(new Product(UUID.randomUUID(), "Koshyk", 80), HOUSEHOLD),
    VIDRO(new Product(UUID.randomUUID(), "Vidro", 250), HOUSEHOLD),
    TARILKA(new Product(UUID.randomUUID(), "Tarilka", 35), HOUSEHOLD),
    VYDELKA(new Product(UUID.randomUUID(), "Vydelka", 145), HOUSEHOLD);

    private final Product product;
    private final ShopType shopType;

    Products(Product product, ShopType shopType) {
        this.product = product;
        this.shopType = shopType;
    }

    public Product getProduct() {
        return product;
    }

    public ShopType getShopType() {
        return shopType;
    }
}
