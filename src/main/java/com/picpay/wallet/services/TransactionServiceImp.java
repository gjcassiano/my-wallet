package com.picpay.wallet.services;

import com.picpay.wallet.entities.Transaction;
import com.picpay.wallet.entities.User;
import com.picpay.wallet.repositories.TransactionRepository;
import com.picpay.wallet.repositories.UserRepository;
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

//        transactions.forEach(transaction -> transactionRepository.delete(transaction));

        return transactions.stream().map(transaction -> {
            String log = transaction.getDate().toString() + " ";
            switch (transaction.getTransactionType()) {
                case DEPOSIT:
                    log += "deposit " + transaction.getValue() +  " in your account! New balance equals " + transaction.getWalletFrom().getBalance();
                    break;
                case TRANSFER:

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
                case WITHDRAW:

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
