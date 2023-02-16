package com.bumblebee.bumblebeebackend.exception;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/

public class TransactionException extends RuntimeException {
    public TransactionException(String message) {
        super(message);
    }
}
