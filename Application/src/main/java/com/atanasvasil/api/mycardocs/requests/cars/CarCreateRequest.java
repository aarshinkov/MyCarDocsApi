package com.atanasvasil.api.mycardocs.requests.cars;

import lombok.*;

import java.io.Serializable;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarCreateRequest implements Serializable {

    private String brand;
    private String model;
    private String color;
    private Integer year;
    private String licensePlate;
    private String alias;
    private Long userId;
}
