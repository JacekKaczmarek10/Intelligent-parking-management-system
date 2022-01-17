package pl.kaczmarek.parking_place.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChangePlace {

    List<Integer> parkingPlaces;
    String name;

    public ChangePlace(List<Integer> parkingPlaces, String name) {
        this.parkingPlaces = parkingPlaces;
        this.name = name;
    }
}
