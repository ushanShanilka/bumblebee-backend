package com.bumblebee.bumblebeebackend.api;

import com.bumblebee.bumblebeebackend.dto.BrandDTO;
import com.bumblebee.bumblebeebackend.service.BrandService;
import com.bumblebee.bumblebeebackend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/2/2023
 **/
@RestController
@RequestMapping("/api/v1/brands")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BrandController {

    @Autowired
    BrandService brandService;

    @PostMapping
    public ResponseEntity<StandardResponse> save(@RequestBody BrandDTO dto, @RequestAttribute String userName, @RequestAttribute String type){
        String s = brandService.save(dto, userName,type);
        return new ResponseEntity<>(
                new StandardResponse(201,s,null),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<StandardResponse> update(@RequestBody BrandDTO dto, @RequestAttribute String userName, @RequestAttribute String type){
        String s = brandService.update(dto, userName,type);
        return new ResponseEntity<>(
                new StandardResponse(204,s,null),
                HttpStatus.CREATED
        );
    }

    @GetMapping(params = "id")
    public ResponseEntity<StandardResponse> get(@RequestParam Long id, @RequestAttribute String userName,@RequestAttribute String type){
        Map<String, Object> product = brandService.get(id, userName, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"success",product),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping(params = "id")
    public ResponseEntity<StandardResponse> delete(@RequestParam Long id, @RequestAttribute String userName,@RequestAttribute String type){
        String s = brandService.delete(id, userName,type);
        return new ResponseEntity<>(
                new StandardResponse(203,s,null),
                HttpStatus.CREATED
        );
    }

    @GetMapping(path = "/all", params = "status")
    public ResponseEntity<StandardResponse> getAll(@RequestParam String status, @RequestAttribute String type){

        List<Map<String, Object>> allProduct = brandService.getAll(status, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"success",allProduct),
                HttpStatus.CREATED
        );
    }
}
