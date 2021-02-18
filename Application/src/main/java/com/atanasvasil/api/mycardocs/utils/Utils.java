package com.atanasvasil.api.mycardocs.utils;

import com.atanasvasil.api.mycardocs.entities.CarEntity;
import com.atanasvasil.api.mycardocs.entities.UserEntity;
import com.atanasvasil.api.mycardocs.responses.cars.CarGetResponse;
import com.atanasvasil.api.mycardocs.responses.users.UserGetResponse;

public class Utils {

    public static UserGetResponse getUserFromEntity(UserEntity user) {
        UserGetResponse ugr = new UserGetResponse();
        ugr.setUserId(user.getUserId());
        ugr.setEmail(user.getEmail());
        ugr.setFirstName(user.getFirstName());
        ugr.setLastName(user.getLastName());
        ugr.setEditedOn(user.getEditedOn());

        return ugr;
    }

    public static CarGetResponse getCarFromEntity(CarEntity car) {
        CarGetResponse cgr = new CarGetResponse();
        cgr.setCarId(car.getCarId());
        cgr.setBrand(car.getBrand());
        cgr.setModel(car.getModel());
        cgr.setColor(car.getColor());
        cgr.setYear(car.getYear());
        cgr.setLicensePlate(car.getLicensePlate());
        cgr.setAlias(car.getAlias());

        UserGetResponse ugr = getUserFromEntity(car.getOwner());

        cgr.setOwner(ugr);
        cgr.setAddedOn(car.getAddedOn());
        cgr.setEditedOn(car.getEditedOn());

        return cgr;
    }
}
