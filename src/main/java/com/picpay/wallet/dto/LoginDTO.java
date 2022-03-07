package com.picpay.wallet.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class LoginDTO{

    @NotEmpty(message = "Key is required")
    private String key;

    @NotEmpty(message = "password is required")
    private String password;

}
