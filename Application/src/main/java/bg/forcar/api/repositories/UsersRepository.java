package bg.forcar.api.repositories;

import bg.forcar.api.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUserId(String userId);

    UserEntity findByEmail(String email);

    UserEntity findByResetPassCode(String resetPassCode);

    boolean existsByEmail(String email);

    boolean existsByUserId(String userId);

    boolean existsByResetPassCode(String resetPassCode);
}
