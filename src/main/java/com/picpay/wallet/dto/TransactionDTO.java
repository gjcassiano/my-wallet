package com.picpay.wallet.dto;

import com.picpay.wallet.entities.Wallet;
import com.picpay.wallet.enuns.TransactionStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class TransactionDTO extends BaseEntityDTO{

    private Long id;
    private long value;
    private TransactionStatus transactionStatus;
    private WalletDTO walletFrom;
    private Long fromBalanceBefore;
    private Long fromBalanceAfter;
    private WalletDTO walletTo;
    private Long toBalanceBefore;
    private Long toBalanceAfter;

}
