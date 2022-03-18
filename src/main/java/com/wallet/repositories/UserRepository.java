package com.wallet.repositories;

import com.wallet.entities.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
@Repository
@Lazy
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByKey(String key);
}
