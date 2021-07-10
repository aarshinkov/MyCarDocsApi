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
@Table(name = "fuel_expenses")
@DynamicInsert
@DynamicUpdate
public class FuelExpenseEntity implements Serializable {

    @Id
    @Column(name = "fuel_expense_id")
    private String fuelExpenseId;

    @Column(name = "price_per_litre")
    private Double pricePerLitre;

    @Column(name = "litres")
    private Double litres;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "mileage")
    private Long mileage;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "car_id")
    private CarEntity car;

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
//    private UserEntity user;
    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "edited_on")
    private Timestamp editedOn;
}
