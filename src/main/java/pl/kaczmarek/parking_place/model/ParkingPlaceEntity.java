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
}
