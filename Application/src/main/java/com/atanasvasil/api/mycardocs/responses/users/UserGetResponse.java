package com.atanasvasil.api.mycardocs.responses.users;

import java.io.Serializable;
import java.sql.Timestamp;
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
public class UserGetResponse implements Serializable {

    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private Timestamp editedOn;
}
