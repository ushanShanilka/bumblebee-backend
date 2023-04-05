package com.bumblebee.bumblebeebackend.api;

import com.bumblebee.bumblebeebackend.dto.AdminDTO;
import com.bumblebee.bumblebeebackend.service.AdminService;
import com.bumblebee.bumblebeebackend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/5/2023
 **/
@RestController
@RequestMapping("/api/v1/admins")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping(path = "/all")
    public ResponseEntity<StandardResponse> getAllAdmin(@RequestAttribute String type){
        List<Map<String, Object>> allAdmin = adminService.getAllAdmin(type);
        return new ResponseEntity<>(
                new StandardResponse(200,"success",allAdmin),
                HttpStatus.OK
        );
    }

    @PutMapping
    public ResponseEntity<StandardResponse> updateAdmin(@RequestBody AdminDTO dto, @RequestAttribute String type){
        String s = adminService.updateAdmin(dto, type);
        return new ResponseEntity<>(
                new StandardResponse(204,s,null),
                HttpStatus.OK
        );
    }

    @GetMapping(params = {"id"})
    public ResponseEntity<StandardResponse> getAdmin(@RequestParam Long id, @RequestAttribute String type){
        Map<String, Object> admin = adminService.getAdmin(id, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"success",admin),
                HttpStatus.OK
        );
    }
}
