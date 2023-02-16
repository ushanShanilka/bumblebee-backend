package com.bumblebee.bumblebeebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PasswordChangeDTO {
    private String oldPassword;
    private String newPassword;
    private String repeatNewPassword;
}
