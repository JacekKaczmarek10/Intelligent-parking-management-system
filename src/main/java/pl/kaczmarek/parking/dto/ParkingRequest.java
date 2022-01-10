package pl.kaczmarek.parking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParkingRequest {

    public String email;
    public String name;

    public String city;
    public String street;
    public String postalCode;
    public Integer number;

    public String phoneNumber;
    public Boolean isGuarded;
    public int numberOfPlaces;

    public ParkingRequest(
        String name,
        String email,
        String city,
        String street,
        String postalCode,
        Integer number,
        String phoneNumber, Boolean isGuarded, int numberOfPlaces) {
        this.email = email;
        this.name = name;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.number = number;
        this.phoneNumber = phoneNumber;
        this.isGuarded = isGuarded;
        this.numberOfPlaces = numberOfPlaces;
    }
}



