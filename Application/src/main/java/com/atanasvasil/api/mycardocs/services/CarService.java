package com.atanasvasil.api.mycardocs.services;

import com.atanasvasil.api.mycardocs.entities.CarEntity;
import com.atanasvasil.api.mycardocs.requests.cars.CarCreateRequest;
import com.atanasvasil.api.mycardocs.requests.cars.CarUpdateRequest;

import java.util.List;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public interface CarService {

    List<CarEntity> getCars();

    List<CarEntity> getCarsByOwner(String userId);

    CarEntity getCarByCarId(String carId);

    CarEntity getCarByLicensePlate(String licensePlate);

    CarEntity createCar(CarCreateRequest ccr) throws Exception;

    CarEntity updateCar(String carId, CarUpdateRequest cur) throws Exception;

    void deleteCar(String carId) throws Exception;

    Boolean hasUserCars(String userId);
    
    Long getCarsCountByUserId(String userId);
}
