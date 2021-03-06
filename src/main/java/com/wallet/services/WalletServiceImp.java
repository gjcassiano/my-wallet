package com.wallet.services;

import com.wallet.common.GenericServiceImpl;
import com.wallet.entities.Transaction;
import com.wallet.entities.Wallet;
import com.wallet.enuns.TransactionStatus;
import com.wallet.enuns.TransactionType;
import com.wallet.exceptions.BadRequestException;
import com.wallet.mapper.WalletMapper;
import com.wallet.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */

@Service
public class WalletServiceImp extends GenericServiceImpl<Wallet> implements WalletService{

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;

    WalletRepository walletRepository;

    @Autowired
    public WalletServiceImp(WalletRepository repo) {
        super(repo);
        this.walletRepository = repo;
    }

    @Override
    public Wallet deposit(Long money) {
        Wallet loggedWallet = userService.getLoggedUser().getWallet();
        if (loggedWallet == null) {
            throw new BadRequestException("User doesn't have wallet yet!");
        }
        if (money <= 0) {
            throw new BadRequestException("Money should be more the zero!");
        }

        Long newBalance = loggedWallet.getBalance() + money;

        if (newBalance <= 0) {
            throw new BadRequestException("The new balance broke the int64!");
        }
        Transaction transaction = new Transaction();

        transaction.setValue(money);
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setFromBalanceBefore(loggedWallet.getBalance());

        loggedWallet.setBalance(newBalance);

        transaction.setFromBalanceAfter(newBalance);

        transaction.setWalletFrom(WalletMapper.INSTANCE.toDTO(loggedWallet));
        transaction.setWalletFromKey(loggedWallet.getOwner().getKey());
        transaction.setDate(new Date());

        walletRepository.save(loggedWallet);

        transactionService.save(transaction);

        return loggedWallet;
    }

    @Override
    public Wallet withdraw(Long money) {

        Wallet loggedWallet = userService.getLoggedUser().getWallet();
        if (loggedWallet == null) {
            throw new BadRequestException("User doesn't have wallet yet!");
        }
        if (money <= 0) {
            throw new BadRequestException("Money should be more the zero!");
        }

        Long newBalance = loggedWallet.getBalance() - money;

        if (newBalance < 0) {
            throw new BadRequestException("You doesn't have money to withdraw! your balance is " + loggedWallet.getBalance());
        }


        Transaction transaction = new Transaction();

        transaction.setValue(money);
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setFromBalanceBefore(loggedWallet.getBalance());

        loggedWallet.setBalance(newBalance);

        transaction.setFromBalanceAfter(newBalance);

        transaction.setWalletFrom(WalletMapper.INSTANCE.toDTO(loggedWallet));
        transaction.setWalletFromKey(loggedWallet.getOwner().getKey());

        transaction.setDate(new Date());


        walletRepository.save(loggedWallet);

        transactionService.save(transaction);

        return loggedWallet;
    }


    @Override
    public Wallet transfer(String toKey, Long money) {
        Wallet loggedWallet = userService.getLoggedUser().getWallet();
        if (loggedWallet == null) {
            throw new BadRequestException("User doesn't have wallet yet!");
        }

        if (money <= 0) {
            throw new BadRequestException("Money should be more the zero!");
        }

        Wallet toWallet = userService.getUserByKey(toKey).getWallet();
        if (toWallet == null) {
            throw new BadRequestException("To user doesn't have wallet yet!");
        }


        Long newBalance = loggedWallet.getBalance() - money;

        if (newBalance < 0) {
            throw new BadRequestException("You doesn't have money to transfer! your balance is " + loggedWallet.getBalance());
        }


        Transaction transaction = new Transaction();

        transaction.setValue(money);
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setFromBalanceBefore(loggedWallet.getBalance());
        transaction.setToBalanceBefore(toWallet.getBalance());


        loggedWallet.setBalance(newBalance);

        Long newToBalance = toWallet.getBalance() + money;
        toWallet.setBalance(newToBalance);


        transaction.setFromBalanceAfter(newBalance);

        transaction.setWalletFrom(WalletMapper.INSTANCE.toDTO(loggedWallet));
        transaction.setWalletFromKey(loggedWallet.getOwner().getKey());
        transaction.setWalletTo(WalletMapper.INSTANCE.toDTO(toWallet));
        transaction.setWalletToKey(toWallet.getOwner().getKey());
        transaction.setDate(new Date());


        walletRepository.saveAll(Arrays.asList(loggedWallet, toWallet));
        transactionService.save(transaction);


        return loggedWallet;
    }

    @Override
    public Wallet loggedWallet() {
        Wallet loggedWallet = userService.getLoggedUser().getWallet();
        if (loggedWallet == null) {
            throw new BadRequestException("User doesn't have wallet yet!");
        }
        return loggedWallet;
    }

}
