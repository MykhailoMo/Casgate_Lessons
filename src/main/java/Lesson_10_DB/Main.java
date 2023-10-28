package Lesson_10_DB;

import Lesson_10_DB.Base.DataBaseImpl;

public class Main {
    public static void main(String[] args) {
        DataBaseImpl.getInstance();
        Data.fillStudents();
        Data.printStudents();
        Data.fillGroups();
        Data.fillTasks();
        Data.printTasks();
        MainMethods.studentTasksExecution();
        MainMethods.studentTasksResult();
        MainMethods.studentGroupSet();
        System.out.println("_______--------=================-----------__________");
        Data.printStudents();
    }

}
