package bg.forcar.api.services;

import bg.forcar.api.dao.PoliciesDao;
import bg.forcar.api.entities.CarEntity;
import bg.forcar.api.entities.PolicyEntity;
import bg.forcar.api.repositories.CarsRepository;
import bg.forcar.api.repositories.PoliciesRepository;
import bg.forcar.api.requests.policies.PolicyCreateRequest;
import bg.forcar.api.requests.policies.PolicyUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Service
public class PolicyServiceImpl implements PolicyService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PoliciesDao policiesDao;

    @Autowired
    private PoliciesRepository policiesRepository;

    @Autowired
    private CarsRepository carsRepository;

    @Override
    public List<PolicyEntity> getPolicies() {
        return policiesRepository.findAll();
    }

    @Override
    public List<PolicyEntity> getPoliciesByType(Integer type, String userId) {
        return policiesRepository.findAllByTypeAndCarOwnerUserIdOrderByStartDateDesc(type, userId);
    }

    @Override
    public List<PolicyEntity> getPoliciesByCarId(String carId) {
        return policiesRepository.findAllByCarCarId(carId);
    }

    @Override
    public List<PolicyEntity> getPoliciesByCarLicensePlate(String licensePlate) {
        return policiesRepository.findAllByCarLicensePlateIgnoreCase(licensePlate);
    }

    @Override
    public List<PolicyEntity> getPoliciesByUserId(String userId) {
        return policiesRepository.findAllByCarOwnerUserIdOrderByStartDateDesc(userId);
    }

    @Override
    public List<PolicyEntity> getPoliciesByStatus(Integer status, String userId) {
        return policiesDao.getPoliciesByStatusForUser(status, userId);
    }

    @Override
    public List<PolicyEntity> getPoliciesFiltered(Integer type, Integer status, String carId, String userId) {
        return policiesDao.getPoliciesFiltered(type, status, carId, userId);
    }

    @Override
    public PolicyEntity getPolicyByPolicyId(String policyId) {
        return policiesRepository.findByPolicyId(policyId);
    }

    @Override
    public PolicyEntity createPolicy(PolicyCreateRequest pcr) throws Exception {

        CarEntity car = carsRepository.findByCarId(pcr.getCarId());

        if (car == null) {
            throw new Exception("Car does not exist");
        }

        PolicyEntity policy = new PolicyEntity();
        policy.setPolicyId(UUID.randomUUID().toString());
        policy.setNumber(pcr.getNumber());
        policy.setType(pcr.getType());
        policy.setInsName(pcr.getInsName());
        policy.setCar(car);
        policy.setStartDate(new Timestamp(pcr.getStartDate().getTime()));
        policy.setEndDate(new Timestamp(pcr.getEndDate().getTime()));

        return policiesRepository.save(policy);
    }

    @Override
    public PolicyEntity updatePolicy(PolicyUpdateRequest pur) throws Exception {

        PolicyEntity policy = policiesRepository.findByPolicyId(pur.getPolicyId());
        CarEntity car = carsRepository.findByCarId(pur.getCarId());

        if (policy == null) {
            throw new Exception("Policy does not exist");
        }

        policy.setPolicyId(pur.getPolicyId());
        policy.setNumber(pur.getNumber());
        policy.setType(pur.getType());
        policy.setInsName(pur.getInsName());
        policy.setCar(car);
        policy.setStartDate(new Timestamp(pur.getStartDate().getTime()));
        policy.setEndDate(new Timestamp(pur.getEndDate().getTime()));

        return policiesRepository.save(policy);
    }

    @Override
    public void deletePolicy(String policyId) throws Exception {

        PolicyEntity policy = policiesRepository.findByPolicyId(policyId);

        if (policy == null) {
            throw new Exception("Policy does not exist");
        }

        policiesRepository.delete(policy);
    }

    @Override
    public Long getPoliciesCountByUserId(String userId) {

        return policiesRepository.countByCarOwnerUserId(userId);
    }
}
