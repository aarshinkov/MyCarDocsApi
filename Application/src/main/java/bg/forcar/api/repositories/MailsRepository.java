package bg.forcar.api.repositories;

import bg.forcar.api.entities.MailEntity;
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
