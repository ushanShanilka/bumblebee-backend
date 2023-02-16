package com.bumblebee.bumblebeebackend.exception;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/

public class CustomException extends RuntimeException {
    public CustomException() {
    }
    public CustomException(String message) {
        super(message);
    }
}
