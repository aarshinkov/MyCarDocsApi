package bg.forcar.api.requests.expenses.service;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceExpenseCreateRequest implements Serializable {

    private Integer type;
    private String carId;
    private Double price;
    private String notes;
    private Long mileage;
}
