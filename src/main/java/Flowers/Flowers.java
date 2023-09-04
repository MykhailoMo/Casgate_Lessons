package Flowers;

public class Flowers {
    private String name;
    private Integer length;
    private Integer price;
    private Integer quantity;
    private final Integer BASE_PRICE = 100;

    public Flowers(String name, Integer length, Integer quantity) {
        this.name = name;
        this.length = length;
        if (name.contains("Rose")) {
            this.price = length * BASE_PRICE / 100;
        }
        this.quantity = quantity;
    }

    public Flowers(String name, Integer length, Integer price, Integer quantity) {
        this.name = name;
        this.length = length;
        this.price = price;
        this.quantity = quantity;
    }

    public Flowers() {
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String show() {
        if (quantity > 0) {
            return name +
                    ", length=" + length +
                    ", price=" + price +
                    ", quantity=" + quantity;
        } else {
            return "-----Out of stock-----";
        }
    }
}
