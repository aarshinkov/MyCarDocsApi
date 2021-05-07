package com.atanasvasil.api.mycardocs.requests.expenses.fuel;

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
public class FuelExpenseCreateRequest implements Serializable {

    private Double pricePerLitre;
    private Double litres;
    private Double discount;
    private Long mileage;
    private String carId;
}
