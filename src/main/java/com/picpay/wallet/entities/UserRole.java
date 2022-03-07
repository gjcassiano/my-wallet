package com.picpay.wallet.entities;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ROLE_ADMIN, ROLE_CLIENT;

    public String getAuthority() {
        return name();
    }

}