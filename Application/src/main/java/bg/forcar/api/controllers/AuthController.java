package bg.forcar.api.controllers;

import bg.forcar.api.responses.AuthenticationResponse;
import bg.forcar.api.exceptions.AuthException;
import bg.forcar.api.entities.UserEntity;
import bg.forcar.api.responses.users.UserGetResponse;
import bg.forcar.api.security.JwtUtil;
import bg.forcar.api.services.UserService;
import static bg.forcar.api.utils.Utils.getUserFromEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.2.0
 */
@RestController
@Api(value = "Authentication", tags = "Authentication")
public class AuthController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Login")
    @PostMapping(value = "/api/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> login(@RequestParam("email") String email, @RequestParam("password") String password) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            final UserDetails userDetails = userService.loadUserByUsername(email);
            final String jwt = jwtUtil.generateToken(userDetails);
            AuthenticationResponse response = new AuthenticationResponse();
            response.setTokenType("Bearer");
            response.setAccessToken(jwt);

            List<String> authorities = new ArrayList<>();

            userDetails.getAuthorities().forEach((authority) -> {
                authorities.add(authority.getAuthority());
            });

            response.setAccess(authorities);

            UserEntity storedUser = userService.getUserByEmail(email);

            UserGetResponse user = getUserFromEntity(storedUser);

            response.setUser(user);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            AuthException authException = new AuthException(1001, "Bad credentials", "The user entered wrong email or password");
            log.debug("AuthException: " + authException);

            throw authException;
        }
    }
}
