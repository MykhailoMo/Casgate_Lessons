package shoppingMall.enums;

public enum Constants {
    VAT("VAT", 20);
    private final String name;
    private final double value;

    Constants(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public double getVatMultiplyer() {
        return (100 + VAT.value) / 100;
    }
}
