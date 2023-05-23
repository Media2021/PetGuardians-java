package pets.example.guardians.repository.entity;

import lombok.*;

import javax.persistence.*;

import java.util.Date;

@Entity
@Data

@Table(name = "adoption_requests")
@NoArgsConstructor


public class AdoptionRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "adopter_id",referencedColumnName = "id")
    private UserEntity user;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "adopted_Pets", referencedColumnName = "id")
    private PetEntity pet;

    private String status;

    private String notes;

    private Date requestDate;

}
