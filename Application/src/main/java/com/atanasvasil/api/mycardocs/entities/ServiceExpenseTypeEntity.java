package com.atanasvasil.api.mycardocs.entities;

import java.io.*;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@Entity
@Table(name = "service_expense_types")
@DynamicUpdate
@DynamicInsert
public class ServiceExpenseTypeEntity implements Serializable {

    @Id
    @Column(name = "type")
    private Integer type;

    @Column(name = "type_description")
    private String typeDescription;
}
