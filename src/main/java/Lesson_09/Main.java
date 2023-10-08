package Lesson_09;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Animal> animalInZoo = new ArrayList<>();
        animalInZoo.add(new Bird("Chicken", new Parameter("Ukraine"),"Ku-Ka-Re-Ku"));
        animalInZoo.add(new Fish("Shark", new Parameter("Blue")));
        animalInZoo.add(new Reptile("Dragon", new Parameter("Black")));

        for (Animal animal: animalInZoo) {
            System.out.println(animal);
            animal.voice();
        }
    }
}
