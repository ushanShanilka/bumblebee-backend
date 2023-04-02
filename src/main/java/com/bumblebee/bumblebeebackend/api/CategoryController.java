package com.bumblebee.bumblebeebackend.api;

import com.bumblebee.bumblebeebackend.dto.CategoryDTO;
import com.bumblebee.bumblebeebackend.service.CategoryService;
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
@RequestMapping("/api/v1/category")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<StandardResponse> save(@RequestBody CategoryDTO dto, @RequestAttribute String userName, @RequestAttribute String type){
        String s = categoryService.save(dto, userName,type);
        return new ResponseEntity<>(
                new StandardResponse(201,s,null),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<StandardResponse> update(@RequestBody CategoryDTO dto, @RequestAttribute String userName, @RequestAttribute String type){
        String s = categoryService.update(dto, userName,type);
        return new ResponseEntity<>(
                new StandardResponse(204,s,null),
                HttpStatus.CREATED
        );
    }

    @GetMapping(params = "id")
    public ResponseEntity<StandardResponse> get(@RequestParam Long id, @RequestAttribute String userName,@RequestAttribute String type){
        Map<String, Object> product = categoryService.get(id, userName, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"success",product),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping(params = "id")
    public ResponseEntity<StandardResponse> delete(@RequestParam Long id, @RequestAttribute String userName,@RequestAttribute String type){
        String s = categoryService.delete(id, userName,type);
        return new ResponseEntity<>(
                new StandardResponse(203,s,null),
                HttpStatus.CREATED
        );
    }

    @GetMapping(path = "/all", params = "status")
    public ResponseEntity<StandardResponse> getAll(@RequestParam String status, @RequestAttribute String type){

        List<Map<String, Object>> allProduct = categoryService.getAll(status, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"success",allProduct),
                HttpStatus.CREATED
        );
    }
}
