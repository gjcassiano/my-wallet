package com.picpay.wallet.services;

import com.picpay.wallet.common.GenericService;
import com.picpay.wallet.entities.Wallet;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
public interface WalletService extends GenericService<Wallet> {

    Wallet deposit(Long money);
    Wallet withdraw(Long money);
    Wallet transfer(String toKey, Long money);
    Wallet loggedWallet();
}
