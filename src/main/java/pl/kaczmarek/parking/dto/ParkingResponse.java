package pl.kaczmarek.parking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingResponse {

    public int numberOfPlaces;
    public String city;
    public String name;
    public String street;
    public String postalCode;

    public ParkingResponse(int numberOfPlaces, String city, String name, String street, String postalCode) {
        this.numberOfPlaces = numberOfPlaces;
        this.city = city;
        this.name = name;
        this.street = street;
        this.postalCode = postalCode;
    }

}
