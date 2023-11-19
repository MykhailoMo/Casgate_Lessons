package planets;

import lombok.Getter;
import lombok.ToString;

@ToString
public enum PlanetsEnum {
    MERCURY ("Mercury", 0.39, 3.3e23),
    VENUS ("Venus", 0.72, 4.9e24),
    EARTH ("Earth", 1, 6e24),
    MARS ("Mars", 1.52,  6.4e23),
    JUPITER ("Jupiter", 5.2, 1.9e27 ),
    SATURN ("Saturn", 9.54, 5.7e26),
    URANUS ("Uranus", 19.22, 8.9e25),
    NEPTUNE ("Neptune", 30.06, 1e26);

    private final String name;
    private final double distanceFromSun;
    private final double distanceFromEarth = 0;
    private final double weight;

    PlanetsEnum(String name, double distanceFromSun, double weight) {
        this.name = name;
        this.distanceFromSun = distanceFromSun;
        this.weight = weight;
    }

    public double getDistanceFromEarth() {

        return Math.abs(this.distanceFromSun - EARTH.distanceFromSun);
    }

    public String getName() {
        return name;
    }

    public double getDistanceFromSun() {
        return distanceFromSun;
    }

    public double getWeight() {
        return weight;
    }
}
