package com.atanasvasil.api.mycardocs.dao;

import com.atanasvasil.api.mycardocs.entities.CarEntity;
import com.atanasvasil.api.mycardocs.entities.FuelExpenseEntity;
import com.atanasvasil.api.mycardocs.exceptions.MCDException;
import com.atanasvasil.api.mycardocs.repositories.CarsRepository;
import com.atanasvasil.api.mycardocs.repositories.FuelExpensesRepository;
import com.atanasvasil.api.mycardocs.requests.expenses.fuel.FuelExpenseCreateRequest;
import com.atanasvasil.api.mycardocs.responses.expenses.fuel.FuelExpenseGetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Repository
public class ExpensesDaoImpl implements ExpensesDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CarsRepository carsRepository;

    @Autowired
    private FuelExpensesRepository fuelExpensesRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public FuelExpenseEntity createFuelExpense(FuelExpenseCreateRequest fecr) throws MCDException {

//        CarEntity car = carsRepository.findByLicensePlate(fecr.getCarId());
//        if (car == null) {
//            car = carsRepository.findByCarId(fecr.getCarIdentifier());
//        }
        CarEntity car = carsRepository.findByCarId(fecr.getCarId());

        if (car == null) {
            throw new MCDException(800, "Car not found", "Car with identifier " + fecr.getCarId() + " not found!", HttpStatus.NOT_FOUND);
        }

        final String sql = "INSERT INTO fuel_expenses (fuel_expense_id, price_per_litre, litres, discount, mileage, car_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            try {
                conn.setAutoCommit(false);

                final String fuelExpenseId = UUID.randomUUID().toString();

                pstmt.setString(1, fuelExpenseId);
                pstmt.setDouble(2, fecr.getPricePerLitre());
                pstmt.setDouble(3, fecr.getLitres());

                if (fecr.getDiscount() == null) {
                    pstmt.setNull(4, Types.NUMERIC);
                } else {
                    pstmt.setDouble(4, fecr.getDiscount());
                }

                if (fecr.getMileage() == null) {
                    pstmt.setNull(5, Types.NUMERIC);
                } else {
                    pstmt.setDouble(5, fecr.getMileage());
                }

                pstmt.setString(6, car.getCarId());
                pstmt.setString(7, fecr.getUserId());

                pstmt.execute();

                conn.commit();
                FuelExpenseEntity fuelExpense = fuelExpensesRepository.findByFuelExpenseId(fuelExpenseId);
                return fuelExpense;

            } catch (SQLException ex) {
                log.error("Error creating fuel expense", ex);
                conn.rollback();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            log.error("Error creating fuel expense", e);
        }

        return null;
    }
}
