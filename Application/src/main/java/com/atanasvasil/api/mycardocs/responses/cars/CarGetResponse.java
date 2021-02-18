package com.atanasvasil.api.mycardocs.responses.cars;

import com.atanasvasil.api.mycardocs.responses.users.UserGetResponse;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarGetResponse implements Serializable {

    private String carId;
    private String brand;
    private String model;
    private String color;
    private Integer year;
    private String licensePlate;
    private String alias;
    private UserGetResponse owner;
    private Timestamp addedOn;
    private Timestamp editedOn;
}
