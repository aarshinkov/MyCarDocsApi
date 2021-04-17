package com.atanasvasil.api.mycardocs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.atanasvasil.api.mycardocs.entities.*;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Repository
public interface RolesRepository extends JpaRepository<RoleEntity, String> {

    RoleEntity findByRole(String role);

}
