package com.wallet.services;

import com.wallet.entities.Transaction;

import java.util.List;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
public interface TransactionService {

    List<Transaction> loggedExtract();
    List<String> loggedPrettyExtract();
    Transaction save(Transaction transactionToBeSaved);
}
