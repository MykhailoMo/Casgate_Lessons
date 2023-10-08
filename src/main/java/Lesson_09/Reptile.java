package Lesson_09;

public class Reptile extends Animal implements Ability{
    private Parameter type;

    public Reptile(String name, Parameter area) {
        super(name);
        this.type = area;
    }

    @Override
    public void voice(){
        System.out.println("Animal " + this.getName() + " do voice: " + "S-S-S-S");
    }

    @Override
    public String toString() {
        return "\nReptile || name: " + super.getName() + ", " +
                "type: " + type;
    }
}
