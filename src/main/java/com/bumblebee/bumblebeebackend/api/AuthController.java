package com.bumblebee.bumblebeebackend.api;

import com.bumblebee.bumblebeebackend.dto.AdminRegisterDTO;
import com.bumblebee.bumblebeebackend.dto.AuthenticationRequestDTO;
import com.bumblebee.bumblebeebackend.dto.LoginResponseDTO;
import com.bumblebee.bumblebeebackend.service.AdminService;
import com.bumblebee.bumblebeebackend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/

@RestController
@RequestMapping("/api/v1/authenticates")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AdminService adminService;

    @PostMapping(path = "/admin/login")
    public ResponseEntity<LoginResponseDTO> adminLogin(@RequestBody AuthenticationRequestDTO dto){
        LoginResponseDTO response = adminService.adminLogin(dto);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @PostMapping(path = "/admin")
    public ResponseEntity<StandardResponse> adminSingUp(@RequestBody AdminRegisterDTO dto, @RequestAttribute String userName){
        String s = adminService.adminSingUp(dto, userName);
        return new ResponseEntity<>(
                new StandardResponse(201,s,null),
                HttpStatus.CREATED
        );
    }
}
