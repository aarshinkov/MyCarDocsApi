package bg.forcar.api.repositories;

import bg.forcar.api.entities.FuelExpenseEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Repository
public interface FuelExpensesRepository extends JpaRepository<FuelExpenseEntity, String> {

    FuelExpenseEntity findByFuelExpenseId(String fuelExpenseId);

    List<FuelExpenseEntity> findAllByCarOwnerUserId(String userId);

    List<FuelExpenseEntity> findAllByCarOwnerEmail(String email);
}
