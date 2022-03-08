package com.picpay.wallet.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class WalletDTO extends BaseEntityDTO{

    private Long id;
    // Must be string because Long broke the Jackson json
    private String balance;
    private UserDTO owner;
}
