package pl.kaczmarek.parking_space_exchange.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import pl.kaczmarek.rest.EntityDTO;


@JsonSerialize
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class HireResponse extends EntityDTO<UUID> {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}