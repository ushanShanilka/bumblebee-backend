package com.bumblebee.bumblebeebackend.exception;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/

public class StatusException extends RuntimeException {
    public StatusException() {
    }

    public StatusException(String message) {
        super(message);
    }
}
