package com.wallet.logging;

import com.wallet.exceptions.BadRequestException;
import com.wallet.exceptions.NotAuthorizedException;
import com.wallet.exceptions.NotFoundException;
import com.wallet.response.ResponseError;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
@ControllerAdvice
public class RestResponseExceptionHandler {
    public static final String ERROR_500_DEFAULT = "Unknown error";
    public static final String NOT_AUTHORIZED_DEFAULT = "Not authorized!";

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ResponseError> handleBadRequest(final Exception ex, final WebRequest request) {
        ex.printStackTrace();
        return generatedError(ex.getMessage(), HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ResponseError> handleNotFound(final Exception ex, final WebRequest request) {
        return generatedError(ex.getMessage(), HttpStatus.NOT_FOUND, ex);
    }


    @ExceptionHandler({NotAuthorizedException.class})
    public ResponseEntity<ResponseError> handleNotAuthorized(final Exception ex, final WebRequest request) {
        return generatedError(NOT_AUTHORIZED_DEFAULT, HttpStatus.FORBIDDEN, ex);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseError> handleInternal(final Exception ex, final WebRequest request) {
        ex.printStackTrace();
        return generatedError(ERROR_500_DEFAULT, HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseError> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex, final WebRequest request) {
        String errors = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return generatedError(errors, HttpStatus.BAD_REQUEST, ex);
    }

    private ResponseEntity<ResponseError> generatedError(String message, HttpStatus http, Exception ex) {
        return new ResponseEntity<>(new ResponseError(message), http);
    }
}
