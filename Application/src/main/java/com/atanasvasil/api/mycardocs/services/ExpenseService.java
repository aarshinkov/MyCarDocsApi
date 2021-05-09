package com.atanasvasil.api.mycardocs.services;

import com.atanasvasil.api.mycardocs.collections.ObjCollection;
import com.atanasvasil.api.mycardocs.entities.*;
import com.atanasvasil.api.mycardocs.exceptions.MCDException;
import com.atanasvasil.api.mycardocs.requests.expenses.ExpenseSummaryRequest;
import com.atanasvasil.api.mycardocs.requests.expenses.fuel.FuelExpenseCreateRequest;
import com.atanasvasil.api.mycardocs.requests.expenses.service.ServiceExpenseCreateRequest;
import com.atanasvasil.api.mycardocs.responses.expenses.*;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
public interface ExpenseService {

    ExpensesSummaryResponse getExpensesSummary(ExpenseSummaryRequest esr);

    // FUEL
    ObjCollection<FuelExpenseEntity> getFuelExpensesByUser(Integer page, Integer limit, String userId);

    FuelExpenseEntity getFuelExpenseById(String fuelExpenseId);

    FuelExpenseEntity createFuelExpense(FuelExpenseCreateRequest fecr) throws MCDException;

    // SERVICE
    ObjCollection<ServiceExpenseEntity> getServiceExpensesByUser(Integer page, Integer limit, String userId);

    ServiceExpenseEntity getServiceExpenseById(String serviceExpenseId);

    ServiceExpenseEntity createServiceExpense(ServiceExpenseCreateRequest secr) throws MCDException;
}
