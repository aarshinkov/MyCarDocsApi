package bg.forcar.api.dao;

import java.util.List;
import bg.forcar.api.entities.PolicyEntity;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
public interface PoliciesDao {

    List<PolicyEntity> getPoliciesByStatusForUser(Integer status, String userId);

    List<PolicyEntity> getPoliciesFiltered(Integer type, Integer status, String carId, String userId);
}
