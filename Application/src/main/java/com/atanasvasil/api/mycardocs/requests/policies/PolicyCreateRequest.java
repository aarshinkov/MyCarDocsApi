package com.atanasvasil.api.mycardocs.requests.policies;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.3.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PolicyCreateRequest implements Serializable {

    private String number;
    private Integer type;
    private String insName;
    private String carId;
    private Date startDate;
    private Date endDate;
}
