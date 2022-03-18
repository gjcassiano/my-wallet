package com.wallet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException(String message) {
        super(message);
    }
}
