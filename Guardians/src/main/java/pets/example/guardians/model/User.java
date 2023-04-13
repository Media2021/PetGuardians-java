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
    @NotBlank(message = "name  is required")
    private String firstName;
    @NotBlank(message = "last name  is required")
    private String lastName ;
    @NotBlank(message = "username  is required")
    @Column(name = "username")
    private String username;
    @NotBlank(message = "email is required")
    private String email;
    @NonNull
    private String address;

    @NotBlank(message = "password is required")
    private String password;
    @Min(value = 10, message = "phone number  must be at least 10 numbers long")
    private Long phone;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Past(message = "Birth date must be in the past")
    private Date birthdate;
    private UserRole role;


}
