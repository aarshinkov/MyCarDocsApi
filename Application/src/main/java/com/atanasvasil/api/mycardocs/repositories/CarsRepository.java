package com.atanasvasil.api.mycardocs.repositories;

import com.atanasvasil.api.mycardocs.entities.CarEntity;
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

    CarEntity findByOwnerUserId(Long userId);

    List<CarEntity> findAllByOwnerUserId(Long userId);
}
