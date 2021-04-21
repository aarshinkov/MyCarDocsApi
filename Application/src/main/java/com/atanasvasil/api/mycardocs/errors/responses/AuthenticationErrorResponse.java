package com.atanasvasil.api.mycardocs.errors.responses;

import com.fasterxml.jackson.annotation.*;
import java.time.*;
import lombok.*;

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
public class AuthenticationErrorResponse {

    private Integer code;
    private String message;
    private String details;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
}
