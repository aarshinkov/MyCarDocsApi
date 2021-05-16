package com.atanasvasil.api.mycardocs.responses.expenses.service;

import java.io.Serializable;
import com.atanasvasil.api.mycardocs.responses.cars.*;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceExpenseGetResponse implements Serializable {

    private String serviceExpenseId;
    private ServiceExpenseTypeGetResponse type;
    private CarGetResponse car;
    private Double price;
    private String notes;
    private Long mileage;
    private Timestamp createdOn;
    private Timestamp editedOn;
}
