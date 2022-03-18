package com.wallet.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */

@Data
@Entity(name = "user")
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class User extends BaseEntity {

    @Column(name = "first_name")
    @Size(min = 3)
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 3)
    private String lastName;

    @Column(name = "password")
    @Size(min = 3)
    private String password;

    @Column(name = "key_value", unique = true)
    @NotBlank
    @Size(min = 5)
    private String key;

    @OneToOne(fetch = FetchType.EAGER)
    private Wallet wallet;

    @ElementCollection(fetch = FetchType.EAGER)
    List<UserRole> appUserRoles;
}
