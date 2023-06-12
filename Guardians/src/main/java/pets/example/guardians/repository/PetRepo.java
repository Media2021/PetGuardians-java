package pets.example.guardians.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pets.example.guardians.repository.entity.PetEntity;

import java.util.List;


@Repository
public interface PetRepo extends JpaRepository<PetEntity, Long> {


//    @Query("SELECT COUNT(p) FROM PetEntity p WHERE p.status = 'ADOPTED' AND p.type = 0")
//    int countByStatusAdoptedAndTypeDog();
//
//    @Query("SELECT COUNT(p) FROM PetEntity p WHERE p.status = 'ADOPTED' AND p.type = 1")
//    int countByStatusAdoptedAndTypeCat();

    @Query("SELECT COUNT(p) FROM PetEntity p WHERE p.status = 'AVAILABLE'")
    int countByStatusAvailable();

    @Query("SELECT COUNT(p) FROM PetEntity p")
    int countAllPets();

    @Query("" +
            "SELECT p.type AS type, COUNT(p) AS count " +
            "FROM PetEntity p " +
            "WHERE p.status = 'ADOPTED' " +
            "GROUP BY p.type")
    List<PetTypeCount> countByStatusAdoptedAndType();
    @Query("" +
            "SELECT p.type AS type, COUNT(p) AS count " +
            "FROM PetEntity p " +
            "WHERE p.status = 'AVAILABLE' " +
            "GROUP BY p.type")
    List<AvailableListPetType> countByStatusAvailableAndType();

    @Query("SELECT p FROM PetEntity p WHERE p.status = 'AVAILABLE'")
    List<PetEntity> findAvailablePets();



}
