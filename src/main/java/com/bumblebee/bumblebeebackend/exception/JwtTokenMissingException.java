package com.bumblebee.bumblebeebackend.exception;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/

public class JwtTokenMissingException extends RuntimeException {
    public JwtTokenMissingException() {
    }

    public JwtTokenMissingException(String message) {
        super(message);
    }
}
