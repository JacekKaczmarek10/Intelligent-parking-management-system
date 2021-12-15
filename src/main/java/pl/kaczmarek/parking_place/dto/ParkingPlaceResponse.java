package pl.kaczmarek.parking_place.dto;


public class ParkingPlaceResponse {

    private Long id;
    private Integer x_1;
    private Integer y_1;
    private Integer x_2;
    private Integer y_2;
    private Integer x_3;
    private Integer y_3;
    private Integer x_4;
    private Integer y_4;

    public ParkingPlaceResponse(Long id, Integer x_1, Integer y_1, Integer x_2, Integer y_2,
                                Integer x_3, Integer y_3, Integer x_4, Integer y_4) {
        this.id = id;
        this.x_1 = x_1;
        this.y_1 = y_1;
        this.x_2 = x_2;
        this.y_2 = y_2;
        this.x_3 = x_3;
        this.y_3 = y_3;
        this.x_4 = x_4;
        this.y_4 = y_4;
    }

    public Integer getX_1() {
        return x_1;
    }

    public void setX_1(Integer x_1) {
        this.x_1 = x_1;
    }

    public Integer getY_1() {
        return y_1;
    }

    public void setY_1(Integer y_1) {
        this.y_1 = y_1;
    }

    public Integer getX_2() {
        return x_2;
    }

    public void setX_2(Integer x_2) {
        this.x_2 = x_2;
    }

    public Integer getY_2() {
        return y_2;
    }

    public void setY_2(Integer y_2) {
        this.y_2 = y_2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getX_3() {
        return x_3;
    }

    public void setX_3(Integer x_3) {
        this.x_3 = x_3;
    }

    public Integer getY_3() {
        return y_3;
    }

    public void setY_3(Integer y_3) {
        this.y_3 = y_3;
    }

    public Integer getX_4() {
        return x_4;
    }

    public void setX_4(Integer x_4) {
        this.x_4 = x_4;
    }

    public Integer getY_4() {
        return y_4;
    }

    public void setY_4(Integer y_4) {
        this.y_4 = y_4;
    }
}
