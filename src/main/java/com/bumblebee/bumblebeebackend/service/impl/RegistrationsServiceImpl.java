package com.bumblebee.bumblebeebackend.service.impl;

import com.bumblebee.bumblebeebackend.dto.RegisterDTO;
import com.bumblebee.bumblebeebackend.service.AdminService;
import com.bumblebee.bumblebeebackend.service.RegistrationsService;
import com.bumblebee.bumblebeebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/
@Service
public class RegistrationsServiceImpl implements RegistrationsService {

    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;


    @Override
    public String singUp (RegisterDTO dto, String userName, String type) {
        if (type.equals("ADMIN")){
            return adminService.adminSingUp(dto,userName);
        }else if (type.equals("USER")){
            return userService.userSingUp(dto);
        }
        return null;
    }
}
