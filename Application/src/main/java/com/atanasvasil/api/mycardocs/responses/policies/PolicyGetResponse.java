package com.atanasvasil.api.mycardocs.responses.policies;

import com.atanasvasil.api.mycardocs.entities.CarEntity;
import com.atanasvasil.api.mycardocs.responses.cars.CarGetResponse;
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
public class PolicyGetResponse implements Serializable {

    private String policyId;
    private String number;
    private Integer type;
    private String insName;
    private CarGetResponse car;
    private Timestamp startDate;
    private Timestamp endDate;
}
