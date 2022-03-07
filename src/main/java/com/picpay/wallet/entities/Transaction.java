package com.picpay.wallet.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.picpay.wallet.dto.WalletDTO;
import com.picpay.wallet.enuns.TransactionStatus;
import com.picpay.wallet.enuns.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    private Long id;

    @Index
    private long value;
    @Index
    private TransactionStatus transactionStatus;

    @Index
    private TransactionType transactionType;

    @Index
    private WalletDTO walletFrom;
    @Index
    private String walletFromKey;

    @Index
    private Long fromBalanceBefore;
    @Index
    private Long fromBalanceAfter;
    @Index
    private WalletDTO walletTo;
    @Index
    private String walletToKey;
    @Index
    private Long toBalanceBefore;
    @Index
    private Long toBalanceAfter;

    @Index
    private Date date;
}
