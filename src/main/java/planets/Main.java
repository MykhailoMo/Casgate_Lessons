package planets;

import java.util.ArrayList;
import java.util.List;

import static planets.PlanetsEnum.*;
import static planets.Utils.*;

public class Main {
    public static void main(String[] args) {
        List<PlanetsEnum> planets = new ArrayList<>();
        planets.add(MERCURY);
        planets.add(VENUS);
        planets.add(EARTH);
        planets.add(MARS);
        planets.add(JUPITER);
        planets.add(SATURN);
        planets.add(URANUS);
        planets.add(NEPTUNE);
        for (PlanetsEnum planet: planets) {
            System.out.println("Time from Sun shutdown to astronomers is: " + timeToEarth(planet) + "s to see the " + planet.getName());
        }
    }
}
