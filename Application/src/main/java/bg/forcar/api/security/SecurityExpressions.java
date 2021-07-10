package bg.forcar.api.security;

import bg.forcar.api.repositories.PoliciesRepository;
import bg.forcar.api.repositories.ServiceExpensesRepository;
import bg.forcar.api.repositories.CarsRepository;
import bg.forcar.api.repositories.UsersRepository;
import bg.forcar.api.repositories.FuelExpensesRepository;
import bg.forcar.api.entities.ServiceExpenseEntity;
import bg.forcar.api.entities.PolicyEntity;
import bg.forcar.api.entities.CarEntity;
import bg.forcar.api.entities.FuelExpenseEntity;
import bg.forcar.api.entities.UserEntity;
import java.security.Principal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public class SecurityExpressions {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CarsRepository carsRepository;

    @Autowired
    private PoliciesRepository policiesRepository;

    @Autowired
    private FuelExpensesRepository fuelExpensesRepository;

    @Autowired
    private ServiceExpensesRepository serviceExpensesRepository;

    public boolean isUserSelf(Principal principal, String userId) {

        if (principal == null) {
            log.debug("User is not authenticated");
            return false;
        }

        final String email = principal.getName();

        UserEntity user = usersRepository.findByEmail(email);

        boolean result = userId.equals(user.getUserId());

        if (result) {
            log.debug("The user is the one whom is requested to");
        } else {
            log.debug("The user is not the same as the requested");
        }

        return result;
    }

    public boolean isUserOwnerOfPolicy(Principal principal, String policyId) {

        if (principal == null) {
            log.debug("User is not authenticated");
            return false;
        }

        final String email = principal.getName();

        List<PolicyEntity> policies = policiesRepository.findAllByCarOwnerEmail(email);

        for (PolicyEntity policy : policies) {
            if (policyId.equals(policy.getPolicyId())) {
                log.debug("User \"" + email + "\" is owner of policy with ID: " + policyId);
                return true;
            }
        }

        log.debug("User \"" + email + "\" is NOT the owner of policy with ID: " + policyId);

        return false;
    }

    public boolean isUserOwnerOfCar(Principal principal, String carId) {

        if (principal == null) {
            log.debug("User is not authenticated");
            return false;
        }

        final String email = principal.getName();

        List<CarEntity> cars = carsRepository.findAllByOwnerEmail(email);

        for (CarEntity car : cars) {
            if (carId.equals(car.getCarId())) {
                log.debug("User \"" + email + "\" is owner of car with ID: " + carId);
                return true;
            }
        }

        log.debug("User \"" + email + "\" is NOT the owner of car with ID: " + carId);

        return false;
    }

    public boolean isUserOwnerOfCarWithLicensePlate(Principal principal, String licensePlate) {

        if (principal == null) {
            log.debug("User is not authenticated");
            return false;
        }

        final String email = principal.getName();

        List<CarEntity> cars = carsRepository.findAllByOwnerEmail(email);

        for (CarEntity car : cars) {
            if (licensePlate.equals(car.getLicensePlate())) {
                log.debug("User \"" + email + "\" is owner of car with license plate: " + licensePlate);
                return true;
            }
        }

        log.debug("User \"" + email + "\" is NOT the owner of car with license plate: " + licensePlate);

        return false;
    }

    public boolean isUserOwnerOfFuelExpense(Principal principal, String fuelExpenseId) {

        if (principal == null) {
            log.debug("User is not authenticated");
            return false;
        }

        final String email = principal.getName();

        List<FuelExpenseEntity> fuelExpenses = fuelExpensesRepository.findAllByCarOwnerEmail(email);

        for (FuelExpenseEntity fuelExpense : fuelExpenses) {
            if (fuelExpenseId.equals(fuelExpense.getFuelExpenseId())) {
                log.debug("User \"" + email + "\" is owner of fuel expense with ID: " + fuelExpenseId);
                return true;
            }
        }

        log.debug("User \"" + email + "\" is NOT the owner of fuel expense with ID: " + fuelExpenseId);

        return false;
    }

    public boolean isUserOwnerOfServiceExpense(Principal principal, String serviceExpenseId) {

        if (principal == null) {
            log.debug("User is not authenticated");
            return false;
        }

        final String email = principal.getName();

        List<ServiceExpenseEntity> serviceExpenses = serviceExpensesRepository.findAllByCarOwnerEmail(email);

        for (ServiceExpenseEntity serviceExpense : serviceExpenses) {
            if (serviceExpenseId.equals(serviceExpense.getServiceExpenseId())) {
                log.debug("User \"" + email + "\" is owner of servce expense with ID: " + serviceExpenseId);
                return true;
            }
        }

        log.debug("User \"" + email + "\" is NOT the owner of service expense with ID: " + serviceExpenseId);

        return false;
    }
}
