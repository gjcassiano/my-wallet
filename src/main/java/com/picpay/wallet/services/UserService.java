package com.picpay.wallet.services;

import com.picpay.wallet.common.GenericService;
import com.picpay.wallet.entities.User;
import com.picpay.wallet.exceptions.BadRequestException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
public interface UserService extends GenericService<User> {

    User getUserByKey(String key);

    String signin(String username, String password);
    String createAndGetToken(User user);
    User getLoggedUser();
    String refresh(String key);
}
