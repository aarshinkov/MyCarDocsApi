package bg.forcar.api.responses.expenses;

import java.io.Serializable;
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
public class ExpensesSummaryResponse implements Serializable {

    private String userId;
    private String carId;
    private Integer year;
    private List<ExpenseSummaryItem> fuel;
    private List<ExpenseSummaryItem> service;
}
