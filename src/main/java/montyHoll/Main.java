package montyHoll;

import java.util.ArrayList;
import java.util.List;

import static montyHoll.Utils.*;

public class Main {
    public static void main(String[] args) {
        int maxValue = 100000;
        int firstDoor;
        int secondDoor;
        int openedDoor;
        List<Boolean> results = new ArrayList<>();
        for (int i = 1; i <= maxValue; i ++) {
//            System.out.println("------TASK: " + i + " ------");
            List<Boolean> doors = genarateThreeDoorObjects();
            firstDoor = selectFirstDoor();
            openedDoor = openOneDoor(doors, firstDoor);
            secondDoor = selectSecondDoor(firstDoor, openedDoor, CHANGE_DOOR);
            if (doors.get(secondDoor)) {
                results.add(true);
            } else {
                results.add(false);
            }
        }
        showResults(results);
    }
}
