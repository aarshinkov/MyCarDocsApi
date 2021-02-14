package com.atanasvasil.api.mycardocs.controllers;

import java.util.ArrayList;
import java.util.List;

import com.atanasvasil.api.mycardocs.entities.UserEntity;
import com.atanasvasil.api.mycardocs.requests.users.UserCreateRequest;
import com.atanasvasil.api.mycardocs.repositories.UsersRepository;
import com.atanasvasil.api.mycardocs.responses.users.UserGetResponse;
import com.atanasvasil.api.mycardocs.utils.Identifier;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.atanasvasil.api.mycardocs.utils.Utils.getUserFromEntity;

/**
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
    public ResponseEntity<List<UserGetResponse>> getUsers() {

        List<UserGetResponse> users = new ArrayList<>();

        for (UserEntity user : usersRepository.findAll()) {
            UserGetResponse ugr = getUserFromEntity(user);
            users.add(ugr);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiOperation(value = "Get particular user")
    @GetMapping(value = "/api/users/{identifier}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserGetResponse> getUser(@PathVariable("identifier") String identifier) {

        UserEntity storedUser;

        Identifier id = new Identifier();

        if (id.isUserId(identifier)) {
            Long userId = Long.parseLong(identifier);
            storedUser = usersRepository.findByUserId(userId);
        } else {
            storedUser = usersRepository.findByEmail(identifier);
        }

        if (storedUser == null) {
            return new ResponseEntity<>(new UserGetResponse(), HttpStatus.OK);
        }

        UserGetResponse ugr = getUserFromEntity(storedUser);

        return new ResponseEntity<>(ugr, HttpStatus.OK);
    }

    @ApiOperation(value = "Create user")
    @PostMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserGetResponse> createUser(@RequestBody UserCreateRequest ucr) {

        UserEntity user = new UserEntity();
        user.setEmail(ucr.getEmail());
        user.setPassword(ucr.getPassword());
        user.setFirstName(ucr.getFirstName());
        user.setLastName(ucr.getLastName());

        UserEntity createdUser = usersRepository.save(user);

        UserGetResponse ugr = getUserFromEntity(createdUser);

        return new ResponseEntity<>(ugr, HttpStatus.CREATED);
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

    @ApiOperation(value = "Checks if the user with email exists")
    @GetMapping(value = "/api/users/exists/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean isUserExistsByEmail(@PathVariable("email") String email) {

        return usersRepository.existsByEmail(email);
    }
}
