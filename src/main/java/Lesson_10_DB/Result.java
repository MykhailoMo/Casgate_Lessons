package Lesson_10_DB;

import Lesson_10_DB.Base.ObjectWithData;

import java.util.UUID;

public class Result extends ObjectWithData {
    UUID id;
    UUID studentId;
    UUID taskId;
    boolean passed;

    public Result(UUID id, UUID studentId, UUID taskId, boolean passed) {
        this.id = id;
        this.studentId = studentId;
        this.taskId = taskId;
        this.passed = passed;
    }

    public UUID getId() {
        return id;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public boolean isPassed() {
        return passed;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id = " + id +
                ", studentId = " + studentId +
                ", taskId = " + taskId +
                ", passed = " + passed +
                '}';
    }
}
