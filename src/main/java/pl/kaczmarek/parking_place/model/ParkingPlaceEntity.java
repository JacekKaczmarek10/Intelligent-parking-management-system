package pl.kaczmarek.parking_place.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import pl.kaczmarek.parking.model.ParkingEntity;

@Entity
@Data
@Table(name = "Parking_place")
public class ParkingPlaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="parking")
    private ParkingEntity parkingEntity;

    @Column(name = "is_free")
    private Boolean isFree;

    @Column(name = "x_1")
    private Integer x_1;

    @Column(name = "y_1")
    private Integer y_1;

    @Column(name = "x_2")
    private Integer x_2;

    @Column(name = "y_2")
    private Integer y_2;

    public ParkingPlaceEntity() {
    }

    public ParkingPlaceEntity(
        ParkingEntity parkingEntity,
        Integer x_1,
        Integer y_1,
        Integer x_2,
        Integer y_2) {
        this.parkingEntity = parkingEntity;
        this.isFree = true;
        this.x_1 = x_1;
        this.y_1 = y_1;
        this.x_2 = x_2;
        this.y_2 = y_2;
    }
}
