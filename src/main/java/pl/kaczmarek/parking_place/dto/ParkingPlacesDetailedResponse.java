package pl.kaczmarek.parking_place.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ParkingPlacesDetailedResponse {

    public String name;
    public Integer placesNumber;
    public List<ParkingPlaceResponse> parkingPlaceResponseList;

    public ParkingPlacesDetailedResponse(
        String name,
        Integer placesNumber,
        List<ParkingPlaceResponse> parkingPlaceResponseList) {
        this.name = name;
        this.placesNumber=placesNumber;
        this.parkingPlaceResponseList = parkingPlaceResponseList;
    }
}
