package com.wallet.services;

import com.wallet.entities.Transaction;
import com.wallet.entities.User;
import com.wallet.repositories.TransactionRepository;
import com.wallet.enuns.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */

@Service
public class TransactionServiceImp implements TransactionService{

    @Autowired
    UserService userService;


    @Autowired
    TransactionRepository transactionRepository;



    @Override
    public List<Transaction> loggedExtract() {
        return transactionRepository.findAll();
    }

    @Override
    public List<String> loggedPrettyExtract() {
        User logged = userService.getLoggedUser();
        List<Transaction> transactions = transactionRepository.findAllByUserKey(logged.getKey());

        return transactions.stream().map(transaction -> {
            String log = transaction.getDate().toString() + " ";
            switch (transaction.getTransactionType()) {
                case TransactionType.DEPOSIT:
                    log += "deposit " + transaction.getValue() +  " in your account! New balance equals " + transaction.getWalletFrom().getBalance();
                    break;
                case TransactionType.TRANSFER:

                    if (logged.getKey().equals(transaction.getWalletFrom().getOwner().getKey())) {
                        log += "transfer " + transaction.getValue() + " to " +
                                "client " + transaction.getWalletTo().getOwner().getFirstName() + " with key " +
                                transaction.getWalletTo().getOwner().getKey() +  " in your account! New balance equals " + transaction.getWalletFrom().getBalance();
                    } else {
                        log += "received " + transaction.getValue() + " from " +
                                "client " + transaction.getWalletFrom().getOwner().getFirstName() + " with key " +
                                transaction.getWalletFrom().getOwner().getKey() +  " in your account! New balance equals " + transaction.getWalletTo().getBalance();
                    }
                    break;
                case TransactionType.WITHDRAW:

                    log += "withdraw " + transaction.getValue() +  " in your account! New balance equals " + transaction.getWalletFrom().getBalance();
                    break;
            }

            return log;
        }).collect(Collectors.toList());

    }

    @Override
    public Transaction save(Transaction transactionToBeSaved) {
        transactionRepository.save(transactionToBeSaved);
        return transactionToBeSaved;
    }
}
