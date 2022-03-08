package com.picpay.wallet.repositories;

import com.picpay.wallet.entities.Transaction;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
public interface TransactionRepository extends DatastoreRepository<String, Transaction> {

}
