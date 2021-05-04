package com.atanasvasil.api.mycardocs.exceptions.handlers;

import java.time.LocalDateTime;

import com.atanasvasil.api.mycardocs.exceptions.*;
import com.atanasvasil.api.mycardocs.errors.responses.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@ControllerAdvice
public class GlobalExceptionsHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<GlobalErrorResponse> handleOtherExceptions(Exception ex, WebRequest request) {
        log.error("Error", ex);
        GlobalErrorResponse ger = new GlobalErrorResponse();
        ger.setMessage("An error occured");
        ger.setTimestamp(LocalDateTime.now());
        ger.setException(ex.getMessage());

        return new ResponseEntity<>(ger, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<AuthenticationErrorResponse> handleAuthenticationException(AuthException ex, WebRequest request) {
        AuthenticationErrorResponse errors = new AuthenticationErrorResponse();
        errors.setCode(ex.getCode());
        errors.setMessage(ex.getMessage());
        errors.setDetails(ex.getDetails());
        errors.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

//  @ExceptionHandler(BadCredentialsException.class)
//  public ResponseEntity<BssErrorResponse> handleBadCredentials(BadCredentialsException ex, WebRequest request)
//  {
//    BssErrorResponse errors = new BssErrorResponse();
//    errors.setMessage(ex.getMessage());
//    errors.setCode(111);
//    errors.setDetails("Invalid credentials");
//    errors.setTimestamp(LocalDateTime.now());
//
//    return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
//  }

    @ExceptionHandler(MCDException.class)
    public ResponseEntity<MCDErrorResponse> mcdErrorHandler(MCDException ex, WebRequest request) {

        MCDErrorResponse error = new MCDErrorResponse();
        error.setMessage(ex.getMessage());
        error.setCode(ex.getCode());
        error.setDetails(ex.getDetails());
        error.setTimestamp(LocalDateTime.now());

        log.error("Error: " + error.toString());

        return new ResponseEntity<>(error, ex.getStatus());
    }

//    @ExceptionHandler(FieldValidationException.class)
//    public ResponseEntity<FieldValidationErrorResponse> fieldValidationHandler(FieldValidationException ex, WebRequest request) {
//        FieldValidationErrorResponse error = new FieldValidationErrorResponse();
//        error.setField(ex.getField());
//        error.setCode(ex.getCode());
//        error.setDetails(ex.getDetails());
//        error.setTimestamp(LocalDateTime.now());
//
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }
}
