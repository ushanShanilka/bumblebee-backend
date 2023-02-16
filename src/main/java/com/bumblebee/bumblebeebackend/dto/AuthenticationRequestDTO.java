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
public class AuthenticationRequestDTO implements Serializable {
    private String username;
    private String password;
}
