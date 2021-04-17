package com.atanasvasil.api.mycardocs.services;

import com.atanasvasil.api.mycardocs.entities.*;
import com.atanasvasil.api.mycardocs.repositories.RolesRepository;
import com.atanasvasil.api.mycardocs.repositories.UsersRepository;
import com.atanasvasil.api.mycardocs.requests.users.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
