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
    @Column(name = "email", unique = true)
    public String email;

    @NotNull
    @Column(name = "name", unique = true)
    public String name;

    @NotNull
    @Column(name = "city")
    public String city;

    @NotNull
    @Column(name = "postal_code")
    public String postalCode;

    @NotNull
    @Column(name = "street")
    public String street;

    @NotNull
    @Column(name = "number")
    public Integer number;

    @NotNull
    @Column(name = "lat")
    public Float lat;

    @NotNull
    @Column(name = "lng")
    public Float lng;

    @NotNull
    @Column(name = "phone_number", unique = true)
    public String phoneNumber;

    @NotNull
    @Column(name = "is_guarded")
    public boolean isGuarded;

    @NotNull
    @Column(name = "number_of_places")
    public int numberOfPlaces;

    @NotNull
    @Column(name = "has_added_points")
    public boolean hasAddedPoints;

    @NotNull
    @Column(name = "image_path", unique = true)
    public String imagePath;

    @NotNull
    @Column(name = "upload_path", unique = true)
    public String uploadPath;

    @Column(name = "parking_status", unique = true)
    public ParkingStatus parkingStatus;


    public ParkingEntity() {
    }

    public ParkingEntity(String name) {
        this.name = name;
    }

    public ParkingEntity(
        String email,
        String name,
        String city,
        String postalCode,
        String street,
        Integer number,
        String phoneNumber,
        boolean isGuarded,
        int numberOfPlaces) {
        this.email = email;
        this.name = name;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.number = number;
        this.phoneNumber = phoneNumber;
        this.numberOfPlaces = numberOfPlaces;
        this.isGuarded = isGuarded;
        this.parkingStatus = ParkingStatus.WAITING;
    }
}
