package com.atanasvasil.api.mycardocs.responses.expenses;

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
public class ExpenseSummaryItem implements Serializable {

    private Integer year;
    private Integer month;
    private Double total;
}
