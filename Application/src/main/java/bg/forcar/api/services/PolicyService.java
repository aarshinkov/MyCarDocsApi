package bg.forcar.api.services;

import bg.forcar.api.entities.PolicyEntity;
import bg.forcar.api.requests.policies.PolicyCreateRequest;
import bg.forcar.api.requests.policies.PolicyUpdateRequest;

import java.util.List;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public interface PolicyService {

    List<PolicyEntity> getPolicies();

    List<PolicyEntity> getPoliciesByType(Integer type, String userId);

    List<PolicyEntity> getPoliciesByCarId(String carId);

    List<PolicyEntity> getPoliciesByCarLicensePlate(String licensePlate);

    List<PolicyEntity> getPoliciesByUserId(String userId);

    List<PolicyEntity> getPoliciesByStatus(Integer status, String userId);

    List<PolicyEntity> getPoliciesFiltered(Integer type, Integer status, String carId, String userId);

    PolicyEntity getPolicyByPolicyId(String policyId);

    PolicyEntity createPolicy(PolicyCreateRequest pcr) throws Exception;

    PolicyEntity updatePolicy(PolicyUpdateRequest pur) throws Exception;

    void deletePolicy(String policyId) throws Exception;

    Long getPoliciesCountByUserId(String userId);
}
