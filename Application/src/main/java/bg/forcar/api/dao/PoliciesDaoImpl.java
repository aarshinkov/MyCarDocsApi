package bg.forcar.api.dao;

import bg.forcar.api.entities.PolicyEntity;
import bg.forcar.api.entities.CarEntity;
import bg.forcar.api.repositories.CarsRepository;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @since 2.0.0
 * @author Atanas Yordanov Arshinkov
 */
@Repository
public class PoliciesDaoImpl implements PoliciesDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CarsRepository carsRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<PolicyEntity> getPoliciesByStatusForUser(Integer status, String userId) {

        try (Connection conn = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement pstmt = conn.prepareCall("{? = call get_user_policies_by_status(?, ?)}")) {
            try {

                conn.setAutoCommit(false);

                pstmt.setInt(1, status);
                pstmt.setString(2, userId);

                ResultSet rset = pstmt.executeQuery();

                List<PolicyEntity> policies = getPoliciesFromResultSet(rset);

                conn.commit();

                return policies;

            } catch (SQLException e) {
                conn.rollback();
                log.error("Error getting policies by status " + status + " for user " + userId);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception ex) {
            log.error("Error getting policies by status " + status + " for user " + userId);
        }

        return null;
    }

    @Override
    public List<PolicyEntity> getPoliciesFiltered(Integer type, Integer status, String carId, String userId) {

        try (Connection conn = jdbcTemplate.getDataSource().getConnection();
                CallableStatement cstmt = conn.prepareCall("{? = call get_user_policies_by_criteria(?, ?, ?, ?)}")) {

            boolean autoCommit = conn.getAutoCommit();
            log.debug("Auto commit: " + autoCommit);
            try {
                conn.setAutoCommit(false);

                if (type == null) {
                    cstmt.setNull(1, Types.INTEGER);
                } else {
                    cstmt.setInt(1, type);
                }

                if (status == null) {
                    cstmt.setNull(2, Types.INTEGER);
                } else {
                    cstmt.setInt(2, status);
                }

                if (carId == null) {
                    cstmt.setNull(3, Types.VARCHAR);
                } else {
                    if (carId.isEmpty()) {
                        cstmt.setNull(3, Types.VARCHAR);
                    } else {
                        cstmt.setString(3, carId);
                    }
                }

                cstmt.setString(4, userId);

                cstmt.registerOutParameter(5, Types.REF_CURSOR);

                cstmt.execute();

                ResultSet rset = (ResultSet) cstmt.getObject(5);

                List<PolicyEntity> policies = getPoliciesFromResultSet(rset);

                conn.commit();

                return policies;

            } catch (SQLException e) {
                conn.rollback();
                log.error("Error getting policies by status " + status + " for user " + userId, e);
            } finally {
                conn.setAutoCommit(autoCommit);
            }
        } catch (Exception ex) {
            log.error("Error getting policies by status " + status + " for user " + userId, ex);
        }

        return null;
    }

    private List<PolicyEntity> getPoliciesFromResultSet(ResultSet rset) throws SQLException {

        List<PolicyEntity> policies = new ArrayList<>();

        while (rset.next()) {
            PolicyEntity policy = getPolicyEntityFromResultSet(rset);
            policies.add(policy);
        }

        return policies;
    }

    private PolicyEntity getPolicyEntityFromResultSet(ResultSet rset) throws SQLException {

        PolicyEntity policy = new PolicyEntity();
        policy.setPolicyId(rset.getString("policy_id"));
        policy.setNumber(rset.getString("number"));
        policy.setType(rset.getInt("type"));
        policy.setInsName(rset.getString("ins_name"));

        CarEntity car = carsRepository.findByCarId(rset.getString("car_id"));
        policy.setCar(car);

        policy.setStartDate(rset.getTimestamp("start_date"));
        policy.setEndDate(rset.getTimestamp("end_date"));

        return policy;
    }
}
