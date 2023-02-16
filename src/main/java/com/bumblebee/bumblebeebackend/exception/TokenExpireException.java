package com.bumblebee.bumblebeebackend.exception;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/

public class TokenExpireException extends RuntimeException {
    public TokenExpireException(String message) {
        super(message);
    }
}
