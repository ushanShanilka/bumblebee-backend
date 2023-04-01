package com.bumblebee.bumblebeebackend.api;

import com.bumblebee.bumblebeebackend.dto.RegisterDTO;
import com.bumblebee.bumblebeebackend.dto.AuthenticationRequestDTO;
import com.bumblebee.bumblebeebackend.dto.LoginResponseDTO;
import com.bumblebee.bumblebeebackend.service.AdminService;
import com.bumblebee.bumblebeebackend.service.RegistrationsService;
import com.bumblebee.bumblebeebackend.service.UserService;
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
    @Autowired
    RegistrationsService registrationsService;
    @Autowired
    UserService userService;

    @PostMapping(path = "/admin/login")
    public ResponseEntity<StandardResponse> adminLogin(@RequestBody AuthenticationRequestDTO dto){
        LoginResponseDTO response = adminService.adminLogin(dto);
        return new ResponseEntity<>(
                new StandardResponse(200,"success",response),
                HttpStatus.CREATED
        );
    }

    @PostMapping(path = "/admin")
    public ResponseEntity<StandardResponse> adminSingUp(@RequestBody RegisterDTO dto, @RequestAttribute String userName){
        String s = registrationsService.singUp(dto, userName, "ADMIN");
        return new ResponseEntity<>(
                new StandardResponse(201,s,null),
                HttpStatus.CREATED
        );
    }

    @PostMapping(path = "/user/signup")
    public ResponseEntity<StandardResponse> userSignUp(@RequestBody RegisterDTO dto){
        String s = registrationsService.singUp(dto, "", "USER");
        return new ResponseEntity<>(
                new StandardResponse(201,s,null),
                HttpStatus.OK
        );
    }

    @PostMapping(path = "/user/login")
    public ResponseEntity<LoginResponseDTO> userLogin(@RequestBody AuthenticationRequestDTO dto){
        LoginResponseDTO response = userService.userLogin(dto);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }
}
