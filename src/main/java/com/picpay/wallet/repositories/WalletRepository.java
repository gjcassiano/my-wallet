package com.picpay.wallet.repositories;

import com.picpay.wallet.entities.Wallet;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
@Repository
@Lazy
public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
