package com.picpay.wallet.services;

import com.picpay.wallet.entities.Transaction;

import java.util.List;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
public interface TransactionService {

    List<Transaction> loggedExtract();
    List<String> loggedPrettyExtract();
    Transaction save(Transaction transactionToBeSaved);
}
