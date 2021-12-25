package pl.kaczmarek.parking_space_exchange.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.kaczmarek.rest.IEntity;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "land_lord")
public class LandLordEntity extends IEntity {

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "email",unique=true)
    private String email;

    @NotNull
    @Column(name = "phone",unique=true)
    private String phone;

    @NotNull
    @Column(name = "number_of_parkings")
    private int numberOfParkings;

    public LandLordEntity() {

    }

    public LandLordEntity(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.numberOfParkings = 0;
    }


}
