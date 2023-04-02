package com.bumblebee.bumblebeebackend.api;

import com.bumblebee.bumblebeebackend.dto.ProductDTO;
import com.bumblebee.bumblebeebackend.service.ProductHasImagesService;
import com.bumblebee.bumblebeebackend.service.ProductService;
import com.bumblebee.bumblebeebackend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/2/2023
 **/
@RestController
@RequestMapping("/api/v1/images")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductHasImageController {

    @Autowired
    ProductHasImagesService productHasImagesService;

    @DeleteMapping(params = {"id"})
    public ResponseEntity<StandardResponse> saveProduct(@RequestParam Long id, @RequestAttribute String userName, @RequestAttribute String type){
        String s = productHasImagesService.deleteImage(id, userName,type);
        return new ResponseEntity<>(
                new StandardResponse(203,s,null),
                HttpStatus.CREATED
        );
    }
}
