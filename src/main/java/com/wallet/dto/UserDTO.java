package com.wallet.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class UserDTO extends BaseEntityDTO{

    private Long id;

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotEmpty(message = "Key is required")
    private String key;

    @NotEmpty(message = "password is required")
    private String password;

}
