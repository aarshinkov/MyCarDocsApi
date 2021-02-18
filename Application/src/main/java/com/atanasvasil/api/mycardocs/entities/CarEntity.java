package com.atanasvasil.api.mycardocs.entities;

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
@Table(name = "cars")
@DynamicInsert
@DynamicUpdate
public class CarEntity implements Serializable {

    @Id
    @Column(name = "car_id")
    private String carId;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "color")
    private String color;

    @Column(name = "year")
    private Integer year;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "alias")
    private String alias;

    @OneToOne
    @JoinColumn(name = "owner", referencedColumnName = "user_id")
    private UserEntity owner;

    @Column(name = "added_on")
    private Timestamp addedOn;

    @Column(name = "edited_on")
    private Timestamp editedOn;
}
