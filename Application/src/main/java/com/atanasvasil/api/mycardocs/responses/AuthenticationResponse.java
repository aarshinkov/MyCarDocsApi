package com.atanasvasil.api.mycardocs.responses;

import com.atanasvasil.api.mycardocs.responses.users.*;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthenticationResponse implements Serializable {

    private String tokenType;
    private String accessToken;
    private List<String> access;
    private UserGetResponse user;
}
