package com.atanasvasil.api.mycardocs.controllers;

import com.atanasvasil.api.mycardocs.entities.FuelExpenseEntity;
import com.atanasvasil.api.mycardocs.requests.expenses.fuel.FuelExpenseCreateRequest;
import com.atanasvasil.api.mycardocs.responses.expenses.fuel.*;
import com.atanasvasil.api.mycardocs.services.ExpenseService;
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

import static com.atanasvasil.api.mycardocs.utils.Utils.getFuelExpenseFromEntity;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@RestController
@Api(value = "Expenses", tags = "Expenses")
public class ExpensesController {

    @Autowired
    private ExpenseService expenseService;

    @ApiOperation(value = "Create fuel expense")
    @PreAuthorize("@securityExpressions.isUserSelf(#principal, #fecr.userId) and @securityExpressions.isUserOwnerOfCar(#principal, #fecr.carId)")
    @PostMapping(value = "/api/expenses/fuel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FuelExpenseGetResponse> createFuelExpense(@RequestBody FuelExpenseCreateRequest fecr, Principal principal) {

        FuelExpenseEntity createdFuelExpense = expenseService.createFuelExpense(fecr);

        FuelExpenseGetResponse fegr = getFuelExpenseFromEntity(createdFuelExpense);

        return new ResponseEntity<>(fegr, HttpStatus.CREATED);
    }
}
