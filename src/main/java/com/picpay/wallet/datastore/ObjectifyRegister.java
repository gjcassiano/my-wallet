package com.picpay.wallet.datastore;

import com.googlecode.objectify.ObjectifyService;
import com.picpay.wallet.entities.Transaction;

public class ObjectifyRegister {

    private ObjectifyRegister() {}
    public static void registerClasses() {
        ObjectifyService.register(Transaction.class);
    }
}