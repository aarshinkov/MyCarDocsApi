package bg.forcar.api.entities;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "service_expenses")
@DynamicInsert
@DynamicUpdate
public class ServiceExpenseEntity implements Serializable {

    @Id
    @Column(name = "service_expense_id")
    private String serviceExpenseId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type", referencedColumnName = "type")
    private ServiceExpenseTypeEntity type;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "car_id")
    private CarEntity car;

    @Column(name = "price")
    private Double price;

    @Column(name = "notes")
    private String notes;

    @Column(name = "mileage")
    private Long mileage;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "edited_on")
    private Timestamp editedOn;
}
