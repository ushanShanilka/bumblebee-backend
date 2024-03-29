package com.bumblebee.bumblebeebackend.api;

import com.bumblebee.bumblebeebackend.dto.ProductDTO;
import com.bumblebee.bumblebeebackend.service.ProductService;
import com.bumblebee.bumblebeebackend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<StandardResponse> saveProduct(@RequestBody ProductDTO dto, @RequestAttribute String userName, @RequestAttribute String type){
        String s = productService.productSave(dto, userName,type);
        return new ResponseEntity<>(
                new StandardResponse(201,s,null),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<StandardResponse> updateProduct(@RequestBody ProductDTO dto, @RequestAttribute String userName,@RequestAttribute String type){
        String s = productService.productUpdate(dto, userName,type);
        return new ResponseEntity<>(
                new StandardResponse(204,s,null),
                HttpStatus.CREATED
        );
    }

    @GetMapping(params = "id")
    public ResponseEntity<StandardResponse> getProduct(@RequestParam Long id, @RequestAttribute String userName,@RequestAttribute String type){
        Map<String, Object> product = productService.getProduct(id, userName, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"success",product),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping(params = "id")
    public ResponseEntity<StandardResponse> deleteProduct(@RequestParam Long id, @RequestAttribute String userName,@RequestAttribute String type){
        String s = productService.deleteProduct(id, userName,type);
        return new ResponseEntity<>(
                new StandardResponse(203,s,null),
                HttpStatus.CREATED
        );
    }

    @GetMapping(path = "/all", params = {"value"})
    public ResponseEntity<StandardResponse> getAllProduct(@RequestParam String value,
                                                          @RequestAttribute String type){

        List<Map<String, Object>> allProduct = productService.getAllProduct(value, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"success",allProduct),
                HttpStatus.CREATED
        );
    }
}
