package com.atanasvasil.api.mycardocs.responses.expenses.service;

import java.io.Serializable;
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
public class ServiceExpenseTypeGetResponse implements Serializable {

    private Integer type;
    private String typeDescription;
}
