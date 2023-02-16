package com.bumblebee.bumblebeebackend.advisor;

import com.bumblebee.bumblebeebackend.exception.*;
import com.bumblebee.bumblebeebackend.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.UnexpectedTypeException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/

@RestControllerAdvice
public class AppWideExceptionHandler {

    @ExceptionHandler({ EntryDuplicateException.class})
    public ResponseEntity<StandardResponse> entryDuplicateHandler(EntryDuplicateException e){
        return new ResponseEntity<>(
                new StandardResponse(404, "Entry Duplicate Exception", e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ TransactionException.class})
    public ResponseEntity<StandardResponse> transactionExceptionHandler(TransactionException e){
        return new ResponseEntity<>(
                new StandardResponse(417, "Transaction Exception", e.getMessage()),
                HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler({ EntryNotFoundException.class})
    public ResponseEntity<StandardResponse> entryNotFoundHandler(EntryNotFoundException e){
        return new ResponseEntity<>(
                new StandardResponse(404, "Entry Not Found Exception", e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ CustomException.class})
    public ResponseEntity<StandardResponse> customHandler(CustomException e){
        return new ResponseEntity<>(
                new StandardResponse(404, "Custom Exception", e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ StatusException.class})
    public ResponseEntity<StandardResponse> statusHandler(StatusException e){
        return new ResponseEntity<>(
                new StandardResponse(404, "Status Exception", e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ UsernameNotFoundException.class})
    public ResponseEntity<StandardResponse> usernameNotFoundHandler(UsernameNotFoundException e){
        return new ResponseEntity<>(
                new StandardResponse(404, "Username Not Found Exception", e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ TokenExpireException.class})
    public ResponseEntity<StandardResponse> tokenExpiredException(TokenExpireException e) {
        return new ResponseEntity<>(
                new StandardResponse(401, "Token is expired", e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ BadCredentialsException.class})
    public ResponseEntity<Object> badCredentialHandler(BadCredentialsException e){
        return new ResponseEntity<>(
                new StandardResponse(401, "Bad Credentials Exception", e.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ UnexpectedTypeException.class})
    protected ResponseEntity<StandardResponse> apiValidationHandler(UnexpectedTypeException e){
        return new ResponseEntity<>(
                new StandardResponse(400, "Validation Exception", e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        Map<String,String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) ->{
            errors.put(
                    ((FieldError) error).getField(),
                    error.getDefaultMessage()
            );
        });
        return new ResponseEntity<>(
                new StandardResponse(400, "Validation Exception", errors),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<StandardResponse> handleResponseStatusException(ResponseStatusException ex){
        return new ResponseEntity<>(
                new StandardResponse(401, "Status Error", ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<StandardResponse> handleNullPointException(NullPointerException ex){
        ex.printStackTrace();
        return new ResponseEntity<>(
                new StandardResponse(404, "Null Point Error", ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<StandardResponse> handleSqlException(SQLException ex){
        return new ResponseEntity<>(
                new StandardResponse(404, "SQL Error", ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardResponse> handleGlobalException(Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>(
                new StandardResponse(500, "Server Error", ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

}
