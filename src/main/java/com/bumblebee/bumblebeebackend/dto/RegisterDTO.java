package com.bumblebee.bumblebeebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/14/2023
 **/

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterDTO implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String confirmEmail;
    private String nic;
    private String address;
    private String password;
    private String confirmPassword;
    private String countryCode;
    private String phoneNumber;
}
