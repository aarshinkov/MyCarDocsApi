package bg.forcar.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MCDException extends RuntimeException {

    private Integer code;
    private String message;
    private String details;
    private HttpStatus status;

    public MCDException(String message) {
        super(message);
    }
}
