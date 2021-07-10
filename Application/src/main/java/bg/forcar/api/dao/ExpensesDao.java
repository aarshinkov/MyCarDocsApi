package bg.forcar.api.dao;

import bg.forcar.api.entities.ServiceExpenseEntity;
import bg.forcar.api.entities.FuelExpenseEntity;
import bg.forcar.api.collections.ObjCollection;
import bg.forcar.api.exceptions.MCDException;
import bg.forcar.api.requests.expenses.ExpenseSummaryRequest;
import bg.forcar.api.requests.expenses.fuel.FuelExpenseCreateRequest;
import bg.forcar.api.requests.expenses.service.ServiceExpenseCreateRequest;
import bg.forcar.api.responses.expenses.ExpensesSummaryResponse;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
public interface ExpensesDao {

    ExpensesSummaryResponse getExpensesSummary(ExpenseSummaryRequest esr);

    // FUEL
    ObjCollection<FuelExpenseEntity> getFuelExpensesByUser(Integer page, Integer limit, String userId);

    FuelExpenseEntity createFuelExpense(FuelExpenseCreateRequest fecr) throws MCDException;

    // SERVICE
    ServiceExpenseEntity createServiceExpense(ServiceExpenseCreateRequest secr) throws MCDException;

    ObjCollection<ServiceExpenseEntity> getServiceExpensesByUser(Integer page, Integer limit, String userId);
}
