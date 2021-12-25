package pl.kaczmarek.parking_space_exchange.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class HireRequest implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}

