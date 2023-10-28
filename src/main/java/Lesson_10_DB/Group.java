package Lesson_10_DB;

import Lesson_10_DB.Base.ObjectWithData;

import java.util.UUID;

public class Group extends ObjectWithData {
    private UUID id;
    private String name;

    public Group(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
