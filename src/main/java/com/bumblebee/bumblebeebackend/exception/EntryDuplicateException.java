package com.bumblebee.bumblebeebackend.exception;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/

public class EntryDuplicateException extends RuntimeException {
    public EntryDuplicateException() {
    }

    public EntryDuplicateException(String message) {
        super(message);
    }
}
