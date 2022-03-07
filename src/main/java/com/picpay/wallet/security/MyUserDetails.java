package com.picpay.wallet.security;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
import com.picpay.wallet.entities.User;
import com.picpay.wallet.entities.UserRole;
import com.picpay.wallet.exceptions.BadRequestException;
import com.picpay.wallet.exceptions.NotFoundException;
import com.picpay.wallet.repositories.UserRepository;
import com.picpay.wallet.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;


@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> appUser = userRepository.findByKey(username);

        if (appUser.isPresent()) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(username)
                    .password(appUser.get().getPassword())
                    .authorities(appUser.get().getAppUserRoles())
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        } else {
            throw new NotFoundException("User '" + username + "' not found");


        }
    }



}