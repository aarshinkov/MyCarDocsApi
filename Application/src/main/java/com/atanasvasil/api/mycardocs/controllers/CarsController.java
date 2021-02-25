package com.atanasvasil.api.mycardocs.controllers;

import com.atanasvasil.api.mycardocs.entities.CarEntity;
import com.atanasvasil.api.mycardocs.requests.cars.CarCreateRequest;
import com.atanasvasil.api.mycardocs.requests.cars.CarUpdateRequest;
import com.atanasvasil.api.mycardocs.responses.cars.CarGetResponse;
import com.atanasvasil.api.mycardocs.services.CarService;
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

import static com.atanasvasil.api.mycardocs.utils.Utils.getCarFromEntity;

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
    @GetMapping(value = "/api/cars/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarGetResponse>> getUserCars(@PathVariable("userId") Long userId) {

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
    @GetMapping(value = "/api/cars/{carId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarGetResponse> getCar(@PathVariable("carId") String carId) {

        CarEntity car = carService.getCarByCarId(carId);

        if (car == null) {
            return new ResponseEntity<>(new CarGetResponse(), HttpStatus.BAD_REQUEST);
        }

        CarGetResponse cgr = getCarFromEntity(car);

        return new ResponseEntity<>(cgr, HttpStatus.OK);
    }

    @ApiOperation(value = "Create car")
    @PostMapping(value = "/api/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarGetResponse> createCar(@RequestBody CarCreateRequest ccr) {

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
    @PutMapping(value = "/api/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarGetResponse> updateCar(@RequestBody CarUpdateRequest cur) {

        try {
            CarEntity updatedCar = carService.updateCar(cur);

            CarGetResponse cgr = getCarFromEntity(updatedCar);

            return new ResponseEntity<>(cgr, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new CarGetResponse(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Delete car")
    @DeleteMapping(value = "/api/cars/{carId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteCar(@PathVariable("carId") String carId) {

        try {
            carService.deleteCar(carId);
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
        }
    }
}
