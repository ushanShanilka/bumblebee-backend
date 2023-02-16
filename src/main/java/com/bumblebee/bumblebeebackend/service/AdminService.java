package com.bumblebee.bumblebeebackend.service;

import com.bumblebee.bumblebeebackend.dto.RegisterDTO;
import com.bumblebee.bumblebeebackend.dto.AuthenticationRequestDTO;
import com.bumblebee.bumblebeebackend.dto.LoginResponseDTO;
import com.bumblebee.bumblebeebackend.dto.PasswordChangeDTO;

import java.util.List;
import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/14/2023
 **/

public interface AdminService {
    LoginResponseDTO adminLogin(AuthenticationRequestDTO dto);
    String adminSingUp(RegisterDTO dto, String userName);
    LoginResponseDTO adminPasswordChange(PasswordChangeDTO dto);
    List<Map<String, Object>> getAllAdmin(String value);
    String deleteUser(Long adminId);
}
