package com.bumblebee.bumblebeebackend.api;

import com.bumblebee.bumblebeebackend.dto.OtpConfirmationDTO;
import com.bumblebee.bumblebeebackend.service.OtpService;
import com.bumblebee.bumblebeebackend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
@RestController
@RequestMapping("/api/v1/otp")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OtpController {

    @Autowired
    OtpService otpService;

    @PostMapping
    public ResponseEntity<StandardResponse> checkOtp(@RequestBody OtpConfirmationDTO dto){
        String s = otpService.otpConfirmation(dto);
        return new ResponseEntity<>(
                new StandardResponse(200,s,null),HttpStatus.OK);
    }

    @GetMapping(path = "/resend", params = "email")
    public ResponseEntity<StandardResponse> resendOtp(@RequestParam String email){
        String s = otpService.resendOtp(email);
        return new ResponseEntity<>(
                new StandardResponse(200,s,null),HttpStatus.OK);
    }
}
