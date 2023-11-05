package montyHoll;

public enum SelectDoorAlgorithm {
    CHANGE_DOOR("change door"),
    NOT_CHANGE_DOOR("not change door");
    public final String value;
    SelectDoorAlgorithm(String value) {
        this.value = value;
    }
}
//secondDoor = selectSecondDoor(firstDoor, openedDoor, CHANGE_DOOR);
//static int selectSecondDoor(int firstDoor, int opendDoor, SelectDoorAlgorithm algorythmType) {
//        if (algorythmType.value.equals(NOT_CHANGE_DOOR.value)) {
//        return firstDoor;
//        }
