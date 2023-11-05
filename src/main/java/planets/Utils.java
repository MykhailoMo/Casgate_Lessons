package planets;

import static planets.Constants.*;

public class Utils {
    public static double timeToEarth(PlanetsEnum planet) {
        return (planet.getDistanceFromEarth() + planet.getDistanceFromSun()) * AU.value / SUN_VELOCITY.value
                - planet.getWeight() * LIGHT_DELAY.value;
    }
}
