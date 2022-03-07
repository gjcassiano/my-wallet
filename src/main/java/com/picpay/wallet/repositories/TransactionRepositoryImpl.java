package com.picpay.wallet.repositories;

import com.picpay.wallet.entities.Transaction;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
@Lazy
@Repository
public class TransactionRepositoryImpl extends DatastoreRepositoryImpl<String, Transaction> implements TransactionRepository{

    public TransactionRepositoryImpl() {
        super(String.class, Transaction.class);
    }
}
