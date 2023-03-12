package pets.example.guardians.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pets.example.guardians.Repository.Entity.PetEntity;

@Repository
public interface PetRepo extends JpaRepository<PetEntity, Long> {

}
