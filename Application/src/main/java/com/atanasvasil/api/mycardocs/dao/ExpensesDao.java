package com.atanasvasil.api.mycardocs.dao;

import com.atanasvasil.api.mycardocs.entities.FuelExpenseEntity;
import com.atanasvasil.api.mycardocs.exceptions.MCDException;
import com.atanasvasil.api.mycardocs.requests.expenses.fuel.FuelExpenseCreateRequest;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
public interface ExpensesDao {

    FuelExpenseEntity createFuelExpense(FuelExpenseCreateRequest fecr) throws MCDException;
}
