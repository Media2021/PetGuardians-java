package pets.example.guardians.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;
import pets.example.guardians.model.PetType;
import pets.example.guardians.repository.entity.AdoptionRequestEntity;


import java.util.List;


@Repository
public interface AdoptionRepo extends JpaRepository<AdoptionRequestEntity, Long> {
    List<AdoptionRequestEntity> findByUserId(Long userId);
List<AdoptionRequestEntity> findAllByStatusAndPet_Type(String status, PetType petType);

}
