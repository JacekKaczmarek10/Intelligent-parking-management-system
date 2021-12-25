package pl.kaczmarek.test;

import pl.kaczmarek.test.Car;

public class TestClass {

    static void alaMa(){

    }

    void alaMaX(){

    }

    public static void main(String[] args) {
        String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};
        for (String i : cars) {
            System.out.println(cars.length);
        }
        Car car = new Car();
        car.honk();

        Animal myPig = new Pig(); // Create a Pig object
        myPig.animalSound();
        myPig.sleep();

        try {
            int[] myNumbers = {1, 2, 3};
            System.out.println(myNumbers[2]);
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        } finally {
            System.out.println("The 'try catch' is finished.");
        }
    }
}
