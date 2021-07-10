package bg.forcar.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthException extends RuntimeException {

    private Integer code;
    private String message;
    private String details;
}
