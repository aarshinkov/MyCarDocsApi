package bg.forcar.api.repositories;

import bg.forcar.api.entities.PolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Repository
public interface PoliciesRepository extends JpaRepository<PolicyEntity, String> {

    PolicyEntity findByPolicyId(String policyId);

    List<PolicyEntity> findAllByCarCarId(String carId);

    List<PolicyEntity> findAllByCarLicensePlate(String licensePlate);

    List<PolicyEntity> findAllByCarLicensePlateIgnoreCase(String licensePlate);

    List<PolicyEntity> findAllByCarOwnerUserIdOrderByStartDate(String userId);

    List<PolicyEntity> findAllByCarOwnerUserIdOrderByStartDateDesc(String userId);

    List<PolicyEntity> findAllByTypeAndCarOwnerUserIdOrderByStartDateDesc(Integer type, String userId);

    Long countByCarOwnerUserId(String userId);

    List<PolicyEntity> findAllByCarOwnerEmail(String email);
}
