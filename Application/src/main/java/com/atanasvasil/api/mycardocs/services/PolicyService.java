package com.atanasvasil.api.mycardocs.services;

import com.atanasvasil.api.mycardocs.entities.PolicyEntity;
import com.atanasvasil.api.mycardocs.requests.policies.PolicyCreateRequest;
import com.atanasvasil.api.mycardocs.requests.policies.PolicyUpdateRequest;

import java.util.List;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public interface PolicyService {

    List<PolicyEntity> getPolicies();

    List<PolicyEntity> getPoliciesByCarId(String carId);

    List<PolicyEntity> getPoliciesByCarLicensePlate(String licensePlate);

    List<PolicyEntity> getPoliciesByUserId(Long userId);

    PolicyEntity getPolicyByPolicyId(String policyId);

    PolicyEntity createPolicy(PolicyCreateRequest pcr) throws Exception;

    PolicyEntity updatePolicy(PolicyUpdateRequest pur) throws Exception;

    void deletePolicy(String policyId) throws Exception;
}
