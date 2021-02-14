package com.atanasvasil.api.mycardocs.controllers;

import com.atanasvasil.api.mycardocs.entities.UserEntity;
import com.atanasvasil.api.mycardocs.repositories.UsersRepository;
import com.atanasvasil.api.mycardocs.responses.users.UserGetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.atanasvasil.api.mycardocs.utils.Utils.getUserFromEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.2.0
 */
@RestController
@Api(value = "Authentication", tags = "Authentication")
public class AuthController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UsersRepository usersRepository;

    @ApiOperation(value = "Login")
    @PostMapping(value = "/api/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserGetResponse> login(@RequestParam("email") String email, @RequestParam("password") String password) {

        UserEntity user = usersRepository.findByEmail(email);

        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (!password.equals(user.getPassword())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(getUserFromEntity(user), HttpStatus.OK);
    }
}
