package pets.example.guardians.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import pets.example.guardians.model.UserRole;

import javax.persistence.*;

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
    @NonNull
    private String password;
@Min(value = 1000000000, message = "phone number  must be at least 10 numbers long")
private Long phone;
    @JsonFormat(pattern = "dd-MM-yyyy")
   @Past(message = "Birth date must be in the past")
    private Date birthdate;
    private UserRole role;

}
