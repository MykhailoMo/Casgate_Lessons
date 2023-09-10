package Flowers;

public class Flowers {
    private String name;
    private int length;
    private int price;
    private int quantity;

    public Flowers(String name, Integer length, Integer quantity) {
        int basePrice = 100;
        this.name = name;
        this.length = length;
        if (name.contains("Rose")) {
            this.price = length * basePrice / 100;
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
