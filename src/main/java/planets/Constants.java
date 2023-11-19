package planets;

import lombok.Data;

public enum Constants {
    SUN_VELOCITY(3e8),
    AU(1.5e11),
    LIGHT_DELAY(5e-25);

    public final double value;

    Constants(double value) {
        this.value = value;
    }

}
