package pets.example.guardians.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pets.example.guardians.repository.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long>

{


    Optional<UserEntity> findByUsernameAndPassword(String username, String password);
}
