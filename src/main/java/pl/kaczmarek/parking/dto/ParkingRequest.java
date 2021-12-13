package pl.kaczmarek.parking.dto;

public class ParkingRequest {

    public ParkingRequest() {
    }

    public int numberOfRows;
    public int numberOfColumns;
    public String name;

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public String getName() {
        return name;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ParkingRequest(int numberOfRows, int numberOfColumns, String name) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.name = name;
    }
}
