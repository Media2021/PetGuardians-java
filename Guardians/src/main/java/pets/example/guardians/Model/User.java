package pets.example.guardians.Model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;


@Data
@Builder
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


//    @Min(value = 1000000000, message = "Phone number must be 10 digits")
//    @Max(value = 9999999999L, message = "Phone number must be 10 digits")


    private long phone;


    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Past(message = "Birth date must be in the past")
    private Date birthdate;

    private UserRole role;
}
