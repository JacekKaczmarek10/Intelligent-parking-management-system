package pl.kaczmarek.parking_space_exchange.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "parking_space_exchange_entity")
public class ParkingSpaceExchangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "number")
    private String number;

    @Column(name = "file1_path")
    private String file1Path;

    @Column(name = "file2_path")
    private String file2Path;

    @Column(name = "file3_path")
    private String file3Path;

    @Column(name = "is_free")
    private Boolean isFree;

    @ManyToOne
    private LandLordEntity landLordEntity;

    public ParkingSpaceExchangeEntity() {

    }

    public ParkingSpaceExchangeEntity(
        String street,
        String city,
        String postalCode,
        String number,
        String file1Path,
        String file2Path, String file3Path, Boolean isFree, LandLordEntity landLordEntity) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.number = number;
        this.file1Path = file1Path;
        this.file2Path = file2Path;
        this.file3Path = file3Path;
        this.isFree = isFree;
        this.landLordEntity = landLordEntity;
    }


}
