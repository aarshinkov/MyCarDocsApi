package com.atanasvasil.api.mycardocs.services;

import com.atanasvasil.api.mycardocs.dao.ExpensesDao;
import com.atanasvasil.api.mycardocs.entities.FuelExpenseEntity;
import com.atanasvasil.api.mycardocs.exceptions.MCDException;
import com.atanasvasil.api.mycardocs.requests.expenses.fuel.FuelExpenseCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpensesDao expensesDao;

    @Override
    public FuelExpenseEntity createFuelExpense(FuelExpenseCreateRequest fecr) throws MCDException {
        return expensesDao.createFuelExpense(fecr);
    }
}
