package com.bumblebee.bumblebeebackend.service;


import com.bumblebee.bumblebeebackend.dto.LoginResponse;
import com.bumblebee.bumblebeebackend.dto.OtpConfirmationDTO;
import com.bumblebee.bumblebeebackend.entity.User;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/3/2023
 **/
public interface OtpService {
    String otpConfirmation(OtpConfirmationDTO dto);
    String commonOtpConfirmation(OtpConfirmationDTO dto);
    String sendOtp(String email, User user);
    String resendOtp(String email);
}
