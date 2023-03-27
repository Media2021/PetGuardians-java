package pets.example.guardians.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pets.example.guardians.repository.Entity.PetEntity;

@Repository
public interface PetRepo extends JpaRepository<PetEntity, Long> {

}
