package Lesson_09;

public class Fish extends Animal implements Ability{
    private Parameter colour;


    public Fish(String name, Parameter area) {
        super(name);
        this.colour = area;
    }


    @Override
    public void voice(){
        System.out.println("Animal " + this.getName() + " do not have voice");
    }

    @Override
    public String toString() {
        return "\nFish || name: " + super.getName() + ", " +
                "colour: " + colour;
    }
}
