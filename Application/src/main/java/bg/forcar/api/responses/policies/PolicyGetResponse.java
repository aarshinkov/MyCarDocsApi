package bg.forcar.api.responses.policies;

import bg.forcar.api.responses.cars.CarGetResponse;
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
