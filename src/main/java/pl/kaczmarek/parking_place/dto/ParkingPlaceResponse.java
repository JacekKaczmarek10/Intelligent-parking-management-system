package pl.kaczmarek.parking_place.dto;



import javax.persistence.Column;

public class ParkingPlaceResponse {

    private Integer x_1;
    private Integer y_1;
    private Integer x_2;
    private Integer y_2;

    public ParkingPlaceResponse(Integer x_1, Integer y_1, Integer x_2, Integer y_2) {
        this.x_1 = x_1;
        this.y_1 = y_1;
        this.x_2 = x_2;
        this.y_2 = y_2;
    }
}
