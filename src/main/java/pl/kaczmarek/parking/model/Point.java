package pl.kaczmarek.parking.model;

import lombok.Getter;

@Getter
public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
    }
}
