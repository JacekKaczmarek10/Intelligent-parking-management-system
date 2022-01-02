package pl.kaczmarek.parking_space_exchange.dto;


import lombok.Data;
import pl.kaczmarek.parking_space_exchange.model.Role;

@Data
public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Role role;
}
