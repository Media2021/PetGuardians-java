package pets.example.guardians.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pets.example.guardians.model.PetType;

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

    @NotBlank(message = "name  is required")
    private String name;

    private int age;
    @NonNull
    private String description;
    private PetType type;
    @NotBlank(message = "status is required")
    private String status;
    private String gender;
}
