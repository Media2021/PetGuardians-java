package pets.example.guardians.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    private long id;
    private String name;
    private int age;
    private String description;
    private PetType type;
    private String status;
    private String gender;

    private User adopter ;




}
