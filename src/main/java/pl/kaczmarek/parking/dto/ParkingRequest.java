package pl.kaczmarek.parking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParkingRequest {

    public int numberOfPlaces;
    public String city;
    public String name;
    public String street;
    public String postalCode;
    public Integer number;
    public Float lat;
    public Float lng;
    public Boolean isGuarded;


    public ParkingRequest(
        int numberOfPlaces,
        String city,
        String name,
        String street,
        String postalCode,
        Integer number,
        Float lat,
        Float lng,
        Boolean isGuarded) {
        this.numberOfPlaces = numberOfPlaces;
        this.city = city;
        this.name = name;
        this.street = street;
        this.postalCode = postalCode;
        this.number = number;
        this.lat = lat;
        this.lng = lng;
        this.isGuarded = isGuarded;
    }
}



