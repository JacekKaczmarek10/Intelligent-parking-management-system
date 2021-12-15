package pl.kaczmarek.parking_place.dto;

import java.util.List;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParkingPlaceResponse> getParkingPlaceResponseList() {
        return parkingPlaceResponseList;
    }

    public void setParkingPlaceResponseList(List<ParkingPlaceResponse> parkingPlaceResponseList) {
        this.parkingPlaceResponseList = parkingPlaceResponseList;
    }
}
