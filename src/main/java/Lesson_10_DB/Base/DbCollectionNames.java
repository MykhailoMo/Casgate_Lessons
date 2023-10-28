package Lesson_10_DB.Base;

public enum DbCollectionNames {

    STUDENTS_DB_COLLECTION("students"),
    GROUPS_DB_COLLECTION("groups"),
    TASKS_DB_COLLECTION("tasks"),
    RESULTS_DB_COLLECTION("results");

    private String name;

    DbCollectionNames(String name) {
        this.name = name;
    }
}
