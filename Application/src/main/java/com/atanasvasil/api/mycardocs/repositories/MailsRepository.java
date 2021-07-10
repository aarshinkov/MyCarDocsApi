package com.atanasvasil.api.mycardocs.repositories;

import com.atanasvasil.api.mycardocs.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Repository
public interface MailsRepository extends JpaRepository<MailEntity, Integer> {

    MailEntity findByMailId(Integer mailId);
}
