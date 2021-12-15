package pl.kaczmarek.parking.dto;

public class ParkingRequest {

    public ParkingRequest() {
    }


    public int numberOfPlaces;
    public String city;
    public String name;
    public String street;
    public String postalCode;

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setNumberOfPlaces(int numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public ParkingRequest(int numberOfPlaces, String city, String name, String street, String postalCode) {
        this.numberOfPlaces = numberOfPlaces;
        this.city = city;
        this.name = name;
        this.street = street;
        this.postalCode = postalCode;
    }
}



