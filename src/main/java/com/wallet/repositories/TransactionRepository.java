package com.wallet.repositories;

import com.wallet.entities.Transaction;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
public interface TransactionRepository extends DatastoreRepository<String, Transaction> {

}
