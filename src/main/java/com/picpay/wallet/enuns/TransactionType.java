package com.picpay.wallet.enuns;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */
public enum TransactionType {
    DEPOSIT {
        @Override
        public String toString() {
            return "deposit";
        }
    },
    WITHDRAW {
        @Override
        public String toString() {
            return "withdraw";
        }
    },
    TRANSFER
    {
        @Override
        public String toString() {
            return "transfer";
        }
    },
}
