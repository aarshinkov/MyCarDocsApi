package com.atanasvasil.api.mycardocs.repositories;

import com.atanasvasil.api.mycardocs.entities.PolicyEntity;
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

    List<PolicyEntity> findAllByCarOwnerUserIdOrderByStartDate(Long userId);

    Long countByCarOwnerUserId(Long userId);
}
