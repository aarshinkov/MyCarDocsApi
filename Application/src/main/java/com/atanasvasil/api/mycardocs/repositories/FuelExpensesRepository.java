package com.atanasvasil.api.mycardocs.repositories;

import com.atanasvasil.api.mycardocs.entities.FuelExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Repository
public interface FuelExpensesRepository extends JpaRepository<FuelExpenseEntity, String> {

    FuelExpenseEntity findByFuelExpenseId(String fuelExpenseId);
}
