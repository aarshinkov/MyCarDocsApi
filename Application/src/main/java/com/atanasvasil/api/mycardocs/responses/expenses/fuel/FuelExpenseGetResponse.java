package com.atanasvasil.api.mycardocs.responses.expenses.fuel;

import java.io.Serializable;
import com.atanasvasil.api.mycardocs.responses.cars.*;
import com.atanasvasil.api.mycardocs.responses.users.*;
import java.sql.Timestamp;
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
public class FuelExpenseGetResponse implements Serializable {

    private String fuelExpenseId;
    private Double pricePerLitre;
    private Double litres;
    private Double discount;
    private Long mileage;
    private CarGetResponse car;
    private UserGetResponse user;
    private Timestamp createdOn;
    private Timestamp editedOn;
}