package Lesson_10_DB;

import Lesson_10_DB.Base.DataBaseImpl;
import Lesson_10_DB.entities.Group;
import Lesson_10_DB.entities.Result;
import Lesson_10_DB.entities.Student;
import Lesson_10_DB.entities.Task;

import java.util.*;

import static Lesson_10_DB.Base.DbCollectionNames.*;

public class MainMethods {

    public static void studentTasksExecution() {
        List<Student> students = new ArrayList(DataBaseImpl.INSTANCE.getAllEntities(STUDENTS_DB_COLLECTION));
        List<Task> tasks = new ArrayList(DataBaseImpl.INSTANCE.getAllEntities(TASKS_DB_COLLECTION));
        for (Student student : students) {
            for (Task task : tasks) {
                Result result = new Result(UUID.randomUUID(), student.getId(), task.getId(), randomBool());
                DataBaseImpl.INSTANCE.saveNewEntity(RESULTS_DB_COLLECTION, result.getId(), result);
            }
        }
    }

    public static void studentTasksResult() {
        List<Student> students = new ArrayList(DataBaseImpl.INSTANCE.getAllEntities(STUDENTS_DB_COLLECTION));
        List<Result> results = new ArrayList(DataBaseImpl.INSTANCE.getAllEntities(RESULTS_DB_COLLECTION));
        UUID studentId;
        int studentSummResult;
        int taskMark;
        for (Student student: students) {
            studentId = student.getId();
            studentSummResult = 0;
            for (Result result: results) {
                if (result.getStudentId().equals(studentId) && result.isPassed()) {
                    Task task = (Task) DataBaseImpl.INSTANCE.getEntity(TASKS_DB_COLLECTION, result.getTaskId());
                    taskMark = task.getMark();
                    studentSummResult = studentSummResult + taskMark;
                }
            }
            student.setResult(studentSummResult);
            DataBaseImpl.INSTANCE.updateEntity(STUDENTS_DB_COLLECTION, studentId, student);
        }
    }

    public static void studentGroupSet() {
        List<Student> students = new ArrayList(DataBaseImpl.INSTANCE.getAllEntities(STUDENTS_DB_COLLECTION));
        List<Group> groups = new ArrayList(DataBaseImpl.INSTANCE.getAllEntities(GROUPS_DB_COLLECTION));
        int studentPercentResult;
        boolean isDeleted = false;
        int maximum = maxTasksResult();
        for (Student student: students) {
            studentPercentResult = student.getResult() * 100 / maximum;
            if (studentPercentResult >= 75) {
                student.setGroupId(groups.get(0).getId());
            } else if (studentPercentResult >= 50) {
                student.setGroupId(groups.get(1).getId());
            } else if (studentPercentResult >= 25) {
                student.setGroupId(groups.get(2).getId());
            } else {
                isDeleted = true;
            }
            if (isDeleted) {
                DataBaseImpl.INSTANCE.deleteEntity(STUDENTS_DB_COLLECTION, student.getId());
                isDeleted = false;
            } else {
                DataBaseImpl.INSTANCE.updateEntity(STUDENTS_DB_COLLECTION, student.getId(), student);
            }
        }
    }

    public static int maxTasksResult() {
        List<Task> tasks = new ArrayList(DataBaseImpl.INSTANCE.getAllEntities(TASKS_DB_COLLECTION));
        int result = 0;
        for (Task task: tasks) {
            result = result + task.getMark();
        }
        return result;
    }

    static boolean randomBool() {
        Random randomData = new Random();
        return randomData.nextBoolean();
    }
}
