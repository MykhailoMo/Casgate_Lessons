package Lesson_10_DB;

import Lesson_10_DB.Base.ObjectWithData;

import java.util.UUID;

public class Student extends ObjectWithData {
    private UUID id;
    private String firstName;
    private String secondName;
    private UUID groupId;
    private int result;

    public Student(UUID id, String firstName, String secondName) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", groupId=" + groupId +
                ", result=" + result +
                '}';
    }
}
