package bg.forcar.api.controllers;

import bg.forcar.api.entities.PolicyEntity;
import bg.forcar.api.requests.policies.PolicyCreateRequest;
import bg.forcar.api.requests.policies.PolicyUpdateRequest;
import bg.forcar.api.responses.policies.PolicyGetResponse;
import bg.forcar.api.services.PolicyService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static bg.forcar.api.utils.Utils.*;

import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@RestController
@Api(value = "Policies", tags = "Policies")
public class PoliciesController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PolicyService policyService;

    @ApiOperation(value = "Get policies")
    @GetMapping(value = "/api/policies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PolicyGetResponse>> getPolicies() {

        List<PolicyEntity> storedPolicies = policyService.getPolicies();

        if (storedPolicies.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        List<PolicyGetResponse> policies = new ArrayList<>();

        for (PolicyEntity policy : storedPolicies) {
            PolicyGetResponse pgr = getPolicyFromEntity(policy);
            policies.add(pgr);
        }

        return new ResponseEntity<>(policies, HttpStatus.OK);
    }

    @Deprecated
    @ApiOperation(value = "Get policies by type")
    @PreAuthorize("@securityExpressions.isUserSelf(#principal, #userId)")
    @GetMapping(value = "/api/policies/type", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PolicyGetResponse>> getPoliciesByType(@RequestParam("type") Integer type, @RequestParam("userId") String userId, Principal principal) {

        List<PolicyEntity> storedPolicies = policyService.getPoliciesByType(type, userId);

        if (storedPolicies.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        List<PolicyGetResponse> policies = new ArrayList<>();

        for (PolicyEntity policy : storedPolicies) {
            PolicyGetResponse pgr = getPolicyFromEntity(policy);
            policies.add(pgr);
        }

        return new ResponseEntity<>(policies, HttpStatus.OK);
    }

    @Deprecated
    @ApiOperation(value = "Get policies by status")
    @PreAuthorize("@securityExpressions.isUserSelf(#principal, #userId)")
    @GetMapping(value = "/api/policies/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PolicyGetResponse>> getPoliciesByStatus(@RequestParam("status") Integer status, @RequestParam("userId") String userId, Principal principal) {

        List<PolicyEntity> storedPolicies = policyService.getPoliciesByStatus(status, userId);

        if (storedPolicies == null || storedPolicies.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        List<PolicyGetResponse> policies = new ArrayList<>();

        for (PolicyEntity policy : storedPolicies) {
            PolicyGetResponse pgr = getPolicyFromEntity(policy);
            policies.add(pgr);
        }

        return new ResponseEntity<>(policies, HttpStatus.OK);
    }

    @ApiOperation(value = "Get policies by criteria")
    @PreAuthorize("@securityExpressions.isUserSelf(#principal, #userId)")
    @GetMapping(value = "/api/policies/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PolicyGetResponse>> getPoliciesFiltered(@RequestParam(name = "type", required = false) Integer type,
            @RequestParam(name = "status", required = true, defaultValue = "-1") Integer status,
            @RequestParam(name = "carId", required = false) String carId,
            @RequestParam("userId") String userId, Principal principal) {

        log.debug("Policies filter: type - " + type + "; status - " + status + "; carId - " + carId + "; userId - " + userId);

        List<PolicyEntity> storedPolicies = policyService.getPoliciesFiltered(type, status, carId, userId);

        if (storedPolicies == null || storedPolicies.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        log.debug("Policisies found: " + storedPolicies.size());

        List<PolicyGetResponse> policies = new ArrayList<>();

        for (PolicyEntity policy : storedPolicies) {
            PolicyGetResponse pgr = getPolicyFromEntity(policy);
            policies.add(pgr);
        }

        return new ResponseEntity<>(policies, HttpStatus.OK);
    }

    @ApiOperation(value = "Get policies by car ID")
    @PreAuthorize("@securityExpressions.isUserOwnerOfCar(#principal, #carId)")
    @GetMapping(value = "/api/policies/car/id/{carId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PolicyGetResponse>> getPoliciesByCarId(@PathVariable("carId") String carId, Principal principal) {

        List<PolicyEntity> storedPolicies = policyService.getPoliciesByCarId(carId);

        if (storedPolicies.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

        List<PolicyGetResponse> policies = getPoliciesFromEntities(storedPolicies);

        return new ResponseEntity<>(policies, HttpStatus.OK);
    }

    @ApiOperation(value = "Get policies by car license plate")
    @PreAuthorize("@securityExpressions.isUserOwnerOfCarWithLicensePlate(#principal, #licensePlate)")
    @GetMapping(value = "/api/policies/car/plate/{licensePlate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PolicyGetResponse>> getPoliciesByCarLicensePlate(@PathVariable("licensePlate") String licensePlate, Principal principal) {

        licensePlate = licensePlate.trim().replaceAll("\\s+", "");

        List<PolicyEntity> storedPolicies = policyService.getPoliciesByCarLicensePlate(licensePlate);

        if (storedPolicies.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

        List<PolicyGetResponse> policies = getPoliciesFromEntities(storedPolicies);

        return new ResponseEntity<>(policies, HttpStatus.OK);
    }

    @ApiOperation(value = "Get policies by user ID")
    @PreAuthorize("@securityExpressions.isUserSelf(#principal, #userId)")
    @GetMapping(value = "/api/policies/user/id/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PolicyGetResponse>> getPoliciesByUserId(@PathVariable("userId") String userId, Principal principal) {

        List<PolicyEntity> storedPolicies = policyService.getPoliciesByUserId(userId);

        if (storedPolicies.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

        List<PolicyGetResponse> policies = getPoliciesFromEntities(storedPolicies);

        return new ResponseEntity<>(policies, HttpStatus.OK);
    }

    @ApiOperation(value = "Get particular policy")
    @PreAuthorize("@securityExpressions.isUserOwnerOfPolicy(#principal, #policyId)")
    @GetMapping(value = "/api/policies/{policyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PolicyGetResponse> getPolicy(@PathVariable("policyId") String policyId, Principal principal) {

        PolicyEntity policy = policyService.getPolicyByPolicyId(policyId);

        if (policy == null) {
            return new ResponseEntity<>(new PolicyGetResponse(), HttpStatus.BAD_REQUEST);
        }

        PolicyGetResponse pgr = getPolicyFromEntity(policy);

        return new ResponseEntity<>(pgr, HttpStatus.OK);
    }

    @ApiOperation(value = "Create policy")
    @PreAuthorize("@securityExpressions.isUserSelf(#principal, #pcr.userId)")
    @PostMapping(value = "/api/policies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PolicyGetResponse> createPolicy(@RequestBody PolicyCreateRequest pcr, Principal principal) {

        try {
            PolicyEntity createdPolicy = policyService.createPolicy(pcr);

            PolicyGetResponse pgr = getPolicyFromEntity(createdPolicy);

            return new ResponseEntity<>(pgr, HttpStatus.CREATED);

        } catch (Exception e) {
            log.error("Error creating policy", e);
            return new ResponseEntity<>(new PolicyGetResponse(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Update policy")
    @PreAuthorize("@securityExpressions.isUserOwnerOfPolicy(#principal, #policyId) and @securityExpressions.isUserSelf(#principal, #pur.userId)")
    @PutMapping(value = "/api/policies/{policyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PolicyGetResponse> updatePolicy(@PathVariable("policyId") String policyId, @RequestBody PolicyUpdateRequest pur, Principal principal) {

        try {
            pur.setPolicyId(policyId);
            PolicyEntity updatedPolicy = policyService.updatePolicy(pur);

            PolicyGetResponse pgr = getPolicyFromEntity(updatedPolicy);

            return new ResponseEntity<>(pgr, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new PolicyGetResponse(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Delete policy")
    @PreAuthorize("@securityExpressions.isUserOwnerOfPolicy(#principal, #policyId)")
    @DeleteMapping(value = "/api/policies/{policyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteCar(@PathVariable("policyId") String policyId, Principal principal) {

        try {
            policyService.deletePolicy(policyId);
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Get policies count for user")
    @PreAuthorize("@securityExpressions.isUserSelf(#principal, #userId)")
    @GetMapping(value = "/api/policies/count/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> getPoliciesCountByUserId(@PathVariable("userId") String userId, Principal principal) {

        Long policiesCount = policyService.getPoliciesCountByUserId(userId);

        return new ResponseEntity(policiesCount, HttpStatus.OK);
    }
}
