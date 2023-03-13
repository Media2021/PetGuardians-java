package pets.example.guardians.Repository.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pets.example.guardians.Model.PetType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Table(name = "pets")
@NoArgsConstructor
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    @NotBlank(message = "name  is required")
    private String name;
    @NonNull
    private int age;
    @NonNull
    private String description;
    private PetType type;
    @NotBlank(message = "status is required")
    private String status;
    private String gender;
}
