package com.picpay.wallet.repositories;

import com.picpay.wallet.entities.Transaction;
import com.picpay.wallet.entities.Wallet;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
public interface TransactionRepository extends DatastoreRepository<String, Transaction> {

}
