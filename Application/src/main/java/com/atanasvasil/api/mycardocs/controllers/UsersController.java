package com.atanasvasil.api.mycardocs.controllers;

import java.util.List;
import com.atanasvasil.api.mycardocs.entities.UserEntity;
import com.atanasvasil.api.mycardocs.requests.users.UserCreateRequest;
import com.atanasvasil.api.mycardocs.repositories.UsersRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@RestController
@Api(value = "Users", tags = "Users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @ApiOperation(value = "Get users")
    @GetMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserEntity> getUsers() {

        return usersRepository.findAll();
    }

    @ApiOperation(value = "Get particular user")
    @GetMapping(value = "/api/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserEntity getUser(@PathVariable("userId") Long userId) {

        return usersRepository.findByUserId(userId);
    }

    @ApiOperation(value = "Create user")
    @PostMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserEntity createUser(@RequestBody UserCreateRequest ucr) {

        UserEntity user = new UserEntity();
        user.setEmail(ucr.getEmail());
        user.setPassword(ucr.getPassword());
        user.setFirstName(ucr.getFirstName());
        user.setLastName(ucr.getLastName());

        UserEntity createdUser = usersRepository.save(user);

        return createdUser;
    }

    @ApiOperation(value = "Delete user")
    @DeleteMapping(value = "/api/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean deleteUser(@PathVariable("userId") Long userId) {
        UserEntity user = usersRepository.findByUserId(userId);

        if (user == null) {
            return false;
        }

        try {
            usersRepository.delete(user);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
