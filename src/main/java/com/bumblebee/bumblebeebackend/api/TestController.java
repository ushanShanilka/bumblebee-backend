package com.bumblebee.bumblebeebackend.api;

import com.bumblebee.bumblebeebackend.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/

@RestController
@RequestMapping("/api/v1/test")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestController {

    @GetMapping
    public ResponseEntity<StandardResponse> adminSingUp(){
        return new ResponseEntity<>(
                new StandardResponse(201,"success",null),
                HttpStatus.CREATED
        );
    }
}
