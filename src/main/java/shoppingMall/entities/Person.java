package shoppingMall.entities;

import shoppingMall.base.ObjectWithData;
import lombok.Data;
import shoppingMall.enums.PersonType;

import java.util.UUID;

import static shoppingMall.enums.PersonType.CUSTOMER;

@Data
public class Person extends ObjectWithData {
    private UUID id;
    private String name;
    private String password;
    private PersonType type;
    private int walletAmount;

    public Person(UUID id, String name, String password, PersonType type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.type = type;
        if (type.equals(CUSTOMER)) {
            this.setWalletAmount(0);
        } else {
            this.setWalletAmount(1000);
        }
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "" + id +
                ", " + name +
                ", " + type +
                ", " + walletAmount + "UAH" +
                '}';
    }
}
