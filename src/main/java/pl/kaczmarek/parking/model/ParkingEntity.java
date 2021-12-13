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

    @Column(name = "number_of_rows")
    private int numberOfRows;

    @Column(name = "number_of_columns")
    private int numberOfColumns;

    @Size(max = 100)
    @Column(name = "name")
    private String name;

    public ParkingEntity() {
    }

    public ParkingEntity(int numberOfRows, int numberOfColumns, String name) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public String getName() {
        return name;
    }
}
