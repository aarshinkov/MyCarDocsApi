package bg.forcar.api.responses.expenses.fuel;

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
public class FuelExpenseGetResponse implements Serializable {

    private String fuelExpenseId;
    private Double pricePerLitre;
    private Double litres;
    private Double discount;
    private Long mileage;
    private CarGetResponse car;
    private Timestamp createdOn;
    private Timestamp editedOn;
}
