package pl.kaczmarek.parking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Class that represents coordinates of each point.
 */
@Getter
@NoArgsConstructor
public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}