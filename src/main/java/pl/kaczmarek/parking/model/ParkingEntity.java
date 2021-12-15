package pl.kaczmarek.parking.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;


import lombok.Data;

@Entity
@Data
@Table(name = "Parking")
public class ParkingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "number_of_places")
    public Integer numberOfPlaces;
    @Column(name = "city")
    public String city;
    @Column(name = "name")
    public String name;
    @Column(name = "street")
    public String street;
    @Column(name = "postalCode")
    public String postalCode;

    public ParkingEntity() {
    }

    public ParkingEntity(int numberOfPlaces, String city, String name, String street, String postalCode) {
        this.numberOfPlaces = numberOfPlaces;
        this.city = city;
        this.name = name;
        this.street = street;
        this.postalCode = postalCode;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
