package Lesson_10;

public class Animal implements Ability{
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void voice() {
    }
}
