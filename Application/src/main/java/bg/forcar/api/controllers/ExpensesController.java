package bg.forcar.api.controllers;

import bg.forcar.api.responses.expenses.service.ServiceExpenseGetResponse;
import bg.forcar.api.responses.expenses.fuel.FuelExpenseGetResponse;
import bg.forcar.api.requests.expenses.ExpenseSummaryRequest;
import bg.forcar.api.entities.ServiceExpenseEntity;
import bg.forcar.api.entities.FuelExpenseEntity;
import bg.forcar.api.collections.FuelExpensesCollection;
import bg.forcar.api.collections.ObjCollection;
import bg.forcar.api.collections.ServiceExpensesCollection;
import bg.forcar.api.requests.expenses.fuel.FuelExpenseCreateRequest;
import bg.forcar.api.requests.expenses.service.ServiceExpenseCreateRequest;
import bg.forcar.api.responses.expenses.ExpensesSummaryResponse;
import bg.forcar.api.services.ExpenseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static bg.forcar.api.utils.Utils.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@RestController
@Api(value = "Expenses", tags = "Expenses")
public class ExpensesController {

    @Autowired
    private ExpenseService expenseService;

    // FUEL
    @ApiOperation(value = "Get fuel expenses for user")
    @PreAuthorize("@securityExpressions.isUserSelf(#principal, #userId)")
    @GetMapping(value = "/api/expenses/fuel")
    public ResponseEntity<ObjCollection<FuelExpenseGetResponse>> getFuelExpensesForUser(@RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "6") Integer limit, @RequestParam(name = "userId", required = true) String userId, Principal principal) {

        if (page <= 0) {
            page = 1;
        }

        if (limit <= 0) {
            limit = 6;
        }

        ObjCollection<FuelExpenseEntity> fuelExpenses = expenseService.getFuelExpensesByUser(page, limit, userId);
        ObjCollection<FuelExpenseGetResponse> result = new FuelExpensesCollection<>();
        result.setPage(fuelExpenses.getPage());
        List<FuelExpenseGetResponse> fuelExpensesCollection = getFuelExpensesResponseFromEntity(fuelExpenses.getData());
        result.setData(fuelExpensesCollection);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get fuel expense")
    @PreAuthorize("@securityExpressions.isUserOwnerOfFuelExpense(#principal, #fuelExpenseId)")
    @GetMapping(value = "/api/expenses/fuel/{fuelExpenseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FuelExpenseGetResponse> getFuelExpense(@PathVariable("fuelExpenseId") String fuelExpenseId, Principal principal) {

        FuelExpenseEntity fuelExpense = expenseService.getFuelExpenseById(fuelExpenseId);

        if (fuelExpense == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        FuelExpenseGetResponse fegr = getFuelExpenseFromEntity(fuelExpense);

        return new ResponseEntity<>(fegr, HttpStatus.OK);
    }

    @ApiOperation(value = "Create fuel expense")
    @PreAuthorize("@securityExpressions.isUserSelf(#principal, #userId) and @securityExpressions.isUserOwnerOfCar(#principal, #fecr.carId)")
    @PostMapping(value = "/api/expenses/fuel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FuelExpenseGetResponse> createFuelExpense(@RequestBody FuelExpenseCreateRequest fecr,
            @RequestParam(name = "userId", required = true) String userId, Principal principal) {

        FuelExpenseEntity createdFuelExpense = expenseService.createFuelExpense(fecr);

        FuelExpenseGetResponse fegr = getFuelExpenseFromEntity(createdFuelExpense);

        return new ResponseEntity<>(fegr, HttpStatus.CREATED);
    }

    // SERVICE
    @ApiOperation(value = "Get service expenses for user")
    @PreAuthorize("@securityExpressions.isUserSelf(#principal, #userId)")
    @GetMapping(value = "/api/expenses/service")
    public ResponseEntity<ObjCollection<ServiceExpenseGetResponse>> getServiceExpensesForUser(@RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "6") Integer limit, @RequestParam(name = "userId", required = true) String userId, Principal principal) {

        if (page <= 0) {
            page = 1;
        }

        if (limit <= 0) {
            limit = 6;
        }

        ObjCollection<ServiceExpenseEntity> serviceExpenses = expenseService.getServiceExpensesByUser(page, limit, userId);
        ObjCollection<ServiceExpenseGetResponse> result = new ServiceExpensesCollection<>();
        result.setPage(serviceExpenses.getPage());
        List<ServiceExpenseGetResponse> serviceExpensesCollection = getServiceExpensesResponseFromEntity(serviceExpenses.getData());
        result.setData(serviceExpensesCollection);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get service expense")
    @PreAuthorize("@securityExpressions.isUserOwnerOfServiceExpense(#principal, #serviceExpenseId)")
    @GetMapping(value = "/api/expenses/service/{serviceExpenseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceExpenseGetResponse> getServiceExpense(@PathVariable("serviceExpenseId") String serviceExpenseId, Principal principal) {

        ServiceExpenseEntity serviceExpense = expenseService.getServiceExpenseById(serviceExpenseId);

        if (serviceExpense == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        ServiceExpenseGetResponse fegr = getServiceExpenseFromEntity(serviceExpense);

        return new ResponseEntity<>(fegr, HttpStatus.OK);
    }

    @ApiOperation(value = "Create service expense")
    @PreAuthorize("@securityExpressions.isUserSelf(#principal, #userId) and @securityExpressions.isUserOwnerOfCar(#principal, #secr.carId)")
    @PostMapping(value = "/api/expenses/service", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceExpenseGetResponse> createServiceExpense(@RequestBody ServiceExpenseCreateRequest secr,
            @RequestParam(name = "userId", required = true) String userId, Principal principal) {

        ServiceExpenseEntity createdServiceExpense = expenseService.createServiceExpense(secr);

        ServiceExpenseGetResponse segr = getServiceExpenseFromEntity(createdServiceExpense);

        return new ResponseEntity<>(segr, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get expenses summary")
    @GetMapping(value = "/api/expenses/summary", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExpensesSummaryResponse> getExpensesSummary(ExpenseSummaryRequest esr) {

        ExpensesSummaryResponse response = expenseService.getExpensesSummary(esr);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
