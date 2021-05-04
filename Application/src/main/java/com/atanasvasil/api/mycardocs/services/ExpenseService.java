package com.atanasvasil.api.mycardocs.services;

import com.atanasvasil.api.mycardocs.entities.FuelExpenseEntity;
import com.atanasvasil.api.mycardocs.exceptions.MCDException;
import com.atanasvasil.api.mycardocs.requests.expenses.fuel.FuelExpenseCreateRequest;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
public interface ExpenseService {

    FuelExpenseEntity createFuelExpense(FuelExpenseCreateRequest fecr) throws MCDException;
}
