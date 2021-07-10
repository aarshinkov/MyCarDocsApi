package bg.forcar.api.services;

import bg.forcar.api.requests.users.RoleAssignRequest;
import bg.forcar.api.requests.users.UserCreateRequest;
import bg.forcar.api.entities.RoleEntity;
import bg.forcar.api.entities.UserEntity;
import bg.forcar.api.exceptions.MCDException;
import bg.forcar.api.repositories.RolesRepository;
import bg.forcar.api.repositories.UsersRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Override
    public List<UserEntity> getUsers() {
        return usersRepository.findAll();
    }

    @Override
    public UserEntity getUserByUserId(String userId) {

        UserEntity storedUser = usersRepository.findByUserId(userId);

        if (storedUser == null) {
            return null;
//      throw new UserServiceException(ErrorMessages.USER_NO_EXIST.getErrorMessage());
        }

        return storedUser;
    }

    @Override
    public UserEntity getUserByEmail(String email) {

        UserEntity storedUser = usersRepository.findByEmail(email);

        if (storedUser == null) {
            return null;
//      throw new UserServiceException(ErrorMessages.USER_NO_EXIST.getErrorMessage());
        }

        return storedUser;
    }

    @Override
    public UserEntity createUser(UserCreateRequest ucr, List<RoleAssignRequest> roles) {

        UserEntity user = new UserEntity();

        user.setUserId(UUID.randomUUID().toString());
        user.setEmail(ucr.getEmail());
        user.setPassword(passwordEncoder.encode(ucr.getPassword()));
        user.setFirstName(ucr.getFirstName());
        user.setLastName(ucr.getLastName());

        List<RoleEntity> roleEntities = new ArrayList<>();

        for (RoleAssignRequest role : roles) {
            roleEntities.add(rolesRepository.findByRole(role.getRole().toUpperCase()));
        }

        user.setRoles(roleEntities);

        UserEntity createdUser = usersRepository.save(user);

        return createdUser;
    }

    @Override
    public void deleteUser(UserEntity user) throws Exception {
        usersRepository.delete(user);
    }

    @Override
    public Boolean doUserExist(String identifier) {

        Boolean result = usersRepository.existsByEmail(identifier);

        if (!result) {
            result = usersRepository.existsByUserId(identifier);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean forgotPassword(String email) throws MCDException {

        String code;

        do {
            code = UUID.randomUUID().toString();
        } while (usersRepository.existsByResetPassCode(code));

        UserEntity user = usersRepository.findByEmail(email);

        if (user == null) {
            throw new MCDException(700, "User not found", "User with email " + email + " not found!", HttpStatus.NOT_FOUND);
        }

        user.setResetPassCode(code);
        usersRepository.save(user);

        mailService.sendResetPasswordMail(email, code);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean resetPassword(String password, String code) {

        UserEntity user = usersRepository.findByResetPassCode(code);

        if (user == null) {
            throw new MCDException(700, "User not found", "User with reset password code " + code + " not found!", HttpStatus.NOT_FOUND);
        }

        user.setPassword(passwordEncoder.encode(password));
        user.setResetPassCode(null);
        usersRepository.save(user);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = usersRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        List<GrantedAuthority> roles = new ArrayList<>();

        userEntity.getRoles().forEach((roleEntity) -> {
            roles.add(new SimpleGrantedAuthority(roleEntity.getRole()));
        });

        return new User(userEntity.getEmail(), userEntity.getPassword(), roles);
    }
}
