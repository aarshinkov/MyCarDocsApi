package com.atanasvasil.api.mycardocs.services;

import com.atanasvasil.api.mycardocs.entities.CarEntity;
import com.atanasvasil.api.mycardocs.entities.UserEntity;
import com.atanasvasil.api.mycardocs.repositories.CarsRepository;
import com.atanasvasil.api.mycardocs.repositories.UsersRepository;
import com.atanasvasil.api.mycardocs.requests.cars.CarCreateRequest;
import com.atanasvasil.api.mycardocs.requests.cars.CarUpdateRequest;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Service
public class CarServiceImpl implements CarService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CarsRepository carsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<CarEntity> getCars() {
        return carsRepository.findAll();
    }

    @Override
    public List<CarEntity> getCarsByOwner(Long userId) {
        return carsRepository.findAllByOwnerUserId(userId);
    }

    @Override
    public CarEntity getCarByCarId(String carId) {
        return carsRepository.findByCarId(carId);
    }

    @Override
    public CarEntity createCar(CarCreateRequest ccr) throws Exception {

        UserEntity owner = usersRepository.findByUserId(ccr.getUserId());

        if (owner == null) {
            throw new Exception("User does not exist");
        }

        CarEntity car = new CarEntity();
        car.setCarId(UUID.randomUUID().toString());
        car.setBrand(ccr.getBrand());
        car.setModel(ccr.getModel());
        car.setColor(ccr.getColor());
        car.setTransmission(ccr.getTransmission());
        car.setPowerType(ccr.getPowerType());
        car.setYear(ccr.getYear());
        car.setLicensePlate(ccr.getLicensePlate());
        car.setAlias(ccr.getAlias());
        car.setOwner(owner);

        return carsRepository.save(car);
    }

    @Override
    public CarEntity updateCar(CarUpdateRequest cur) throws Exception {

        CarEntity car = carsRepository.findByCarId(cur.getCarId());

        if (car == null) {
            throw new Exception("Car does not exist");
        }

        car.setCarId(cur.getCarId());
        car.setBrand(cur.getBrand());
        car.setModel(cur.getModel());
        car.setColor(cur.getColor());
        car.setTransmission(cur.getTransmission());
        car.setPowerType(cur.getPowerType());
        car.setYear(cur.getYear());
        car.setLicensePlate(cur.getLicensePlate());
        car.setAlias(cur.getAlias());
        car.setEditedOn(new Timestamp(System.currentTimeMillis()));

        return carsRepository.save(car);
    }

    @Override
    public void deleteCar(String carId) throws Exception {

        CarEntity car = carsRepository.findByCarId(carId);

        if (car == null) {
            throw new Exception("Car does not exist");
        }

        carsRepository.delete(car);
    }
}
