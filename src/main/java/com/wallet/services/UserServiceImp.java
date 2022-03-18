package com.wallet.services;

import com.wallet.common.GenericServiceImpl;
import com.wallet.entities.User;
import com.wallet.entities.UserRole;
import com.wallet.entities.Wallet;
import com.wallet.exceptions.BadRequestException;
import com.wallet.exceptions.DuplicateRequestException;
import com.wallet.exceptions.NotFoundException;
import com.wallet.repositories.UserRepository;
import com.wallet.repositories.WalletRepository;
import com.wallet.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */

@Service
public class UserServiceImp extends GenericServiceImpl<User> implements UserService{

    UserRepository userRepository;

    @Autowired
    WalletRepository walletRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImp(UserRepository repo) {
        super(repo);
        this.userRepository = repo;
    }

    @Override
    public User getUserByKey(String key) {
        Optional<User> user = this.userRepository.findByKey(key);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException(String.format("User with key [%s] not found", key));
        }
    }


    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, getUserByKey(username).getAppUserRoles());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Invalid username/password supplied");
        }
    }

    public String createAndGetToken(User user) {
        if (this.userRepository.findByKey(user.getKey()).isPresent()) {
            throw new DuplicateRequestException("the key is already used!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAppUserRoles(Collections.singletonList(UserRole.ROLE_CLIENT));

        Wallet wallet = new Wallet();
        wallet.setBalance(0L);
        user.setWallet(wallet);


        walletRepository.save(wallet);

        userRepository.save(user);

        return jwtTokenProvider.createToken(user.getKey(), user.getAppUserRoles());
    }


    public User getLoggedUser() {
        Optional<User> loggedUser = userRepository.findByKey(SecurityContextHolder.getContext().getAuthentication().getName());

        if (loggedUser.isPresent()) {
            return loggedUser.get();
        } else {
            throw new BadRequestException("Not found logged user");
        }

    }

    public String refresh(String key) {
        return jwtTokenProvider.createToken(key, userRepository.findByKey(key).get().getAppUserRoles());
    }


}
