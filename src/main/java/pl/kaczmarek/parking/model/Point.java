package pl.kaczmarek.parking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Class that represents coordinates of each point.
 */
@Getter
@NoArgsConstructor
public class Point {

    private Integer x;
    private Integer y;

    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }
}