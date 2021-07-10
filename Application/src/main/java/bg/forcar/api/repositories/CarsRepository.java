package bg.forcar.api.repositories;

import bg.forcar.api.entities.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Repository
public interface CarsRepository extends JpaRepository<CarEntity, String> {

    CarEntity findByCarId(String carId);

    CarEntity findByLicensePlate(String licensePlate);

    CarEntity findByLicensePlateContaining(String licensePlate);

    CarEntity findByAliasContaining(String alias);

    CarEntity findByAlias(String alias);

    List<CarEntity> findAllByOwnerUserId(String userId);

    List<CarEntity> findAllByOwnerEmail(String email);

    Long countByOwnerUserId(String userId);
}
