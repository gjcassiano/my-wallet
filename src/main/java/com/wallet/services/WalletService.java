package com.wallet.services;

import com.wallet.common.GenericService;
import com.wallet.entities.Wallet;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
public interface WalletService extends GenericService<Wallet> {

    Wallet deposit(Long money);
    Wallet withdraw(Long money);
    Wallet transfer(String toKey, Long money);
    Wallet loggedWallet();
}
