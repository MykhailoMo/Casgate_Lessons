package Lesson_10_DB.entities;

import Lesson_10_DB.Base.ObjectWithData;
import lombok.Data;

import java.util.UUID;
@Data
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
