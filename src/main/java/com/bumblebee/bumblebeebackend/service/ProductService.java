package com.bumblebee.bumblebeebackend.service;

import com.bumblebee.bumblebeebackend.dto.ProductDTO;
import com.bumblebee.bumblebeebackend.dto.ProductUpdateDTO;

import java.util.List;
import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
public interface ProductService {
    String productSave(ProductDTO dto, String userName);
    String productUpdate(ProductUpdateDTO dto, String userName);
    Map<String, Object> getProduct(Long id, String userName);
    String deleteProduct(Long id, String userName);
    List<Map<String, Object>> getAllProduct(String value);

}
