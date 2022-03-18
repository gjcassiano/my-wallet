package com.wallet.services;

import com.wallet.common.GenericService;
import com.wallet.entities.User;

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
