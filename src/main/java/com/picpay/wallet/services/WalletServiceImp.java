package com.picpay.wallet.services;

import com.google.common.primitives.Longs;
import com.picpay.wallet.common.GenericServiceImpl;
import com.picpay.wallet.entities.Transaction;
import com.picpay.wallet.entities.User;
import com.picpay.wallet.entities.UserRole;
import com.picpay.wallet.entities.Wallet;
import com.picpay.wallet.enuns.TransactionStatus;
import com.picpay.wallet.enuns.TransactionType;
import com.picpay.wallet.exceptions.BadRequestException;
import com.picpay.wallet.exceptions.DuplicateRequestException;
import com.picpay.wallet.exceptions.NotFoundException;
import com.picpay.wallet.mapper.WalletMapper;
import com.picpay.wallet.repositories.UserRepository;
import com.picpay.wallet.repositories.WalletRepository;
import com.picpay.wallet.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

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
