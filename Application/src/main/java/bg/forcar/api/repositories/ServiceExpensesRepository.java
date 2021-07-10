package bg.forcar.api.repositories;

import bg.forcar.api.entities.ServiceExpenseEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Repository
public interface ServiceExpensesRepository extends JpaRepository<ServiceExpenseEntity, String> {

    ServiceExpenseEntity findByServiceExpenseId(String serviceExpenseId);

    List<ServiceExpenseEntity> findAllByCarOwnerUserId(String userId);

    List<ServiceExpenseEntity> findAllByCarOwnerEmail(String email);
}
