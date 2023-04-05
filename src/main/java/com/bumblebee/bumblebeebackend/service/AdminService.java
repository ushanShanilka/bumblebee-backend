package com.bumblebee.bumblebeebackend.service;

import com.bumblebee.bumblebeebackend.dto.*;

import java.util.List;
import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/14/2023
 **/

public interface AdminService {
    LoginResponseDTO adminLogin(AuthenticationRequestDTO dto);
    String adminSingUp(RegisterDTO dto, String userName);
    String updateAdmin(AdminDTO dto,String type);
    LoginResponseDTO adminPasswordChange(PasswordChangeDTO dto);
    List<Map<String, Object>> getAllAdmin(String type);
    Map<String, Object> getAdmin(Long adminId,String type);
    String deleteUser(Long adminId);
}
