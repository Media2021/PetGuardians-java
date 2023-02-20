package pets.example.guardians.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pets.example.guardians.Entity.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long>
{
}
