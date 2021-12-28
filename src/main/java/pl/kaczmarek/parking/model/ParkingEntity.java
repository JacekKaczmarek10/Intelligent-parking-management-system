package pl.kaczmarek.parking.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "Parking")
@Getter
@Setter
public class ParkingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "number_of_places")
    public int numberOfPlaces;
    @Column(name = "city")
    public String city;
    @Column(name = "name")
    public String name;
    @Column(name = "street")
    public String street;
    @Column(name = "postalCode")
    public String postalCode;
    @Column(name = "has_added_points")
    public boolean hasAddedPoints;
    @Column(name = "imagePath")
    public String imagePath;

    public ParkingEntity() {
    }

    public ParkingEntity(String name) {
        this.name = name;
    }

    public ParkingEntity(int numberOfPlaces, String city, String name, String street, String postalCode) {
        this.numberOfPlaces = numberOfPlaces;
        this.city = city;
        this.name = name;
        this.street = street;
        this.postalCode = postalCode;
        this.hasAddedPoints = false;
    }


}
