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
public class LoginResponseDTO implements Serializable {
    private int code;
    private String userName;
    private String message;
    private String jwt;
}
