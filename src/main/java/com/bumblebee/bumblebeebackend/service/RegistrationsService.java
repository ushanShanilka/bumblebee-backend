package com.bumblebee.bumblebeebackend.service;

import com.bumblebee.bumblebeebackend.dto.RegisterDTO;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/
public interface RegistrationsService {
    String singUp(RegisterDTO dto, String userName, String type);
}
