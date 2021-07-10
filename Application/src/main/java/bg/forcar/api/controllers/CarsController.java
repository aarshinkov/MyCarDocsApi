package bg.forcar.api.controllers;

import bg.forcar.api.entities.CarEntity;
import bg.forcar.api.requests.cars.CarCreateRequest;
import bg.forcar.api.requests.cars.CarUpdateRequest;
import bg.forcar.api.responses.cars.CarGetResponse;
import bg.forcar.api.services.CarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import static bg.forcar.api.utils.Utils.getCarFromEntity;
import java.security.Principal;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@RestController
@Api(value = "Cars", tags = "Cars")
public class CarsController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CarService carService;

    @ApiOperation(value = "Get cars")
    @GetMapping(value = "/api/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarGetResponse>> getCars() {

        List<CarEntity> storedCars = carService.getCars();

        if (storedCars.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

        List<CarGetResponse> cars = new ArrayList<>();

        for (CarEntity car : storedCars) {
            CarGetResponse cgr = getCarFromEntity(car);
            cars.add(cgr);
        }

        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @ApiOperation(value = "Get user cars")
    @PreAuthorize("@securityExpressions.isUserSelf(#principal, #userId)")
    @GetMapping(value = "/api/cars/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarGetResponse>> getUserCars(@PathVariable("userId") String userId, Principal principal) {

        List<CarEntity> storedUserCars = carService.getCarsByOwner(userId);

        if (storedUserCars.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

        List<CarGetResponse> userCars = new ArrayList<>();

        for (CarEntity car : storedUserCars) {
            CarGetResponse cgr = getCarFromEntity(car);
            userCars.add(cgr);
        }

        return new ResponseEntity<>(userCars, HttpStatus.OK);
    }

    @ApiOperation(value = "Get particular car")
    @PreAuthorize("@securityExpressions.isUserOwnerOfCar(#principal, #carId)")
    @GetMapping(value = "/api/cars/{carId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarGetResponse> getCar(@PathVariable("carId") String carId, Principal principal) {

        CarEntity car = carService.getCarByCarId(carId);

        if (car == null) {
            return new ResponseEntity<>(new CarGetResponse(), HttpStatus.BAD_REQUEST);
        }

        CarGetResponse cgr = getCarFromEntity(car);

        return new ResponseEntity<>(cgr, HttpStatus.OK);
    }

    @ApiOperation(value = "Get car by license plate")
    @PreAuthorize("@securityExpressions.isUserOwnerOfCarWithLicensePlate(#principal, #licensePlate)")
    @GetMapping(value = "/api/cars/license/{licensePlate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarGetResponse> getCarByLicensePlate(@PathVariable("licensePlate") String licensePlate, Principal principal) {

        CarEntity car = carService.getCarByLicensePlate(licensePlate);

        if (car == null) {
            return new ResponseEntity<>(new CarGetResponse(), HttpStatus.BAD_REQUEST);
        }

        CarGetResponse cgr = getCarFromEntity(car);

        return new ResponseEntity<>(cgr, HttpStatus.OK);
    }

    @ApiOperation(value = "Create car")
    @PreAuthorize("@securityExpressions.isUserSelf(#principal, #ccr.userId)")
    @PostMapping(value = "/api/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarGetResponse> createCar(@RequestBody CarCreateRequest ccr, Principal principal) {

        if (ccr.getAlias().trim().replaceAll("\\s+", "").isEmpty()) {
            ccr.setAlias(null);
        }

        try {
            CarEntity createdCar = carService.createCar(ccr);

            CarGetResponse cgr = getCarFromEntity(createdCar);

            return new ResponseEntity<>(cgr, HttpStatus.CREATED);

        } catch (Exception e) {
            log.error("Error creating car", e);
            return new ResponseEntity<>(new CarGetResponse(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Update car")
    @PreAuthorize("@securityExpressions.isUserOwnerOfCar(#principal, #carId) and @securityExpressions.isUserSelf(#principal, #cur.userId)")
    @PutMapping(value = "/api/cars/{carId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarGetResponse> updateCar(@PathVariable("carId") String carId, @RequestBody CarUpdateRequest cur, Principal principal) {

        try {
            CarEntity updatedCar = carService.updateCar(carId, cur);

            CarGetResponse cgr = getCarFromEntity(updatedCar);

            return new ResponseEntity<>(cgr, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new CarGetResponse(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Delete car")
    @PreAuthorize("@securityExpressions.isUserOwnerOfCar(#principal, #carId)")
    @DeleteMapping(value = "/api/cars/{carId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteCar(@PathVariable("carId") String carId, Principal principal) {

        try {
            carService.deleteCar(carId);
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Get cars count for user")
    @PreAuthorize("@securityExpressions.isUserSelf(#principal, #userId)")
    @GetMapping(value = "/api/cars/count/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> getCarsCountByUserId(@PathVariable("userId") String userId, Principal principal) {

        Long carsCount = carService.getCarsCountByUserId(userId);

        return new ResponseEntity(carsCount, HttpStatus.OK);
    }

    @ApiOperation(value = "Checks if user has cars")
    @PreAuthorize("@securityExpressions.isUserSelf(#principal, #userId)")
    @GetMapping(value = "/api/cars/{userId}/has", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> hasUserCars(@PathVariable("userId") String userId, Principal principal) {

        Boolean hasCars = carService.hasUserCars(userId);

        return new ResponseEntity(hasCars, HttpStatus.OK);
    }
}
