package montyHoll;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
    public static final int NOT_CHANGE_DOOR = 1;
    public static final int CHANGE_DOOR = 2;

    static List<Boolean> genarateThreeDoorObjects() {
        List<Boolean> doors = new ArrayList<>();
        boolean isReady = false;
        for (int i = 0; i < 3; i++) {
            doors.add(false);
            if ((randomBool() || i == 2) && !isReady) {
                doors.set(i, true);
                isReady = true;
            }
//            System.out.println("Door " + (i ) + ": " + doors.get(i));
        }
//        System.out.println();
        return doors;
    }

    static int selectFirstDoor() {
        int door = randomInt(3);
//        System.out.println("firstDoor: " + door);
        return door;
    }

    static int openOneDoor(List<Boolean> doors, int firstDoor) {
        List<Integer> fakeDoorNumbers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (i != firstDoor && !doors.get(i)) {
                fakeDoorNumbers.add(i);
//                System.out.println("fakeDoorNumbers: " + i);
            }
        }
        int door = fakeDoorNumbers.get(randomInt(fakeDoorNumbers.size()));
//        System.out.println("Opened door: " + door);
        return door;
    }

    static int selectSecondDoor(int firstDoor, int opendDoor, int algorythmType) {
        if (algorythmType == NOT_CHANGE_DOOR) {
            return firstDoor;
        } else {
            int secondDoor = 0;
            for (int i = 0; i < 3; i++) {
                if (i != firstDoor && i != opendDoor) {
                    secondDoor = i;
                    break;
                }
            }
            return secondDoor;
        }
    }

    static void showResults(List<Boolean> results) {
        Integer countPositiveResults = 0;
        int countNegativeResults = 0;
        for (Boolean result: results) {
            if (result) {
                countPositiveResults++;
            } else {
                countNegativeResults++;
            }
        }
        double percentPositiveResults = countPositiveResults.doubleValue() * 100 / (countNegativeResults + countPositiveResults);
        System.out.println("Player won the main prise: " + countPositiveResults + " times. It is: " + percentPositiveResults + "% of all tries");
    }

    public static int randomInt(int limit) {
        Random randomData = new Random();
        return randomData.nextInt(limit);
    }

    static boolean randomBool() {
        Random randomData = new Random();
        return randomData.nextBoolean();
    }
}
