package com.atanasvasil.api.mycardocs.dao;

import com.atanasvasil.api.mycardocs.collections.ObjCollection;
import com.atanasvasil.api.mycardocs.entities.*;
import com.atanasvasil.api.mycardocs.exceptions.MCDException;
import com.atanasvasil.api.mycardocs.requests.expenses.fuel.FuelExpenseCreateRequest;
import com.atanasvasil.api.mycardocs.requests.expenses.service.ServiceExpenseCreateRequest;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
public interface ExpensesDao {

    // FUEL
    ObjCollection<FuelExpenseEntity> getFuelExpensesByUser(Integer page, Integer limit, String userId);

    FuelExpenseEntity createFuelExpense(FuelExpenseCreateRequest fecr) throws MCDException;

    // SERVICE
    ServiceExpenseEntity createServiceExpense(ServiceExpenseCreateRequest secr) throws MCDException;
    
    ObjCollection<ServiceExpenseEntity> getServiceExpensesByUser(Integer page, Integer limit, String userId);
}
