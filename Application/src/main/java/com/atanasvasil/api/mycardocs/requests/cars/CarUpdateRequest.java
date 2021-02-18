package com.atanasvasil.api.mycardocs.requests.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarUpdateRequest {

    private String carId;
    private String brand;
    private String model;
    private String color;
    private Integer year;
    private String licensePlate;
    private String alias;
}
