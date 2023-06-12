package pets.example.guardians.repository.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import pets.example.guardians.model.UserRole;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    private String address;
    @NotBlank
    private String password;

    private Long phone;
    @JsonFormat(pattern = "dd-MM-yyyy")
   @Past(message = "Birth date must be in the past")
    private Date birthdate;
    private UserRole role;
    @Column(name = "adopted_Pets")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude

    @OneToMany(mappedBy = "adopter" , cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<PetEntity> adoptedPets = new HashSet<>();

    public void adoptPet(PetEntity pet) {

        adoptedPets.add(pet);
        pet.setAdopter(this);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", phone=" + phone +
                ", birthdate=" + birthdate +
                ", role=" + role +
                ", adoptedPets=" + adoptedPets +
                '}';
    }

}
