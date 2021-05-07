package com.atanasvasil.api.mycardocs.services;

import com.atanasvasil.api.mycardocs.collections.ObjCollection;
import com.atanasvasil.api.mycardocs.dao.ExpensesDao;
import com.atanasvasil.api.mycardocs.entities.*;
import com.atanasvasil.api.mycardocs.exceptions.MCDException;
import com.atanasvasil.api.mycardocs.repositories.*;
import com.atanasvasil.api.mycardocs.requests.expenses.fuel.FuelExpenseCreateRequest;
import com.atanasvasil.api.mycardocs.requests.expenses.service.ServiceExpenseCreateRequest;
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

    @Autowired
    private FuelExpensesRepository fuelExpensesRepository;

    @Autowired
    private ServiceExpensesRepository serviceExpensesRepository;

    // FUEL
    @Override
    public ObjCollection<FuelExpenseEntity> getFuelExpensesByUser(Integer page, Integer limit, String userId) {
        return expensesDao.getFuelExpensesByUser(page, limit, userId);
    }

    @Override
    public FuelExpenseEntity getFuelExpenseById(String fuelExpenseId) {
        return fuelExpensesRepository.findByFuelExpenseId(fuelExpenseId);
    }

    @Override
    public FuelExpenseEntity createFuelExpense(FuelExpenseCreateRequest fecr) throws MCDException {
        return expensesDao.createFuelExpense(fecr);
    }

    // SERVICE
    @Override
    public ObjCollection<ServiceExpenseEntity> getServiceExpensesByUser(Integer page, Integer limit, String userId) {
        return expensesDao.getServiceExpensesByUser(page, limit, userId);
    }

    @Override
    public ServiceExpenseEntity getServiceExpenseById(String serviceExpenseId) {
        return serviceExpensesRepository.findByServiceExpenseId(serviceExpenseId);
    }

    @Override
    public ServiceExpenseEntity createServiceExpense(ServiceExpenseCreateRequest secr) throws MCDException {
        return expensesDao.createServiceExpense(secr);
    }
}
