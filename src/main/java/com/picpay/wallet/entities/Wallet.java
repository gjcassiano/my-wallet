package com.picpay.wallet.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
@Entity(name = "wallet")
@Data
@EqualsAndHashCode(callSuper=false)
public class Wallet extends BaseEntity {

    @Column(name = "balance")
    private Long balance;

    @OneToOne(mappedBy = "wallet", fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private User owner;
}
