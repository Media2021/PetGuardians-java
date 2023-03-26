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

    private String password;

@Min(value = 1000000000, message = "phone number  must be at least 10 numbers long")


private Long phone;


    @DateTimeFormat(pattern = "dd-MM-yyyy")
   @Past(message = "Birth date must be in the past")
  //  @Min(value = 18, message = "Must be at least 18 years old")
    //  @Age(min= 18, message = "Must be at least 18 years old")
    private Date birthdate;
    private UserRole role;

}
