package com.wallet.datastore;

import com.googlecode.objectify.ObjectifyService;
import com.wallet.entities.Transaction;

public class ObjectifyRegister {

    private ObjectifyRegister() {}
    public static void registerClasses() {
        ObjectifyService.register(Transaction.class);
    }
}