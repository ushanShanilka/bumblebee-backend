package com.bumblebee.bumblebeebackend.api;

import com.bumblebee.bumblebeebackend.service.UserService;
import com.bumblebee.bumblebeebackend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/all")
    public ResponseEntity<StandardResponse> getAllUsers(@RequestAttribute String type){
        System.out.println(type);
        List<Map<String, Object>> allUsers = userService.getAllUsers(type);
        return new ResponseEntity<>(
                new StandardResponse(200,"success",allUsers),
                HttpStatus.OK
        );
    }

}
