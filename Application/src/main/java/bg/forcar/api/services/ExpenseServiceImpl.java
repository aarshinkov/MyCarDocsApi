package bg.forcar.api.services;

import bg.forcar.api.repositories.ServiceExpensesRepository;
import bg.forcar.api.repositories.FuelExpensesRepository;
import bg.forcar.api.entities.ServiceExpenseEntity;
import bg.forcar.api.entities.FuelExpenseEntity;
import bg.forcar.api.collections.ObjCollection;
import bg.forcar.api.dao.ExpensesDao;
import bg.forcar.api.exceptions.MCDException;
import bg.forcar.api.requests.expenses.ExpenseSummaryRequest;
import bg.forcar.api.requests.expenses.fuel.FuelExpenseCreateRequest;
import bg.forcar.api.requests.expenses.service.ServiceExpenseCreateRequest;
import bg.forcar.api.responses.expenses.ExpensesSummaryResponse;
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

    @Override
    public ExpensesSummaryResponse getExpensesSummary(ExpenseSummaryRequest esr) {
        return expensesDao.getExpensesSummary(esr);
    }

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
