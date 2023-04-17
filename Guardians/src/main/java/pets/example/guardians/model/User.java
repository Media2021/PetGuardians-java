package pets.example.guardians.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
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
