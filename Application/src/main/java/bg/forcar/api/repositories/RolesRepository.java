package bg.forcar.api.repositories;

import bg.forcar.api.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Repository
public interface RolesRepository extends JpaRepository<RoleEntity, String> {

    RoleEntity findByRole(String role);

}
