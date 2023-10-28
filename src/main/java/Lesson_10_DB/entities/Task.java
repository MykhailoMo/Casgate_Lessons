package Lesson_10_DB.entities;

import Lesson_10_DB.Base.ObjectWithData;

import java.util.UUID;

public class Task extends ObjectWithData {
    private UUID id;
    private String name;
    private int mark;

    public Task(UUID id, String name, int mark) {
        this.id = id;
        this.name = name;
        this.mark = mark;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public int getMark() {
        return mark;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mark=" + mark +
                '}';
    }
}
