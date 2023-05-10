package pets.example.guardians.model;

import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;

    private String firstName;

    private String lastName ;


    private String username;

    private String email;

    private String address;


    private String password;

    private Long phone;

    private Date birthdate;
    private UserRole role;


    private Set<Pet> adoptedPets = new HashSet<>();




}
