package bg.forcar.api.dao;

import bg.forcar.api.utils.Page;
import bg.forcar.api.utils.PageImpl;
import bg.forcar.api.repositories.ServiceExpensesRepository;
import bg.forcar.api.repositories.FuelExpensesRepository;
import bg.forcar.api.entities.ServiceExpenseTypeEntity;
import bg.forcar.api.entities.ServiceExpenseEntity;
import bg.forcar.api.entities.FuelExpenseEntity;
import bg.forcar.api.collections.FuelExpensesCollection;
import bg.forcar.api.collections.ObjCollection;
import bg.forcar.api.collections.ServiceExpensesCollection;
import bg.forcar.api.entities.CarEntity;
import bg.forcar.api.exceptions.MCDException;
import bg.forcar.api.repositories.CarsRepository;
import bg.forcar.api.requests.expenses.ExpenseSummaryRequest;
import bg.forcar.api.requests.expenses.fuel.FuelExpenseCreateRequest;
import bg.forcar.api.requests.expenses.service.ServiceExpenseCreateRequest;
import bg.forcar.api.responses.expenses.ExpenseSummaryItem;
import bg.forcar.api.responses.expenses.ExpensesSummaryResponse;
import java.sql.CallableStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
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
    private ServiceExpensesRepository serviceExpensesRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ExpensesSummaryResponse getExpensesSummary(ExpenseSummaryRequest esr) {

        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource().getConnection());
                CallableStatement cstmt = conn.prepareCall("{? = call get_expenses_summary(?, ?, ?, ?)}")) {

            try {

                conn.setAutoCommit(false);

                cstmt.setString(1, esr.getUserId());

                if (esr.getCarId() == null) {
                    cstmt.setNull(2, Types.VARCHAR);
                } else {
                    cstmt.setString(2, esr.getCarId());
                }

                if (esr.getYear() == null) {
                    cstmt.setNull(3, Types.INTEGER);
                } else {
                    cstmt.setInt(3, esr.getYear());
                }

                cstmt.registerOutParameter(4, Types.REF_CURSOR);
                cstmt.registerOutParameter(5, Types.REF_CURSOR);

                cstmt.execute();

                ExpensesSummaryResponse response = new ExpensesSummaryResponse();
                response.setUserId(esr.getUserId());
                response.setCarId(esr.getCarId());
                response.setYear(esr.getYear());

                ResultSet rsetFuel = (ResultSet) cstmt.getObject(4);

                List<ExpenseSummaryItem> fuel = new ArrayList<>();

                while (rsetFuel.next()) {
                    ExpenseSummaryItem item = new ExpenseSummaryItem();
                    item.setYear(rsetFuel.getInt("year"));
                    item.setMonth(rsetFuel.getInt("month"));
                    item.setTotal(rsetFuel.getDouble("total"));

                    fuel.add(item);
                }

                response.setFuel(fuel);

                ResultSet rsetService = (ResultSet) cstmt.getObject(5);

                List<ExpenseSummaryItem> service = new ArrayList<>();

                while (rsetService.next()) {
                    ExpenseSummaryItem item = new ExpenseSummaryItem();
                    item.setYear(rsetService.getInt("year"));
                    item.setMonth(rsetService.getInt("month"));
                    item.setTotal(rsetService.getDouble("total"));

                    service.add(item);
                }

                response.setService(service);

                conn.commit();

                return response;

            } catch (SQLException ex) {
                conn.rollback();
                log.error("Error getting expenses summary", ex);
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (Exception e) {
            log.error("Error getting expenses summary", e);
        }

        return null;
    }

    // FUEL
    @Override
    public ObjCollection<FuelExpenseEntity> getFuelExpensesByUser(Integer page, Integer limit, String userId) {

        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
                CallableStatement cstmt = conn.prepareCall("{? = call get_fuel_expenses_for_user(?, ?, ?, ?)}")) {

            try {

                conn.setAutoCommit(false);

                cstmt.setInt(1, page);
                cstmt.setInt(2, limit);
                cstmt.setString(3, userId);

                cstmt.registerOutParameter(4, Types.BIGINT);
                cstmt.registerOutParameter(5, Types.REF_CURSOR);

                cstmt.execute();

                Long globalCount = cstmt.getLong(4);

                ResultSet rset = (ResultSet) cstmt.getObject(5);

                ObjCollection<FuelExpenseEntity> collection = new FuelExpensesCollection<>();

                List<FuelExpenseEntity> fuelExpenses = getFuelExpensesFromResultSet(rset);

                collection.setData(fuelExpenses);

                long collectionCount = collection.getData().size();

                int start = (page - 1) * limit + 1;
                int end = start + collection.getData().size() - 1;

                Page pageWrapper = new PageImpl();
                pageWrapper.setCurrentPage(page);
                pageWrapper.setMaxElementsPerPage(limit);
                pageWrapper.setStartPage(start);
                pageWrapper.setEndPage(end);
                pageWrapper.setLocalTotalElements(collectionCount);
                pageWrapper.setGlobalTotalElements(globalCount);

                collection.setPage(pageWrapper);

                conn.commit();

                return collection;

            } catch (SQLException ex) {
                log.error("Error getting fuel expenses for user '" + userId + "'", ex);
                conn.rollback();
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (Exception e) {
            log.error("Error getting fuel expenses for user '" + userId + "'", e);
        }

        return null;
    }

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

        final String sql = "INSERT INTO fuel_expenses (fuel_expense_id, price_per_litre, litres, discount, mileage, car_id) VALUES (?, ?, ?, ?, ?, ?)";

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

    private List<FuelExpenseEntity> getFuelExpensesFromResultSet(ResultSet rset) throws SQLException {

        List<FuelExpenseEntity> fuelExpenses = new ArrayList<>();

        while (rset.next()) {
            FuelExpenseEntity fuelExpense = getFuelExpenseEntityFromResultSet(rset);
            fuelExpenses.add(fuelExpense);
        }

        return fuelExpenses;
    }

    private FuelExpenseEntity getFuelExpenseEntityFromResultSet(ResultSet rset) throws SQLException {

        FuelExpenseEntity fuelExpense = new FuelExpenseEntity();
        fuelExpense.setFuelExpenseId(rset.getString("fuel_expense_id"));
        fuelExpense.setPricePerLitre(rset.getDouble("price_per_litre"));
        fuelExpense.setLitres(rset.getDouble("litres"));
        fuelExpense.setDiscount(rset.getDouble("discount"));
        fuelExpense.setMileage(rset.getLong("mileage"));

        CarEntity car = carsRepository.findByCarId(rset.getString("car_id"));
        fuelExpense.setCar(car);

        fuelExpense.setCreatedOn(rset.getTimestamp("created_on"));
        fuelExpense.setEditedOn(rset.getTimestamp("edited_on"));

        return fuelExpense;
    }

    // SERVICE
    @Override
    public ServiceExpenseEntity createServiceExpense(ServiceExpenseCreateRequest secr) throws MCDException {

//        CarEntity car = carsRepository.findByLicensePlate(fecr.getCarId());
//        if (car == null) {
//            car = carsRepository.findByCarId(fecr.getCarIdentifier());
//        }
        CarEntity car = carsRepository.findByCarId(secr.getCarId());

        if (car == null) {
            throw new MCDException(800, "Car not found", "Car with identifier " + secr.getCarId() + " not found!", HttpStatus.NOT_FOUND);
        }

        final String sql = "INSERT INTO service_expenses (service_expense_id, type, car_id, price, notes, mileage) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            try {
                conn.setAutoCommit(false);

                final String serviceExpenseId = UUID.randomUUID().toString();

                pstmt.setString(1, serviceExpenseId);
                pstmt.setInt(2, secr.getType());
                pstmt.setString(3, car.getCarId());
                pstmt.setDouble(4, secr.getPrice());

                if (secr.getNotes() == null) {
                    pstmt.setNull(5, Types.VARCHAR);
                } else {
                    pstmt.setString(5, secr.getNotes());
                }

                if (secr.getMileage() == null) {
                    pstmt.setNull(6, Types.NUMERIC);
                } else {
                    pstmt.setDouble(6, secr.getMileage());
                }

                pstmt.execute();

                conn.commit();
                ServiceExpenseEntity serviceExpense = serviceExpensesRepository.findByServiceExpenseId(serviceExpenseId);
                return serviceExpense;

            } catch (SQLException ex) {
                log.error("Error creating service expense", ex);
                conn.rollback();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            log.error("Error creating service expense", e);
        }

        return null;
    }

    @Override
    public ObjCollection<ServiceExpenseEntity> getServiceExpensesByUser(Integer page, Integer limit, String userId) {

        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
                CallableStatement cstmt = conn.prepareCall("{? = call get_service_expenses_for_user(?, ?, ?, ?)}")) {

            try {

                conn.setAutoCommit(false);

                cstmt.setInt(1, page);
                cstmt.setInt(2, limit);
                cstmt.setString(3, userId);

                cstmt.registerOutParameter(4, Types.BIGINT);
                cstmt.registerOutParameter(5, Types.REF_CURSOR);

                cstmt.execute();

                Long globalCount = cstmt.getLong(4);

                ResultSet rset = (ResultSet) cstmt.getObject(5);

                ObjCollection<ServiceExpenseEntity> collection = new ServiceExpensesCollection<>();

                List<ServiceExpenseEntity> serviceExpenses = getServiceExpensesFromResultSet(rset);

                collection.setData(serviceExpenses);

                long collectionCount = collection.getData().size();

                int start = (page - 1) * limit + 1;
                int end = start + collection.getData().size() - 1;

                Page pageWrapper = new PageImpl();
                pageWrapper.setCurrentPage(page);
                pageWrapper.setMaxElementsPerPage(limit);
                pageWrapper.setStartPage(start);
                pageWrapper.setEndPage(end);
                pageWrapper.setLocalTotalElements(collectionCount);
                pageWrapper.setGlobalTotalElements(globalCount);

                collection.setPage(pageWrapper);

                conn.commit();

                return collection;

            } catch (SQLException ex) {
                log.error("Error getting fuel expenses for user '" + userId + "'", ex);
                conn.rollback();
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (Exception e) {
            log.error("Error getting fuel expenses for user '" + userId + "'", e);
        }

        return null;
    }

    private List<ServiceExpenseEntity> getServiceExpensesFromResultSet(ResultSet rset) throws SQLException {

        List<ServiceExpenseEntity> serviceExpenses = new ArrayList<>();

        while (rset.next()) {
            ServiceExpenseEntity serviceExpense = getServiceExpenseEntityFromResultSet(rset);
            serviceExpenses.add(serviceExpense);
        }

        return serviceExpenses;
    }

    private ServiceExpenseEntity getServiceExpenseEntityFromResultSet(ResultSet rset) throws SQLException {

        ServiceExpenseEntity serviceExpense = new ServiceExpenseEntity();
        serviceExpense.setServiceExpenseId(rset.getString("service_expense_id"));

        ServiceExpenseTypeEntity type = new ServiceExpenseTypeEntity();
        type.setType(rset.getInt("type"));
        type.setTypeDescription(rset.getString("type_description"));
        serviceExpense.setType(type);

        CarEntity car = carsRepository.findByCarId(rset.getString("car_id"));
        serviceExpense.setCar(car);

        serviceExpense.setPrice(rset.getDouble("price"));
        serviceExpense.setNotes(rset.getString("notes"));
        serviceExpense.setMileage(rset.getLong("mileage"));
        serviceExpense.setCreatedOn(rset.getTimestamp("created_on"));
        serviceExpense.setEditedOn(rset.getTimestamp("edited_on"));

        return serviceExpense;
    }
}
