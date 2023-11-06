package planets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static planets.PlanetsEnum.*;
import static planets.Utils.*;

public class Main {
    public static void main(String[] args) {
//        List<PlanetsEnum> planets = new ArrayList<>(); //1
//        planets.add(MERCURY);
//        planets.add(VENUS);
//        planets.add(EARTH);
//        planets.add(MARS);
//        planets.add(JUPITER);
//        planets.add(SATURN);
//        planets.add(URANUS);
//        planets.add(NEPTUNE);
//        List<PlanetsEnum> planets = List.of(PlanetsEnum.values());  //2
        List<PlanetsEnum> planets = Arrays.asList(PlanetsEnum.values());  //3
//        Arrays.stream(PlanetsEnum.values()).toList();  //4
//        List<PlanetsEnum> planets = new ArrayList<>();
//        Collections.addAll(planets, PlanetsEnum.values());  //5

        for (PlanetsEnum planet: planets) {
            System.out.println("Time from Sun shutdown to astronomers is: " + timeToEarth(planet) + "s to see the " + planet.getName());
        }
    }
}
