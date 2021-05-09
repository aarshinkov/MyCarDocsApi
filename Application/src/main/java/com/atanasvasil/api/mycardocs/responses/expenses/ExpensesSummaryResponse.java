package com.atanasvasil.api.mycardocs.responses.expenses;

import java.util.List;
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
public class ExpensesSummaryResponse {

    private String userId;
    private String carId;
    private Integer year;
    private List<ExpenseSummaryItem> fuel;
    private List<ExpenseSummaryItem> service;
}
