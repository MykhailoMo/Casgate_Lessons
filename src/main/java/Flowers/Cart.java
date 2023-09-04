package Flowers;

public class Cart {
    private Integer id;
    private Integer flowerType;
    private Integer quantity;
    public static Integer money = 1000;

    public Cart(Integer flowerType, Integer quantity) {
        this.flowerType = flowerType;
        this.quantity = quantity;
    }

    public void setFlowerType(Integer flowerType) {
        this.flowerType = flowerType;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getFlowerType() {
        return flowerType;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
