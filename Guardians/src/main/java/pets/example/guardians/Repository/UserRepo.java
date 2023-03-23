package pets.example.guardians.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pets.example.guardians.Model.User;
import pets.example.guardians.Repository.Entity.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long>

{



}
