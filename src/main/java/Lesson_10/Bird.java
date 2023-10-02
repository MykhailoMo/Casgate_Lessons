package Lesson_10;

public class Bird extends Animal implements Ability{
    private Parameter area;
    private String song;

    public Bird(String name, Parameter area, String song) {
        super(name);
        this.area = area;
        this.song = song;
    }

    public Bird(String name, Parameter area) {
        super(name);
        this.area = area;
    }

    @Override
    public void voice() {
        System.out.println("Animal " + this.getName() + " do voice: " + this.song);
    }


    @Override
    public String toString() {
        voice();
        return "\nBird || name: " + super.getName() +
                ", area: " + area + '\'';
    }

}
