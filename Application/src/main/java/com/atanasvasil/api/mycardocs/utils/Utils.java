package com.atanasvasil.api.mycardocs.utils;

import com.atanasvasil.api.mycardocs.entities.*;
import com.atanasvasil.api.mycardocs.responses.cars.CarGetResponse;
import com.atanasvasil.api.mycardocs.responses.expenses.fuel.FuelExpenseGetResponse;
import com.atanasvasil.api.mycardocs.responses.expenses.service.*;
import com.atanasvasil.api.mycardocs.responses.policies.PolicyGetResponse;
import com.atanasvasil.api.mycardocs.responses.users.*;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static UserGetResponse getUserFromEntity(UserEntity user) {
        UserGetResponse ugr = new UserGetResponse();
        ugr.setUserId(user.getUserId());
        ugr.setEmail(user.getEmail());
        ugr.setFirstName(user.getFirstName());
        ugr.setLastName(user.getLastName());
        ugr.setCreatedOn(user.getCreatedOn());
        ugr.setEditedOn(user.getEditedOn());

        List<RoleGetResponse> roles = new ArrayList<>();

        for (RoleEntity storedRole : user.getRoles()) {
            RoleGetResponse role = new RoleGetResponse();
            role.setRole(storedRole.getRole());
            roles.add(role);
        }

        ugr.setRoles(roles);

        return ugr;
    }

    public static CarGetResponse getCarFromEntity(CarEntity car) {
        CarGetResponse cgr = new CarGetResponse();
        cgr.setCarId(car.getCarId());
        cgr.setBrand(car.getBrand());
        cgr.setModel(car.getModel());
        cgr.setColor(car.getColor());
        cgr.setTransmission(car.getTransmission());
        cgr.setPowerType(car.getPowerType());
        cgr.setYear(car.getYear());
        cgr.setLicensePlate(car.getLicensePlate());
        cgr.setAlias(car.getAlias());

        UserGetResponse ugr = getUserFromEntity(car.getOwner());

        cgr.setOwner(ugr);
        cgr.setAddedOn(car.getAddedOn());
        cgr.setEditedOn(car.getEditedOn());

        return cgr;
    }

    public static List<PolicyGetResponse> getPoliciesFromEntities(List<PolicyEntity> storedPolicies) {

        List<PolicyGetResponse> policies = new ArrayList<>();

        for (PolicyEntity policy : storedPolicies) {
            PolicyGetResponse pgr = getPolicyFromEntity(policy);
            policies.add(pgr);
        }

        return policies;
    }

    public static PolicyGetResponse getPolicyFromEntity(PolicyEntity policy) {
        PolicyGetResponse pgr = new PolicyGetResponse();

        pgr.setPolicyId(policy.getPolicyId());
        pgr.setNumber(policy.getNumber());
        pgr.setType(policy.getType());
        pgr.setInsName(policy.getInsName());

        pgr.setCar(getCarFromEntity(policy.getCar()));

        pgr.setStartDate(policy.getStartDate());
        pgr.setEndDate(policy.getEndDate());

        return pgr;
    }

    // FUEL
    public static List<FuelExpenseGetResponse> getFuelExpensesResponseFromEntity(List<FuelExpenseEntity> fuelExpenses) {

        List<FuelExpenseGetResponse> result = new ArrayList<>();

        for (FuelExpenseEntity fuelExpense : fuelExpenses) {
            FuelExpenseGetResponse fuelExpenseGetResponse = getFuelExpenseFromEntity(fuelExpense);
            result.add(fuelExpenseGetResponse);
        }

        return result;
    }

    public static FuelExpenseGetResponse getFuelExpenseFromEntity(FuelExpenseEntity fuelExpense) {

        FuelExpenseGetResponse fegr = new FuelExpenseGetResponse();
        fegr.setFuelExpenseId(fuelExpense.getFuelExpenseId());
        fegr.setPricePerLitre(fuelExpense.getPricePerLitre());
        fegr.setLitres(fuelExpense.getLitres());
        fegr.setDiscount(fuelExpense.getDiscount());
        fegr.setMileage(fuelExpense.getMileage());
        fegr.setCar(getCarFromEntity(fuelExpense.getCar()));
        fegr.setCreatedOn(fuelExpense.getCreatedOn());
        fegr.setEditedOn(fuelExpense.getEditedOn());

        return fegr;
    }

    // SERVICE
    public static List<ServiceExpenseGetResponse> getServiceExpensesResponseFromEntity(List<ServiceExpenseEntity> serviceExpenses) {

        List<ServiceExpenseGetResponse> result = new ArrayList<>();

        for (ServiceExpenseEntity serviceExpense : serviceExpenses) {
            ServiceExpenseGetResponse serviceExpenseGetResponse = getServiceExpenseFromEntity(serviceExpense);
            result.add(serviceExpenseGetResponse);
        }

        return result;
    }

    public static ServiceExpenseGetResponse getServiceExpenseFromEntity(ServiceExpenseEntity serviceExpense) {

        ServiceExpenseGetResponse segr = new ServiceExpenseGetResponse();
        segr.setServiceExpenseId(serviceExpense.getServiceExpenseId());

        ServiceExpenseTypeGetResponse type = new ServiceExpenseTypeGetResponse();
        type.setType(serviceExpense.getType().getType());
        type.setTypeDescription(serviceExpense.getType().getTypeDescription());
        segr.setType(type);

        segr.setCar(getCarFromEntity(serviceExpense.getCar()));
        segr.setPrice(serviceExpense.getPrice());
        segr.setNotes(serviceExpense.getNotes());
        segr.setCreatedOn(serviceExpense.getCreatedOn());
        segr.setEditedOn(serviceExpense.getEditedOn());

        return segr;
    }
}
