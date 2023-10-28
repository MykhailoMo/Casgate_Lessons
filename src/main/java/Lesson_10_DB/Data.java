package Lesson_10_DB;

import Lesson_10_DB.Base.DataBaseImpl;
import Lesson_10_DB.entities.Group;
import Lesson_10_DB.entities.Result;
import Lesson_10_DB.entities.Student;
import Lesson_10_DB.entities.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static Lesson_10_DB.Base.DbCollectionNames.*;

public class Data {
    static List<String> firstName = List.of("Mykola", "Ivan", "Petro", "Semen", "Mykhailo", "Dmytro", "Olena", "Nata",
            "Marina", "Katerina");
    static List<String> secondName = List.of("Derevyanko", "Yevtukh", "Yovenko", "Kozlov", "Kozachenko", "Koziy",
            "Makarenko", "Marmurchuk", "Oliynyk", "Osipenko");
    static List<String> groupName = List.of("AA-2023", "BB-2023", "CC-2023");

    public static void fillStudents() {
        for (int i = 0; i < 100; i++) {
            Student student = new Student(UUID.randomUUID(), firstName.get(random()), secondName.get(random()));
            DataBaseImpl.INSTANCE.saveNewEntity(STUDENTS_DB_COLLECTION, student.getId(), student);
        }
    }

    public static void fillGroups() {
        for (int i = 0; i < 3; i++) {
            Group group = new Group(UUID.randomUUID(), groupName.get(i));
            DataBaseImpl.INSTANCE.saveNewEntity(GROUPS_DB_COLLECTION, group.getId(), group);
        }
    }

    public static void fillTasks() {
        for (int i = 0; i < 10; i++) {
            String taskName = "TS-00" + i;
            Task task = new Task(UUID.randomUUID(), taskName, random() + 1);
            DataBaseImpl.INSTANCE.saveNewEntity(TASKS_DB_COLLECTION, task.getId(), task);
        }
    }

    public static void printStudents() {
        List <Student> students = new ArrayList(DataBaseImpl.INSTANCE.getAllEntities(STUDENTS_DB_COLLECTION));
        int i = 1;
        for (Student student:students) {
            System.out.println(i + ": " + student.toString());
            i++;
        }
    }

    public static void printTasks() {
        List <Task> tasks = new ArrayList(DataBaseImpl.INSTANCE.getAllEntities(TASKS_DB_COLLECTION));
        int i = 1;
        int maxMark = 0;
        System.out.println("\nTasks---------------------");
        for (Task task: tasks) {
            System.out.println(i + ": " + task.toString());
            i++;
            maxMark = maxMark + task.getMark();
        }
//        System.out.println("MaxResult = " + maxMark + ", 75% = " + (maxMark * 75 / 100) + ", 50% = " + (maxMark * 50 / 100) + ", 25% = " + (maxMark * 25 / 100));
    }
    public static void printResults() {
        List <Result> results = new ArrayList(DataBaseImpl.INSTANCE.getAllEntities(RESULTS_DB_COLLECTION));
        int i = 1;
        System.out.println("----*************************");
        for (Result result:results) {
            System.out.println(i + ": " + result.toString());
            i++;
        }
    }

    static int random() {
        double data = Math.random() * 9;
        return Math.toIntExact(Math.round(data));
    }
}
