package com.picpay.wallet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class DuplicateRequestException extends BadRequestException {

    public DuplicateRequestException(String message) {
        super(message);
    }
}
