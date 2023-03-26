package pets.example.guardians.Repository.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;
import pets.example.guardians.Model.UserRole;

import javax.persistence.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

@Entity
@Data
@Table(name = "pet_users")
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
//
//    @Min(value = 3, message = "The password must be at least 3 numbers long")
//    @Max(value = 6, message = "Password must be less than or equal to 6 numbers")

    private String password;

//
//    @Min(value = 7, message = "The password must be at least 7 numbers long")
//    @Max(value = 10, message = "Password must be less than or equal to 10 numbers")
//@Min(value = 1000000000, message = "Phone number must be 10 digits")
//@Max(value = 9999999999L, message = "Phone number must be 10 digits")
    private long phone;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
   @Past(message = "Birth date must be in the past")
  //  @Min(value = 18, message = "Must be at least 18 years old")
    //  @Age(min= 18, message = "Must be at least 18 years old")
    private Date birthdate;
    private UserRole role;

}
