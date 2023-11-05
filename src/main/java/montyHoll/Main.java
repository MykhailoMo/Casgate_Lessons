package montyHoll;

import java.util.ArrayList;
import java.util.List;

import static montyHoll.Utils.*;

public class Main {
    public static void main(String[] args) {
        int maxValue = 1000000;
        int firstDoor;
        int secondDoor;
        int openedDoor;
        List<Boolean> results = new ArrayList<>();
        int algorythmType = NOT_CHANGE_DOOR;
        boolean nextGame = true;
        while (nextGame) {
            for (int i = 1; i <= maxValue; i++) {
                List<Boolean> doors = genarateThreeDoors();
                firstDoor = selectFirstDoor();
                openedDoor = openOneDoor(doors, firstDoor);
                secondDoor = selectSecondDoor(firstDoor, openedDoor, algorythmType);
                if (doors.get(secondDoor)) {
                    results.add(true);
                } else {
                    results.add(false);
                }
            }
            showResults(results, algorythmType);
            results.clear();
            if (algorythmType == CHANGE_DOOR) {
                nextGame = false;
            } else {
                algorythmType = CHANGE_DOOR;
            }
        }
    }
}
