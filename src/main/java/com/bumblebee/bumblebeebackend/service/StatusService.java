package com.bumblebee.bumblebeebackend.service;

import com.bumblebee.bumblebeebackend.entity.Status;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
public interface StatusService {
    Status getStatus(short id);
}
