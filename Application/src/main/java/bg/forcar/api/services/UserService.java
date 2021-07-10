package bg.forcar.api.services;

import bg.forcar.api.entities.UserEntity;
import bg.forcar.api.exceptions.MCDException;
import bg.forcar.api.requests.users.RoleAssignRequest;
import bg.forcar.api.requests.users.UserCreateRequest;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public interface UserService extends UserDetailsService {

    List<UserEntity> getUsers();

    UserEntity getUserByUserId(String userId);

    UserEntity getUserByEmail(String email);

    UserEntity createUser(UserCreateRequest ucr, List<RoleAssignRequest> roles);

    void deleteUser(UserEntity user) throws Exception;

    Boolean doUserExist(String identifier);

    Boolean forgotPassword(String email) throws MCDException;

    Boolean resetPassword(String password, String code);
}
