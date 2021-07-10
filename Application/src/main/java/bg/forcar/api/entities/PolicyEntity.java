package bg.forcar.api.entities;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
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
@Entity
@Table(name = "policies")
@DynamicInsert
@DynamicUpdate
public class PolicyEntity implements Serializable {

    @Id
    @Column(name = "policy_id")
    private String policyId;

    @Column(name = "number")
    private String number;

    @Column(name = "type")
    private Integer type;

    @Column(name = "ins_name")
    private String insName;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "car_id")
    private CarEntity car;

    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "end_date")
    private Timestamp endDate;
}
