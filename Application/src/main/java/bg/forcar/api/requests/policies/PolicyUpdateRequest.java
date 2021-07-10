package bg.forcar.api.requests.policies;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.3.1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PolicyUpdateRequest implements Serializable {

    private String policyId;
    private String number;
    private Integer type;
    private String insName;
    private String carId;
    private Date startDate;
    private Date endDate;
    private String userId;
}
