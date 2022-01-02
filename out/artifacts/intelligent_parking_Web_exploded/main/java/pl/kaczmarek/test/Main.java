package pl.kaczmarek.test;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        numbers.add(5);
        numbers.add(9);
        numbers.add(8);
        numbers.add(1);
        numbers.forEach( (n) -> { System.out.println(n); } );


        String x ="pies";
        String y = null;
        String s = "Hello World"; s.replace("World", "Jacek");
        System.out.println(s);
        s = s.replace("World", "Jacek");
        System.out.println(s);
    }
}