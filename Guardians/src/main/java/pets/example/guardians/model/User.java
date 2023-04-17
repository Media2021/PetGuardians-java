package pets.example.guardians.model;

import lombok.*;
import javax.persistence.Column;
import java.util.Date;


@Data

@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;

    private String firstName;

    private String lastName ;

    @Column(name = "username")
    private String username;

    private String email;

    private String address;


    private String password;

    private Long phone;

    private Date birthdate;
    private UserRole role;


}
