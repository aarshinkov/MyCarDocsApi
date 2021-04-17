package com.atanasvasil.api.mycardocs.controllers;

import java.util.ArrayList;
import java.util.List;

import com.atanasvasil.api.mycardocs.entities.UserEntity;
import com.atanasvasil.api.mycardocs.requests.users.*;
import com.atanasvasil.api.mycardocs.responses.users.UserGetResponse;
import com.atanasvasil.api.mycardocs.services.CarService;
import com.atanasvasil.api.mycardocs.services.UserService;
import static com.atanasvasil.api.mycardocs.utils.Roles.USER;
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
    private UserService userService;

    @Autowired
    private CarService carService;

    @ApiOperation(value = "Get users")
    @GetMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserGetResponse>> getUsers() {

        List<UserGetResponse> users = new ArrayList<>();

        for (UserEntity user : userService.getUsers()) {
            UserGetResponse ugr = getUserFromEntity(user);
            users.add(ugr);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiOperation(value = "Get particular user")
    @GetMapping(value = "/api/users/{identifier}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserGetResponse> getUser(@PathVariable("identifier") String identifier) {

        UserEntity storedUser;

        storedUser = userService.getUserByUserId(identifier);

        if (storedUser == null) {
            return new ResponseEntity<>(new UserGetResponse(), HttpStatus.OK);
        }

        UserGetResponse ugr = getUserFromEntity(storedUser);

        return new ResponseEntity<>(ugr, HttpStatus.OK);
    }

    @ApiOperation(value = "Create user")
    @PostMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserGetResponse> createUser(@RequestBody UserCreateRequest ucr) {

        List<RoleAssignRequest> roles = new ArrayList<>();

        roles.add(new RoleAssignRequest(USER.getRole().toUpperCase()));

        UserEntity createdUser = userService.createUser(ucr, roles);

        UserGetResponse ugr = getUserFromEntity(createdUser);

        return new ResponseEntity<>(ugr, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete user")
    @DeleteMapping(value = "/api/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteUser(@PathVariable("userId") String userId) {

        UserEntity user = userService.getUserByUserId(userId);

        if (user == null) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
        }

        try {
            userService.deleteUser(user);
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Checks if the user with email exists")
    @GetMapping(value = "/api/users/exists/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isUserExistsByEmail(@PathVariable("email") String email) {

        return new ResponseEntity(userService.doUserExist(email), HttpStatus.OK);
    }

    @ApiOperation(value = "Has user cars")
    @GetMapping(value = "/api/users/{userId}/has/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> hasUserCars(@PathVariable("userId") String userId) {

        return new ResponseEntity(carService.hasUserCars(userId), HttpStatus.OK);
    }
}
