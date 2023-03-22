package pets.example.guardians.Model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
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
    private String userName;
    @NotBlank(message = "email is required")
    private String email;
    @NonNull
    private String address;


    private String password;



    private int phone;


    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Past(message = "Birth date must be in the past")
    private Date birthdate;

    private UserRole role;
}
