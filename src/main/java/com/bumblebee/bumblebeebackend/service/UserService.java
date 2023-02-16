package com.bumblebee.bumblebeebackend.service;

import com.bumblebee.bumblebeebackend.dto.AuthenticationRequestDTO;
import com.bumblebee.bumblebeebackend.dto.LoginResponseDTO;
import com.bumblebee.bumblebeebackend.dto.RegisterDTO;

import java.util.List;
import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/
public interface UserService {
    String userSingUp(RegisterDTO dto);
    LoginResponseDTO userLogin(AuthenticationRequestDTO dto);
    List<Map<String, Object>> getAllUsers(String type);
}
