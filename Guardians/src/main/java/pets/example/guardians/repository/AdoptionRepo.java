package pets.example.guardians.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import pets.example.guardians.repository.entity.AdoptionRequestEntity;



@Repository
public interface AdoptionRepo extends JpaRepository<AdoptionRequestEntity, Long> {


}
