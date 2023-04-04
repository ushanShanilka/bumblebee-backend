package com.bumblebee.bumblebeebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/3/2023
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OtpConfirmationDTO {
    private String email;
    private int otp;
}
