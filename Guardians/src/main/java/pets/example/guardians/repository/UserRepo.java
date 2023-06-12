package pets.example.guardians.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pets.example.guardians.repository.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long>

{
    Optional<UserEntity> findByUsernameAndPassword(String username, String password);

    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT DISTINCT u" +
            " FROM UserEntity u" +
            " LEFT JOIN FETCH u.adoptedPets ap" +
            " WHERE size(ap) > 0")
    List<UserEntity> findAllWithAdoptedPets();




}
