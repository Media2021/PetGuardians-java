package pets.example.guardians.repository.entity;

import lombok.*;

import javax.persistence.*;

import java.util.Date;

@Entity
@Data
@Builder
@Table(name = "adoption_requests")
@NoArgsConstructor
@AllArgsConstructor

public class AdoptionRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserEntity user;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private PetEntity pet;

    private String status;

    private String notes;

    private Date requestDate;

}
