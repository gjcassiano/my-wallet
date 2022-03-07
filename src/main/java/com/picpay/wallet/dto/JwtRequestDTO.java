package com.picpay.wallet.dto;

import lombok.Data;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
@Data
public class JwtRequestDTO {
    private String key;
    private String password;
}
