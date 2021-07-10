package bg.forcar.api.services;

import bg.forcar.api.entities.CarEntity;
import bg.forcar.api.entities.UserEntity;
import bg.forcar.api.repositories.CarsRepository;
import bg.forcar.api.requests.cars.CarCreateRequest;
import bg.forcar.api.requests.cars.CarUpdateRequest;
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
    private UserService userService;

    @Override
    public List<CarEntity> getCars() {
        return carsRepository.findAll();
    }

    @Override
    public List<CarEntity> getCarsByOwner(String userId) {
        return carsRepository.findAllByOwnerUserId(userId);
    }

    @Override
    public CarEntity getCarByCarId(String carId) {
        return carsRepository.findByCarId(carId);
    }

    @Override
    public CarEntity getCarByLicensePlate(String licensePlate) {
        return carsRepository.findByLicensePlateContaining(licensePlate);
    }

    @Override
    public CarEntity createCar(CarCreateRequest ccr) throws Exception {

        UserEntity owner = userService.getUserByUserId(ccr.getUserId());

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
    public CarEntity updateCar(String carId, CarUpdateRequest cur) throws Exception {

        CarEntity car = carsRepository.findByCarId(carId);

        if (car == null) {
            throw new Exception("Car does not exist");
        }

        car.setCarId(carId);
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

    @Override
    public Boolean hasUserCars(String userId) {

        log.debug("Checking if user has any cars");

        final Long carsCount = getCarsCountByUserId(userId);
        final boolean hasUserCars = carsCount != 0;

        if (hasUserCars) {
            log.debug("User has cars: " + carsCount);
        } else {
            log.debug("User hasn't got any cars");
        }

        return hasUserCars;
    }

    @Override
    public Long getCarsCountByUserId(String userId) {

        return carsRepository.countByOwnerUserId(userId);
    }
}
