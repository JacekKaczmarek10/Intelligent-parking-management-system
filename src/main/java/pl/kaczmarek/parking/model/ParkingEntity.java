package pl.kaczmarek.parking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents parking object.
 */
@Entity
@Data
@Table(name = "Parking")
@Getter
@Setter
public class ParkingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "number_of_places")
    public int numberOfPlaces;

    @NotNull
    @Column(name = "city")
    public String city;

    @NotNull
    @Column(name = "name", unique = true)
    public String name;

    @NotNull
    @Column(name = "street")
    public String street;

    @NotNull
    @Column(name = "postal_code")
    public String postalCode;

    @NotNull
    @Column(name = "has_added_points")
    public boolean hasAddedPoints;

    @NotNull
    @Column(name = "image_path", unique = true)
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
