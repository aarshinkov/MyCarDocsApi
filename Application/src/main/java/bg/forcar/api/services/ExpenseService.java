package bg.forcar.api.services;

import bg.forcar.api.responses.expenses.ExpensesSummaryResponse;
import bg.forcar.api.entities.ServiceExpenseEntity;
import bg.forcar.api.entities.FuelExpenseEntity;
import bg.forcar.api.collections.ObjCollection;
import bg.forcar.api.exceptions.MCDException;
import bg.forcar.api.requests.expenses.ExpenseSummaryRequest;
import bg.forcar.api.requests.expenses.fuel.FuelExpenseCreateRequest;
import bg.forcar.api.requests.expenses.service.ServiceExpenseCreateRequest;

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
