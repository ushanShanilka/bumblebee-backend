package com.bumblebee.bumblebeebackend.service;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/3/2023
 **/
public interface MailService {
    void mailSender(String subject, String content, String email);
}
