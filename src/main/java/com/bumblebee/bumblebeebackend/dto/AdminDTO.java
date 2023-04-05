package com.bumblebee.bumblebeebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/5/2023
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminDTO implements Serializable {
    private Long id;
    private String countryCode;
    private String birthDay;
    private String email;
    private String confirmEmail;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
    private int status;
}
